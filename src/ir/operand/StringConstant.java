package ir.operand;

import ir.typesystem.ArrayType;
import ir.typesystem.IntegerType;
import ir.typesystem.PointerType;

public class StringConstant extends IROperand{
    // the pointer to string in .data fragment
    public String value;
    public String name;
    public final int length;
    public StringConstant(String name,String val){
        this.name=name;
        value=val;
        length=val.length()+1;
        type=new PointerType(new ArrayType(new IntegerType(8),val.length()));
    }

    @Override
    public String tell() {
        return value;
    }

    public String toDefinition(){
        String converted=value.replace("\n","\\0A")
                .replace("\\","\\5C")
                .replace("\t","\\09")
                .replace("\"","\\22");
        return "@"+name+" = private unnamed_addr constant [ "+length+" x i8 ] "+"c\""+converted+"\\00\", align 1";
    }
}
