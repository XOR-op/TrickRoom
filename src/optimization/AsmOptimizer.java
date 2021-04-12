package optimization;

import assembly.RVInfo;
import optimization.assembly.Peephole;
import optimization.assembly.RVBlockCoalesce;

public class AsmOptimizer{
    private RVInfo rvInfo;
    public AsmOptimizer(RVInfo info){
        rvInfo=info;
    }

    public void invoke(){
        rvInfo.forEachFunction(f -> {
            new RVBlockCoalesce(f).invoke();
            new Peephole(f).invoke();
        });
    }

}
