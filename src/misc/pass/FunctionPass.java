package misc.pass;

import ir.IRFunction;
import misc.pass.Pass;

public abstract class FunctionPass extends Pass {
    public IRFunction irFunc;

    public FunctionPass(IRFunction f) {
        irFunc = f;
    }

    public void invoke(){
        if(!irFunc.isBuiltin())run();
    }

}
