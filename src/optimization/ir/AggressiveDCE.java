package optimization.ir;

import ir.IRFunction;
import misc.pass.IRFunctionPass;

public class AggressiveDCE extends IRFunctionPass {
    public AggressiveDCE(IRFunction f) {
        super(f);
    }

    @Override
    protected void run() {

    }
}
