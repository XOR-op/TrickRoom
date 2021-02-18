package riscv.operand;

public class Imm extends RVOperand{

    private int val;

    public Imm(int val){
        this.val=val;
    }

    @Override
    public String tell() {
        return Integer.toString(val);
    }
}
