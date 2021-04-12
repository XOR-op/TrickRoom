package assembly.construct;

import assembly.RVBlock;
import assembly.RVFunction;
import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;

import java.util.*;

public class LivenessAnalyzer {
    /*
     *  LiveOut(n)=|{next in n.nexts}(allUses(next)|(LiveOut(next)&(!allDefs(next)))))
     */
    private final HashMap<RVBlock, HashSet<RVRegister>> allUses = new HashMap<>(), allDefs = new HashMap<>(),
            LiveOut = new HashMap<>(), LiveIn = new HashMap<>();
    private final HashSet<RVBlock> visited = new HashSet<>();
    private final RVFunction asmFunc;

    private void buildDefAndUse(RVBlock block) {
        var use = new HashSet<RVRegister>();
        var def = new HashSet<RVRegister>();
        block.instructions.forEach(inst -> {
            inst.forEachRegSrc(r -> {
                if (r instanceof VirtualRegister && !def.contains(r))
                    use.add(r);
            });
            inst.forEachRegDest(def::add);
        });
        LiveIn.put(block, new HashSet<>());
        LiveOut.put(block, new HashSet<>());
        allUses.put(block, use);
        allDefs.put(block, def);
    }

    private void update(RVBlock block, Queue<RVBlock> queue) {
        if (visited.contains(block)) return;
        visited.add(block);
        var conjunction = new HashSet<RVRegister>();
        block.nexts.forEach(n -> conjunction.addAll(LiveIn.get(n)));
        LiveOut.put(block, conjunction);
        var newLiveIn = new HashSet<RVRegister>(conjunction);
        newLiveIn.removeAll(allDefs.get(block));
        newLiveIn.addAll(allUses.get(block));

        if (!LiveIn.containsKey(block) || !newLiveIn.equals(LiveIn.get(block))) {
            LiveIn.put(block, newLiveIn);
            visited.removeAll(block.prevs);
        }
        queue.addAll(block.prevs);
    }

    public void run() {
        asmFunc.blocks.forEach(this::buildDefAndUse);
        asmFunc.blocks.forEach(block -> {
            if (block.nexts.isEmpty()) {
                visited.add(block);
                LiveIn.put(block, new HashSet<>(allUses.get(block)));
            }
        });
        var workList = new LinkedList<RVBlock>();
        asmFunc.blocks.forEach(block -> {
            if (block.nexts.isEmpty()) workList.addAll(block.prevs);
        });
        while (!workList.isEmpty()) {
            update(workList.poll(), workList);
        }
        LiveOut.forEach((b, s) -> b.liveOut = s);
    }

    public LivenessAnalyzer(RVFunction asmFunc) {
        this.asmFunc = asmFunc;
    }
}
