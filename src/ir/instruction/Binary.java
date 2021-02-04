package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

public class Binary extends IRDestedInst{
    public enum BinInstEnum {add,sub,mul,sdiv,srem,shl,ashr,and,or,xor}
    public BinInstEnum inst;
    public IROperand operand1,operand2;

    @Override
    public String tell() {
        return dest+" = "+inst+" "+dest.type+' '+operand1+", "+operand2;
    }

    public Binary(BinInstEnum inst,Register dest,IROperand op1,IROperand op2){
        this.inst=inst;
        this.dest=dest;
        operand1=op1;
        operand2=op2;
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
            case "%" ->{
                return BinInstEnum.srem;
            }
            case "<<" -> {
                return Binary.BinInstEnum.shl;
            }
            case ">>" -> {
                return Binary.BinInstEnum.ashr;
            }
            case "&", "&&" -> {
                return Binary.BinInstEnum.and;
            }
            case "|", "||" -> {
                return Binary.BinInstEnum.or;
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
