package assembly.instruction;

import assembly.AsmFunction;

public class Call extends RVInst{

    private AsmFunction dest;

    public Call(AsmFunction func){
        dest=func;
    }

    @Override
    public String tell() {
        return "call "+dest;
    }
}
