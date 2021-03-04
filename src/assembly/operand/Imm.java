package assembly.operand;

public class Imm extends RVOperand{

    private final int val;

    public Imm(int val){
        this.val=val;
    }

    public int getVal(){
        return val;
    }

    public boolean isRealVal(){return true;}

    @Override
    public String tell() {
        return Integer.toString(val);
    }
}
