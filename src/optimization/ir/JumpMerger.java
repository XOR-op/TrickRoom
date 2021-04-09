package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Branch;
import ir.instruction.Jump;
import misc.pass.IRFunctionPass;
import misc.tools.DisjointSet;

import java.util.HashSet;

public class JumpMerger extends IRFunctionPass {

    /*
     * buggy, so not in use
     */
    private final DisjointSet<IRBlock> unionFind = new DisjointSet<>();

    public JumpMerger(IRFunction f) {
        super(f);
    }

    @Override
    protected void run() {
        jumpMerge();
    }

    private IRBlock replace(IRBlock block, IRBlock next) {
        var trueNext = unionFind.query(next);
        if (next != trueNext) {
            next.prevs.remove(block);
            trueNext.prevs.add(block);
            block.nexts.remove(next);
            block.nexts.add(trueNext);
            // find which block reaches trueNext
            IRBlock predecessor = next;
            while (((Jump) predecessor.terminatorInst).target != trueNext)
                predecessor = ((Jump) predecessor.terminatorInst).target;
            for (var phi : trueNext.phiCollection) {
                for (var s : phi.arguments) {
                    if (s.block == predecessor) {
                        phi.append(s.val,block);
                        break;
                    }
                }
            }
            return trueNext;
        } else return next;
    }

    private void jumpMerge() {
        irFunc.blocks.forEach(irBlock -> {
            if (irBlock.insts.isEmpty() && irBlock.phiCollection.isEmpty() && irBlock.terminatorInst instanceof Jump) {
                unionFind.put(irBlock, ((Jump) irBlock.terminatorInst).target);
            }
        });
        irFunc.blocks.forEach(block -> {
            if (block.terminatorInst instanceof Jump) {
                var next = ((Jump) block.terminatorInst).target;
                ((Jump) block.terminatorInst).target = replace(block, next);
            } else if (block.terminatorInst instanceof Branch) {
                var next = ((Branch) block.terminatorInst).trueBranch;
                ((Branch) block.terminatorInst).trueBranch = replace(block, next);
                next = ((Branch) block.terminatorInst).falseBranch;
                ((Branch) block.terminatorInst).falseBranch = replace(block, next);
            }
        });
    }
}
