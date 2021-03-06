package ir.instruction;

import misc.Cst;
import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import ir.typesystem.PointerType;

import java.util.function.Consumer;
import java.util.function.Function;

public class Assign extends IRDestedInst {
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RESET = "\u001B[0m";
    public IROperand src;

    public Assign(Register dst, IROperand src) {
        this.dest = dst;
        this.src = src;
    }

    @Override
    public String tell() {
        if (dest.type.equals(Cst.int32) || dest.type.equals(Cst.bool))
            return new Binary(Binary.BinInstEnum.add, dest, src, new IntConstant(0)).tell();
        else {
            assert dest.type instanceof PointerType;
            return new GetElementPtr(dest, src, new IntConstant(0)).tell();
        }
    }

    @Override
    public void renameOperand(Register reg) {
        if (reg.sameNaming(src)) src = reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        src = Register.replace(replace, src);
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        if (src instanceof Register)
            consumer.accept((Register) src);
    }

    @Override
    public boolean hasSideEffect() {
        return false;
    }
}
