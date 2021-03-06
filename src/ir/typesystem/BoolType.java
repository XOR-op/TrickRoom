package ir.typesystem;

import ir.operand.BoolConstant;
import ir.operand.IROperand;

public class BoolType extends IRType{
    @Override
    public int size() {
        return 1;
    }

    @Override
    public String tell() {
        return "i1";
    }

    @Override
    public IROperand defaultValue() {
        return new BoolConstant(false);
    }

    @Override
    public boolean matches(IRType rhs) {
        return rhs instanceof BoolType;
    }
}
