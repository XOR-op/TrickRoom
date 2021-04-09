package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Jump;
import misc.pass.IRFunctionPass;
import misc.tools.DisjointSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class BlockCoalesce extends IRFunctionPass {
    public BlockCoalesce(IRFunction f) {
        super(f);
    }

    private final Stack<IRBlock> dfsStack = new Stack<>();

    private HashMap<IRBlock, IRBlock> dfs() {
        HashMap<IRBlock, IRBlock> rt = new HashMap<>();
        HashSet<IRBlock> looked = new HashSet<>(), cannot = new HashSet<>();
        dfsStack.push(irFunc.entryBlock);
        while (!dfsStack.isEmpty()) {
            dfs(rt, looked, cannot, dfsStack.pop());
        }
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
        cur.nexts.forEach(dfsStack::push);
    }

    @Override
    protected void run() {
        while (true) {
            var list = dfs();
            if (list.isEmpty()) break;
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
