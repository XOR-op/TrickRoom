package assembly.operand;

import ir.operand.GlobalVar;
import ir.operand.IROperand;
import ir.operand.StringConstant;

public class AddrImm extends Imm {
    private Part part;
    private String name;

    public enum Part {hi, lo}

    public AddrImm(Part part, IROperand var){
        super(0);
        assert (var instanceof StringConstant)||(var instanceof GlobalVar);
        this.part=part;
        if(var instanceof StringConstant)name= ((StringConstant) var).name;
        else name= ((GlobalVar) var).getName();
    }

    @Override
    public int getVal() {
        throw new IllegalStateException();
    }

    @Override
    public boolean isRealVal() {
        return false;
    }

    @Override
    public String tell() {
        return "%" + part + "(" + name + ")";
    }
}
