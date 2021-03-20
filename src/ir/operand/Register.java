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

    public String getName() {
        return name;
    }

    protected String name;

    public Register(IRType ty) {
        this.name = "_A" + (counter++);
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

    public void replaceWith(String name) {
        this.name = name;
        this.isAnonymous = false;
    }

    @Override
    public Register copy(String arg) {
        var rt = new Register(type, arg + name);
        rt.isAnonymous = isAnonymous;
        rt.renaming = renaming;
        return rt;
    }


    public boolean isAnonymous() {
        return isAnonymous;
    }

    public boolean sameNaming(IROperand rhs) {
        return rhs instanceof Register && name.equals(((Register) rhs).name);
    }

    public static IROperand replace(Function<Register, Register> mapping, IROperand operand) {
        if (operand instanceof Register && !((Register) operand).isAnonymous())
            return mapping.apply((Register) operand);
        else return operand;
    }

    public String identifier() {
        return name + "_" + renaming;
    }

    public boolean sameIdentifier(IROperand rhs) {
        return rhs instanceof Register && identifier().equals(((Register) rhs).identifier());
    }

    public boolean equals(Register rhs) {
        return name.equals(rhs.name) && renaming == rhs.renaming;
    }

    public boolean equals(IROperand rhs) {
        return rhs instanceof Register && equals((Register) rhs);
    }

    @Override
    public String tell() {
        return "%" + (renaming == 0 ? "" : ("_" + renaming + "$")) + name;
    }
}
