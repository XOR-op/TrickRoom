package ir.operand;

import ir.Cst;
import ir.typesystem.IRType;
import ir.typesystem.PointerType;

public class GlobalVar extends Register {
    public IROperand initValue;
    private Integer strLength = null;

    public GlobalVar(IRType ty, String name, IROperand init) {
        super(ty, name);
        initValue = init;
    }

    public GlobalVar(IRType ty, String name) {
        this(ty, name, ty.defaultValue());
    }

    public static GlobalVar toStaticString(String name, int length) {
        var g = new GlobalVar(Cst.str, name);
        g.strLength = length;
        return g;
    }

    @Override
    public String tell() {
        if (strLength == null)
            return "@" + name;
        else
            return "getelementptr inbounds ([" + strLength + " x i8], [" + strLength + " x i8]* @" + name + ", i32 0,i32 0)";
    }
}
