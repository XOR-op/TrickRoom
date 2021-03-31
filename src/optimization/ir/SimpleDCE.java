package optimization.ir;

import ir.IRFunction;
import ir.construct.RegisterTracker;
import misc.pass.IRFunctionPass;

public class SimpleDCE extends IRFunctionPass {
    private final RegisterTracker tracker;

    public SimpleDCE(IRFunction f) {
        super(f);
        tracker = new RegisterTracker(f);
    }

    @Override
    protected void run() {
        tracker.run();
        boolean flag;
        do {
            flag = false;
            for (var iter = tracker.uses.entrySet().iterator(); iter.hasNext(); ) {
                var entry = iter.next();
                var name = entry.getKey();
                if (entry.getValue().isEmpty()) {
                    var inst = tracker.querySingleDef(name);
                    if (!inst.hasSideEffect()) {
                        inst.forEachRegSrc(reg -> tracker.queryRegisterUses(reg).remove(inst));
                        iter.remove();
                        tracker.defs.remove(name);
                        tracker.queryInstBelongedBlock(inst).removeInst(inst);
                        tracker.instToBlock.remove(inst);
                        flag=true;
                    }
                }
            }
        } while (flag);
    }
}
