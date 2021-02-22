package assembly.instruction;

import assembly.operand.Imm;
import assembly.operand.RVRegister;
import assembly.operand.PhysicalRegister;

public class LoadImm extends Computation{
    public LoadImm(RVRegister rd,int i) {
        super(rd, CompType.add, PhysicalRegister.Zero(), new Imm(i));
    }

    @Override
    public String tell() {
        return "la "+imm;
    }
}
