package ir.operand;

import ir.typesystem.IRType;
import ir.typesystem.PointerType;

public class NullptrConstant extends IROperand{
    public NullptrConstant(PointerType type){
        this.type= type;
    }
    @Override
    public String tell() {
        return "null";
    }
}
