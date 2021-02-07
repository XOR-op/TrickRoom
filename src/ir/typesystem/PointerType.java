package ir.typesystem;

import ir.Cst;
import ir.operand.IROperand;
import ir.operand.NullptrConstant;

public class PointerType extends IRType{
    public IRType baseType;
    private int dim;
    public PointerType(IRType base){
        if(base instanceof PointerType){
            baseType=((PointerType) base).baseType;
            dim=((PointerType) base).dim+1;
        }else {
            baseType = base;
            dim=1;
        }
    }

    public IRType subType(){
        if(dim==1)return baseType;
        else {
            var rt=new PointerType(this);
            rt.dim=dim-1;
            return rt;
        }
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public String tell() {
        return baseType==null?"nullptr*":(baseType+"*".repeat(dim));
    }
    @Override
    public IROperand defaultValue() {
        return new NullptrConstant();
    }

    public static PointerType nullptr(){
        return new PointerType(null);
    }

    public static PointerType baseArrayType(){
        return new PointerType(Cst.int32);
    }
}
