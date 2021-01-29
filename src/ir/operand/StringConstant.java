package ir.operand;

import ir.typesystem.ArrayType;
import ir.typesystem.IntegerType;
import ir.typesystem.PointerType;

public class StringConstant extends IROperand{
    // the pointer to string in .data fragment
    public String value;
    public String name;
    public StringConstant(String name,String val){
        value=val;
        type=new PointerType(new ArrayType(new IntegerType(8),val.length()));
    }

    @Override
    public String tell() {
        return "@"+name;
    }
}
