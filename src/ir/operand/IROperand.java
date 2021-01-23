package ir.operand;

import ir.typesystem.IRType;

public abstract class IROperand {
    public IRType type;
    public abstract String tell();

    @Override
    public String toString() {
        return tell();
    }
}
