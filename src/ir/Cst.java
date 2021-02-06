package ir;

import ir.typesystem.*;

public class Cst {
    public static final String STRUCT="struct.";
    public static final String STR_FUNC="builtin.str.";
    public static final String STR_LITERAL="str.";
    public static final String ARRAY_TYPE="array.";
    public static final String ARRAY_SIZE="builtin.array.size";
    public static final String TYPE_CAST="typecast.";
    public static final String MALLOC="builtin.malloc";
    public static final IRType int32 =new IntegerType();
    public static final IRType str=new PointerType(new IntegerType(8));
    public static final IRType bool=new BoolType();
    public static final IRType void_t=new VoidType();
    public static final IRType byte_t=new IntegerType(8);
}
