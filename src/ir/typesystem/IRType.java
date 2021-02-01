package ir.typesystem;

import ir.operand.IROperand;

public abstract class IRType {
    public abstract int size();
    public abstract String tell();
    public abstract IROperand defaultValue();

    @Override
    public String toString() {
        return tell();
    }
}
