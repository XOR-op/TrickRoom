package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;

public class Compare extends IRInst{
    public enum CmpEnum{eq,ne,sgt,sge,slt,sle}
    public IROperand operand1,operand2;
    public Register dest;
    public CmpEnum type;
    public Compare(CmpEnum inst,Register dst,IROperand op1,IROperand op2){
        dest=dst;
        type=inst;
        operand1=op1;
        operand2=op2;
    }
    @Override
    public String tell() {
        return dest+" = "+type+' '+operand1.type+' '+operand1+", "+operand2;
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
