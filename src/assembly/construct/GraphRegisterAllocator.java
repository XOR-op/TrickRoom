package assembly.construct;

import assembly.AsmFunction;
import assembly.instruction.Move;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class GraphRegisterAllocator {
    /*
     * Based on the algorithm in Tiger Book
     */
    private final int REG_NUM = 27;
    private final AsmFunction asmFunc;

    // register structures

    private final HashMap<RVRegister, HashSet<RVRegister>> interferenceGraph = new HashMap<>();
    private final HashMap<RVRegister, Integer> degrees = new HashMap<>();
    private final HashMap<RVRegister, Integer> weights = new HashMap<>();

    private final HashSet<RVRegister>
            preColored = new HashSet<>(),
            initialList = new HashSet<>(),
            simplifyWorkList = new HashSet<>(),
            freezeWorkList = new HashSet<>(),
            spillWorkList = new HashSet<>(),
            decidedSpillSet = new HashSet<>(),
            coalescedSet = new HashSet<>(),
            coloredSet = new HashSet<>();

    private final HashSet<RVRegister> selectedRegisters = new HashSet<>();

    // move instructions
    private final HashSet<Move>
            workListMoves = new HashSet<>(),
            constrainedMoves = new HashSet<>(),
            coalescedMoves = new HashSet<>(),
            frozenMoves = new HashSet<>(),
            activeMoves = new HashSet<>();

    private final HashMap<RVRegister, HashSet<Move>> moveRelation = new HashMap<>(); // mapping reg to all moves containing the reg
    private final HashMap<RVRegister, RVRegister> aliasMapping = new HashMap<>();

    public GraphRegisterAllocator(AsmFunction asmFunc) {
        this.asmFunc = asmFunc;
    }

    private void init() {
        for (int i = 5; i <= 31; ++i) {
            var reg = PhysicalRegister.get(i);
            preColored.add(reg);
            degrees.put(reg, Integer.MAX_VALUE);
        }
        asmFunc.blocks.forEach(blk->{
            // todo calculate weight for each register
        });

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

    private boolean hasEdge(RVRegister r1, RVRegister r2) {
        return interferenceGraph.containsKey(r1) && interferenceGraph.get(r1).contains(r2);
    }

    public void run() {

    }

    private void buildInterfere() {
        asmFunc.blocks.forEach(b -> {
            var live = new HashSet<>(b.liveOut);
            for (var iter = b.instructions.descendingIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if (inst instanceof Move) {
                    Consumer<RVRegister> lambda = r -> {
                        if (!moveRelation.containsKey(r)) moveRelation.put(r, new HashSet<>());
                        moveRelation.get(r).add((Move) inst);
                    };
                    inst.forEachRegSrc(lambda);
                    inst.forEachRegDest(lambda);
                    workListMoves.add((Move) inst);
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

    private boolean isRegRelatedToMove(RVRegister reg) {
        if (!moveRelation.containsKey(reg)) return false;
        for (var move : moveRelation.get(reg)) {
            if (workListMoves.contains(move) || activeMoves.contains(move)) return true;
        }
        return false;
    }

    private void buildWorkList() {
        initialList.forEach(reg -> {
            if (degrees.get(reg) >= REG_NUM)
                spillWorkList.add(reg);
            else if (isRegRelatedToMove(reg))
                freezeWorkList.add(reg);
            else simplifyWorkList.add(reg);
        });
        initialList.clear();
    }

    private void forEachRelatedMoves(RVRegister reg, Consumer<Move> consumer) {
        moveRelation.get(reg).forEach(r -> {
            if (!activeMoves.contains(r) && !workListMoves.contains(r))
                consumer.accept(r);
        });
    }

    private void forEachAdjacent(RVRegister reg, Consumer<RVRegister> consumer) {
        interferenceGraph.get(reg).forEach(r -> {
            if (!selectedRegisters.contains(r) && !coalescedSet.contains(r))
                consumer.accept(r);
        });
    }

    private void decreaseDegree(RVRegister reg) {
        int deg = degrees.get(reg);
        degrees.put(reg, deg - 1);
        if (deg == REG_NUM) {
            // enable moves
            enableMoves(reg);
            forEachAdjacent(reg, this::enableMoves);
            // move to correct work list
            spillWorkList.remove(reg);
            if (moveRelation.containsKey(reg))
                freezeWorkList.add(reg);
            else
                simplifyWorkList.add(reg);
        }
    }

    private void simplify() {
        var iter = simplifyWorkList.iterator();
        var reg = iter.next();
        iter.remove();
        selectedRegisters.add(reg);
        forEachAdjacent(reg, this::decreaseDegree);
    }

    private void enableMoves(RVRegister reg) {
        forEachRelatedMoves(reg, mov -> {
            if (activeMoves.contains(mov)) {
                activeMoves.remove(mov);
                workListMoves.add(mov);
            }
        });
    }

    private RVRegister getAlias(RVRegister reg) {
        while (coalescedSet.contains(reg))
            reg = aliasMapping.get(reg);
        return reg;
    }

    private void addRegToWorkList(RVRegister reg) {
        if (!preColored.contains(reg) && !(isRegRelatedToMove(reg) && degrees.get(reg) < REG_NUM)) {
            freezeWorkList.remove(reg);
            simplifyWorkList.add(reg);
        }
    }

    private boolean ableToCoalesce(RVRegister u, RVRegister v) {
        if (!preColored.contains(u)) return false;
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        forEachAdjacent(v, adj -> {
            if (flag.get() && (!(degrees.get(adj) < REG_NUM || preColored.contains(adj) || hasEdge(adj, u))))
                flag.set(false);
        });
        return flag.get();
    }

    private boolean BriggsCriterion(RVRegister u, RVRegister v) {
        if (preColored.contains(u)) return false;
        var conjunction = new HashSet<RVRegister>();
        forEachAdjacent(u, conjunction::add);
        forEachAdjacent(v, conjunction::add);
        return (conjunction.stream().filter(reg -> degrees.get(reg) >= REG_NUM).count()) < REG_NUM;
    }

    private void combine(RVRegister to, RVRegister from) {
        if (freezeWorkList.contains(from))
            freezeWorkList.remove(from);
        else
            spillWorkList.remove(from);
        coalescedSet.add(from);
        aliasMapping.put(from, to);
        moveRelation.get(to).addAll(moveRelation.get(from));
        enableMoves(from);
        forEachAdjacent(from, adj -> {
            addEdge(adj, to);
            decreaseDegree(adj);
        });
        if (degrees.get(to) >= REG_NUM && freezeWorkList.contains(to)) {
            freezeWorkList.remove(to);
            spillWorkList.add(to);
        }
    }

    private void coalesceValidMoves() {
        var iter = workListMoves.iterator();
        var move = iter.next();
        iter.remove();
        RVRegister u = getAlias(move.rd), v = getAlias(move.rs1);
        if (preColored.contains(v)) {
            var tmp = u;
            u = v;
            v = tmp;
        }
        workListMoves.remove(move);
        if (u == v) {
            coalescedMoves.add(move);
            addRegToWorkList(u);
        } else if (preColored.contains(v) || hasEdge(u, v)) {
            // conflict
            constrainedMoves.add(move);
            addRegToWorkList(u);
            addRegToWorkList(v);
        } else if (ableToCoalesce(u, v) || BriggsCriterion(u, v)) {
            // able to coalescing
            coalescedMoves.add(move);
            combine(u, v);
            addRegToWorkList(u);
        } else {
            // delay coalescence
            activeMoves.add(move);
        }
    }

    private void freezeRelatedMoves(RVRegister reg) {
        forEachRelatedMoves(reg, move -> {
            var v = (getAlias(move.rs1) == getAlias(reg)) ? getAlias(move.rd) : getAlias(move.rs1);
            activeMoves.remove(move);
            frozenMoves.add(move);
            // test if v.relatedMoves.isEmpty()
            AtomicBoolean noRelated = new AtomicBoolean(true);
            forEachRelatedMoves(v, m -> noRelated.set(false));
            if (noRelated.get() && degrees.get(v) < REG_NUM) {
                freezeWorkList.remove(v);
                simplifyWorkList.add(v);
            }
        });
    }


    private void freeze() {
        var iter = freezeWorkList.iterator();
        var reg = iter.next();
        iter.remove();
        simplifyWorkList.add(reg);
        freezeRelatedMoves(reg);
    }

    private RVRegister selectPolicy() {

    }

    private void selectToSpill() {
        var reg = selectPolicy();
        spillWorkList.remove(reg);
        simplifyWorkList.add(reg);
        freezeRelatedMoves(reg);
    }

    private void rewriteProgram() {

    }

}
