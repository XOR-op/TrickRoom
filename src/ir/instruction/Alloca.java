package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;
import ir.typesystem.IRType;

public class Alloca extends IRDestedInst{
    private int align;
    private IRType type;
    private int size;
    public Alloca(Register dst,IRType ty, int sz, int alignment){
        align =alignment;
        dest=dst;
        type=ty;
        size=sz;
    }
    public Alloca(Register dest,IRType ty,int sz){
        this(dest,ty,sz,0);
    }
    public Alloca(Register dest,IRType ty){
        this(dest,ty,1);
    }

    @Override
    public String tell() {
        return dest+" = alloca "+type+(size==1?"":(", "+type+" "+size))+(align ==0?"":", align "+align);
    }
}
