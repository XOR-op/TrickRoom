package ir.typesystem;

import ir.operand.IROperand;
import ir.operand.NullptrConstant;
import misc.Cst;

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
        // verify llvm-IR using llc on 64-bit machine
        return Cst.pointerSize;
    }

    @Override
    public String tell() {
        return baseType + "*".repeat(dim);
    }

    @Override
    public IROperand defaultValue() {
        return new NullptrConstant(new PointerType(baseType));
    }

    @Override
    public boolean matches(IRType rhs) {
        return rhs instanceof PointerType && baseType.matches(((PointerType) rhs).baseType) && dim == ((PointerType) rhs).dim;
    }

}
