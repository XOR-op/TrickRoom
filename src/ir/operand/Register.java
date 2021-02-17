package ir.operand;

import ir.instruction.IRDestedInst;
import ir.instruction.IRInst;
import ir.typesystem.IRType;

import java.util.function.Function;

public class Register extends IROperand {
    // global allocation
    private static int counter = 0;

    public static void reset() {
        reset(0);
    }

    public static void reset(int new_counter) {
        counter = new_counter;
    }

    private boolean isAnonymous;
    private int renaming = 0;
    public String name;

    public Register(IRType ty) {
        this.name = "ANONY_"+(counter++);
        type = ty;
        isAnonymous = true;
    }

    public Register(IRType ty, String name) {
        this.name = name;
        type = ty;
        isAnonymous = false;
    }

    public Register rename(int newCount) {
        var reg = new Register(type, name);
        reg.renaming = newCount;
        return reg;
    }

    public void replaceWith(String name){
        this.name=name;
        this.isAnonymous=false;
    }

    public Register copy(){
        var rt=new Register(type,name);
        rt.isAnonymous=isAnonymous;
        rt.renaming=renaming;
        return rt;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public boolean sameNaming(IROperand rhs){return rhs instanceof Register&&name.equals(((Register)rhs).name);}

    public static IROperand replace(Function<Register,Register> rep,IROperand operand){
        if(operand instanceof Register&& !((Register) operand).isAnonymous())
            return rep.apply((Register) operand);
        else return operand;
    }

    public boolean equals(Register rhs){
        return name.equals(rhs.name)&&renaming== rhs.renaming;
    }

    @Override
    public String tell() {
        return "%"+ (renaming==0?"":("rem_"+renaming+"."))+ name;
    }
}
