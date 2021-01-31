package ir.typesystem;

public class TypeEnum {
    public static final IRType int32 =new IntegerType();
    public static final IRType str=new PointerType(new IntegerType(8));
    public static final IRType bool=new BoolType();
    public static final IRType void_t=new VoidType();
}
