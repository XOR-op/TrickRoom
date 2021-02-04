package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

public class GetElementPtr extends IRDestedInst{
    public IROperand base;
    public IROperand offset;
    public GetElementPtr(Register dst,IROperand src,IROperand off){
        dest=dst;
        base=src;
        offset=off;
    }

    @Override
    public String tell() {
        return dest+" = getelementptr "+base.type+" "+base+", "+offset.type+" "+offset;
    }
}
