package ir.operand;

import ir.typesystem.BoolType;

public class BoolConstant extends IRConstant {
    public boolean value;
    public BoolConstant(boolean val){
        type=new BoolType();
        value=val;
    }

    @Override
    public String tell() {
        return value?"true":"false";
    }

    @Override
    public IROperand copy() {
        return new BoolConstant(value);
    }

    @Override
    public boolean sameConst(IRConstant rhs) {
        return rhs instanceof BoolConstant&&((BoolConstant) rhs).value==value;
    }
}
