package assembly.instruction;

import assembly.operand.Imm;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class Lui extends RVInst {

    private Imm imm;
    private RVRegister rd;


    public Lui(RVRegister rd, Imm imm) {
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String tell() {
        return "lui " + rd.tell() + ", " + imm.tell();
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        // do nothing
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        consumer.accept(rd);
    }

    @Override
    public void replaceRegSrc(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }

    @Override
    public void replaceRegDest(RVRegister newReg, RVRegister oldReg) {
        if (rd == oldReg) rd = newReg;
    }
}
