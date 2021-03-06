package ir.instruction;

import ir.IRBlock;
import ir.operand.Register;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class IRInst {
    public IRBlock parentBlock;

    public abstract String tell();

    public abstract void renameOperand(Register reg);

    public abstract void renameOperand(Function<Register, Register> replace);

    public abstract void forEachRegSrc(Consumer<Register> consumer);

    public void forEachRegDest(Consumer<Register> consumer) {
        // do nothing for no-IRDestInst
    }

    @Override
    public String toString() {
        return tell();
    }

    public boolean isTerminal() {
        return false;
    }

    public boolean containsDest() {
        return false;
    }

    public abstract boolean hasSideEffect();
}
