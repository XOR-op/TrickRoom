package ir.instruction;

import ir.BasicBlock;
import ir.operand.Register;

import java.util.function.Function;

public abstract class IRInst {
    public BasicBlock parentBlock;
    public abstract String tell();
    public abstract void renameOperand(Register reg);
    public abstract void renameOperand(Function<Register,Register> replace);
    @Override
    public String toString() {
        return tell();
    }

    public boolean isTerminal(){return false;}

    public boolean containsDest(){return false;}
}
