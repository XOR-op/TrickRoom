package assembly.instruction;

import assembly.operand.Imm;
import assembly.operand.RVRegister;
import assembly.operand.PhysicalRegister;

import java.util.function.Consumer;

public class LoadImm extends Computation{
    public LoadImm(RVRegister rd,int i) {
        super(rd, CompType.add, PhysicalRegister.Zero(), new Imm(i));
    }

    @Override
    public String tell() {
        return "addi "+rd.tell()+", zero, "+imm;
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        // do nothing
    }

    @Override
    public void replaceRegSrc(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }
}
