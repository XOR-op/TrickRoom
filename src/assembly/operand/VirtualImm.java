package assembly.operand;

public class VirtualImm extends Imm {

    public int offset;

    public VirtualImm(int offsetBeforeSP) {
        super(0);
        offset = offsetBeforeSP;
    }

    @Override
    public String tell() {
        throw new IllegalStateException();
    }
}
