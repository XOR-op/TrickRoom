package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Assign;
import ir.instruction.Call;
import ir.instruction.IRInst;
import ir.instruction.Ret;
import ir.operand.Register;
import misc.Cst;
import misc.pass.IRFunctionPass;

import java.util.*;

public class Inliner extends IRFunctionPass {
    private final Set<IRFunction> inlineCandidates;
    private final List<IRBlock> pendingBlocks = new LinkedList<>();

    public Inliner(IRFunction f, Set<IRFunction> inlineCandidates) {
        super(f);
        this.inlineCandidates = inlineCandidates;
    }

    @Override
    protected void run() {
        irFunc.blocks.forEach(this::replaceInBlock);
        irFunc.blocks.addAll(pendingBlocks);
    }

    private boolean okToInline(IRFunction function){
        return inlineCandidates.contains(function)&&GlobalInliner.inlinePolicy(function);
    }

    private void replaceInBlock(IRBlock block){
        replaceInBlock(block,0);
    }

    private void replaceInBlock(IRBlock block, int currentCount) {
        var newInst = new LinkedList<IRInst>();
        Call call = null;
        // find first inline-available call
        for (var iter = block.insts.iterator(); iter.hasNext(); ) {
            var inst = iter.next();
            if (call == null) {
                if (inst instanceof Call && okToInline(((Call) inst).function)) {
                    call = (Call) inst;
                    iter.remove();
                }
            } else {
                iter.remove();
                newInst.add(inst);
            }
        }
        // inline
        if (call != null) {
            var inlinedFunc = call.function;
            var newBlock = block.splitBlockWithInsts(newInst,currentCount);
            if (irFunc.exitBlock == block)
                irFunc.exitBlock = newBlock;
            pendingBlocks.add(newBlock);
            var prefix = Cst.inlinePrefix(inlinedFunc.name, irFunc.inlineSerial);
            var tuple = inlinedFunc.inlineClone(irFunc.inlineSerial++);
            // (entryBlock:IRBlock, exitBlock:IRBlock, blockSet:HashSet<IRBlock>)
            assert tuple.length == 3;
            assert tuple[0] instanceof IRBlock && tuple[1] instanceof IRBlock && tuple[2] instanceof HashSet;
            pendingBlocks.addAll((HashSet<IRBlock>) tuple[2]);
            // arguments
            for (int i = 0; i < inlinedFunc.parameters.size(); ++i) {
                block.appendInst(new Assign(new Register(call.args.get(i).type,
                        prefix + inlinedFunc.parameters.get(i).getName()), call.args.get(i)));
            }
            block.setJumpTerminator((IRBlock) tuple[0]);
            // return value
            var exitBlock = (IRBlock) tuple[1];
            if (call.containsDest()) {
                var returnStmt = (Ret) exitBlock.terminatorInst;
                exitBlock.appendInst(new Assign(call.dest, returnStmt.value));
            }
            exitBlock.terminatorInst = null;
            exitBlock.setJumpTerminator(newBlock);
            replaceInBlock(newBlock,currentCount+1);
        }
    }
}
