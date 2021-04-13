package assembly.instruction;

import assembly.RVInfo;
import assembly.operand.AddrImm;
import assembly.operand.Imm;
import assembly.operand.RVRegister;
import assembly.operand.VirtualImm;

import java.util.function.Consumer;

public class LoadMem extends RVInst {

    public RVRegister rd, rs1;
    public Imm imm;
    public WidthType wt;

    public LoadMem(RVRegister rd, WidthType wt, RVRegister rs1, Imm imm) {
        this.rd = rd;
        this.wt = wt;
        this.rs1 = rs1;
        this.imm = imm;
    }

    public RVRegister getRd() {
        return rd;
    }

    public void replaceImm(int stackLength) {
        if (imm instanceof VirtualImm) {
            imm = new Imm(((VirtualImm) imm).offset + stackLength);
        }
    }

    @Override
    public String tell() {
        if (imm instanceof AddrImm || RVInfo.isShortImm(imm.getVal()))
            return "l" + wt + " " + rd.tell() + "," + imm.tell() + "(" + rs1.tell() + ")";
        else {
            return "li " + rd.tell() + "," + imm.tell() + "\n\t" +
                    "add " + rd.tell() + " " + rd.tell() + "," + rs1.tell() + "\n\t" +
                    "l" + wt + " " + rd.tell() + "," + "0(" + rd.tell() + ")";
        }
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        consumer.accept(rs1);
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        consumer.accept(rd);
    }

    @Override
    public void replaceRegSrc(RVRegister newReg, RVRegister oldReg) {
        if (rs1 == oldReg) rs1 = newReg;
    }

    @Override
    public void replaceRegDest(RVRegister newReg, RVRegister oldReg) {
        if (rd == oldReg) rd = newReg;
    }
}
