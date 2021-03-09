package misc.pass;

import assembly.RVFunction;
import misc.pass.Pass;

public abstract class RVFunctionPass extends Pass {
    protected RVFunction rvFunc;

    public RVFunctionPass(RVFunction rvFunc) {
        this.rvFunc = rvFunc;
    }

    @Override
    public void invoke() {
        if (!rvFunc.isBuiltin())
            run();
    }
}
