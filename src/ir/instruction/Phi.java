package ir.instruction;

import exception.UnimplementedError;
import ir.operand.Register;

public class Phi extends IRDestedInst{

    public Phi(Register reg){
        dest=reg.copy();
    }

    @Override
    public String tell() {
        throw new UnimplementedError();
    }
}
