package ir.instruction;

import ir.operand.IROperand;

public class Store extends IRInst{
    private IROperand address;
    private IROperand source;
    public Store(IROperand src,IROperand addr){
        address=addr;
        source=src;
    }

    @Override
    public String tell() {
        return "store "+source.type+" "+source+", "+address.type+" "+address;
    }
}
