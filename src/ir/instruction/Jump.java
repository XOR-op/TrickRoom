package ir.instruction;

import ir.BasicBlock;

public class Jump extends IRInst{
    // unconditional branch
    private BasicBlock target;
    public Jump(BasicBlock tgt){
        target=tgt;
    }

    @Override
    public String tell() {
        return "br label "+target.getBlockName();
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
