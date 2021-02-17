package ir.typesystem;

import ir.operand.IROperand;
import ir.operand.NullptrConstant;

public class PointerType extends IRType {
    public IRType baseType;
    private int dim;

    public PointerType(IRType base) {
        assert base != null;
        if (base instanceof PointerType) {
            baseType = ((PointerType) base).baseType;
            dim = ((PointerType) base).dim + 1;
        } else {
            baseType = base;
            dim = 1;
        }
    }

    public PointerType(IRType base, int dim) {
        assert !(base instanceof PointerType);
        baseType = base;
        this.dim = dim;
    }

    public IRType subType() {
        return dim == 1 ? baseType : new PointerType(baseType, dim - 1);
    }

    @Override
    public int size() {
        // for debug purpose, you may modify this
        return 8;
    }

    @Override
    public String tell() {
        return baseType + "*".repeat(dim);
    }

    @Override
    public IROperand defaultValue() {
        return new NullptrConstant(new PointerType(baseType));
    }

}
