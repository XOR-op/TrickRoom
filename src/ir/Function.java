package ir;

import ir.instruction.Ret;
import ir.operand.Register;
import ir.typesystem.IRType;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Function {
    public String name;
    public IRType retTy;
    public ArrayList<BasicBlock> blocks=new ArrayList<>();
    public ArrayList<Register> parameters=new ArrayList<>();
    public BasicBlock entryBlock,exitBlock;
    public Register returnValue;
    private boolean isBuiltin;
    public Function(String name,IRType returnType,boolean isBuiltin) {
        this.name = name;
        this.isBuiltin = isBuiltin;
        retTy = returnType;
        entryBlock = new BasicBlock("entry");
        exitBlock = new BasicBlock("exit");
        blocks.add(entryBlock);
        blocks.add(exitBlock);
    }
    public Function(String name,IRType returnType){
        this(name,returnType,false);
    }
    public Function addParam(Register reg){
        parameters.add(reg);
        return this;
    }
    public Function addParam(IRType ty,String name){
        parameters.add(new Register(ty,name));
        return this;
    }
    public String tell(){
        var builder=new StringBuilder();
        var argJoiner=new StringJoiner(",","(",")");
        parameters.forEach(p->argJoiner.add(p.type.tell()+" "+p.name));
        builder.append("define ").append(retTy.tell()).append(" @").append(name).append(argJoiner.toString()).append(" #0 {\n");
        blocks.forEach(b->builder.append(b.tell()));
        return builder.append("}\n").toString();
    }

    public boolean isBuiltin(){
        return isBuiltin;
    }
}
