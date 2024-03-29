package assembly.construct;

import assembly.RVBlock;
import assembly.RVFunction;
import assembly.instruction.*;
import assembly.operand.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GraphRegisterAllocator {

    public GraphRegisterAllocator(RVFunction asmFunc) {
        this.asmFunc = asmFunc;
        identity = 0;
    }

    public void runAll() {
        for (boolean flag = false; !flag; ++identity) flag = run();
    }

    /*
     * Based on the algorithm in Tiger Book
     */
    private final List<Integer> immutableColorList = new ArrayList<>();
    private final int REG_NUM = 27;
    private final RVFunction asmFunc;
    private int identity;
    private int anonymousCounter = 0;

    // register structures

    private final Map<RVRegister, LinkedHashSet<RVRegister>> interferenceGraph = new LinkedHashMap<>();
    private final Map<RVRegister, Integer> degrees = new LinkedHashMap<>();
    private final Map<RVRegister, Integer> weights = new LinkedHashMap<>();
    private final Map<RVRegister, Integer> color = new LinkedHashMap<>();

    private final Set<RVRegister>
            preColored = new LinkedHashSet<>(),
            initialList = new LinkedHashSet<>(),
            simplifyWorkList = new LinkedHashSet<>(),
            freezeWorkList = new LinkedHashSet<>(),
            highDegreeWorkList = new LinkedHashSet<>(),
            decidedSpillSet = new LinkedHashSet<>(),
            coalescedSet = new LinkedHashSet<>(),
            coloredSet = new LinkedHashSet<>();

    private final LinkedHashSet<RVRegister> selectedRegisterStack = new LinkedHashSet<>();

    // move instructions
    private final Set<Move>
            workListMoves = new LinkedHashSet<>(),
            constrainedMoves = new LinkedHashSet<>(),
            coalescedMoves = new LinkedHashSet<>(),
            frozenMoves = new LinkedHashSet<>(),
            activeMoves = new LinkedHashSet<>();

    private final LinkedHashMap<RVRegister, LinkedHashSet<Move>> moveRelation = new LinkedHashMap<>(); // mapping reg to all moves containing the reg
    private final LinkedHashMap<RVRegister, RVRegister> aliasMapping = new LinkedHashMap<>();

    private void build() {
        preColored.clear();
        initialList.clear();
        simplifyWorkList.clear();
        freezeWorkList.clear();
        highDegreeWorkList.clear();
        decidedSpillSet.clear();
        coalescedSet.clear();
        coloredSet.clear();
        workListMoves.clear();
        constrainedMoves.clear();
        coalescedMoves.clear();
        frozenMoves.clear();
        activeMoves.clear();
        selectedRegisterStack.clear();
        moveRelation.clear();
        aliasMapping.clear();
        interferenceGraph.clear();
        degrees.clear();
        weights.clear();
        color.clear();
        for (int i = 0; i <= 31; ++i) {
            var reg = PhysicalRegister.get(i);
            preColored.add(reg);
            color.put(reg, i);
            weights.put(reg, Integer.MAX_VALUE);
            degrees.put(reg, 0);
            moveRelation.put(reg, new LinkedHashSet<>());
        }
        for (int i = 5; i <= 31; ++i) {
            immutableColorList.add(i);
        }
        BiConsumer<RVRegister, Integer> plus = (reg, more) -> {
            if (reg instanceof PhysicalRegister) return;
            if (!weights.containsKey(reg)) weights.put(reg, 0);
            weights.put(reg, weights.get(reg) + more);
        };
        asmFunc.blocks.forEach(blk -> {
            blk.instructions.forEach(inst -> {
                inst.forEachRegDest(reg -> plus.accept(reg, (int) Math.pow(6, blk.loopDepth)));
                inst.forEachRegSrc(reg -> plus.accept(reg, (int) Math.pow(6, blk.loopDepth)));
                inst.forEachRegSrc(reg -> {
                    if (!preColored.contains(reg)) initialList.add(reg);
                });
                inst.forEachRegDest(reg -> {
                    if (!preColored.contains(reg)) initialList.add(reg);
                });
            });
        });
        initialList.forEach(reg -> {
            assert !preColored.contains(reg);
        });
        initialList.forEach(reg -> {
            degrees.put(reg, 0);
            moveRelation.put(reg, new LinkedHashSet<>());
        });
    }

    private void singleEdge(RVRegister rv1, RVRegister rv2) {
        if (!interferenceGraph.containsKey(rv1)) {
            var newSet = new LinkedHashSet<RVRegister>();
            newSet.add(rv2);
            interferenceGraph.put(rv1, newSet);
            degrees.put(rv1, preColored.contains(rv1) ? 0 : 1);
        } else {
            var getSet = interferenceGraph.get(rv1);
            if (!getSet.contains(rv2)) {
                getSet.add(rv2);
                if (!preColored.contains(rv1)) {
                    degrees.replace(rv1, degrees.get(rv1) + 1);
                }
            }
        }
    }

    private void addEdge(RVRegister rv1, RVRegister rv2) {
        if (rv1 == rv2) return;
        singleEdge(rv1, rv2);
        singleEdge(rv2, rv1);
    }

    private boolean hasEdge(RVRegister r1, RVRegister r2) {
        return interferenceGraph.containsKey(r1) && interferenceGraph.get(r1).contains(r2);
    }

    private void optimize(RVBlock block) {
        var iter = block.instructions.listIterator();
        while (iter.hasNext()) {
            var inst = iter.next();
            if (inst instanceof Move && ((Move) inst).rd.getColor() == ((Move) inst).rs1.getColor()) {
                iter.remove();
            }
        }
    }

    private boolean run() {
        build();
        new LivenessAnalyzer(asmFunc).run();
        buildInterfere();
        buildWorkList();
        while (!(simplifyWorkList.isEmpty() && workListMoves.isEmpty() && freezeWorkList.isEmpty() && highDegreeWorkList.isEmpty())) {
            /*
             * todo tune methods to extract element from simplifyWorkList, workListMoves and freezeWorkList
             */
            if (!simplifyWorkList.isEmpty()) simplify();
            else if (!workListMoves.isEmpty()) coalesceValidMoves();
            else if (!freezeWorkList.isEmpty()) freeze();
            else selectHighDegree();
        }
        assignColor();
        if (decidedSpillSet.isEmpty()) {
            // end optimization
            color.forEach((reg, col) -> reg.setColor(PhysicalRegister.get(col)));
            asmFunc.blocks.forEach(this::optimize);
            new PostCleaner(asmFunc).run();
            return true;
        } else {
            rewriteProgram();
            return false;
        }
    }

    private void buildInterfere() {
        asmFunc.blocks.forEach(b -> {
            var live = new LinkedHashSet<>(b.liveOut);
            for (var iter = b.instructions.descendingIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if (inst instanceof Move) {
                    Consumer<RVRegister> lambda = r -> {
                        if (!moveRelation.containsKey(r)) moveRelation.put(r, new LinkedHashSet<>());
                        moveRelation.get(r).add((Move) inst);
                    };
                    inst.forEachRegSrc(lambda);
                    inst.forEachRegDest(lambda);
                    workListMoves.add((Move) inst);
                }
                inst.forEachRegDest(live::add);
                inst.forEachRegDest(r -> live.forEach(l -> addEdge(l, r)));
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
            assert reg instanceof VirtualRegister;
            if (degrees.get(reg) >= REG_NUM)
                highDegreeWorkList.add(reg);
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
        if (interferenceGraph.containsKey(reg)) {
            interferenceGraph.get(reg).forEach(r -> {
                if (!selectedRegisterStack.contains(r) && !coalescedSet.contains(r))
                    consumer.accept(r);
            });
        }
    }

    private void decreaseDegree(RVRegister reg) {
        int deg = degrees.get(reg);
        degrees.replace(reg, deg - 1);
        if (deg == REG_NUM) {
            // enable moves
            enableMoves(reg);
            forEachAdjacent(reg, this::enableMoves);
            // move to correct work list
            assert highDegreeWorkList.contains(reg) || freezeWorkList.contains(reg);
            /*
             * Here is something different with what in the Tiger Book.
             * We want to guarantee any remove is valid, and while the algorithm in Tiger Book
             * won't execute the else branch if reg is in freezeWorkList, it can be wrote down explicitly.
             */
            if (highDegreeWorkList.contains(reg))
                highDegreeWorkList.remove(reg);
            else freezeWorkList.remove(reg);
            if (isRegRelatedToMove(reg)) {
                freezeWorkList.add(reg);
            } else
                simplifyWorkList.add(reg);
        }
    }

    private void simplify() {
        var iter = simplifyWorkList.iterator();
        var reg = iter.next();
        iter.remove();
        assert !selectedRegisterStack.contains(reg);
        assert !(reg instanceof PhysicalRegister);
        selectedRegisterStack.add(reg);
        forEachAdjacent(reg, this::decreaseDegree);
    }

    private void enableMoves(RVRegister reg) {
        // degree is ok
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
        if (!preColored.contains(reg) && !isRegRelatedToMove(reg) && (degrees.get(reg) < REG_NUM)) {
            // not related to live move
            assert freezeWorkList.contains(reg);
            freezeWorkList.remove(reg);
            simplifyWorkList.add(reg);
        }
    }

    private boolean GeorgeCriterion(RVRegister u, RVRegister v) {
        if (!preColored.contains(u)) return false;
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        forEachAdjacent(v, adj -> {
            // assure coalesce u and v doesn't introduce more edges for u
            if (flag.get() && (!(degrees.get(adj) < REG_NUM || preColored.contains(adj) || hasEdge(adj, u))))
                flag.set(false);
        });
        return flag.get();
    }

    private boolean BriggsCriterion(RVRegister u, RVRegister v) {
        if (preColored.contains(u)) return false;
        var conjunction = new LinkedHashSet<RVRegister>();
        forEachAdjacent(u, conjunction::add);
        forEachAdjacent(v, conjunction::add);
        return (conjunction.stream().filter(reg -> degrees.get(reg) >= REG_NUM).count()) < REG_NUM;
    }

    private void combine(RVRegister to, RVRegister from) {
        if (freezeWorkList.contains(from))
            freezeWorkList.remove(from);
        else {
            assert highDegreeWorkList.contains(from);
            highDegreeWorkList.remove(from);
        }
        coalescedSet.add(from);
        aliasMapping.put(from, to);
        moveRelation.get(to).addAll(moveRelation.get(from));
        enableMoves(from);
        forEachAdjacent(from, adj -> {
            addEdge(adj, to);
            decreaseDegree(adj);
        });
        if (degrees.get(to) >= REG_NUM && freezeWorkList.contains(to)) {
            assert freezeWorkList.contains(to);
            freezeWorkList.remove(to);
            highDegreeWorkList.add(to);
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
        } else if (u == PhysicalRegister.get("zero") || preColored.contains(v) || hasEdge(u, v)) {
            // both pre-colored or conflict
            constrainedMoves.add(move);
            addRegToWorkList(u);
            addRegToWorkList(v);
        } else if (GeorgeCriterion(u, v) || BriggsCriterion(u, v)) {
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
                assert freezeWorkList.contains(v);
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

    private void selectHighDegree() {
        // the earlier entering stack, the more probable spilled out
        RVRegister selection = null;
        double minCost = Double.POSITIVE_INFINITY;
        for (var reg : highDegreeWorkList) {
            double cost = ((double) weights.get(reg)) / degrees.get(reg);
            if (cost < minCost) {
                selection = reg;
                minCost = cost;
            }
        }
        assert selection != null && highDegreeWorkList.contains(selection);
        highDegreeWorkList.remove(selection);
        simplifyWorkList.add(selection);
        freezeRelatedMoves(selection);
    }

    private void assignColor() {
        Stack<RVRegister> tmpStack = new Stack<>();
        for (var ele : selectedRegisterStack) tmpStack.push(ele);
        while (!tmpStack.isEmpty()) {
            var reg = tmpStack.pop();
            var remainingColor = new LinkedHashSet<>(immutableColorList);
            if (interferenceGraph.containsKey(reg)) {
                interferenceGraph.get(reg).forEach(adj -> {
                    var w = getAlias(adj);
                    assert w != null;
                    if (coloredSet.contains(w) || preColored.contains(w)) {
                        remainingColor.remove(color.get(w));
                    }
                });
            }
            if (remainingColor.isEmpty()) {
                decidedSpillSet.add(reg);
            } else {
                coloredSet.add(reg);
                color.put(reg, remainingColor.iterator().next());
            }
        }
        coalescedSet.forEach(reg -> color.put(reg, color.get(getAlias(reg))));
    }

    private String newTemporaryName(String s) {
        return "rewrite_" + s + "_t" + identity + "_" + anonymousCounter++;
    }

    private void rewriteProgram() {
        /*
        if (decidedSpillSet.size() > 100) {
            PriorityQueue<Object[]> queue = new PriorityQueue<>((e1, e2) -> (int) e2[1] - (int) e1[1]);
            decidedSpillSet.forEach(r -> {
                var o = new Object[2];
                o[0] = r;
                o[1] = weights.get(r);
                queue.offer(o);
            });
            for(int i=0,total=decidedSpillSet.size()/4;i<total;++i){
                decidedSpillSet.remove(queue.poll()[0]);
            }
        }*/
        decidedSpillSet.forEach(asmFunc::addVarOnStack);
        asmFunc.blocks.forEach(block -> {
            var iter = block.instructions.listIterator();
            while (iter.hasNext()) {
                var inst = iter.next();
                if (inst instanceof Move && decidedSpillSet.contains(((Move) inst).getRd()) && decidedSpillSet.contains(((Move) inst).getRs())) {
                    // avoid two spills case
                    RVRegister rd = ((Move) inst).getRd(), rs = ((Move) inst).getRs();
                    var tmpReg = new VirtualRegister(newTemporaryName(rd.toString() + "_" + rs.toString()));
                    iter.set(new LoadMem(tmpReg, RVInst.WidthType.w, PhysicalRegister.get("sp"), new VirtualImm(asmFunc.getVarOffset(rs))));
                    iter.add(new StoreMem(RVInst.WidthType.w, PhysicalRegister.get("sp"), tmpReg, new VirtualImm(asmFunc.getVarOffset(rd))));
                } else {
                    // able to replace
                    inst.forEachRegSrc(reg -> {
                        if (decidedSpillSet.contains(reg)) {
                            int offset = asmFunc.getVarOffset(reg);
                            if (inst instanceof Move) {
                                iter.set(new LoadMem(((Move) inst).rd, RVInst.WidthType.w, PhysicalRegister.get("sp"), new VirtualImm(offset)));
                            } else {
                                var tmp = new VirtualRegister(newTemporaryName(reg.toString()));
//                                    L.l(tmp.toString() + "::" + offset);
                                var loading = new LoadMem(tmp, RVInst.WidthType.w,
                                        PhysicalRegister.get("sp"), new VirtualImm(offset));
                                inst.replaceRegSrc(loading.getRd(), reg);
                                // insert before inst
                                iter.previous();
                                iter.add(loading);
                                iter.next();
                            }
                        }
                    });
                    inst.forEachRegDest(reg -> {
                        if (decidedSpillSet.contains(reg)) {
                            int offset = asmFunc.getVarOffset(reg);
                            if (inst instanceof Move) {
                                iter.set(new StoreMem(RVInst.WidthType.w, PhysicalRegister.get("sp"), ((Move) inst).rs1, new VirtualImm(offset)));
                            } else {
                                var tmp = new VirtualRegister(newTemporaryName(reg.toString()));
//                                    L.l(tmp.toString() + "::" + offset);
                                inst.replaceRegDest(tmp, reg);
                                var storing = new StoreMem(RVInst.WidthType.w, PhysicalRegister.get("sp"),
                                        tmp, new VirtualImm(offset));
                                iter.add(storing);
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean testEnum(RVRegister reg) {
        int belong = 0;
        if (preColored.contains(reg)) ++belong;
        if (initialList.contains(reg)) ++belong;
        if (simplifyWorkList.contains(reg)) ++belong;
        if (freezeWorkList.contains(reg)) ++belong;
        if (decidedSpillSet.contains(reg)) ++belong;
        if (coalescedSet.contains(reg)) ++belong;
        if (coloredSet.contains(reg)) ++belong;
        if (selectedRegisterStack.contains(reg)) ++belong;
        if (highDegreeWorkList.contains(reg)) ++belong;
        assert belong == 1;
        return true;
    }
}
