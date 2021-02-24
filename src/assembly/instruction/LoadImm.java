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
        return "la "+imm;
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        // do nothing
    }
}
