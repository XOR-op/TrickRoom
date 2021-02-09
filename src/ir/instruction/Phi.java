package ir.instruction;

import exception.UnimplementedError;
import ir.operand.Register;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.Function;

public class Phi extends IRDestedInst{
    public ArrayList<Register> arguments=new ArrayList<>();

    public Phi(Register reg){
        dest=reg.copy();
    }

    public void append(Register reg){
        assert reg.name.equals(dest.name);
        arguments.add(reg);
    }

    @Override
    public String tell() {
        StringJoiner sj=new StringJoiner(", ",dest+" = phi [","]");
        arguments.forEach(a->sj.add(a.tell()));
        return sj.toString();
    }

    @Override
    public void renameOperand(Register reg) {
        throw new IllegalStateException();
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        throw new IllegalStateException();
    }
}
