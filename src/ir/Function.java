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
    public Function(String name,IRType returnType){
        this.name=name;
        retTy=returnType;
        entryBlock=new BasicBlock("entry");
        exitBlock=new BasicBlock("exit");
        blocks.add(entryBlock);
        blocks.add(exitBlock);
        returnValue=new Register(retTy);
        exitBlock.appendInst(new Ret(returnValue));
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
        parameters.forEach(p->argJoiner.add(p.type+" "+p.name));
        builder.append("define ").append(retTy).append('@').append(name).append(argJoiner.toString()).append(" #0 {\n");
        blocks.forEach(b->builder.append(b.tell()));
        return builder.append("}").toString();
    }

}
