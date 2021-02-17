package ir.operand;

import ir.typesystem.IRType;

public class UndefConstant extends IROperand {

    public UndefConstant(IRType ty){
        type=ty;
    }

    @Override
    public String tell() {
        return "undef";
    }
}
