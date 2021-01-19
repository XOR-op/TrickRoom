package ir.instruction;

public class Binary extends IRInst{
    public enum BinInst{ADD,SUB,MUL,UDIV,SDIV,UREM,SREM,SHL,LSHR,ASHR,AND,OR,XOR}
    public BinInst inst;

}
