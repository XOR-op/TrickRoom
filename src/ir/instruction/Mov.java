package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

public class Mov extends IRInst{
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RESET = "\u001B[0m";
    public Register dest;
    public IROperand src;
    public Mov(Register dst, IROperand src){
        this.dest=dst;
        this.src=src;
    }

    @Override
    public String tell() {
        return ANSI_CYAN+dest.type+" "+dest+ " = pseudo mv from "+src.type+" "+src+ANSI_RESET;
    }
}
