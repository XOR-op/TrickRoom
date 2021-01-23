package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

public class Load extends IRInst{
    private Register dest;
    private IROperand address;
    public Load(Register dst,IROperand addr){
        dest=dst;
        address=addr;
    }

    @Override
    public String tell() {
        return dest+" = load "+dest.type+", "+address.type+" "+address;
    }
}
