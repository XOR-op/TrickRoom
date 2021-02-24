package assembly.construct;

import assembly.AsmFunction;
import assembly.instruction.Move;
import assembly.instruction.RVInst;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;

import java.util.HashMap;
import java.util.HashSet;

public class GraphRegisterAllocator {
    /*
     * Based on the implementation of Tiger Book
     */
    private final AsmFunction asmFunc;

    // register structures

    private final HashMap<RVRegister, HashSet<RVRegister>> interferenceGraph = new HashMap<>();
    private final HashMap<RVRegister, Integer> degrees = new HashMap<>();

    private final HashSet<RVRegister>
            preColored = new HashSet<>(),
            simplifyWorkList = new HashSet<>(),
            freezeWorkList = new HashSet<>(),
            spillWorkList = new HashSet<>(),
            decidedSpillList = new HashSet<>(),
            coalescedList = new HashSet<>(),
            coloredList = new HashSet<>();


    // move instructions
    private final HashSet<Move>
            workListMoves = new HashSet<>(),
            constrainedMoves = new HashSet<>(),
            coalescedMoves = new HashSet<>(),
            frozenMoves = new HashSet<>(),
            activeMoves = new HashSet<>();

    private final HashMap<Move, HashSet<Move>> moveBelonging = new HashMap<>();

    public GraphRegisterAllocator(AsmFunction asmFunc) {
        this.asmFunc = asmFunc;
    }

    private void init() {
        // todo preColored
        for (int i = 6; i <= 31; ++i) {
            var reg = PhysicalRegister.get(i);
            preColored.add(reg);
            degrees.put(reg, Integer.MAX_VALUE);
        }

    }

    private void singleEdge(RVRegister rv1, RVRegister rv2) {
        if (!interferenceGraph.containsKey(rv1)) {
            interferenceGraph.put(rv1, new HashSet<>());
            degrees.put(rv1, 0);
        }
        interferenceGraph.get(rv1).add(rv2);
        if (!preColored.contains(rv1)) {
            degrees.replace(rv1, degrees.get(rv1) + 1);
        }
    }

    private void addEdge(RVRegister rv1, RVRegister rv2) {
        if (rv1.equals(rv2)) return;
        singleEdge(rv1, rv2);
        singleEdge(rv2, rv1);
    }

    public void run() {

    }

    private void buildInterfere() {
        asmFunc.blocks.forEach(b -> {
            var live = new HashSet<>(b.liveOut);
            for (var iter = b.instructions.descendingIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if (inst instanceof Move) {
                    // todo
                }
                inst.forEachRegDest(live::add);
                inst.forEachRegDest(r -> {
                    live.forEach(l -> addEdge(l, r));
                });
                inst.forEachRegDest(live::remove);
                inst.forEachRegSrc(live::add);
            }
        });
    }

    private void simplify() {

    }

    private void spill() {

    }

    private void select() {

    }

    private void startOver() {

    }

}
