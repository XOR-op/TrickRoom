package ir.construct;

import ir.IRBlock;
import ir.IRFunction;
import misc.pass.IRFunctionPass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DominanceTracker extends IRFunctionPass {
    private final HashMap<IRBlock, Integer> order = new HashMap<>();
    private final ArrayList<IRBlock> blocksByOrder = new ArrayList<>();
    public final HashMap<IRBlock, IRBlock> iDoms = new HashMap<>();
    public final HashMap<IRBlock, HashSet<IRBlock>> domTree = new HashMap<>();
    public final HashMap<IRBlock, ArrayList<IRBlock>> dominanceFrontier = new HashMap<>();

    private int maxOrder;

    public DominanceTracker(IRFunction f) {
        super(f);
    }

    @Override
    protected void run() {
        maxOrder = irFunc.blocks.size();
        reversePostorder();
        calculateDom();
        calcDominanceFrontier();
    }

    private void reversePostorder() {
        reversePostorder(irFunc.entryBlock, new HashSet<>());
        assert order.size()==maxOrder;
        Collections.reverse(blocksByOrder);
    }

    private void reversePostorder(IRBlock blk, HashSet<IRBlock> sets) {
        sets.add(blk);
        for (var b : blk.nexts) {
            if (!sets.contains(b)) reversePostorder(b, sets);
        }
        blocksByOrder.add(blk);
        order.put(blk, maxOrder - blocksByOrder.size());
    }

    private IRBlock intersect(IRBlock i, IRBlock j) {
        while (i != j) {
            while (order.get(i) > order.get(j)) {
                assert iDoms.get(i) != null;
                i = iDoms.get(i);
            }
            while (order.get(j) > order.get(i)) {
                assert iDoms.get(j) != null;
                j = iDoms.get(j);
            }
        }
        return i;
    }

    public void calculateDom() {
        irFunc.blocks.forEach(b -> {
            domTree.put(b, new HashSet<>());
        });
        blocksByOrder.forEach(b -> iDoms.put(b, null));
        iDoms.put(irFunc.entryBlock, irFunc.entryBlock);
        for (boolean flag = true; flag; ) {
            flag = false;
            for (int cur = 1; cur < blocksByOrder.size(); ++cur) {
                var curBlock = blocksByOrder.get(cur);
                var iter = curBlock.prevs.iterator();
                IRBlock newIDom = iter.next();
                while (iDoms.get(newIDom) == null) newIDom = iter.next(); // pick first processed one
                while (iter.hasNext()) {
                    var onePrev = iter.next();
                    if (iDoms.get(onePrev) != null)
                        newIDom = intersect(onePrev, newIDom);
                }
                if (iDoms.get(curBlock) != newIDom) {
                    iDoms.put(curBlock, newIDom);
                    flag = true;
                }
            }
        }
        iDoms.forEach((b, prev) -> {
            if (b != prev)
                domTree.get(prev).add(b);
        });
    }

    public void calcDominanceFrontier() {
        irFunc.blocks.forEach(b -> dominanceFrontier.put(b, new ArrayList<>()));
        for (var block : blocksByOrder) {
            if (block.prevs.size() > 1) {
                for (var prev : block.prevs) {
                    for (var cur = prev; cur != iDoms.get(block); cur = iDoms.get(cur))
                        dominanceFrontier.get(cur).add(block);
                }
            }
        }
    }

}
