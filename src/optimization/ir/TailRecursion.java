package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Call;
import ir.instruction.Jump;
import ir.instruction.Phi;
import ir.operand.IROperand;
import ir.operand.Register;
import misc.pass.IRFunctionPass;

import java.util.ArrayList;
import java.util.HashMap;

public class TailRecursion extends IRFunctionPass {
    public TailRecursion(IRFunction f) {
        super(f);
    }

    @Override
    protected void run() {
        if (irFunc.exitBlock.prevs.size() > 1) {
            HashMap<IRBlock, Call> tailCall = new HashMap<>();
            // for recursive function, there must be at least 2 return statement
            irFunc.exitBlock.prevs.forEach(block -> {
                if (block.insts.size() > 0 && block.insts.getLast() instanceof Call && ((Call) block.insts.getLast()).function == irFunc) {
                    if (block.terminatorInst instanceof Jump && ((Jump) block.terminatorInst).target == irFunc.exitBlock) {
                        tailCall.put(block, (Call) block.insts.getLast());
                        block.insts.removeLast();
                    }
                }
            });
            if (!tailCall.isEmpty()) {
                // CFG structure
                var pseudoEntry = irFunc.entryBlock;
                var newEntry = new IRBlock("new" + pseudoEntry.blockName);
                newEntry.setJumpTerminator(pseudoEntry);
                irFunc.entryBlock = newEntry;
                irFunc.addBlock(newEntry);

                // collect arguments
                var paraToPhi = new ArrayList<HashMap<IRBlock, IROperand>>();
                for (int i = 0; i < irFunc.parameters.size(); ++i) paraToPhi.add(new HashMap<>());
                tailCall.forEach((b, call) -> {
                    b.removeFromNext(b.nexts.iterator().next());
                    b.nexts.clear();
                    for (int i = 0; i < call.args.size(); ++i) {
                        paraToPhi.get(i).put(b, call.args.get(i));
                    }
                    b.terminatorInst = null;
                    b.setJumpTerminator(pseudoEntry);
                });

                // rename and add phis
                for (int i = 0; i < paraToPhi.size(); ++i) {
                    var argPhi = new Phi(irFunc.parameters.get(i));
                    irFunc.parameters.set(i, new Register(argPhi.dest.type, "_par_" + argPhi.dest.getName()));
                    argPhi.append(irFunc.parameters.get(i), newEntry);
                    paraToPhi.get(i).forEach((b, op) -> argPhi.append(op, b));
                    pseudoEntry.appendPhi(argPhi);
                }
                // fix invoked function
                irFunc.invokedFunctions.clear();
                irFunc.blocks.forEach(b -> {
                    b.insts.forEach(inst -> {
                        if (inst instanceof Call) {
                            var f = ((Call) inst).function;
                            if (irFunc.invokedFunctions.containsKey(f))
                                irFunc.invokedFunctions.put(f, irFunc.invokedFunctions.get(f) + 1);
                            else
                                irFunc.invokedFunctions.put(f, 1);
                        }
                    });
                });
            }
        }
    }
}
