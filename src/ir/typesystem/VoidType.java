package ir.typesystem;

import ir.operand.IROperand;
import misc.UnimplementedError;

public class VoidType extends IRType {
    @Override
    public int size() {
        throw new IllegalStateException();
    }

    @Override
    public String tell() {
        return "void";
    }

    @Override
    public IROperand defaultValue() {
        throw new IllegalStateException();
    }

    @Override
    public boolean matches(IRType rhs) {
        return rhs instanceof VoidType;
    }
}
