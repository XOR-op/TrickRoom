package misc.pass;

import ir.IRInfo;

public abstract class IRInfoPass extends Pass {
    protected IRInfo info;

    protected IRInfoPass(IRInfo info){
        this.info=info;
    }

    @Override
    public void invoke() {
        run();
    }
}
