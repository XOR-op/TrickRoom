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

import java.util.HashMap;

/*
 * Address registers emerge from GlobalVar, Assign, BitCast, Call, GetElementPtr, Load and Phi.
 */
public class AliasOptimizer extends IRFunctionPass {
    private enum stat {NEVER, MAY, MUST}

    private final RegisterTracker tracker;
    private final HashMap<String, String> unionFind = new HashMap<>();
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
            for (var iter = block.insts.listIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if ((inst instanceof Load)) {
                    var load = (Load) inst;
                    assert load.address instanceof Register;
                    var addressName = ((Register) load.address).identifier();
                    var root = query(addressName);
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
                                iter.add(new Store(s.getValue(), str2reg(s.getKey())));
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
                        }
                    } else {
                        // no previous record
                        memRefStorage.put(addr, store.source);
                    }
                } else if (inst instanceof Call) {
                    memRefStorage.clear();
                }
            }
        });
    }

    private boolean noConflict(HashMap<String, IROperand> storage, String addr) {
        for (var s : storage.entrySet())
            if (check(s.getKey(), addr) != stat.NEVER && !addr.equals(s.getKey())) return false;
        return true;
    }

    private String query(String reg) {
        if (!unionFind.containsKey(reg)) return reg;
        var root = query(unionFind.get(reg));
        unionFind.put(reg, root);
        return root;
    }

    private void analyze() {
        tracker.defs.forEach((name, inst) -> {
            var def = inst.iterator().next();
            Register reg = null;
            if (def instanceof Assign && ((Assign) def).src instanceof Register) {
                unionFind.put(def.dest.identifier(), query(((Register) ((Assign) def).src).identifier()));
                reg = (Register) ((Assign) def).src;
            }
//            else if (def instanceof BitCast && ((BitCast) def).from instanceof Register) {
//                unionFind.put(def.dest.identifier(), query(((Register) ((BitCast) def).from).identifier()));
//                reg = (Register) ((BitCast) def).from;
//            }
            if (reg != null)
                unionFind.put(def.dest.identifier(), query(reg.identifier()));
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
        var inst = tracker.querySingleDef(query(reg));
        return inst instanceof Call && ((Call) inst).function == irInfo.getFunction(Cst.MALLOC);
    }


    private stat check(String reg1, String reg2) {
        if (isGlobal(reg1) || isGlobal(reg2)) return eqOrNot(reg1, reg2);
        else if (isMalloc(reg1) && isMalloc(reg2)) return eqOrNot(reg1, reg2);
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
