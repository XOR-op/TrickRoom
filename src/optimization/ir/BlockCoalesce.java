package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Jump;
import misc.pass.FunctionPass;

import java.util.HashMap;
import java.util.HashSet;

public class BlockCoalesce extends FunctionPass {
    public BlockCoalesce(IRFunction f) {
        super(f);
    }

    private HashMap<IRBlock, IRBlock> dfs() {
        HashMap<IRBlock, IRBlock> rt = new HashMap<>();
        HashSet<IRBlock> looked = new HashSet<>(), cannot = new HashSet<>();
        dfs(rt, looked, cannot, irFunc.entryBlock);
        return rt;
    }

    private void dfs(HashMap<IRBlock, IRBlock> meet, HashSet<IRBlock> looked, HashSet<IRBlock> cannot, IRBlock cur) {
        if (looked.contains(cur) || cannot.contains(cur)) return;
        looked.add(cur);
        if (cur.terminatorInst instanceof Jump) {
            var dest = ((Jump) cur.terminatorInst).getTarget();
            if (dest.prevs.size() == 1) {
                meet.put(cur, dest);
                cannot.add(dest); // avoid overlapping edge
            }
        }
        cur.nexts.forEach(n -> dfs(meet, looked, cannot, n));
    }

    @Override
    protected void run() {
        while (true) {
            var list = dfs();
            if (list.isEmpty()) return;
            list.forEach((from, to) -> {
                from.nexts = to.nexts;
                from.terminatorInst = to.terminatorInst;
                from.phiCollection.addAll(to.phiCollection);
                from.insts.addAll(to.insts);
                to.nexts.forEach(n -> {
                    n.phiCollection.forEach(phi -> phi.replaceBlock(from, to));
                    n.prevs.remove(to);
                    n.prevs.add(from);
                });
                irFunc.blocks.remove(to);
                if (irFunc.exitBlock == to) irFunc.exitBlock = from;
            });
        }
    }
}
