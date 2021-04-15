package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.IRInfo;
import ir.instruction.*;
import ir.operand.GlobalVar;
import ir.operand.Register;
import misc.analysis.AliasAnalyzer;
import misc.analysis.FuncCallAnalyzer;
import misc.analysis.LoopAnalyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;

public class LoopInvariantCodeMotion extends LoopAnalyzer {
    private final AliasAnalyzer aliasAnalyzer;
    private final FuncCallAnalyzer funcCallAnalyzer;
    private final Set<IRFunction> dfsInlineVisited = new HashSet<>();
    private final IRInfo info;

    public LoopInvariantCodeMotion(IRFunction f, IRInfo info) {
        super(f);
        this.info = info;
        aliasAnalyzer = new AliasAnalyzer(f, info);
        funcCallAnalyzer = new FuncCallAnalyzer(info);
    }

    @Override
    protected void run() {
        super.run();
        aliasAnalyzer.invoke();
        funcCallAnalyzer.invoke();
        dfsInline(info.getInit());
        motionAll();
    }

    private boolean dfsInline(IRFunction func) {
        var hasSideEffect = false;
        if (!dfsInlineVisited.contains(func)) {
            dfsInlineVisited.add(func);
            for (IRFunction f : funcCallAnalyzer.callGraph.get(func)) {
                if (!dfsInlineVisited.contains(f))
                    hasSideEffect |= dfsInline(f);
            }
        }
        if (!hasSideEffect) {
            // search for side-effected instructions
            for (var block : func.blocks) {
                for (var inst : block.insts) {
                    if (inst instanceof Load || inst instanceof Store) {
                        func.hasSideEffect = true;
                        return true;
                    }
                }
            }
        }
        func.hasSideEffect = hasSideEffect;
        return hasSideEffect;
    }


    private void motionAll() {
        rootLoops.forEach(rootLoop -> loopDFS(rootLoop, null));
    }

    private void loopDFS(Loop loop, Loop parent) {
        if (!loop.subLoop.isEmpty()) {
            loop.subLoop.forEach(sub -> loopDFS(sub, loop));
        }
        findAndMotion(loop, parent);
    }

    private class AliasPolicy {
        boolean safeAlias = true;
        private final HashSet<IRInst> noIntervene = new HashSet<>();

        public AliasPolicy(Loop loop) {
            // naive alias
            boolean f = false;
            HashMap<IRInst, Register> memInsts = new HashMap<>();
            for (var block : loop.loopBlock) {
                if (f) break;
                if (usageTracker.sideEffectInst.containsKey(block)) {
                    for (var i : usageTracker.sideEffectInst.get(block)) {
                        if (i instanceof Call && ((Call) i).function.hasSideEffect) {
                            f = true;
                            safeAlias = false;
                            break;
                        } else if (i instanceof Load || i instanceof Store) {
                            memInsts.put(i, (Register) (i instanceof Load ? ((Load) i).address : ((Store) i).address));
                        }
                    }
                }
            }
            if (safeAlias) {
                for (var iter = memInsts.entrySet().iterator(); iter.hasNext(); ) {
                    var pair = iter.next();
                    var inst = pair.getKey();
                    var addr = pair.getValue();
                    boolean noInterveneFlag = true;
                    for (var anoPair : memInsts.entrySet()) {
                        if (anoPair.getKey() != inst) {
                            if (aliasAnalyzer.check(anoPair.getValue().identifier(), addr.identifier()) != AliasAnalyzer.stat.NEVER
                                    && (inst instanceof Store || anoPair.getKey() instanceof Store)) {
                                noInterveneFlag = false;
                                break;
                            }
                        }
                    }
                    if (noInterveneFlag) {
                        iter.remove();
                        noIntervene.add(inst);
                    }
                }
            }
        }

        public boolean okToMotion(IRInst inst, Loop loop) {
            return safeAlias && noIntervene.contains(inst) && instDefOutside(inst, loop, new LinkedList<>());
        }
    }

