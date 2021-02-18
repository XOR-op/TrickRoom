package riscv.operand;

public class Register extends RVOperand{

    private final int numbering;

    public Register(int numbering){
        assert numbering>=0&&numbering<=31;
        this.numbering=numbering;
    }

    @Override
    public String tell() {
        return "x"+numbering;
    }
}
