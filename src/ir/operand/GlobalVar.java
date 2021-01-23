package ir.operand;

import ir.typesystem.IRType;

public class GlobalVar extends IROperand{
    public String name;
    public GlobalVar(IRType ty,String name){
        this.name=name;
        type=ty;
    }

    @Override
    public String tell() {
        return "@"+name;
    }
}
