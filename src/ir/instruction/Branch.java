package ir.instruction;

import ir.BasicBlock;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Function;

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
        return "br i1 "+condition+", label %"+ trueBranch.getBlockName()+", label %"+falseBranch.getBlockName();
    }

    @Override
    public void renameOperand(Register reg) {
        if(reg.sameNaming(condition)) condition=reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        condition=Register.replace(replace,condition);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
