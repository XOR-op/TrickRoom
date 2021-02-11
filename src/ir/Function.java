package ir;

import ir.operand.Parameter;
import ir.operand.Register;
import ir.typesystem.IRType;

import java.util.*;

public class Function {
    public String name;
    public IRType retTy;
    public ArrayList<BasicBlock> blocks = new ArrayList<>();
    public ArrayList<Register> parameters = new ArrayList<>();
    public Map<String,HashSet<BasicBlock>> definedVariables =new HashMap<>();
    public Map<String,IRType> varType=new HashMap<>();
    public BasicBlock entryBlock, exitBlock;
    public Register returnValue;
    private boolean isBuiltin;

    public Function(String name, IRType returnType, boolean isBuiltin) {
        this.name = name;
        this.isBuiltin = isBuiltin;
        retTy = returnType;
        entryBlock = new BasicBlock("entry");
        exitBlock = new BasicBlock("exit");
        blocks.add(entryBlock);
    }

    public Function(String name, IRType returnType) {
        this(name, returnType, false);
    }

    public Function addParam(Register reg) {
        parameters.add(reg);
        definedVariables.put(reg.name,new HashSet<>());
        varType.put(reg.name,reg.type);
        return this;
    }

    public Function addParam(IRType ty, String name) {
        return addParam(new Register(ty,name));
    }

    public void defVariable(Register reg,BasicBlock bb){
        // define and update Defs(var)
        bb.defVariable(reg);
        if(definedVariables.containsKey(reg)){
            definedVariables.get(reg).add(bb);
        }else {
            var newSet=new HashSet<BasicBlock>();
            newSet.add(bb);
            definedVariables.put(reg.name,newSet);
        }
    }

    public void done(){
        blocks.add(exitBlock);
    }

    public Function addBlock(BasicBlock bl){
        blocks.add(bl);
        return this;
    }

    public String toDeclaration(){
        var builder = new StringBuilder();
        var argJoiner = new StringJoiner(",", "(", ")");
        parameters.forEach(p -> argJoiner.add(p.type.tell()));
        builder.append("declare ").append(retTy.tell()).append(" @").append(name).append(argJoiner.toString()).append(" \n");
        return builder.toString();
    }

    public String tell() {
        var builder = new StringBuilder();
        var argJoiner = new StringJoiner(",", "(", ")");
        parameters.forEach(p -> argJoiner.add(p.type.tell() + " " + p.name));
        builder.append("define ").append(retTy.tell()).append(" @").append(name).append(argJoiner.toString()).append(" #0 {\n");
        blocks.forEach(b -> builder.append(b.tell()));
        return builder.append("}\n").toString();
    }

    public boolean isBuiltin() {
        return isBuiltin;
    }


}
