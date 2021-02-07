package ir.instruction;

import ir.BasicBlock;
import ir.operand.Register;

import java.util.function.Function;

public class Jump extends IRInst{
    // unconditional branch
    private BasicBlock target;
    public Jump(BasicBlock tgt){
        target=tgt;
    }

    @Override
    public String tell() {
        return "br label %"+target.getBlockName();
    }

    @Override
    public void renameOperand(Register reg) {
        // do nothing
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        // do nothing
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
