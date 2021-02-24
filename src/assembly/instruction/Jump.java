package assembly.instruction;

import assembly.AsmBlock;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class Jump extends RVInst {

    private AsmBlock dest;

    public Jump(AsmBlock dest) {
        this.dest = dest;
    }

    @Override
    public String tell() {
        return "j " + dest.getName();
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
