package optimization.ir;

import ir.IRFunction;
import ir.construct.RegisterTracker;
import ir.instruction.IRInst;
import ir.instruction.Load;
import ir.operand.Register;
import misc.pass.IRFunctionPass;

import java.util.HashSet;
import java.util.LinkedList;

public class AggressiveDCE extends IRFunctionPass {
    private final HashSet<IRInst> reachable = new HashSet<>();
    private final LinkedList<IRInst> instWorkList = new LinkedList<>();
    private final HashSet<Register> regAccessed = new HashSet<>();
    private final RegisterTracker tracker;

    public AggressiveDCE(IRFunction f) {
        super(f);
        tracker = new RegisterTracker(f);
        tracker.run();
    }

    @Override
    protected void run() {
        irFunc.blocks.forEach(block -> {
            reachable.add(block.terminatorInst);
            instWorkList.add(block.terminatorInst);
            block.insts.forEach(inst -> {
                if (inst.hasSideEffect()&&!(inst instanceof Load)) {
                    reachable.add(inst);
                    instWorkList.add(inst);
                }
            });
        });
        while (!instWorkList.isEmpty()){
            var inst=instWorkList.pop();
            inst.forEachRegSrc(src->{
                if(!regAccessed.contains(src)) {
                    regAccessed.add(src);
                    if(tracker.defs.containsKey(src.identifier())) {
                        // not a parameter
                        var def = tracker.querySingleDef(src);
                        if (!reachable.contains(def)) {
                            reachable.add(def);
                            instWorkList.add(def);
                        }
                    }
                }
            });
        }
        irFunc.blocks.forEach(block -> {
            block.insts.removeIf(inst -> !reachable.contains(inst));
            block.phiCollection.removeIf(inst -> !reachable.contains(inst));
        });
    }
}
