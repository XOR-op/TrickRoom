package ir.operand;

public abstract class IRConstant extends IROperand{
    public abstract boolean sameConst(IRConstant rhs);
}
