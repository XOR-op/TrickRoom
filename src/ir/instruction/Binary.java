package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

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
            case "+" -> {
                return Binary.BinInstEnum.add;
            }
            case "-" -> {
                return Binary.BinInstEnum.sub;
            }
            case "*" -> {
                return Binary.BinInstEnum.mul;
            }
            case "/" -> {
                return Binary.BinInstEnum.sdiv;
            }
            case "%" -> {
                return BinInstEnum.srem;
            }
            case "<<" -> {
                return Binary.BinInstEnum.shl;
            }
            case ">>" -> {
                return Binary.BinInstEnum.ashr;
            }
            case "&" -> {
                return Binary.BinInstEnum.and;
            }
            case "&&" -> {
                return BinInstEnum.logic_and;
            }
            case "|" -> {
                return Binary.BinInstEnum.or;
            }
            case "||" -> {
                return BinInstEnum.logic_or;
            }
            case "^" -> {
                return Binary.BinInstEnum.xor;
            }
            default -> {
                // compare
                return null;
            }
        }
    }
}
