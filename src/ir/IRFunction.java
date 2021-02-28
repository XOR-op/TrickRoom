package ir;

import ir.instruction.Alloca;
import ir.operand.Register;
import ir.typesystem.IRType;
import ir.typesystem.PointerType;

import java.util.*;

public class IRFunction {
    public String name;
    public IRType retTy;
    public ArrayList<BasicBlock> blocks = new ArrayList<>();
    public ArrayList<Register> parameters = new ArrayList<>();
    public Map<String, HashSet<BasicBlock>> varDefs = new HashMap<>();
    public Map<String, IRType> varType = new HashMap<>();
    public BasicBlock entryBlock, exitBlock;
    public ArrayList<BasicBlock> returnBlocks = new ArrayList<>();
    private final boolean isBuiltin;

    public IRFunction(String name, IRType returnType, boolean isBuiltin) {
        this.name = name;
        this.isBuiltin = isBuiltin;
        retTy = returnType;
        entryBlock = new BasicBlock("entry",0);
        exitBlock = new BasicBlock("exit",0);
        blocks.add(entryBlock);
    }

    public IRFunction(String name, IRType returnType) {
        this(name, returnType, false);
    }

    public IRFunction addParam(Register reg) {
        parameters.add(reg);
        varDefs.put(reg.name, new HashSet<>());
        varType.put(reg.name, reg.type);
        return this;
    }

    public void addReturn(BasicBlock bb) {
        returnBlocks.add(bb);
    }

    public IRFunction addParam(IRType ty, String name) {
        return addParam(new Register(ty, name));
    }

    public void defineVar(Register reg, BasicBlock bb) {
        // define and update Defs(var)
        bb.defVariable(reg);
        varDefs.get(reg.name).add(bb);
    }

    public void declareVar(Register reg) {
        var newSet = new HashSet<BasicBlock>();
        varDefs.put(reg.name, newSet);
        varType.put(reg.name, reg.type);
    }

    public IRFunction addBlock(BasicBlock bl) {
        blocks.add(bl);
        return this;
    }

    public void addAlloca(Register reg) {
        assert reg.type instanceof PointerType;
        declareVar(reg);
        entryBlock.insertInstFromHead(new Alloca(reg, ((PointerType) reg.type).subType()));
        defineVar(reg, entryBlock);
    }

    public String toDeclaration() {
        var builder = new StringBuilder();
        var argJoiner = new StringJoiner(",", "(", ")");
        parameters.forEach(p -> argJoiner.add(p.type.tell()));
        builder.append("declare ").append(retTy.tell()).append(" @").append(name).append(argJoiner.toString()).append(" \n");
        return builder.toString();
    }

    public String tell() {
        var builder = new StringBuilder();
        var argJoiner = new StringJoiner(",", "(", ")");
        parameters.forEach(p -> argJoiner.add(p.type.tell() + " " + p.tell()));
        builder.append("define ").append(retTy.tell()).append(" @").append(name).append(argJoiner.toString()).append(" #0 {\n");
        blocks.forEach(b -> builder.append(b.tell()));
        return builder.append("}\n").toString();
    }

    public boolean isBuiltin() {
        return isBuiltin;
    }


}
