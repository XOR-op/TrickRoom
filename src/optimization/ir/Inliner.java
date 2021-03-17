package optimization.ir;

import assembly.instruction.Move;
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

    private void replaceInBlock(IRBlock block) {
        var newInst = new LinkedList<IRInst>();
        Call call = null;
        // find first inline-available call
        for (var iter = block.insts.iterator(); iter.hasNext(); ) {
            var inst = iter.next();
            if (call == null) {
                if (inst instanceof Call && inlineCandidates.contains(((Call) inst).function)) {
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
            var newBlock = block.splitBlockWithInsts(newInst);
            pendingBlocks.add(newBlock);
            var tuple = inlinedFunc.inlineClone();
            // (entryBlock:IRBlock, exitBlock:IRBlock, blockSet:HashSet<IRBlock>)
            assert tuple.length == 3;
            pendingBlocks.addAll((HashSet<IRBlock>) tuple[2]);
            // arguments
            for (int i = 0; i < inlinedFunc.parameters.size(); ++i) {
                block.appendInst(new Assign(new Register(call.args.get(i).type,
                        Cst.inlineRename(inlinedFunc.parameters.get(i).getName(), inlinedFunc.name)), call.args.get(i)));
            }
            block.setJumpTerminator((IRBlock) tuple[0]);
            // return value
            var exitBlock = (IRBlock) tuple[1];
            exitBlock.terminatorInst = null;
            exitBlock.setJumpTerminator(newBlock);
            if (call.containsDest()) {
                var returnStmt = (Ret) exitBlock.terminatorInst;
                exitBlock.appendInst(new Assign(call.dest, returnStmt.value));
            }
            replaceInBlock(newBlock);
        }
    }
}
