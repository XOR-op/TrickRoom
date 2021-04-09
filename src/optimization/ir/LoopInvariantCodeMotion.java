package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.construct.DominanceTracker;
import ir.construct.RegisterTracker;
import ir.instruction.Branch;
import ir.instruction.IRDestedInst;
import ir.instruction.IRInst;
import ir.instruction.Jump;
import misc.pass.IRFunctionPass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Consumer;

public class LoopInvariantCodeMotion extends LoopAnalyzer {


    public LoopInvariantCodeMotion(IRFunction f) {
        super(f);

    }

    @Override
    protected void run() {
        super.run();
        motionAll();
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

    private void findAndMotion(Loop loop, Loop parentLoop) {
        var motionInstSet = new HashSet<IRInst>();
        Consumer<IRBlock> perBlockLambda = irBlock -> {
            for (var iter = irBlock.insts.iterator(); iter.hasNext(); ) {
                var irInst = iter.next();
                if (irInst.containsDest() && !irInst.hasSideEffect()) {
                    var destInst = (IRDestedInst) irInst;
                    // def outside
                    boolean ok = true;
                    for (var reg : destInst.getRegSrc()) {
                        if (!usageTracker.isParameter(reg) &&
                                (loop.loopBlock.contains(usageTracker.querySingleDefBlock(reg))
                                        && !motionInstSet.contains(usageTracker.querySingleDef(reg)))) {
                            ok = false;
                            break;
                        }
                    }
                    if (!ok) continue;
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
            // add pre-header and perform code motion
            var preheader = new IRBlock(loop.header.blockName.substring(2) + "PH");
            var loopHeader = loop.header;
            if (irFunc.entryBlock == loopHeader) irFunc.entryBlock = preheader;
            preheader.phiCollection = loopHeader.phiCollection;
            preheader.prevs = loopHeader.prevs;
            loopHeader.phiCollection = new HashSet<>();
            loopHeader.prevs = new HashSet<>();
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
            preheader.insts.addAll(motionInstSet);
            irFunc.addBlock(preheader);
            preheader.insts.forEach(inst -> usageTracker.updateInstBelonging(inst, preheader));
            if (parentLoop != null)
                parentLoop.loopBlock.add(preheader);
        }
    }

}
