package ir.instruction;

import ir.operand.IROperand;

public class Ret extends IRInst{
    private IROperand value;
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
    public boolean isTerminal() {
        return true;
    }
}
