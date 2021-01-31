package ir;

import ir.instruction.IRInst;

import java.util.ArrayList;

public class BasicBlock {
    private String blockName;
    private ArrayList<BasicBlock> prevs=new ArrayList<>(),nexts=new ArrayList<>();
    private ArrayList<IRInst> insts=new ArrayList<>();
    private IRInst terminatorInst=null;

    public BasicBlock(String name){
        blockName=name;
    }

    public void appendInst(IRInst newInst){
        assert !hasTerminal();
        insts.add(newInst);
    }
    public BasicBlock split(String name){
        /*
         * split this basic block into two blocks
         * the prior contains all instructions of the original one, while the later contains nothing
         */
        var later=new BasicBlock(name);
        later.nexts=this.nexts;
        this.nexts=new ArrayList<>();
        this.nexts.add(later);
        later.prevs.add(this);
        return later;
    }

    public void setTerminator(IRInst terInst){
        assert terminatorInst==null&&terInst.isTerminal();
        terminatorInst=terInst;
    }

    public String getBlockName() {
        return blockName;
    }

    public boolean hasTerminal(){return terminatorInst!=null;}
}
