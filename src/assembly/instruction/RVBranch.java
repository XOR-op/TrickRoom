package assembly.instruction;

import assembly.AsmBlock;
import assembly.operand.RVRegister;
import exception.UnimplementedError;

import java.util.function.Consumer;

public class RVBranch extends RVInst {

    private RelaType rt;
    private RVRegister rs1, rs2;
    private AsmBlock trueDest, falseDest;
    private boolean isUnsigned;

    public RVBranch(RelaType rt, boolean isUnsigned, RVRegister rs1, RVRegister rs2, AsmBlock trueDest, AsmBlock falseDest) {
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
        return "b"+rt+(isUnsigned?"u":"")+" "+rs1.tell()+", "+rs2.tell()+", "+trueDest.getName()+" ;else "+falseDest.getName();
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
}
