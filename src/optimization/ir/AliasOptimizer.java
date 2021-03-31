package optimization.ir;

import ir.IRFunction;
import ir.IRInfo;
import ir.construct.RegisterTracker;
import ir.instruction.Assign;
import ir.instruction.BitCast;
import ir.instruction.Call;
import ir.operand.GlobalVar;
import ir.operand.Register;
import misc.Cst;
import misc.pass.IRFunctionPass;

import java.util.BitSet;
import java.util.HashMap;

/*
 * Address registers emerge from GlobalVar, Assign, BitCast, Call, GetElementPtr, Load and Phi.
 */
public class AliasOptimizer extends IRFunctionPass {
    private enum stat {NEVER, MAY, MUST}

    private final RegisterTracker tracker;
    private final HashMap<Register, Register> unionFind = new HashMap<>();
    private final IRInfo irInfo;

    public AliasOptimizer(IRFunction f, IRInfo irInfo) {
        super(f);
        tracker = new RegisterTracker(f);
        this.irInfo = irInfo;
    }

    @Override
    protected void run() {

    }

    private Register query(Register reg) {
        while (unionFind.containsKey(reg))
            reg = unionFind.get(reg);
        return reg;
    }

    private void analyze() {
        tracker.defs.forEach((name, inst) -> {
            var def = inst.iterator().next();
            Register reg = null;
            if (def instanceof Assign && ((Assign) def).src instanceof Register) reg = (Register) ((Assign) def).src;
            else if (def instanceof BitCast && ((BitCast) def).from instanceof Register)
                reg = (Register) ((BitCast) def).from;
            if (reg != null)
                unionFind.put(def.dest, query(reg));
        });
    }

    private stat eqOrNot(Register reg1, Register reg2) {
        return reg1 == reg2 ? stat.MUST : stat.NEVER;
    }

    private boolean isGlobal(Register reg) {
        return reg instanceof GlobalVar;
    }

    private boolean isMalloc(Register reg) {
        var inst = tracker.querySingleDef(reg);
        return inst instanceof Call && ((Call) inst).function == irInfo.getFunction(Cst.MALLOC);
    }

    private stat check(Register reg1, Register reg2) {
        if (isGlobal(reg1) || isGlobal(reg2)) return eqOrNot(reg1, reg2);
        if (isMalloc(reg1) && isMalloc(reg2)) return eqOrNot(reg1, reg2);
        // todo
        return null;
    }
}
