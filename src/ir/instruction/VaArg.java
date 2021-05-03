package ir.instruction;

import ir.IRFunction;
import ir.operand.Register;

public class VaArg extends Call {
    public VaArg(Register dst, IRFunction func) {
        super(dst, func);
    }

    @Override
    public boolean isMalloc() {
        return false;
    }

    @Override
    public String tell() {
        throw new UnsupportedOperationException("Non-standard llvm instruction");
    }

    @Override
    public IRDestedInst copy(String arg) {
        var newCall = new VaArg(dest != null ? dest.copy(arg) : null, function);
        args.forEach(a -> newCall.push(a.copy(arg)));
        return newCall;
    }

}
