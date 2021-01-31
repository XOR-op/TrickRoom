package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

public class Mov extends IRInst{
    public Register dest;
    public IROperand src;
    public Mov(Register dst, IROperand src){
        this.dest=dst;
        this.src=src;
    }

    @Override
    public String tell() {
        return " ; "+dest.type+" "+dest+ " pseudo mv from "+src.type+" "+src;
    }
}
