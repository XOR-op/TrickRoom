package ir.instruction;

import ir.IRBlock;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Consumer;
import java.util.function.Function;

public class Branch extends IRInst {
    public IRBlock trueBranch, falseBranch;
    public IROperand condition;

    public Branch(IROperand cond, IRBlock tb, IRBlock fb) {
        condition = cond;
        trueBranch = tb;
        falseBranch = fb;
    }

    public void replaceBranch(IRBlock from, IRBlock to) {
        if (trueBranch == from) trueBranch = to;
        if (falseBranch == from) falseBranch = to;
    }

    @Override
    public String tell() {
        return "br i1 " + condition + ", label %" + trueBranch.getBlockName() + ", label %" + falseBranch.getBlockName();
    }

    @Override
    public void renameOperand(Register reg) {
        if (reg.sameNaming(condition)) condition = reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        condition = Register.replace(replace, condition);
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        if (condition instanceof Register) consumer.accept((Register) condition);
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
