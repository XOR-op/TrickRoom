package ir.typesystem;

import ir.operand.IROperand;
import misc.UnimplementedError;

public class ArrayType extends IRType{
    public IRType baseType;
    private int length;

    public ArrayType(IRType base,int len){
        baseType=base;
        length=len;
    }
    @Override
    public int size() {
        return baseType.size()*length;
    }

    @Override
    public String tell() {
        return "[ "+ length +" x "+baseType.tell()+"]";
    }

    @Override
    public IROperand defaultValue() {
        throw new IllegalStateException();
    }

    @Override
    public boolean matches(IRType rhs) {
        throw new UnimplementedError();
    }
}
