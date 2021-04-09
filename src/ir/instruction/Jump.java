package ir.instruction;

import ir.IRBlock;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Consumer;
import java.util.function.Function;

public class Jump extends IRInst {

    // unconditional branch
    public IRBlock target;

    public Jump(IRBlock tgt) {
        target = tgt;
    }

    public IRBlock getTarget() {
        return target;
    }

    @Override
    public String tell() {
        return "br label %" + target.getBlockName();
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
    public void replaceRegisterWithOperand(IROperand operand, Register oldReg) {
        // do nothing
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        // do nothing
    }

    @Override
    public IRInst copy(String arg) {
        throw new IllegalStateException();
    }

    public IRInst copy(IRBlock newBlock) {
        return new Jump(newBlock);
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
