package assembly.operand;

public class PhysicalRegister extends RVRegister {
    private int numbering;
    public PhysicalRegister(int numbering){
        this.numbering=numbering;
    }

    @Override
    public String tell() {
        return "x"+numbering;
    }

    public static PhysicalRegister Zero(){
        return new PhysicalRegister(0);
    }
}
