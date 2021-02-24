package assembly.instruction;

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
        // do nothing
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        // do nothing
    }
}
