package optimization.ir;

import ir.IRFunction;
import ir.construct.RegisterTracker;
import ir.construct.RegisterUnification;
import ir.instruction.Assign;
import ir.operand.Register;
import misc.pass.IRFunctionPass;
import misc.tools.DisjointSet;

import java.util.HashMap;
import java.util.HashSet;

/*
 * do not avail against performance
 */
public class CopyPropagation extends IRFunctionPass {
    private final RegisterTracker tracker;
    private final DisjointSet<String> unionFind = new DisjointSet<>();
    private final HashSet<Register> eliminated = new HashSet<>();

    public CopyPropagation(IRFunction f) {
        super(f);
        tracker = new RegisterTracker(f);
    }

    @Override
    protected void run() {
        tracker.run();
        tracker.defs.forEach((name, inst) -> {
            var def = inst.iterator().next();
            if (def instanceof Assign) {
                if (((Assign) def).src instanceof Register) {
                    var source = (Register) ((Assign) def).src;
                    unionFind.put(def.dest.identifier(), unionFind.query(source.identifier()));
                    eliminated.add(def.dest);
                    tracker.queryInstBelongedBlock(def).removeInst(def);
                    tracker.queryRegisterUses(source).remove(def);
                }
            }
        });
        eliminated.forEach(r -> {
            tracker.queryRegisterUses(r).forEach(inst ->
                    inst.replaceRegisterWithOperand(tracker.nameToRegister(unionFind.query(r.identifier())), r));
        });
        new RegisterUnification(irFunc).invoke();
    }
}
