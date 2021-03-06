package ir.typesystem;

import ir.operand.IROperand;
import ir.operand.IntConstant;

public class IntegerType extends IRType {
    private int width;

    public IntegerType() {
        this(32);
    }

    public IntegerType(int wid) {
        width = wid;
    }

    @Override
    public int size() {
        return width / 8;
    }

    @Override
    public String tell() {
        return "i" + width;
    }

    @Override
    public IROperand defaultValue() {
        return new IntConstant(0, width);
    }

    @Override
    public boolean matches(IRType rhs) {
        return rhs instanceof IntegerType && width == ((IntegerType) rhs).width;
    }
}
