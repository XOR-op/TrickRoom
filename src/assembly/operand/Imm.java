package assembly.operand;

public class Imm extends RVOperand{

    public final int val;

    public Imm(int val){
        this.val=val;
    }

    @Override
    public String tell() {
        return Integer.toString(val);
    }
}
