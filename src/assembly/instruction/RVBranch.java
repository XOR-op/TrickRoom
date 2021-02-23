package assembly.instruction;

import assembly.AsmBlock;
import assembly.operand.RVRegister;
import exception.UnimplementedError;

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
        throw new UnimplementedError();
//        return "b"+rt+(isUnsigned?"u":"")+" "+rs1+", "+rs2+", "+trueDest;
    }
}
