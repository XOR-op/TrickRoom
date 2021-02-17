package ir.instruction;

import ir.BasicBlock;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.Function;

public class Phi extends IRDestedInst {
    public class Source {
        public IROperand val;
        public BasicBlock block;

        public Source(IROperand v, BasicBlock b) {
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
        dest = reg.copy();
    }

    public Phi append(IROperand reg, BasicBlock blk) {
        // internal phi cannot be overwritten by generated phi
        for(var s:arguments){
            if(blk==s.block)return this;
        }
        arguments.add(new Source(reg, blk));
        return this;
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
    public boolean hasSideEffect() {
        return false;
    }
}
