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
            } else if (def instanceof BitCast && ((BitCast) def).from instanceof Register) {
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
        else if (tracker.isParameter(reg1) || tracker.isParameter(reg2)) {
            var theReg1 = (tracker.isParameter(reg1)) ? tracker.queryParameter(reg1) : tracker.queryRegisterDefs(reg1).iterator().next().dest;
            var theReg2 = (tracker.isParameter(reg2)) ? tracker.queryParameter(reg2) : tracker.queryRegisterDefs(reg2).iterator().next().dest;
            return (theReg1.type.matches(theReg2.type)) ? stat.MAY : stat.NEVER;
        } else if (isMalloc(reg1) && isMalloc(reg2)) return eqOrNot(reg1, reg2);
        else {
            var inst1 = tracker.querySingleDef(unionFind.query(reg1));
            var inst2 = tracker.querySingleDef(unionFind.query(reg2));
            if (inst1 instanceof GetElementPtr && inst2 instanceof GetElementPtr) {
                GetElementPtr gep1 = (GetElementPtr) inst1, gep2 = (GetElementPtr) inst2;
                if (!inst1.dest.type.matches(inst2.dest.type)) return stat.NEVER;
                if (check(((Register) (gep1).base).identifier(), ((Register) (gep2).base).identifier()) == stat.NEVER)
                    return stat.NEVER;
                if (((Register) (gep1).base).sameIdentifier((gep2).base)) {
                    if ((gep1).indexing instanceof Register
                            && ((Register) (gep1).indexing).sameIdentifier((gep2).indexing) ||
                            ((gep1).indexing instanceof IntConstant && (gep2).indexing instanceof IntConstant &&
                                    ((IntConstant) (gep1).indexing).sameConst((IRConstant) (gep2).indexing))) {
                        if ((gep1).offset == null ^ (gep2).offset == null) {
                            return stat.NEVER;
                        } else if ((gep1).offset == null && (gep2).offset == null) {
                            return stat.MUST;
                        } else {
                            return (gep1).offset.sameConst((gep2).offset) ? stat.MUST : stat.NEVER;
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
