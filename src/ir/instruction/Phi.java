package ir.instruction;

import ir.IRBlock;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Phi extends IRDestedInst {
    public class Source {
        public IROperand val;
        public IRBlock block;

        public Source(IROperand v, IRBlock b) {
            val = v;
            block = b;
        }

        public String tell() {
            return "[" + val + ", %" + block + "]";
        }
    }

    public ArrayList<Source> arguments = new ArrayList<>();

    public Phi(Register reg) {
        assert reg.type != null;
        dest = reg.copy("");
    }

    public Phi append(IROperand reg, IRBlock blk) {
        // internal phi cannot be overwritten by generated phi
        for (var s : arguments) {
            if (blk == s.block) return this;
        }
        arguments.add(new Source(reg, blk));
        return this;
    }

    public void replaceBlock(IRBlock newBlock, IRBlock oldBlock) {
        for (var ele : arguments) {
            if (ele.block == oldBlock) {
                ele.block = newBlock;
                return;
            }
        }
        assert false;
    }

    @Override
    public String tell() {
        StringJoiner sj = new StringJoiner(", ", dest + " = phi " + dest.type.tell() + " ", "");
        arguments.forEach(a -> sj.add(a.tell()));
        return sj.toString();
    }

    @Override
    public void renameOperand(Register reg) {
        throw new IllegalStateException();
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        throw new IllegalStateException();
    }

    @Override
    public void replaceRegisterWithOperand(IROperand operand, Register oldReg) {
        for (Source argument : arguments) {
            if (oldReg.sameIdentifier(argument.val))
                argument.val = operand;
        }
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        arguments.forEach(source -> {
            if (source.val instanceof Register)
                consumer.accept((Register) source.val);
        });
    }

    @Override
    public boolean hasSideEffect() {
        return false;
    }

    @Override
    public IRDestedInst copy(String arg) {
        throw new IllegalStateException();
    }

}
