package ir.operand;

import ir.typesystem.IntegerType;

public class IntConstant extends IROperand{
    private int value;
    public IntConstant(int val,int width){
        value=val;
        type=new IntegerType(width);
    }
    public IntConstant(int val) {
        this(val, 32);
    }

    @Override
    public String tell() {
        return Integer.toString(value);
    }
}
