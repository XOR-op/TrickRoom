package ir.instruction;

import ir.operand.*;

import java.util.function.Consumer;
import java.util.function.Function;

public class Compare extends IRDestedInst {

    public enum CmpEnum {eq, ne, sgt, sge, slt, sle}

    public IROperand operand1, operand2;
    public CmpEnum type;

    public Compare(CmpEnum inst, Register dst, IROperand op1, IROperand op2) {
        dest = dst;
        type = inst;
        operand1 = op1;
        operand2 = op2;
    }

    @Override
    public String tell() {
        return dest + " = icmp " + type + ' ' + operand1.type + ' ' + operand1 + ", " + operand2;
    }

    @Override
    public void renameOperand(Register reg) {
        if (reg.sameNaming(operand1)) operand1 = reg;
        if (reg.sameNaming(operand2)) operand2 = reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        operand1 = Register.replace(replace, operand1);
        operand2 = Register.replace(replace, operand2);
    }

    @Override
    public void replaceRegisterWithOperand(IROperand operand, Register oldReg) {
        if (oldReg.sameIdentifier(operand1)) operand1 = operand;
        if (oldReg.sameIdentifier(operand2)) operand2 = operand;
    }

    @Override
    public void forEachRegSrc(Consumer<Register> consumer) {
        if (operand1 instanceof Register) consumer.accept((Register) operand1);
        if (operand2 instanceof Register) consumer.accept((Register) operand2);
    }

    @Override
    public boolean hasSideEffect() {
        return false;
    }

    public static CmpEnum getCmpOpEnum(String s) {
        switch (s) {
            case "==" -> {
                return CmpEnum.eq;
            }
            case "!=" -> {
                return CmpEnum.ne;
            }
            case "<" -> {
                return CmpEnum.slt;
            }
            case "<=" -> {
                return CmpEnum.sle;
            }
            case ">" -> {
                return CmpEnum.sgt;
            }
            case ">=" -> {
                return CmpEnum.sge;
            }
            default -> {
                return null;
            }
        }
    }

    public static IRConstant evaluateConstant(CmpEnum op, IRConstant val1, IRConstant val2) {
        boolean val;
        if (val1 instanceof IntConstant) {
            int lhs = ((IntConstant) val1).value, rhs = ((IntConstant) val2).value;
            switch (op) {
                case eq -> val = lhs == rhs;
                case ne -> val = lhs != rhs;
                case sge -> val = lhs >= rhs;
                case sgt -> val = lhs > rhs;
                case sle -> val = lhs <= rhs;
                case slt -> val = lhs < rhs;
                default -> throw new IllegalStateException();
            }
            return new BoolConstant(val);
        } else if (val1 instanceof BoolConstant) {
            boolean lhs = ((BoolConstant) val1).value, rhs = ((BoolConstant) val2).value;
            switch (op) {
                case eq -> val = lhs == rhs;
                case ne -> val = lhs != rhs;
                default -> throw new IllegalStateException();
            }
            return new BoolConstant(val);
        } else {
            assert val1 instanceof NullptrConstant;
            return op == CmpEnum.eq ? new BoolConstant(true) : new BoolConstant(false);
        }
    }

    @Override
    public IRDestedInst copy(String arg) {
        return new Compare(type, dest.copy(arg), operand1.copy(arg), operand2.copy(arg));
    }
}