    private boolean instDefOutside(IRInst destInst, Loop loop, LinkedList<IRInst> motionInstSet) {
        for (var reg : destInst.getRegSrc()) {
            if (!(reg instanceof GlobalVar) && !usageTracker.isParameter(reg) &&
                    (loop.loopBlock.contains(usageTracker.querySingleDefBlock(reg))
                            && !motionInstSet.contains(usageTracker.querySingleDef(reg)))) {
                return false;
            }
        }
        return true;
    }

    private void findAndMotion(Loop loop, Loop parentLoop) {
        var motionInstSet = new LinkedList<IRInst>();
        var policy = new AliasPolicy(loop);
        Consumer<IRBlock> perBlockLambda = irBlock -> {
            for (var iter = irBlock.insts.iterator(); iter.hasNext(); ) {
                var irInst = iter.next();
                if ((!irInst.hasSideEffect() && instDefOutside(irInst, loop, motionInstSet)) || (irInst instanceof Load && policy.okToMotion(irInst, loop))) {
                    motionInstSet.add(irInst);
                    iter.remove();
                }
            }
        };
        for (var block : loop.loopBlock) {
            if (loop.subLoop.isEmpty()) perBlockLambda.accept(block);
            else {
                boolean notInSub = true;
                for (var subLoop : loop.subLoop) {
                    if (subLoop.loopBlock.contains(block)) {
                        notInSub = false;
                        break;
                    }
                }
                if (notInSub)
                    perBlockLambda.accept(block);
            }
        }
        if (!motionInstSet.isEmpty()) {
            motion(motionInstSet, loop, parentLoop);
        }

    }

    private void motion(LinkedList<IRInst> motionInstSet, Loop loop, Loop parentLoop) {
        // add pre-header and perform code motion
        var preheader = new IRBlock(loop.header.blockName.substring(2) + "PH");
        var loopHeader = loop.header;
        if (irFunc.entryBlock == loopHeader) irFunc.entryBlock = preheader;
        // block relation
        preheader.prevs = loopHeader.prevs;
        preheader.prevs.remove(loop.backStart);
        loopHeader.prevs = new HashSet<>();
        loopHeader.prevs.add(loop.backStart);
        preheader.setJumpTerminator(loopHeader);
        preheader.prevs.forEach(prevBlock -> {
            prevBlock.nexts.remove(loopHeader);
            prevBlock.nexts.add(preheader);
            if (prevBlock.terminatorInst instanceof Jump) {
                ((Jump) prevBlock.terminatorInst).target = preheader;
            } else {
                assert prevBlock.terminatorInst instanceof Branch;
                var br = (Branch) prevBlock.terminatorInst;
                if (br.trueBranch == loopHeader) br.trueBranch = preheader;
                else {
                    assert br.falseBranch == loopHeader;
                    br.falseBranch = preheader;
                }
            }
        });

        // deal with phi
        preheader.phiCollection = loopHeader.phiCollection;
        loopHeader.phiCollection = new HashSet<>();
        for (var phiIter = preheader.phiCollection.iterator(); phiIter.hasNext(); ) {
            var phi = phiIter.next();
            for (var iter = phi.arguments.iterator(); iter.hasNext(); ) {
                var ele = iter.next();
                if (ele.block == loop.backStart) {
                    var oldDest = phi.dest;
                    var newPhi = new Phi(oldDest);
                    phi.dest = new Register(oldDest.type, "t_" + oldDest.identifier());
                    newPhi.append(phi.dest, preheader);
                    newPhi.append(ele.val, loop.backStart);
                    loopHeader.phiCollection.add(newPhi);
                    iter.remove();
                    break;
                }
            }
            if (phi.arguments.size() == 1) {
                preheader.insertInstFromHead(new Assign(phi.dest, phi.arguments.get(0).val));
                phiIter.remove();
            }
        }

        // add insts
        preheader.insts.addAll(motionInstSet);
        irFunc.addBlock(preheader);
        preheader.insts.forEach(inst -> usageTracker.updateInstBelonging(inst, preheader));
        if (parentLoop != null) {
            parentLoop.loopBlock.add(preheader);
        }
    }

}
