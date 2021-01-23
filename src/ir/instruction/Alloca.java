package ir.instruction;

import ir.operand.IROperand;
import ir.typesystem.IRType;

public class Alloca extends IRInst{
    private int align;
    private IRType type;
    private int size;
    private IROperand dest;
    public Alloca(IROperand dst,IRType ty, int sz, int alignment){
        align =alignment;
        dest=dst;
        type=ty;
        size=sz;
    }
    public Alloca(IROperand dest,IRType ty,int sz){
        this(dest,ty,sz,0);
    }
    public Alloca(IROperand dest,IRType ty){
        this(dest,ty,1);
    }

    @Override
    public String tell() {
        return dest+" = alloca "+type+(size==1?"":(", "+type+" "+size))+(align ==0?"":", align "+align);
    }
}
