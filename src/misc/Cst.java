package misc;

import ir.typesystem.*;

public class Cst {
    public static final String STRUCT="__struct_";
    public static final String STR_FUNC="__builtin_str_";
    public static final String STR_LITERAL="._str_";
    public static final String RETURN_VAL="__ret_val";
    public static final String INIT="_gbl_init";
    public static final String COPY_ELIMINATION="__copy_";
    public static final String LOOP_INCRE_NAME ="__loop_new_";
    public static final String SHORT_CIRCUIT_COND="__short_cond_";
    public static final IRType int32 =new IntegerType();
    public static final IRType str=new PointerType(new IntegerType(8));
    public static final IRType bool=new BoolType();
    public static final IRType void_t=new VoidType();
    public static final IRType byte_t=new IntegerType(8);
    public final static String NAME_GENERATE_PREFIX = "__asm_virtual_reg_";
    public final static String RESERVE_PREFIX = "__reserve_";
}
