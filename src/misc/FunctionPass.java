package misc;

import ir.IRFunction;
import ir.IRInfo;

public abstract class FunctionPass {
    public IRFunction irFunc;

    public FunctionPass(IRFunction f) {
        irFunc = f;
    }

    public void invoke(){
        if(!irFunc.isBuiltin())run();
    }

    protected abstract void run();
}
