package ir.operand;

import ir.typesystem.IRType;

public abstract class IROperand {
    public IRType type;

    public abstract String tell();

    public abstract IROperand copy();

    @Override
    public String toString() {
        return tell();
    }
}
