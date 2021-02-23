package pass;

import ir.BasicBlock;
import ir.IRFunction;
import ir.instruction.Assign;
import ir.operand.IROperand;
import ir.operand.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SSADestructor extends FunctionPass {

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

    private final HashMap<BasicBlock, ParallelCopy> blockToCopy = new HashMap<>();

    public SSADestructor(IRFunction ssaFunc) {
        super(ssaFunc);
    }

    @Override
    public void run() {
        // todo transformed-SSA support
        convSSADestruct();
    }

    private void convSSADestruct() {
        var toAdd = new ArrayList<BasicBlock>();
        irFunc.blocks.forEach(oneBlk -> {
            HashMap<BasicBlock, ParallelCopy> prevToCopy = new HashMap<>();
            // split critical edges
            oneBlk.prevs.forEach(onePrev -> {
                ParallelCopy pc = new ParallelCopy();
                if (onePrev.nexts.size() > 1) {
                    BasicBlock inter = oneBlk.createBetweenPrev(onePrev);
                    prevToCopy.put(onePrev, pc);
                    blockToCopy.put(inter, pc);
                    toAdd.add(inter);
                } else {
                    prevToCopy.put(onePrev, pc);
                    blockToCopy.put(onePrev, pc);
                }
            });
            oneBlk.phiCollection.forEach(onePhi -> {
                onePhi.arguments.forEach(s -> prevToCopy.get(s.block).addPair(onePhi.dest, s.val));
            });
            oneBlk.phiCollection = new HashSet<>();
        });
        // add inter block to func
        toAdd.forEach(b -> irFunc.addBlock(b));
        toAdd.forEach(this::parallelCopyElimination);
    }

    private void parallelCopyElimination(BasicBlock theBlock) {
        var theCopy = blockToCopy.get(theBlock);
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
