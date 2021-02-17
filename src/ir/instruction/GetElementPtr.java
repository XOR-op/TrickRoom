package ir.instruction;

import ir.operand.IROperand;
import ir.operand.Register;
import ir.typesystem.PointerType;

import java.util.function.Function;

public class GetElementPtr extends IRDestedInst{
    public IROperand base;
    public IROperand indexing;
    public IROperand offset;
    public GetElementPtr(Register dst,IROperand src,IROperand index, IROperand off){
        dest=dst;
        base=src;
        indexing=index;
        offset=off;
    }

    public GetElementPtr(Register dst,IROperand src,IROperand index){
        this(dst,src,index,null);
    }
    @Override
    public String tell() {
        return dest+" = getelementptr inbounds "+((PointerType)base.type).subType()+", "+base.type+" "+base+", "+indexing.type+" "+indexing+
                (offset==null?"":(", "+offset.type+" "+offset));
    }

    @Override
    public void renameOperand(Register reg) {
        if(reg.sameNaming(base))base=reg;
        if(reg.sameNaming(indexing))indexing=reg;
        if(offset!=null&&reg.sameNaming(offset))offset=reg;
    }

    @Override
    public void renameOperand(Function<Register, Register> replace) {
        base=Register.replace(replace,base);
        indexing=Register.replace(replace,indexing);
        if(offset!=null)
            offset=Register.replace(replace,offset);
    }

    @Override
    public boolean hasSideEffect() {
        return false;
    }
}
