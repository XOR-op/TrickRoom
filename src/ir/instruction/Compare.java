package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

import java.util.function.Function;

public class Compare extends IRDestedInst{
    public enum CmpEnum{eq,ne,sgt,sge,slt,sle}
    public IROperand operand1,operand2;
    public CmpEnum type;
    public Compare(CmpEnum inst,Register dst,IROperand op1,IROperand op2){
        dest=dst;
        type=inst;
        operand1=op1;
        operand2=op2;
    }
    @Override
    public String tell() {
        return dest+" = icmp "+type+' '+operand1.type+' '+operand1+", "+operand2;
    }

    @Override
    public void renameOperand(Register reg) {
        if(reg.sameNaming(operand1))operand1=reg;
        if(reg.sameNaming(operand2))operand2=reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        operand1=Register.replace(replace,operand1);
        operand2=Register.replace(replace,operand2);
    }

    public static CmpEnum getCmpOpEnum(String s){
        switch (s){
            case "=="->{return CmpEnum.eq;}
            case "!="->{return CmpEnum.ne;}
            case "<"->{return CmpEnum.slt;}
            case "<="->{return CmpEnum.sle;}
            case ">"->{return CmpEnum.sgt;}
            case ">="->{return CmpEnum.sge;}
            default -> {return null;}
        }
    }
}
