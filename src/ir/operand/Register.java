package ir.operand;

import ir.typesystem.IRType;

public class Register extends IROperand{
    private static int counter=0;
    public static void reset(){reset(0);}
    public static void reset(int new_counter){counter=new_counter;}
    public String name;
    public Register(IRType ty){
        this(ty,Integer.toString(counter++));
    }
    public Register(IRType ty,String name){
        this.name=name;
        type=ty;
    }

    @Override
    public String tell() {
        return "#"+name;
    }
}
