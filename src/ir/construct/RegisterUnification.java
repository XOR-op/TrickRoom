package ir.construct;

import ir.IRFunction;
import ir.operand.GlobalVar;
import ir.operand.Register;
import misc.pass.IRFunctionPass;

import java.util.HashMap;

public class RegisterUnification extends IRFunctionPass {
    public RegisterUnification(IRFunction f) {
        super(f);
    }

    @Override
    protected void run() {
        HashMap<String, Register> unified = new HashMap<>();
        irFunc.parameters.forEach(r->unified.put(r.identifier(),r));
        irFunc.blocks.forEach(b -> {
            b.insts.forEach(inst -> {
                inst.renameOperand(r -> {
                    if (r instanceof GlobalVar) return r;
                    if (unified.containsKey(r.identifier())) return unified.get(r.identifier());
                    else {
                        var newReg=new Register(r.type,r.identifier());
                        unified.put(newReg.identifier(), newReg);
                        return newReg;
                    }
                });
            });
        });
    }
}
