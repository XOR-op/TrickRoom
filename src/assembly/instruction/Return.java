package assembly.instruction;

public class Return extends RVInst {
    public Return() {
        // do nothing
    }

    @Override
    public String tell() {
        return "jr ra";
    }
}
