package pass;

import ir.IRFunction;

public abstract class FunctionPass {
    public abstract void run();

    public IRFunction irFunc;

    public FunctionPass(IRFunction f) {
        irFunc = f;
    }
}
