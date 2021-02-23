package assembly.instruction;

import assembly.AsmBlock;

public class Jump extends RVInst {

    private AsmBlock dest;

    public Jump(AsmBlock dest) {
        this.dest = dest;
    }

    @Override
    public String tell() {
        return "j " + dest;
    }
}
