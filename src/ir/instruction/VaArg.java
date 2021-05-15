package ir.instruction;

import ir.IRFunction;
import ir.operand.Register;

import java.util.StringJoiner;

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
        StringJoiner sb=new StringJoiner(",","(",")");
        for(var a:args)sb.add(a.tell());
        return "NSI: va_arg "+sb;
//        throw new UnsupportedOperationException("Non-standard llvm instruction");
    }

    @Override
    public IRDestedInst copy(String arg) {
        var newCall = new VaArg(dest != null ? dest.copy(arg) : null, function);
        args.forEach(a -> newCall.push(a.copy(arg)));
        return newCall;
    }

}
