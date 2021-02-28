package assembly.construct;

import assembly.AsmBlock;
import assembly.AsmFunction;
import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;

import java.util.HashMap;
import java.util.HashSet;

public class LiveAnalyzer {
    /*
     *  LiveOut(n)=|{next in n.nexts}(allUses(next)|(LiveOut(next)&(!allDefs(next)))))
     */
    private final HashMap<AsmBlock, HashSet<RVRegister>> allUses = new HashMap<>(), allDefs = new HashMap<>(),
            LiveOut = new HashMap<>(), LiveIn = new HashMap<>();
    private final HashSet<AsmBlock> visited = new HashSet<>();
    private final AsmFunction asmFunc;

    private void buildDefAndUse(AsmBlock block) {
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

    private void update(AsmBlock block) {
        if (visited.contains(block)) return;
        visited.add(block);
        var conjunction = new HashSet<RVRegister>();
        block.nexts.forEach(n -> conjunction.addAll(LiveIn.get(n)));
        LiveOut.put(block, conjunction);
        var newLiveIn = new HashSet<>(conjunction);
        newLiveIn.removeAll(allDefs.get(block));
        newLiveIn.addAll(allUses.get(block));

        if (!LiveIn.containsKey(block) || !newLiveIn.equals(LiveIn.get(block))) {
            LiveIn.put(block, newLiveIn);
            visited.removeAll(block.prevs);
        }
        block.prevs.forEach(this::update);
    }

    public void run() {
        asmFunc.blocks.forEach(this::buildDefAndUse);
        asmFunc.blocks.forEach(block -> {
            if (block.nexts.isEmpty()) {
                visited.add(block);
                LiveIn.put(block, new HashSet<>(allUses.get(block)));
            }
        });
        asmFunc.blocks.forEach(block -> {
            if (block.nexts.isEmpty()) block.prevs.forEach(this::update);
        });
        LiveOut.forEach((b, s) -> b.liveOut = s);
    }

    public LiveAnalyzer(AsmFunction asmFunc) {
        this.asmFunc = asmFunc;
    }
}
