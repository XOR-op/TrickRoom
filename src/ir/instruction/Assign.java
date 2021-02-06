package ir.instruction;

import ir.Cst;
import ir.operand.IROperand;
import ir.operand.Register;
import ir.typesystem.PointerType;

public class Assign extends IRDestedInst{
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RESET = "\u001B[0m";
    public IROperand src;
    public Assign(Register dst, IROperand src){
        this.dest=dst;
        this.src=src;
    }

    @Override
    public String tell() {
//        return ANSI_CYAN+dest.type+" "+dest+ " = pseudo mv from "+src.type+" "+src+ANSI_RESET;
        if(dest.type.equals(Cst.int32)||dest.type.equals(Cst.bool))
            return dest+" = add "+dest.type+" "+src+", "+dest.type+" 0";
        else {
            assert dest.type instanceof PointerType;
            String s= Cst.TYPE_CAST+src+".1 = bitcast "+src.type+" "+src+" to i32\n";
            s+= Cst.TYPE_CAST+src+".2 = add i32 "+ Cst.TYPE_CAST+src+".1, i32 0\n";
            s+= dest+" = bitcast i32 "+ Cst.TYPE_CAST+src+".2 to "+dest.type;
            return s;
        }
    }
}
