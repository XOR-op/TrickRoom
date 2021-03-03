package assembly.construct;

import assembly.AsmBlock;
import assembly.AsmFunction;
import assembly.instruction.*;
import assembly.operand.Imm;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GraphRegisterAllocator {

    private final AsmFunction asmFunc;

    public GraphRegisterAllocator(AsmFunction asmFunc) {
        this.asmFunc = asmFunc;
    }

    public void run() {
        while (!new AllocatorInstance(asmFunc).run()) {
        }
    }

    private class AllocatorInstance {
        /*
         * Based on the algorithm in Tiger Book
         */
        private final List<Integer> immutableColorList = new ArrayList<>();
        private final int REG_NUM = 27;
        private final AsmFunction asmFunc;
        private int anonymousCounter = 0;

        // register structures

        private final HashMap<RVRegister, HashSet<RVRegister>> interferenceGraph = new HashMap<>();
        private final HashMap<RVRegister, Integer> degrees = new HashMap<>();
        private final HashMap<RVRegister, Integer> weights = new HashMap<>();
        private final HashMap<RVRegister, Integer> color = new HashMap<>();

        private final HashSet<RVRegister>
                preColored = new HashSet<>(),
                initialList = new HashSet<>(),
                simplifyWorkList = new HashSet<>(),
                freezeWorkList = new HashSet<>(),
                highDegreeWorkList = new HashSet<>(),
                decidedSpillSet = new HashSet<>(),
                coalescedSet = new HashSet<>(),
                coloredSet = new HashSet<>();

        private final Stack<RVRegister> selectedRegisterStack = new Stack<>();

        // move instructions
        private final HashSet<Move>
                workListMoves = new HashSet<>(),
                constrainedMoves = new HashSet<>(),
                coalescedMoves = new HashSet<>(),
                frozenMoves = new HashSet<>(),
                activeMoves = new HashSet<>();

        private final HashMap<RVRegister, HashSet<Move>> moveRelation = new HashMap<>(); // mapping reg to all moves containing the reg
        private final HashMap<RVRegister, RVRegister> aliasMapping = new HashMap<>();

        private AllocatorInstance(AsmFunction asmFunc) {
            this.asmFunc = asmFunc;
        }

        private void build() {
            for (int i = 0; i <= 31; ++i) {
                var reg = PhysicalRegister.get(i);
                preColored.add(reg);
                color.put(reg, i);
                weights.put(reg, Integer.MAX_VALUE);
                degrees.put(reg, 0);
                moveRelation.put(reg, new HashSet<>());
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
                    inst.forEachRegDest(reg -> plus.accept(reg, (int) Math.pow(10, blk.loopDepth)));
                    inst.forEachRegSrc(reg -> plus.accept(reg, (int) Math.pow(10, blk.loopDepth)));
                    inst.forEachRegSrc(reg -> {
                        if (!preColored.contains(reg)) initialList.add(reg);
                    });
                    inst.forEachRegDest(reg -> {
                        if (!preColored.contains(reg)) initialList.add(reg);
                    });
                });
            });
            initialList.forEach(reg -> {
                degrees.put(reg, 0);
                moveRelation.put(reg, new HashSet<>());
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
            if (rv1 == rv2) return;
            singleEdge(rv1, rv2);
            singleEdge(rv2, rv1);
        }

        private boolean hasEdge(RVRegister r1, RVRegister r2) {
            return interferenceGraph.containsKey(r1) && interferenceGraph.get(r1).contains(r2);
        }

        private void optimize(AsmBlock block) {
            var iter = block.instructions.listIterator();
            while (iter.hasNext()) {
                var inst = iter.next();
                if (inst instanceof Move && (coalescedMoves.contains(inst) ||
                        (((Move) inst).rd.getColor() == ((Move) inst).rs1.getColor()))) iter.remove();

            }
        }

        private boolean run() {
            build();
            new LiveAnalyzer(asmFunc).run();
            buildInterfere();
            buildWorkList();
            while (!(simplifyWorkList.isEmpty() && workListMoves.isEmpty() && freezeWorkList.isEmpty() && highDegreeWorkList.isEmpty())) {
                if (!simplifyWorkList.isEmpty()) simplify();
                else if (!workListMoves.isEmpty()) coalesceValidMoves();
                else if (!freezeWorkList.isEmpty()) freeze();
                else selectHighDegree();
            }
            assignColor();
            if (decidedSpillSet.isEmpty()) {
                // end optimization
                asmFunc.blocks.forEach(this::optimize);
                color.forEach((reg, col) -> reg.setColor(PhysicalRegister.get(col)));
                asmFunc.blocks.forEach(block -> {
                    block.instructions.forEach(inst -> {
                        if (inst instanceof LoadMem) ((LoadMem) inst).replaceImm(asmFunc.getStackOffset());
                    });
                });
                return true;
            } else {
                rewriteProgram();
                return false;
            }
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
            degrees.put(reg, deg - 1);
            if (deg == REG_NUM) {
                // enable moves
                enableMoves(reg);
                forEachAdjacent(reg, this::enableMoves);
                // move to correct work list
                highDegreeWorkList.remove(reg);
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
            selectedRegisterStack.add(reg);
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
                highDegreeWorkList.remove(from);
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
            assert selection != null;
            highDegreeWorkList.remove(selection);
            simplifyWorkList.add(selection);
            freezeRelatedMoves(selection);
        }

        private void assignColor() {
            while (!selectedRegisterStack.isEmpty()) {
                var reg = selectedRegisterStack.pop();
                var remainingColor = new HashSet<>(immutableColorList);
                forEachAdjacent(reg, adj -> {
                    var w = getAlias(adj);
                    if (coloredSet.contains(w) || preColored.contains(w)) {
                        remainingColor.remove(color.get(w));
                    }
                });
                if (remainingColor.isEmpty()) {
                    decidedSpillSet.add(reg);
                } else {
                    coloredSet.add(reg);
                    color.put(reg, remainingColor.iterator().next());
                }
            }
            coalescedSet.forEach(reg -> color.put(reg, color.get(getAlias(reg))));
        }

        private void rewriteProgram() {
            decidedSpillSet.forEach(asmFunc::addVarOnStack);
            asmFunc.blocks.forEach(block -> {
                var iter = block.instructions.listIterator();
                while (iter.hasNext()) {
                    var inst = iter.next();
                    inst.forEachRegSrc(reg -> {
                        if (decidedSpillSet.contains(reg)) {
                            if (inst instanceof Move) {
                                iter.set(new LoadMem(((Move) inst).rd, RVInst.WidthType.w, PhysicalRegister.get("sp"), new Imm(asmFunc.getVarOffset(reg))));
                            } else {
                                var tmp = new VirtualRegister("rewrite_" + anonymousCounter++);
                                var loading = new LoadMem(tmp, RVInst.WidthType.w,
                                        PhysicalRegister.get("sp"), new Imm(asmFunc.getVarOffset(reg)));
                                inst.replaceRegSrc(loading.getRd(), reg);
                                // insert before inst
                                iter.previous();
                                iter.add(loading);
                                iter.next();
                                weights.put(tmp, 0);
                            }
                        }
                    });
                    inst.forEachRegDest(reg -> {
                        if (decidedSpillSet.contains(reg)) {
                            if (inst instanceof Move) {
                                iter.set(new StoreMem(RVInst.WidthType.w, PhysicalRegister.get("sp"), ((Move) inst).rs1, new Imm(asmFunc.getVarOffset(reg))));
                            } else {
                                var tmp = new VirtualRegister("rewrite_" + anonymousCounter++);
                                inst.replaceRegDest(tmp, reg);
                                var storing = new StoreMem(RVInst.WidthType.w, PhysicalRegister.get("sp"),
                                        tmp, new Imm(asmFunc.getVarOffset(reg)));
                                iter.add(storing);
                                weights.put(tmp, 0);
                            }
                        }
                    });
                }
            });
        }
    }
}
