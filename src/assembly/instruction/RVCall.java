package assembly.instruction;

import assembly.AsmFunction;
import assembly.RVInfo;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class RVCall extends RVInst {

    private AsmFunction dest;

    public RVCall(AsmFunction func) {
        dest = func;
    }

    @Override
    public String tell() {
        return "call " + dest;
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
}
