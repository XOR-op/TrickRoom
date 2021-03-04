package assembly.construct;

import assembly.AsmFunction;
import assembly.instruction.Computation;
import assembly.instruction.LoadMem;
import assembly.instruction.Return;
import assembly.instruction.StoreMem;
import assembly.operand.Imm;
import assembly.operand.PhysicalRegister;

public class PostCleaner {
    private AsmFunction asmFunc;

    public PostCleaner(AsmFunction asmFunc) {
        this.asmFunc = asmFunc;
    }

    public void run() {
        int so = asmFunc.getStackOffset();
        if (so != 0) {
            // allocate stack
            asmFunc.entry.instructions.addFirst(new Computation(
                    PhysicalRegister.get("sp"), Computation.CompType.add, PhysicalRegister.get("sp"), new Imm(-so)
            ));
        }
        asmFunc.blocks.forEach(block -> {
            block.instructions.forEach(inst -> {
                if (inst instanceof LoadMem)
                    ((LoadMem) inst).replaceImm(so);
                else if (inst instanceof StoreMem)
                    ((StoreMem) inst).replaceImm(so);
            });
        });
        if (so != 0) {
            // restore stack
            asmFunc.blocks.forEach(block -> {
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
    }
}
