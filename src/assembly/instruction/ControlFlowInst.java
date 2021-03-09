package assembly.instruction;

import assembly.RVBlock;

public abstract class ControlFlowInst extends RVInst{
    public abstract void replaceBlock(RVBlock newBlock,RVBlock oldBlock);
}
