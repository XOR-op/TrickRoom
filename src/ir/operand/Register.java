package ir.operand;

import ir.typesystem.IRType;

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
        this.name = Integer.toString(counter++);
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
        renaming = newCount;
        return reg;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public boolean equals(Register rhs){
        return name.equals(rhs.name)&&renaming== rhs.renaming;
    }

    @Override
    public String tell() {
        return "%" + name + (renaming == 0 ? "" : "$rname" + renaming);
    }
}
