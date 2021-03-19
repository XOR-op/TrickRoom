package ir.instruction;

import ir.operand.*;
import misc.Cst;
import misc.DividedByZero;

import java.util.function.Consumer;
import java.util.function.Function;

public class Binary extends IRDestedInst {

    public enum BinInstEnum {add, sub, mul, sdiv, srem, shl, ashr, and, or, xor, logic_and, logic_or}

    public BinInstEnum inst;
    public IROperand operand1, operand2;

    @Override
    public String tell() {
        return dest + " = " + inst + " " + dest.type + ' ' + operand1 + ", " + operand2;
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

    public Binary(BinInstEnum inst, Register dest, IROperand op1, IROperand op2) {
        this.inst = inst;
        this.dest = dest;
        operand1 = op1;
        operand2 = op2;
    }

    public static Binary.BinInstEnum getIntBinOpEnum(String s) {
        switch (s) {
            case Cst.ADD -> {
                return Binary.BinInstEnum.add;
            }
            case Cst.MINUS -> {
                return Binary.BinInstEnum.sub;
            }
            case Cst.MUL -> {
                return Binary.BinInstEnum.mul;
            }
            case Cst.DIV -> {
                return Binary.BinInstEnum.sdiv;
            }
            case Cst.MOD -> {
                return BinInstEnum.srem;
            }
            case Cst.LEFT_SHIFT -> {
                return Binary.BinInstEnum.shl;
            }
            case Cst.RIGHT_SHIFT -> {
                return Binary.BinInstEnum.ashr;
            }
            case Cst.AND_ARI -> {
                return Binary.BinInstEnum.and;
            }
            case Cst.AND_LOGIC -> {
                return BinInstEnum.logic_and;
            }
            case Cst.OR_ARI -> {
                return Binary.BinInstEnum.or;
            }
            case Cst.OR_LOGIC -> {
                return BinInstEnum.logic_or;
            }
            case Cst.XOR_ARI -> {
                return Binary.BinInstEnum.xor;
            }
            default -> {
                // compare
                return null;
            }
        }
    }

    public static IRConstant evaluateConstant(BinInstEnum op, IRConstant val1, IRConstant val2) throws DividedByZero {
        if (val1 instanceof IntConstant) {
            int value1 = ((IntConstant) val1).value, value2 = ((IntConstant) val2).value;
            int result;
            switch (op) {
                case add -> result = value1 + value2;
                case sub -> result = value1 - value2;
                case mul -> result = value1 * value2;
                case sdiv -> {
                    if (value2 == 0) throw new DividedByZero();
                    result = value1 / value2;
                }
                case srem -> result = value1 % value2;
                case and -> result = value1 & value2;
                case or -> result = value1 | value2;
                case xor -> result = value1 ^ value2;
                case shl -> result = value1 << value2;
                case ashr -> result = value1 >> value2;
                default -> throw new IllegalStateException();
            }
            return new IntConstant(result);
        } else {
            boolean value1 = ((BoolConstant) val1).value, value2 = ((BoolConstant) val2).value;
            boolean result;
            switch (op) {
                case and -> result = value1 & value2;
                case or -> result = value1 | value2;
                case xor -> result = value1 ^ value2;
                default -> throw new IllegalStateException();
            }
            return new BoolConstant(result);
        }
    }

    @Override
    public IRDestedInst copy(String arg) {
        return new Binary(inst, dest.copy(arg), operand1.copy(arg), operand2.copy(arg));
    }
}
