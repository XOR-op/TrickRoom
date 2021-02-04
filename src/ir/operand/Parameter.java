package ir.operand;

import ir.typesystem.IRType;

public class Parameter extends IROperand{
    public String name;
    public Parameter(IRType ty,String name){
        this.name=name;
        type=ty;
    }

    @Override
    public String tell() {
        return "%"+name;
    }
}
