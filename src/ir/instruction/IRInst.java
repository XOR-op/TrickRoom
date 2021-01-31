package ir.instruction;

public abstract class IRInst {
    public abstract String tell();

    @Override
    public String toString() {
        return tell();
    }

    public boolean isTerminal(){return false;}
}
