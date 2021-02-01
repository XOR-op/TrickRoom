package ir.typesystem;

import ir.operand.BoolConstant;
import ir.operand.IROperand;

public class BoolType extends IRType{
    @Override
    public int size() {
        return 8;
    }

    @Override
    public String tell() {
        return "i1";
    }

    @Override
    public IROperand defaultValue() {
        return new BoolConstant(false);
    }
}
