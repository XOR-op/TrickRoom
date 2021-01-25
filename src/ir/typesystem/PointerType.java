package ir.typesystem;

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

    public static PointerType nullptr(){
        return new PointerType(null);
    }
}
