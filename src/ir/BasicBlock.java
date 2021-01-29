package ir;

import ir.instruction.IRInst;

import java.util.ArrayList;

public class BasicBlock {
    public String blockName;
    public ArrayList<BasicBlock> prevs=new ArrayList<>(),nexts=new ArrayList<>();
    public ArrayList<IRInst> insts=new ArrayList<>();

    public BasicBlock(String name){
        blockName=name;
    }

    public void appendInst(IRInst newInst){
        insts.add(newInst);
    }

    public BasicBlock appendBlock(String name){
        var bb=new BasicBlock(name);
        nexts.add(bb);
        bb.prevs.add(this);
        return bb;
    }
}
