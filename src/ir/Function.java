package ir;

import ir.operand.Parameter;
import ir.operand.Register;
import ir.typesystem.IRType;

import java.util.*;

public class Function {
    public String name;
    public IRType retTy;
    public ArrayList<BasicBlock> blocks = new ArrayList<>();
    public ArrayList<Parameter> parameters = new ArrayList<>();
    public Map<Register,HashSet<BasicBlock>> definedVariables =new HashMap<>();
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

    public Function addParam(Parameter reg) {
        parameters.add(reg);
        return this;
    }

    public Function addParam(IRType ty, String name) {
        parameters.add(new Parameter(ty, name));
        return this;
    }

    public void defVariable(Register reg,BasicBlock bb){
        // define and update Defs(var)
        bb.defVariable(reg);
        if(definedVariables.containsKey(reg)){
            definedVariables.get(reg).add(bb);
        }else {
            var newSet=new HashSet<BasicBlock>();
            newSet.add(bb);
            definedVariables.put(reg,newSet);
        }
    }

    public void done(){
        blocks.add(exitBlock);
    }

    public Function addBlock(BasicBlock bl){
        blocks.add(bl);
        return this;
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
