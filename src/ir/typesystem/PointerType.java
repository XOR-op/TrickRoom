package ir.typesystem;

import ir.operand.IROperand;
import ir.operand.NullptrConstant;

public class PointerType extends IRType{
    public IRType baseType;
    public PointerType(IRType base){
        baseType=base;
    }

    @Override
    public int size() {
        return 32;
    }

    @Override
    public String tell() {
        return baseType==null?"nullptr*":(baseType+"*");
    }

    @Override
    public IROperand defaultValue() {
        return new NullptrConstant();
    }

    public static PointerType nullptr(){
        return new PointerType(null);
    }

    public static PointerType baseArrayType(){
        return new PointerType(TypeEnum.int32);
    }
}
