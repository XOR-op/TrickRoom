package optimization.assembly;

import assembly.RVBlock;
import assembly.RVFunction;
import assembly.instruction.ControlFlowInst;
import assembly.instruction.Jump;
import misc.pass.RVFunctionPass;

import java.util.HashMap;
import java.util.HashSet;

public class RVBlockCoalesce extends RVFunctionPass {
    public RVBlockCoalesce(RVFunction f) {
        super(f);
    }


    private HashMap<RVBlock, RVBlock> dfs() {
        HashMap<RVBlock, RVBlock> rt = new HashMap<>();
        HashSet<RVBlock> looked = new HashSet<>(), cannot = new HashSet<>();
        dfs(rt, looked, cannot, rvFunc.blocks.get(0));
        return rt;
    }

    private void dfs(HashMap<RVBlock, RVBlock> meet, HashSet<RVBlock> looked, HashSet<RVBlock> cannot, RVBlock cur) {
        if (looked.contains(cur) || cannot.contains(cur)) return;
        looked.add(cur);
        if (cur.instructions.getLast() instanceof Jump) {
            var dest = ((Jump) cur.instructions.getLast()).getDest();
            if (dest.prevs.size() == 1) {
                meet.put(cur, dest);
                cannot.add(dest); // avoid overlapping edge
            }
        }
        cur.nexts.forEach(n -> dfs(meet, looked, cannot, n));
    }

    private HashSet<RVBlock> dfsForForward() {
        HashSet<RVBlock> redundant = new HashSet<>();
        HashSet<RVBlock> looked = new HashSet<>(), cannot = new HashSet<>();
        dfsForForward(redundant, looked, cannot, rvFunc.blocks.get(0));
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
        cur.nexts.forEach(n -> dfsForForward(redundant, looked, cannot, n));
    }

    private void runToCoalesce() {
        while (true) {
            var list = dfs();
            if (list.isEmpty()) return;
            list.forEach((from, to) -> {
                from.nexts = to.nexts;
                from.instructions.removeLast();
                from.instructions.addAll(to.instructions);
                to.nexts.forEach(n -> {
                    n.prevs.remove(to);
                    n.prevs.add(from);
                });
                rvFunc.blocks.remove(to);
            });
        }
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
                    rvFunc.entry=next;
                }
                rvFunc.blocks.remove(blk);
            });
        }
    }

    @Override
    protected void run() {
        runToEliminate();
    }
}
