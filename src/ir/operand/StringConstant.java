package ir.operand;

import misc.Cst;

public class StringConstant extends IROperand {
    // the pointer to string in .rodata fragment
    public String value;
    public String name;
    public final int length;

    public StringConstant(String name, String val) {
        this.name = name;
        value = val;
        length = val.length() + 1;
        type= Cst.str;
//        type = new PointerType(new ArrayType(new IntegerType(8), val.length()+1));
    }

    @Override
    public String tell() {
        return "getelementptr inbounds ([" + length + " x i8], [" + length + " x i8]* @" + name + ", i32 0,i32 0)";
    }

    public String toDefinition() {
        String converted = value.replace("\\", "\\5C")
                .replace("\n", "\\0A")
                .replace("\"", "\\22")
                .replace("\t", "\\09");
        return "@" + name + " = private unnamed_addr constant [ " + length + " x i8 ] " + "c\"" + converted + "\\00\", align 1";
    }
}
