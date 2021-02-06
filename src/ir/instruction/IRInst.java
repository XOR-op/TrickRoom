package ir.instruction;

import ir.BasicBlock;

public abstract class IRInst {
    public BasicBlock parentBlock;
    public abstract String tell();

    @Override
    public String toString() {
        return tell();
    }

    public boolean isTerminal(){return false;}
}
