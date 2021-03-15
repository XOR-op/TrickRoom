package ir.operand;

import ir.typesystem.IRType;

public class UndefConstant extends IRConstant {

    public UndefConstant(IRType ty) {
        type = ty;
    }

    @Override
    public String tell() {
        return "undef";
    }

    @Override
    public boolean sameConst(IRConstant rhs) {
        throw new IllegalStateException();
    }
}
