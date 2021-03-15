package misc.pass;

import ir.IRFunction;
import misc.pass.Pass;

public abstract class IRFunctionPass extends Pass {
    public IRFunction irFunc;

    public IRFunctionPass(IRFunction f) {
        irFunc = f;
    }

    public void invoke(){
        if(!irFunc.isBuiltin())run();
    }

}
