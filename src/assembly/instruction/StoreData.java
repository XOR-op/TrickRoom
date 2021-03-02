package assembly.instruction;

import assembly.operand.Imm;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class StoreData extends RVInst {
    private RVRegister rs1, rs2;
    private Imm imm;
    private WidthType wt;

    public StoreData(WidthType wt, RVRegister addr, RVRegister src, Imm offset) {
        this.wt = wt;
        this.rs1 = addr;
        this.rs2 = src;
        this.imm = offset;
    }

    @Override
    public String tell() {
        return "s" + wt + " " + rs2.tell() + "," + imm + "(" + rs1.tell() + ")";
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        consumer.accept(rs1);
        consumer.accept(rs2);
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        // do nothing
    }

    @Override
    public void replaceRegSrc(RVRegister newReg, RVRegister oldReg) {
        if (rs1 == oldReg) rs1 = newReg;
        if (rs2 == oldReg) rs2 = newReg;
    }

    @Override
    public void replaceRegDest(RVRegister newReg, RVRegister oldReg) {
        // do nothing
    }
}
