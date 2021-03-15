package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Consumer;
import java.util.function.Function;

public class Ret extends IRInst {
    public IROperand value;

    public Ret(IROperand val) {
        assert val != null;
        value = val;
    }

    private Ret() {
        value = null;
    }

    public static Ret voidRet() {
        return new Ret();
    }

    @Override
    public String tell() {
        return "ret " + (value == null ? "void" : (value.type + " " + value));
    }

    @Override
    public void renameOperand(Register reg) {
        if (reg.sameNaming(value)) value = reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        value = Register.replace(replace, value);
    }

    @Override
    public void replaceRegisterWithOperand(IROperand operand, Register oldReg) {
        if (value != null && oldReg.sameIdentifier(value)) value = operand;
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        if (value != null && value instanceof Register) consumer.accept((Register) value);
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
