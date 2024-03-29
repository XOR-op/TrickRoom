package assembly.instruction;

import assembly.RVBlock;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class RVBranch extends ControlFlowInst {

    private RelaType rt;
    private RVRegister rs1, rs2;
    public RVBlock trueDest, falseDest;
    private boolean isUnsigned;

    public RVBranch(RelaType rt, boolean isUnsigned, RVRegister rs1, RVRegister rs2, RVBlock trueDest, RVBlock falseDest) {
        this.rt = rt;
        this.isUnsigned = isUnsigned;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
    }

    @Override
    public String tell() {
//        throw new UnimplementedError();
        return "b"+rt+(isUnsigned?"u":"")+" "+rs1.tell()+", "+rs2.tell()+", "+trueDest.getName()
                +"\n\t"+"j "+falseDest.getName();
    }

    public String onlyTrueTell(){
        return "b"+rt+(isUnsigned?"u":"")+" "+rs1.tell()+", "+rs2.tell()+", "+trueDest.getName();
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        consumer.accept(rs1);
        consumer.accept(rs2);
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        // do nothing
    }

    @Override
    public void replaceRegSrc(RVRegister newReg, RVRegister oldReg) {
        if(rs1==oldReg)rs1=newReg;
        if(rs2==oldReg)rs2=newReg;
    }

    @Override
    public void replaceRegDest(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }

    @Override
    public void replaceBlock(RVBlock newBlock, RVBlock oldBlock) {
        if(trueDest==oldBlock)trueDest=newBlock;
        if(falseDest==oldBlock)falseDest=newBlock;
    }
}
