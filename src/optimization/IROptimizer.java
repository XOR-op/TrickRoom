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
        for (int i = 0; i < 3; ++i) {
            info.forEachFunction(f -> {
                new AggressiveDCE(f).invoke();
                new BlockCoalesce(f).invoke();
                new SCCP(f).invoke();
                new CopyPropagation(f).invoke();
                new CommonSubexpElimination(f, CommonSubexpElimination.Type.LOCAL).invoke();
                new ConstantDeducer(f).invoke();
                new Peephole(f).invoke();
                new SimpleDCE(f).invoke();
                new MemAccessOptimizer(f, info).invoke();
                new BlockCoalesce(f).invoke();
                new LoopInvariantCodeMotion(f,info).invoke();
            });
            new GlobalInliner(info).invoke();
        }
    }
}
