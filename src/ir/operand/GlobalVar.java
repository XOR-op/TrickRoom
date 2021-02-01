package ir.operand;

import ir.typesystem.IRType;

public class GlobalVar extends Register{
    public IROperand initValue;
    public GlobalVar(IRType ty,String name,IROperand init){
        super(ty,name);
        initValue=init;
    }
    public GlobalVar(IRType ty,String name){
        this(ty,name,ty.defaultValue());
    }

    @Override
    public String tell() {
        return "@"+name;
    }
}
