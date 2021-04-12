package optimization.assembly;

import assembly.RVFunction;
import assembly.instruction.LoadMem;
import assembly.instruction.RVInst;
import assembly.instruction.StoreMem;
import misc.pass.RVFunctionPass;

public class Peephole extends RVFunctionPass {
    public Peephole(RVFunction rvFunc) {
        super(rvFunc);
    }

    @Override
    protected void run() {
        rvFunc.blocks.forEach(block -> {
            RVInst last = null;
            for (var iter = block.instructions.listIterator(); iter.hasNext(); ) {
                var cur = iter.next();
                if (last != null) {
                    if (last instanceof StoreMem && cur instanceof LoadMem && ((StoreMem) last).rs1.getColor() == ((LoadMem) cur).rs1.getColor()
                            && ((StoreMem) last).rs2.getColor() == ((LoadMem) cur).rd.getColor() && ((StoreMem) last).imm.getVal() == ((LoadMem) cur).imm.getVal()) {
                        iter.remove();
                    } else last = cur;
                } else last = cur;
            }
        });
    }
}
