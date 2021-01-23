package ir.operand;

import ir.typesystem.IntegerType;

public class IntConstant extends IROperand{
    private int value;
    public IntConstant(int val){
        value=val;
        type=new IntegerType();
    }

    @Override
    public String tell() {
        return Integer.toString(value);
    }
}
