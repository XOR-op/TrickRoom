package ir.construct;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Assign;
import ir.operand.IROperand;
import ir.operand.Register;
import misc.pass.IRFunctionPass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SSADestructor extends IRFunctionPass {

    private static class ParallelCopy {
        public final HashSet<Assign> copyList = new HashSet<>();
        private final HashSet<Register> srcCollection = new HashSet<>();

        public void addPair(Register dest, IROperand operand) {
            copyList.add(new Assign(dest, operand));
            if (operand instanceof Register)
                srcCollection.add((Register) operand);
        }

        public boolean notLooped(Assign ass) {
            return !srcCollection.contains(ass.dest);
        }
    }

    private final HashMap<IRBlock, ParallelCopy> blockToCopy = new HashMap<>();

    public SSADestructor(IRFunction ssaFunc) {
        super(ssaFunc);
    }

    @Override
    protected void run() {
        destruct();
    }

    private void destruct() {
        // avoid ConcurrentModificationException
        var toAdd = new ArrayList<IRBlock>();
        irFunc.blocks.forEach(oneBlk -> {
            HashMap<IRBlock, ParallelCopy> prevToCopy = new HashMap<>();
            var prevToAdd = new HashMap<IRBlock, IRBlock>();
            // split critical edges
            oneBlk.prevs.forEach(onePrev -> {
                ParallelCopy pCopy = new ParallelCopy();
                if (onePrev.nexts.size() > 1) {
                    var inter = new IRBlock("F" + onePrev.blockName.substring(2) + "T" + oneBlk.blockName.substring(2),
                            oneBlk.loopDepth);
                    prevToAdd.put(onePrev, inter);
                    prevToCopy.put(onePrev, pCopy);
                    blockToCopy.put(inter, pCopy);
                    toAdd.add(inter);
                } else {
                    prevToCopy.put(onePrev, pCopy);
                    blockToCopy.put(onePrev, pCopy);
                }
            });
            prevToAdd.forEach(oneBlk::createBetweenPrev);
            oneBlk.phiCollection.forEach(onePhi -> {
                onePhi.arguments.forEach(s -> prevToCopy.get(s.block).addPair(onePhi.dest, s.val));
            });
            oneBlk.phiCollection.clear();
        });
        // add inter block to func
        toAdd.forEach(b -> irFunc.addBlock(b));
        blockToCopy.forEach(this::parallelCopyElimination);
    }

    private void parallelCopyElimination(IRBlock theBlock, ParallelCopy theCopy) {
        // append to block's tail
        boolean flag;
        do {
            flag = false;
            for (var iter = theCopy.copyList.iterator(); iter.hasNext(); ) {
                var cur = iter.next();
                if (theCopy.notLooped(cur)) {
                    iter.remove();
                    theBlock.appendInst(cur);
                }
            }
            // detect loop
            for (var iter = theCopy.copyList.iterator(); iter.hasNext(); ) {
                var cur = iter.next();
                if (!cur.dest.equals(cur.src)) {
                    iter.remove();
                    flag = true;
                    var substitute = new Assign(new Register(cur.dest.type), cur.src);
                    theBlock.appendInst(substitute);
                    theCopy.copyList.forEach(each -> {
                        if (cur.dest.equals(each.src))
                            each.src = substitute.dest;
                    });
                    break;
                }
            }
        } while (flag);
    }
}
