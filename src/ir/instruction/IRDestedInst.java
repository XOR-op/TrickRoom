package ir.instruction;

import ir.operand.Register;

import java.util.function.Function;

public abstract class IRDestedInst extends IRInst{
    public Register dest;
    public void renameDest(Register reg){
        if(reg.sameNaming(dest)){
            dest=reg;
        }
    }
    public void renameDest(Function<Register,Register> replace){
        dest= (Register) Register.replace(replace,dest);
    }
    public boolean namedDest(){return !dest.isAnonymous();}
}
