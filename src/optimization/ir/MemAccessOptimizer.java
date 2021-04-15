package optimization.ir;

import assembly.instruction.Move;
import ir.IRFunction;
import ir.IRInfo;
import ir.construct.RegisterTracker;
import ir.instruction.*;
import ir.operand.IRConstant;
import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import misc.Cst;
import misc.pass.IRFunctionPass;
import misc.tools.DisjointSet;

import java.util.HashMap;
import java.util.HashSet;

/*
 * Address registers emerge from GlobalVar, Assign, BitCast, Call, GetElementPtr, Load and Phi.
 */
public class MemAccessOptimizer extends IRFunctionPass {
    private final AliasAnalyzer analyzer;

    public MemAccessOptimizer(IRFunction f, IRInfo irInfo) {
        super(f);
        analyzer = new AliasAnalyzer(f, irInfo);
    }

    @Override
    protected void run() {
        analyzer.run();
        irFunc.blocks.forEach(block -> {
            var memRefStorage = new HashMap<String, IROperand>();
            var dirty = new HashSet<String>();
            for (var iter = block.insts.listIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if ((inst instanceof Load)) {
                    var load = (Load) inst;
                    assert load.address instanceof Register;
                    var addressName = ((Register) load.address).identifier();
                    var root = analyzer.unionFind.query(addressName);
                    if (analyzer.noConflict(memRefStorage, root)) {
                        if (!memRefStorage.containsKey(root)) {
                            // first load
                            memRefStorage.put(root, load.dest);
                        } else {
                            // just copy the previous loaded value
                            iter.set(new Assign(load.dest, memRefStorage.get(root)));
                        }
                    } else {
                        iter.previous();
                        for (var mapIter = memRefStorage.entrySet().iterator(); mapIter.hasNext(); ) {
                            var s = mapIter.next();
                            if (analyzer.check(s.getKey(), root) != AliasAnalyzer.stat.NEVER) {
                                // store conflicted memory data
                                if (dirty.contains(s.getKey())) {
                                    dirty.remove(s.getKey());
                                    iter.add(new Store(s.getValue(), analyzer.str2reg(s.getKey())));
                                }
                                mapIter.remove();
                            }
                        }
                        iter.next();
                    }
                } else if (inst instanceof Store) {
                    var store = (Store) inst;
                    var addr = ((Register) store.address).identifier();
                    if (memRefStorage.containsKey(addr)) {
                        if ((store.source instanceof Register && ((Register) store.source).sameIdentifier(memRefStorage.get(addr))
                                || (store.source instanceof IRConstant && memRefStorage.get(addr) instanceof IRConstant
                                && ((IRConstant) store.source).sameConst((IRConstant) memRefStorage.get(addr))))) {
                            // same value
                            iter.remove();
                        } else {
                            memRefStorage.put(addr, store.source);
                            dirty.add(addr);
                        }
                    } else {
                        // no previous record
                        memRefStorage.put(addr, store.source);
                    }
                } else if (inst instanceof Call) {
                    memRefStorage.clear();
                    dirty.clear();
                }
            }
        });
    }


}
