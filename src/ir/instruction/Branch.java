package ir.instruction;

import ir.BasicBlock;
import ir.operand.IROperand;

public class Branch extends IRInst{
    private BasicBlock trueBranch,falseBranch;
    private IROperand condition;
    public Branch(IROperand cond,BasicBlock tb,BasicBlock fb){
        condition=cond;
        trueBranch=tb;
        falseBranch=fb;
    }

    @Override
    public String tell() {
        return "br i1 "+condition+", label "+ trueBranch.getBlockName()+", label "+falseBranch.getBlockName();
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
