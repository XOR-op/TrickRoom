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
    private final Stack<Object[]> recursionStorage = new Stack<>();

    public Inliner(IRFunction f, Set<IRFunction> inlineCandidates, int recursionCall) {
        super(f);
        this.inlineCandidates = inlineCandidates;
        if (recursionCall > 0) {
            for (int i = 0; i < recursionCall; ++i) {
                var pair = new Object[2];
                pair[0] = Cst.inlinePrefix(irFunc.name, irFunc.inlineSerial);
                pair[1] = irFunc.inlineClone(irFunc.inlineSerial++);
                recursionStorage.push(pair);
            }
        }
    }

    public Inliner(IRFunction f, Set<IRFunction> inlineCandidates) {
        this(f, inlineCandidates, 0);
    }

    @Override
    protected void run() {
        irFunc.blocks.forEach(this::replaceInBlock);
        irFunc.blocks.addAll(pendingBlocks);
    }

    private boolean okToInline(IRFunction function) {
        return inlineCandidates.contains(function) && GlobalInliner.inlinePolicy(function);
    }

    private boolean recurOkToInline(Call inst) {
        return inst.function == irFunc && !recursionStorage.isEmpty();
    }

    private void replaceInBlock(IRBlock block) {
        replaceInBlock(block, 0);
    }

    private void replaceInBlock(IRBlock block, int currentCount) {
        var newInst = new LinkedList<IRInst>();
        Call call = null;
        Object[] tuple = null;
        // (entryBlock:IRBlock, exitBlock:IRBlock, blockSet:HashSet<IRBlock>)
        String prefix = null;
        IRFunction inlinedFunc = null;
        // find first inline-available call
        for (var iter = block.insts.iterator(); iter.hasNext(); ) {
            var inst = iter.next();
            if (call == null) {
                if (inst instanceof Call && (recurOkToInline((Call) inst) || okToInline(((Call) inst).function))) {
                    call = (Call) inst;
                    inlinedFunc = call.function;
                    if (call.function == irFunc) {
                        // avoid recursion overlapping
                        var top = recursionStorage.pop();
                        assert top.length == 2 && top[0] instanceof String && top[1] instanceof Object[];
                        prefix = (String) top[0];
                        tuple = (Object[]) top[1];
                    } else {
                        prefix = Cst.inlinePrefix(inlinedFunc.name, irFunc.inlineSerial);
                        tuple = inlinedFunc.inlineClone(irFunc.inlineSerial++);
                        // (entryBlock:IRBlock, exitBlock:IRBlock, blockSet:HashSet<IRBlock>)
                    }
                    assert tuple.length == 3;
                    assert tuple[0] instanceof IRBlock && tuple[1] instanceof IRBlock && tuple[2] instanceof HashSet;
                    iter.remove();
                }
            } else {
                iter.remove();
                newInst.add(inst);
            }
        }
        // inline
        if (call != null) {
            // now can modify irFunc without contradicting to recursion
            var newBlock = block.splitBlockWithInsts(newInst, currentCount);
            if (irFunc.exitBlock == block)
                irFunc.exitBlock = newBlock;
            pendingBlocks.add(newBlock);
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
            replaceInBlock(newBlock, currentCount + 1);
        }
    }
}
