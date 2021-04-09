package optimization.assembly;

import assembly.RVBlock;
import assembly.RVFunction;
import assembly.instruction.ControlFlowInst;
import assembly.instruction.Jump;
import misc.pass.RVFunctionPass;
import misc.tools.DisjointSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class RVBlockCoalesce extends RVFunctionPass {
    public RVBlockCoalesce(RVFunction f) {
        super(f);
    }

    private final Stack<RVBlock> dfsStack = new Stack<>();
    private final DisjointSet<RVBlock> unionFind = new DisjointSet<>();

    private HashSet<RVBlock> dfsForForward() {
        HashSet<RVBlock> redundant = new HashSet<>();
        HashSet<RVBlock> looked = new HashSet<>(), cannot = new HashSet<>();
        dfsStack.push(rvFunc.blocks.get(0));
        while (!dfsStack.isEmpty())
            dfsForForward(redundant, looked, cannot, dfsStack.pop());
        return redundant;
    }

    private void dfsForForward(HashSet<RVBlock> redundant, HashSet<RVBlock> looked, HashSet<RVBlock> cannot, RVBlock cur) {
        if (looked.contains(cur)) return;
        looked.add(cur);
        if (!cannot.contains(cur) && cur.instructions.size() == 1 && cur.instructions.getLast() instanceof Jump) {
            redundant.add(cur);
            // avoid overlapping
            cannot.addAll(cur.prevs);
            cannot.addAll(cur.nexts);
        }
        cur.nexts.forEach(dfsStack::push);
    }

    private void runToEliminate() {
        while (true) {
            var list = dfsForForward();
            if (list.isEmpty()) return;
            list.forEach(blk -> {
                var next = blk.nexts.iterator().next();
                next.prevs.remove(blk);
                next.prevs.addAll(blk.prevs);
                blk.prevs.forEach(prev -> {
                    prev.nexts.remove(blk);
                    prev.nexts.add(next);
                    assert prev.instructions.getLast() instanceof ControlFlowInst;
                    ((ControlFlowInst) prev.instructions.getLast()).replaceBlock(next, blk);
                });
                if (rvFunc.entry == blk) {
                    rvFunc.entry = next;
                }
                rvFunc.blocks.remove(blk);
            });
        }
    }

    private void jumpMerge() {
        var coll = new HashSet<RVBlock>();
        rvFunc.blocks.forEach(irBlock -> {
            if (irBlock.instructions.size() == 1 && irBlock.instructions.get(0) instanceof Jump) {
                unionFind.put(irBlock, ((Jump) irBlock.instructions.get(0)).getDest());
                coll.add(irBlock);
            }
        });
        coll.forEach(block -> {
            var next = ((Jump) block.instructions.get(0)).getDest();
            var trueNext = unionFind.query(block);
            if (next != trueNext) {
                block.nexts.remove(next);
                block.nexts.add(trueNext);
                next.prevs.remove(block);
                trueNext.prevs.add(block);
                block.instructions.set(0,new Jump(trueNext));
            }
        });
    }

    @Override
    protected void run() {
        runToEliminate();
    }
}
