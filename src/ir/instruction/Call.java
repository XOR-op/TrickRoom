package ir.instruction;

import ir.Function;
import ir.operand.IROperand;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Call extends IRInst{
    private Function function;
    private ArrayList<IROperand> args;
    public Call(Function func){
        function=func;
        args=new ArrayList<>();
    }
    public Call push(IROperand irOperand){
        args.add(irOperand);
        return this;
    }

    @Override
    public String tell() {
        StringJoiner sj=new StringJoiner(", ","(",")");
        args.forEach(arg->sj.add(arg.tell()));
        return "call "+function.retTy+" "+function.name+sj.toString();
    }
}
