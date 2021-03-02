package assembly.instruction;

import assembly.AsmFunction;
import assembly.RVInfo;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class RVCall extends RVInst {

    private AsmFunction dest;

    public RVCall(AsmFunction func) {
        assert func!=null;
        dest = func;
    }

    @Override
    public String tell() {
        return "call " + dest.getName();
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        for (int i = 0; i < Integer.min(8, dest.parameterCount); ++i)
            consumer.accept(PhysicalRegister.get(i + 10));
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        RVInfo.getCallerSave().forEach(consumer);
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
