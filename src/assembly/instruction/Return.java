package assembly.instruction;

import assembly.RVInfo;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class Return extends RVInst {
    public Return() {
        // do nothing
    }

    @Override
    public String tell() {
        return "jr ra";
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        RVInfo.getCalleeSave().forEach(consumer::accept);
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        // do nothing
    }

    @Override
    public void replaceRegSrc(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }

    @Override
    public void replaceRegDest(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }
}
