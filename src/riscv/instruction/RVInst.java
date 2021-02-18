package riscv.instruction;

public abstract class RVInst {
    public enum RelaType{
        eq,ne,lt,le,gt,ge
    }
    public abstract String tell();
}
