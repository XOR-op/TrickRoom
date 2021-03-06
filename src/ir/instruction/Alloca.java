package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;
import ir.typesystem.IRType;

import java.util.function.Consumer;
import java.util.function.Function;

public class Alloca extends IRDestedInst {
    private int align;
    private IRType type;
    private int size;

    public Alloca(Register dst, IRType ty, int sz, int alignment) {
        align = alignment;
        dest = dst;
        type = ty;
        size = sz;
    }

    public Alloca(Register dest, IRType ty, int sz) {
        this(dest, ty, sz, 4);
    }

    public Alloca(Register dest, IRType ty) {
        this(dest, ty, 1);
    }

    @Override
    public String tell() {
        return dest + " = alloca " + type + (size == 1 ? "" : (", " + type + " " + size)) + (align == 0 ? "" : ", align " + align);
    }

    @Override
    public void renameOperand(Register reg) {
        // do nothing
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        // do nothing
    }

    @Override
    public void replaceRegisterWithOperand(IROperand operand, Register oldReg) {
        // do nothing
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        // do nothing
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

}
