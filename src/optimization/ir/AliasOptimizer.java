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
public class AliasOptimizer extends IRFunctionPass {
    private enum stat {NEVER, MAY, MUST}

    private final RegisterTracker tracker;
    private final DisjointSet<String> unionFind = new DisjointSet<>();
    private final IRInfo irInfo;

    public AliasOptimizer(IRFunction f, IRInfo irInfo) {
        super(f);
        tracker = new RegisterTracker(f);
        this.irInfo = irInfo;
    }

    @Override
    protected void run() {
        tracker.run();
        analyze();
        irFunc.blocks.forEach(block -> {
            var memRefStorage = new HashMap<String, IROperand>();
            var dirty = new HashSet<String>();
            for (var iter = block.insts.listIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if ((inst instanceof Load)) {
                    var load = (Load) inst;
                    assert load.address instanceof Register;
                    var addressName = ((Register) load.address).identifier();
                    var root = unionFind.query(addressName);
                    if (noConflict(memRefStorage, root)) {
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
                            if (check(s.getKey(), root) != stat.NEVER) {
                                // store conflicted memory data
                                if (dirty.contains(s.getKey())) {
                                    dirty.remove(s.getKey());
                                    iter.add(new Store(s.getValue(), str2reg(s.getKey())));
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

    private boolean noConflict(HashMap<String, IROperand> storage, String addr) {
        for (var s : storage.entrySet())
            if (check(s.getKey(), addr) != stat.NEVER && !addr.equals(s.getKey())) return false;
        return true;
    }


    private void analyze() {
        tracker.defs.forEach((name, inst) -> {
            var def = inst.iterator().next();
            Register reg = null;
            if (def instanceof Assign && ((Assign) def).src instanceof Register) {
                unionFind.put(def.dest.identifier(), unionFind.query(((Register) ((Assign) def).src).identifier()));
                reg = (Register) ((Assign) def).src;
            }
            if (reg != null)
                unionFind.put(def.dest.identifier(), unionFind.query(reg.identifier()));
        });
    }

    private stat eqOrNot(String reg1, String reg2) {
        return reg1.equals(reg2) ? stat.MUST : stat.NEVER;
    }

    private boolean isGlobal(String reg) {
        return reg.charAt(0) == '@'; // ugly
    }

    private boolean isMalloc(String reg) {
        if (isGlobal(reg)) return false;
        var inst = tracker.querySingleDef(unionFind.query(reg));
        return inst instanceof Call && ((Call) inst).function == irInfo.getFunction(Cst.MALLOC);
    }


    private stat check(String reg1, String reg2) {
        if (isGlobal(reg1) || isGlobal(reg2)) return eqOrNot(reg1, reg2);
        else if (isMalloc(reg1) && isMalloc(reg2)) return eqOrNot(reg1, reg2);
        else {
            var inst1 = tracker.querySingleDef(unionFind.query(reg1));
            var inst2 = tracker.querySingleDef(unionFind.query(reg1));
            if (inst1 instanceof GetElementPtr && inst2 instanceof GetElementPtr) {
                if (((Register) ((GetElementPtr) inst1).base).sameIdentifier(((GetElementPtr) inst2).base)) {
                    if (((GetElementPtr) inst1).indexing instanceof Register
                            && ((Register) ((GetElementPtr) inst1).indexing).sameIdentifier(((GetElementPtr) inst2).indexing) ||
                            (((GetElementPtr) inst1).indexing instanceof IntConstant && ((GetElementPtr) inst2).indexing instanceof IntConstant &&
                                    ((IntConstant) ((GetElementPtr) inst1).indexing).sameConst((IRConstant) ((GetElementPtr) inst2).indexing))) {
                        if (((GetElementPtr) inst1).offset == null ^ ((GetElementPtr) inst2).offset == null) {
                            return stat.NEVER;
                        } else if (((GetElementPtr) inst1).offset == null && ((GetElementPtr) inst2).offset == null) {
                            return stat.MUST;
                        } else {
                            return ((GetElementPtr) inst1).offset.sameConst(((GetElementPtr) inst2).offset) ? stat.MUST : stat.NEVER;
                        }
                    }
                }
            }
        }
        return stat.MAY;
    }

    private Register str2reg(String name) {
        if (isGlobal(name)) {
            assert irInfo.getGlobalVars().containsKey(name.substring(1));
            return irInfo.getGlobalVars().get(name.substring(1));
        } else {
            assert tracker.nameToRegister(name) != null;
            return tracker.nameToRegister(name);
        }
    }
}
