package ir.instruction;

import ir.IRBlock;
import ir.operand.Register;

import java.util.function.Consumer;
import java.util.function.Function;

public class Jump extends IRInst {

    // unconditional branch
    private final IRBlock target;

    public Jump(IRBlock tgt) {
        target = tgt;
    }

    public IRBlock getTarget() {
        return target;
    }

    @Override
    public String tell() {
        return "br label %" + target.getBlockName();
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
    public void forEachRegSrc(Consumer<Register> consumer) {
        // do nothing
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

}
