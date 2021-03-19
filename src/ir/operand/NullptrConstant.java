package ir.operand;

import ir.typesystem.PointerType;

public class NullptrConstant extends IRConstant {
    public NullptrConstant(PointerType type){
        this.type= type;
    }
    @Override
    public String tell() {
        return "null";
    }

    @Override
    public IROperand copy(String arg) {
        return new NullptrConstant((PointerType) type);
    }

    @Override
    public boolean sameConst(IRConstant rhs) {
        return rhs instanceof NullptrConstant;
    }
}
