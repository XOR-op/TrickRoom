package assembly.construct;

import assembly.RVFunction;
import assembly.instruction.*;
import assembly.operand.Imm;
import assembly.operand.PhysicalRegister;
import misc.pass.RVFunctionPass;

public class PostCleaner extends RVFunctionPass {

    public PostCleaner(RVFunction rvFunc) {
        super(rvFunc);
    }

    private boolean overflowFlag = false;
    private static final int STACK_BASE = 2000;
    private int more = 0;

    public void run() {
        int so = rvFunc.getStackOffset();
        if (so != 0) {
            // allocate stack
            if (so > STACK_BASE) {
                // stack size may exceed 12-bits
                overflowFlag = true;
                more = so - STACK_BASE;
                so = STACK_BASE;
            }
            rvFunc.entry.instructions.addFirst(new Computation(
                    PhysicalRegister.get("sp"), Computation.CompType.add, PhysicalRegister.get("sp"), new Imm(-so)
            ));
        }
        for (var block : rvFunc.blocks) {
            for (var iter = block.instructions.listIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if (inst instanceof LoadMem)
                    ((LoadMem) inst).replaceImm(so);
                else if (inst instanceof StoreMem)
                    ((StoreMem) inst).replaceImm(so);
                else if(inst instanceof Computation)
                    ((Computation) inst).replaceImm(so-rvFunc.pointerReg.size()*4);
                else if (overflowFlag && inst instanceof RVCall) {
                    if (iter.hasPrevious()) iter.previous();
                    else iter = block.instructions.listIterator();
                    iter.add(new Computation(
                            PhysicalRegister.get("sp"), Computation.CompType.add, PhysicalRegister.get("sp"), new Imm(-more)
                    ));
                    iter.next();
                    iter.add(new Computation(
                            PhysicalRegister.get("sp"), Computation.CompType.add, PhysicalRegister.get("sp"), new Imm(more)
                    ));
                }
            }
        }
        if (so != 0) {
            // restore stack
            for (var block : rvFunc.blocks) {
                var last = block.instructions.getLast();
                if (last instanceof Return) {
                    block.instructions.removeLast();
                    block.addInst(new Computation(
                            PhysicalRegister.get("sp"), Computation.CompType.add, PhysicalRegister.get("sp"), new Imm(so)
                    ));
                    block.addInst(last);
                }
            }
        }
        // solve too-long label name error
        rvFunc.blocks.forEach(b -> {
            if (b.name.length() >= 64) {
                b.name = "HASH_" + Integer.toHexString(b.name.hashCode() & 0x7fffffff);
            }
        });
    }
}
