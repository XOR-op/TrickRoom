package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;
import ir.typesystem.IRType;

import java.util.function.Function;

public class BitCast extends IRDestedInst {

    private IROperand from;
    private IRType to;

    public BitCast(Register dst, IROperand frm) {
        dest=dst;
        from=frm;
        to=dst.type;
    }

    @Override
    public String tell() {
        return dest+" = bitcast "+from.type+" "+from+" to "+to;
    }

    @Override
    public void renameOperand(Register reg) {
        if(reg.sameNaming(from))from=reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        from=Register.replace(replace,from);
    }
}