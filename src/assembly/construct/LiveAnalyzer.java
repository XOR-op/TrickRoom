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
    private final HashSet<AsmBlock> modified = new HashSet<>();
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
        allUses.put(block, use);
        allDefs.put(block, def);
    }

    private void update(AsmBlock block) {
        boolean changeFlag = false;
        for (var b : block.nexts) {
            if (!LiveIn.containsKey(b)) return; // postpone calculation
            if (modified.contains(b)) changeFlag = true;
        }
        if (changeFlag) {
            modified.add(block);
            var conjunction = new HashSet<RVRegister>();
            block.nexts.forEach(n -> conjunction.addAll(LiveIn.get(n)));
            LiveOut.replace(block, conjunction);
            var newLiveIn = new HashSet<>(conjunction);
            newLiveIn.removeAll(allDefs.get(block));
            newLiveIn.addAll(allUses.get(block));
            LiveIn.replace(block, newLiveIn);
        }
    }

    public void run() {
        asmFunc.blocks.forEach(this::buildDefAndUse);
        do {
            modified.clear();
            for (int i = asmFunc.blocks.size() - 1; i >= 0; --i)
                update(asmFunc.blocks.get(i));
        } while (!modified.isEmpty());
        LiveOut.forEach((b, s) -> b.liveOut = s);
    }

    public LiveAnalyzer(AsmFunction asmFunc) {
        this.asmFunc = asmFunc;
    }
}
