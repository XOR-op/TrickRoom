package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Consumer;
import java.util.function.Function;

public class Store extends IRInst {
    public IROperand address;
    public IROperand source;

    public Store(IROperand src, IROperand addr) {
        address = addr;
        source = src;
    }

    @Override
    public String tell() {
        return "store " + source.type + " " + source + ", " + address.type + " " + address;
    }

    @Override
    public void renameOperand(Register reg) {
        if (reg.sameNaming(address)) address = reg;
        if (reg.sameNaming(source)) source = reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        address = Register.replace(replace, address);
        source = Register.replace(replace, source);
    }

    @Override
    public void replaceRegisterWithOperand(IROperand operand, Register oldReg) {
        if (oldReg.sameIdentifier(address)) address = operand;
        if (oldReg.sameIdentifier(source)) source = operand;
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        if (source instanceof Register) consumer.accept((Register) source);
        if (address instanceof Register) consumer.accept((Register) address);
    }

    @Override
    public IRInst copy(String arg) {
        return new Store(source.copy(arg), address.copy(arg));
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
