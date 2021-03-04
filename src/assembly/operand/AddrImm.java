package assembly.operand;

import ir.operand.GlobalVar;
import ir.operand.StringConstant;

public class AddrImm extends Imm {
    private Part part;
    private String name;

    public enum Part {hi, lo}

    public AddrImm(Part part, GlobalVar var) {
        super(0);
        this.part = part;
        name = var.name;
    }

    public AddrImm(Part part, StringConstant var) {
        super(0);
        this.part = part;
        name = var.name;
    }

    @Override
    public String tell() {
        return "%" + part + "(" + name + ")";
    }
}
