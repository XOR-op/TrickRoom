package ir.instruction;

import ir.IRFunction;
import ir.IRInfo;
import ir.operand.IROperand;
import ir.operand.Register;
import misc.Cst;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class Call extends IRDestedInst {
    public IRFunction function;
    public ArrayList<IROperand> args;

    public Call(Register dst, IRFunction func) {
        assert func != null;
        function = func;
        args = new ArrayList<>();
        dest = dst;
    }

    public Call push(IROperand irOperand) {
        args.add(irOperand);
        return this;
    }

    public boolean isMalloc() {
        return function.name.equals(Cst.globalPrefix(Cst.MALLOC));
    }

    @Override
    public String tell() {
        StringJoiner sj = new StringJoiner(", ", "(", ")");
        args.forEach(arg -> sj.add(arg.type.tell() + " " + arg.tell()));
        return (dest == null ? "" : (dest + " = ")) + "call " + function.retTy + " @" + function.name + sj.toString();
    }

    @Override
    public void renameOperand(Register reg) {
        for (int i = 0; i < args.size(); ++i) {
            if (reg.sameNaming(args.get(i)))
                args.set(i, reg);
        }
    }

    @Override
    public void renameOperand(java.util.function.Function<Register, Register> replace) {
        for (int i = 0; i < args.size(); ++i) {
            args.set(i, Register.replace(replace, args.get(i)));
        }
    }

    @Override
    public void replaceRegisterWithOperand(IROperand operand, Register oldReg) {
        for (int i = 0; i < args.size(); ++i) {
            if (oldReg.sameIdentifier(args.get(i)))
                args.set(i, operand);
        }
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        args.forEach(arg -> {
            if (arg instanceof Register) consumer.accept((Register) arg);
        });
    }

    @Override
    public void forEachRegDest(Consumer<Register> consumer) {
        if (dest != null) consumer.accept(dest);
    }

    @Override
    public IRDestedInst copy(String arg) {
        var newCall = new Call(dest != null ? dest.copy(arg) : null, function);
        args.forEach(a -> newCall.push(a.copy(arg)));
        return newCall;
    }

    @Override
    public boolean containsDest() {
        return dest != null;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
