package assembly.instruction;

import assembly.operand.RVRegister;

import java.util.function.Consumer;

public abstract class RVInst {
    public enum RelaType {
        eq, ne, lt, le, gt, ge
    }

    public enum WidthType {
        b, h, w
    }

    public abstract String tell();

    public abstract void forEachRegSrc(Consumer<RVRegister> consumer);

    public abstract void forEachRegDest(Consumer<RVRegister> consumer);

    public abstract void replaceRegSrc(RVRegister newReg, RVRegister oldReg);

    public abstract void replaceRegDest(RVRegister newReg, RVRegister oldReg);
}
