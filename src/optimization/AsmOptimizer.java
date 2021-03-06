package optimization;

import assembly.RVBlock;
import assembly.RVFunction;
import assembly.instruction.Computation;
import assembly.instruction.Move;

public class AsmOptimizer {
    private RVFunction asmFunc;

    public AsmOptimizer(RVFunction asmFunc) {
        this.asmFunc = asmFunc;
    }

    private void optimize(RVBlock block) {
        var iter = block.instructions.listIterator();
        while (iter.hasNext()) {
            var inst = iter.next();
            if (inst instanceof Computation && ((Computation) inst).ct == Computation.CompType.add
                    && ((Computation) inst).imm != null && ((Computation) inst).imm.isRealVal() && ((Computation) inst).imm.getVal() == 0) {
                iter.set(new Move(((Computation) inst).rd, ((Computation) inst).rs1));
            }
        }
        iter = block.instructions.listIterator();
        while (iter.hasNext()) {
            var inst = iter.next();
            if (inst instanceof Move && ((Move) inst).rd.getColor() != null && ((Move) inst).rd.getColor() == ((Move) inst).rs1.getColor()) {
                iter.remove();
            }
        }
    }

    public void run() {
        asmFunc.blocks.forEach(this::optimize);
    }
}
