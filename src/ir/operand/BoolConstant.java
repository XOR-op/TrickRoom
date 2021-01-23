package ir.operand;

import ir.typesystem.BoolType;

public class BoolConstant extends IROperand{
    private boolean value;
    public BoolConstant(boolean val){
        type=new BoolType();
        value=val;
    }

    @Override
    public String tell() {
        return value?"true":"false";
    }
}
