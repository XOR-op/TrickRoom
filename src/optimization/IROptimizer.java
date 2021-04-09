package optimization;

import ir.IRInfo;
import misc.pass.IRInfoPass;
import optimization.ir.*;

public class IROptimizer extends IRInfoPass {
    public IROptimizer(IRInfo irInfo) {
        super(irInfo);
    }

    @Override
    protected void run() {
        for (int i = 0; i < 5; ++i) {
            info.forEachFunction(f -> {
                new AggressiveDCE(f).invoke();
                new BlockCoalesce(f).invoke();
                new SCCP(f).invoke();
                new CopyPropagation(f).invoke();
                new CommonSubexpElimination(f, CommonSubexpElimination.Type.LOCAL).invoke();
                new InstOptimizer(f).invoke();
                new AliasOptimizer(f, info).invoke();
                new LoopInvariantCodeMotion(f).invoke();
            });
            new GlobalInliner(info).invoke();
        }
    }
}
