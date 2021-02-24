package assembly.instruction;

import assembly.operand.Imm;
import assembly.operand.RVRegister;

import java.util.function.Consumer;

public class StoreData extends RVInst{
    private RVRegister rs1,rs2;
    private Imm imm;
    private WidthType wt;

    public StoreData(WidthType wt, RVRegister addr, RVRegister src, Imm offset){
        this.wt=wt;
        this.rs1=addr;
        this.rs2=src;
        this.imm=offset;
    }

    @Override
    public String tell() {
        return "s"+wt+" "+rs2+",("+imm+")"+rs1;
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
}
