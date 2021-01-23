package ir.instruction;

import ir.operand.IROperand;

public class Load extends IRInst{
    private IROperand dest;
    private IROperand address;
    public Load(IROperand dst,IROperand addr){
        dest=dst;
        address=addr;
    }

    @Override
    public String tell() {
        return dest+" = load "+dest.type+", "+address.type+" "+address;
    }
}
