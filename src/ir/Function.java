package ir;

import ir.operand.Register;
import ir.typesystem.IRType;

import java.util.ArrayList;

public class Function {
    public String name;
    public IRType retTy;
    public ArrayList<BasicBlock> blocks=new ArrayList<>();
    public ArrayList<Register> parameters=new ArrayList<>();
    public Function(String name,IRType returnType){
        this.name=name;
        retTy=returnType;
    }
    public void addParam(Register reg){
        parameters.add(reg);
    }

}
