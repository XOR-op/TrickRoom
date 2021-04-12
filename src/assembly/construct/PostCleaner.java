package assembly.construct;

import assembly.RVFunction;
import assembly.instruction.Computation;
import assembly.instruction.LoadMem;
import assembly.instruction.Return;
import assembly.instruction.StoreMem;
import assembly.operand.Imm;
import assembly.operand.PhysicalRegister;
import misc.pass.RVFunctionPass;

public class PostCleaner extends RVFunctionPass {

    public PostCleaner(RVFunction rvFunc) {
        super(rvFunc);
    }

    public void run() {
        int so = rvFunc.getStackOffset();
        if (so != 0) {
            // allocate stack
            rvFunc.entry.instructions.addFirst(new Computation(
                    PhysicalRegister.get("sp"), Computation.CompType.add, PhysicalRegister.get("sp"), new Imm(-so)
            ));
        }
        rvFunc.blocks.forEach(block -> {
            block.instructions.forEach(inst -> {
                if (inst instanceof LoadMem)
                    ((LoadMem) inst).replaceImm(so);
                else if (inst instanceof StoreMem)
                    ((StoreMem) inst).replaceImm(so);
            });
        });
        if (so != 0) {
            // restore stack
            rvFunc.blocks.forEach(block -> {
                var last = block.instructions.getLast();
                if (last instanceof Return) {
                    block.instructions.removeLast();
                    block.addInst(new Computation(
                            PhysicalRegister.get("sp"), Computation.CompType.add, PhysicalRegister.get("sp"), new Imm(so)
                    ));
                    block.addInst(last);
                }
            });
        }
        // solve too-long label name error
        rvFunc.blocks.forEach(b -> {
            if (b.name.length() >= 64) {
                b.name = "HASH_" + Integer.toHexString(b.name.hashCode()&0x7fffffff);
            }
        });
    }
}
