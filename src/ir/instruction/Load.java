package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Function;

public class Load extends IRDestedInst{
    public IROperand address;
    public Load(Register dst,IROperand addr){
        dest=dst;
        address=addr;
    }

    @Override
    public String tell() {
        return dest+" = load "+dest.type+", "+address.type+" "+address;
    }

    @Override
    public void renameOperand(Register reg) {
        if(reg.sameNaming(address))address=reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        address=Register.replace(replace,address);
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
