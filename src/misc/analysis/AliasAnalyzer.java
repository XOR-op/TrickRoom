package misc.analysis;

import ir.IRFunction;
import ir.IRInfo;
import ir.construct.RegisterTracker;
import ir.instruction.Assign;
import ir.instruction.BitCast;
import ir.instruction.Call;
import ir.instruction.GetElementPtr;
import ir.operand.IRConstant;
import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import misc.pass.IRFunctionPass;
import misc.tools.DisjointSet;

import java.util.HashMap;

public class AliasAnalyzer extends IRFunctionPass {
    public enum stat {NEVER, MAY, MUST}

    private final RegisterTracker tracker;
    private final IRInfo irInfo;
    public final DisjointSet<String> unionFind = new DisjointSet<>();

    public AliasAnalyzer(IRFunction f, IRInfo irInfo) {
        super(f);
        tracker = new RegisterTracker(f);
        this.irInfo = irInfo;
    }

    @Override
    protected void run() {
        tracker.run();
        analyze();
    }

    public boolean noConflict(HashMap<String, IROperand> storage, String addr) {
        for (var s : storage.entrySet())
            if (check(s.getKey(), addr) != stat.NEVER && !addr.equals(s.getKey()))
                return false;
        return true;
    }


    private void analyze() {
        tracker.defs.forEach((name, inst) -> {
            var def = inst.iterator().next();
            Register reg = null;
            if (def instanceof Assign && ((Assign) def).src instanceof Register) {
                unionFind.put(def.dest.identifier(), unionFind.query(((Register) ((Assign) def).src).identifier()));
                reg = (Register) ((Assign) def).src;
            }else if(def instanceof BitCast && ((BitCast) def).from instanceof Register) {
                unionFind.put(def.dest.identifier(), unionFind.query(((Register) ((BitCast) def).from).identifier()));
                reg = (Register) ((BitCast) def).from;
            }
            if (reg != null)
                unionFind.put(def.dest.identifier(), unionFind.query(reg.identifier()));
        });
    }

    private AliasAnalyzer.stat eqOrNot(String reg1, String reg2) {
        return reg1.equals(reg2) ? stat.MUST : stat.NEVER;
    }

    private boolean isGlobal(String reg) {
        return reg.charAt(0) == '@'; // ugly
    }

    private boolean isMalloc(String reg) {
        if (isGlobal(reg)) return false;
        var inst = tracker.querySingleDef(unionFind.query(reg));
        return inst instanceof Call && ((Call) inst).isMalloc();
    }


    public stat check(String reg1, String reg2) {
        if (isGlobal(reg1) || isGlobal(reg2)) return eqOrNot(reg1, reg2);
        else if (isMalloc(reg1) && isMalloc(reg2)) return eqOrNot(reg1, reg2);
        else {
            var inst1 = tracker.querySingleDef(unionFind.query(reg1));
            var inst2 = tracker.querySingleDef(unionFind.query(reg2));
            if (inst1 instanceof GetElementPtr && inst2 instanceof GetElementPtr) {
                if (!inst1.dest.type.matches(inst2.dest.type))return stat.NEVER;
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

    public Register str2reg(String name) {
        if (isGlobal(name)) {
            assert irInfo.getGlobalVars().containsKey(name.substring(1));
            return irInfo.getGlobalVars().get(name.substring(1));
        } else {
            assert tracker.nameToRegister(name) != null;
            return tracker.nameToRegister(name);
        }
    }
}
