package ir.instruction;

import ir.IRBlock;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class IRInst implements Cloneable{

    public abstract String tell();

    public abstract void renameOperand(Register reg);

    public abstract void renameOperand(Function<Register, Register> replace);

    public abstract void replaceRegisterWithOperand(IROperand operand,Register oldReg);

    public abstract void forEachRegSrc(Consumer<Register> consumer);

    public void forEachRegDest(Consumer<Register> consumer) {
        // do nothing for no-IRDestInst
    }

    public HashSet<Register> getRegSrc() {
        var set = new HashSet<Register>();
        forEachRegSrc(set::add);
        return set;
    }

    public HashSet<Register> getRegDest() {
        var set = new HashSet<Register>();
        forEachRegDest(set::add);
        return set;
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
