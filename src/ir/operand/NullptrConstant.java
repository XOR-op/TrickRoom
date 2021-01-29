package ir.operand;

import ir.typesystem.PointerType;

public class NullptrConstant extends IROperand{

    public NullptrConstant(){
        this.type= PointerType.nullptr();
    }

    @Override
    public String tell() {
        return "null";
    }
}
