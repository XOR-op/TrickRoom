package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Function;

public class Ret extends IRInst{
    public IROperand value;
    public Ret(IROperand val){
        assert val!=null;
        value=val;
    }
    private Ret(){value=null;}
    public static Ret voidRet(){
        return new Ret();
    }

    @Override
    public String tell() {
        return "ret "+(value==null?"void":(value.type+" "+value));
    }

    @Override
    public void renameOperand(Register reg) {
        if(reg.sameNaming(value)) value=reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        value=Register.replace(replace,value);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
