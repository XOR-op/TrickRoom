package assembly.instruction;

import assembly.AsmFunction;

public class RVCall extends RVInst{

    private AsmFunction dest;

    public RVCall(AsmFunction func){
        dest=func;
    }

    @Override
    public String tell() {
        return "call "+dest;
    }
}
