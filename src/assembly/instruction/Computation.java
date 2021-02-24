package assembly.instruction;

import assembly.operand.Imm;
import assembly.operand.RVRegister;
import assembly.operand.PhysicalRegister;
import assembly.operand.VirtualRegister;
import ir.instruction.Binary;
import ir.operand.IntConstant;

import java.util.function.Consumer;

public class Computation extends RVInst{
    @Override
    public String tell() {
        return ct+(imm==null?" ":"i ")+rd+","+rs1+","+(imm!=null?imm:rs2);
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        consumer.accept(rs1);
        if(rs2!=null)
            consumer.accept(rs2);
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        consumer.accept(rd);
    }

    public enum CompType {
        add, sub, mul, div, rem, slt, xor, or, and, sll, srl, sra
    }
    public RVRegister rd,rs1,rs2;
    public Imm imm;
    public CompType ct;

    protected Computation(){}

    private Computation(RVRegister rd, CompType type, RVRegister rs1, RVRegister rs2, Imm imm){
        this.ct=type;
        this.rd=rd;
        this.rs1=rs1;
        this.rs2=rs2;
        this.imm=imm;
    }

    public Computation(RVRegister rd, CompType type, RVRegister rs1, RVRegister rs2){
        this(rd,type,rs1,rs2,null);
    }

    public Computation(RVRegister rd, CompType type, RVRegister rs1, Imm imm){
        this(rd,type,rs1,null,imm);
    }

    public static CompType getCompType(Binary.BinInstEnum binEnum){
        Computation.CompType ct;
        switch (binEnum){
            case add->ct= Computation.CompType.add;
            case sub->ct= Computation.CompType.sub;
            case mul->ct= Computation.CompType.mul;
            case sdiv->ct= Computation.CompType.div;
            case srem->ct= Computation.CompType.rem;
            case shl->ct= Computation.CompType.sll;
            case ashr->ct= Computation.CompType.sra;
            case and->ct= Computation.CompType.and;
            case or->ct= Computation.CompType.or;
            case xor->ct= Computation.CompType.xor;
            default -> throw new IllegalStateException();
        }
        return ct;
    }

}
