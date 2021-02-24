package assembly.instruction;

import assembly.operand.Imm;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class LoadData extends RVInst{

    private RVRegister rd,rs1;
    private Imm imm;
    private WidthType wt;

    public LoadData(RVRegister rd, WidthType wt, RVRegister rs1, Imm imm){
        this.rd=rd;
        this.wt=wt;
        this.rs1=rs1;
        this.imm=imm;
    }

    @Override
    public String tell() {
        return "l"+wt+" "+rd+",("+imm+")"+rs1;
    }

    @Override
    public void forEachRegSrc(Consumer<RVRegister> consumer) {
        consumer.accept(rs1);
    }

    @Override
    public void forEachRegDest(Consumer<RVRegister> consumer) {
        consumer.accept(rd);
    }
}
