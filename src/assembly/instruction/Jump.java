package assembly.instruction;

import assembly.RVBlock;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class Jump extends ControlFlowInst {

    public RVBlock getDest() {
        return dest;
    }

    @Override
    public void replaceBlock(RVBlock newBlock, RVBlock oldBlock) {
        assert oldBlock==dest;
        dest=newBlock;
    }

    private RVBlock dest;

    public Jump(RVBlock dest) {
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

    @Override
    public void replaceRegSrc(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }

    @Override
    public void replaceRegDest(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }
}
