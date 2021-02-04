package ir.instruction;

import ir.Function;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Call extends IRDestedInst{
    public Function function;
    public ArrayList<IROperand> args;
    public Call(Register dst, Function func){
        function=func;
        args=new ArrayList<>();
        dest=dst;
    }
    public Call push(IROperand irOperand){
        args.add(irOperand);
        return this;
    }

    @Override
    public String tell() {
        StringJoiner sj=new StringJoiner(", ","(",")");
        args.forEach(arg->sj.add(arg.type.tell()+" "+arg.tell()));
        return dest+" = call "+function.retTy+" "+function.name+sj.toString();
    }
}
