package ir.instruction;

import ir.BasicBlock;
import ir.operand.Register;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.Function;

public class Phi extends IRDestedInst {
    public class Source {
        public Register reg;
        public BasicBlock block;

        public Source(Register r, BasicBlock b) {
            reg = r;
            block = b;
        }

        public String tell() {
            return "[" + reg + ", %" + block + "]";
        }
    }

    public ArrayList<Source> arguments = new ArrayList<>();

    public Phi(Register reg) {
        assert reg.type != null;
        dest = reg.copy();
    }

    public void append(Register reg, BasicBlock blk) {
        assert reg.name.equals(dest.name);
        arguments.add(new Source(reg, blk));
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
}
