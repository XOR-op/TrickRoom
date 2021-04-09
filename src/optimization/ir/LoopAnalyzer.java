package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.construct.DominanceTracker;
import ir.construct.RegisterTracker;
import misc.pass.IRFunctionPass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Consumer;

public class LoopAnalyzer extends IRFunctionPass {
    protected final HashMap<IRBlock, HashSet<IRBlock>> domAncestors = new HashMap<>();
    protected final HashMap<IRBlock, Loop> loops = new HashMap<>();
    protected final DominanceTracker domTracker;
    protected final RegisterTracker usageTracker;
    protected final HashSet<Loop> rootLoops = new HashSet<>();

    protected static class Loop {
        public IRBlock header, backStart;
        public final HashSet<IRBlock> loopBlock = new HashSet<>();
        public final HashSet<Loop> subLoop = new HashSet<>();

        public Loop(IRBlock header, IRBlock backStart) {
            this.header = header;
            this.backStart = backStart;
        }
    }

    public LoopAnalyzer(IRFunction f) {
        super(f);
        domTracker = new DominanceTracker(f);
        domTracker.invoke();
        usageTracker = new RegisterTracker(f);
        usageTracker.run();
    }

    @Override
    protected void run() {
        loopDetect();
    }

    protected void loopDetect() {
        Stack<IRBlock> dfsStack = new Stack<>();
        // build ancestor list
        dfsStack.push(irFunc.entryBlock);
        domAncestors.put(irFunc.entryBlock, new HashSet<>());
        while (!dfsStack.isEmpty()) {
            var blk = dfsStack.pop();
            var idom = domTracker.iDoms.get(blk);
            var newSet = new HashSet<>(domAncestors.get(idom));
            newSet.add(idom);
            domAncestors.put(blk, newSet);
            dfsStack.addAll(domTracker.domTree.get(blk));
        }
        // find back edge
        var visited = new HashSet<IRBlock>();
        dfsStack.push(irFunc.entryBlock);
        while (!dfsStack.isEmpty()) {
            var cur = dfsStack.pop();
            if (visited.contains(cur)) continue;
            visited.add(cur);
            for (var next : cur.nexts) {
                if (domAncestors.get(cur).contains(next)) {
                    loops.put(next, new LoopInvariantCodeMotion.Loop(next, cur));
                }
                dfsStack.push(next);
            }
        }
        // construct loop
        for (var pair : loops.entrySet()) {
            var loop = pair.getValue();
            LinkedList<IRBlock> bfsQueue = new LinkedList<>();
            bfsQueue.push(loop.backStart);
            loop.loopBlock.add(loop.header);
            loop.loopBlock.add(loop.backStart);
            while (!bfsQueue.isEmpty()) {
                var cur = bfsQueue.poll();
                cur.prevs.forEach(b -> {
                    if (!loop.loopBlock.contains(b)) {
                        loop.loopBlock.add(b);
                        bfsQueue.push(b);
                    }
                });
            }
        }
        // find overlapping
        var loopStack = new Stack<LoopInvariantCodeMotion.Loop>();
        visited.clear();
        dfsStack.push(irFunc.entryBlock);
        while (!dfsStack.isEmpty()) {
            var cur = dfsStack.pop();
            if (visited.contains(cur)) continue;
            visited.add(cur);
            // find correct parent loop
            if (!loopStack.isEmpty()) {
                while (!loopStack.isEmpty() && !loopStack.peek().loopBlock.contains(cur)) loopStack.pop();
            }
            if (loops.containsKey(cur)) {
                if (loopStack.isEmpty()) rootLoops.add(loops.get(cur));
                else
                    loopStack.peek().subLoop.add(loops.get(cur));
                loopStack.push(loops.get(cur));
            }
            // dfs next, those belonging to loop first
            HashSet<IRBlock> referSet = loopStack.isEmpty() ? new HashSet<>() : loopStack.peek().loopBlock;
            cur.nexts.forEach(b -> {
                if (!referSet.contains(b)) dfsStack.push(b);
            });
            cur.nexts.forEach(b -> {
                if (referSet.contains(b)) dfsStack.push(b); // LIFO in stack
            });
        }
        // the following is used for debug only
        HashSet<IRBlock> entries = new HashSet<>();
        for (var pair : loops.entrySet()) {
            entries.add(pair.getKey());
        }
        if (entries.size() != loops.size()) throw new RuntimeException("Same header detected");
    }

    public void calculateDepth() {
        HashMap<IRBlock, Integer> depths = new HashMap<>();
        irFunc.blocks.forEach(b -> depths.put(b, 0));
        Consumer<Loop> lambda = loop -> {
            loop.loopBlock.forEach(b -> {
                int v = depths.get(b);
                depths.put(b, v + 1);
            });
        };
        loops.forEach((entry, loop) -> {
            lambda.accept(loop);
            loop.subLoop.forEach(lambda);
        });
        depths.forEach((b, d) -> b.loopDepth = d);
    }
}
