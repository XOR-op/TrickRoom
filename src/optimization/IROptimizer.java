package optimization;

import ir.IRBlock;
import ir.IRInfo;
import ir.construct.DominanceTracker;
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
                f.blocks.forEach(IRBlock::selfTest);
                new AggressiveDCE(f).invoke();
                new BlockCoalesce(f).invoke();
                var a=new DominanceTracker(f);
                a.invoke();
                new SCCP(f).invoke();
                var b=new DominanceTracker(f);
                b.invoke();
                new CopyPropagation(f).invoke();
                new CommonSubexpElimination(f, CommonSubexpElimination.Type.LOCAL).invoke();
                new ConstantDeducer(f).invoke();
                new Peephole(f).invoke();
                new SimpleDCE(f).invoke();
                new AliasOptimizer(f, info).invoke();
                new BlockCoalesce(f).invoke();
                new LoopInvariantCodeMotion(f).invoke();
            });
            new GlobalInliner(info).invoke();
        }
    }
}
