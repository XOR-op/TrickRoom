package optimization.ir;

import ir.IRFunction;
import ir.instruction.Binary;
import ir.instruction.Branch;
import ir.instruction.Jump;
import ir.operand.BoolConstant;
import ir.operand.IntConstant;
import misc.pass.IRFunctionPass;

import java.util.HashMap;
import java.util.HashSet;

public class InstOptimizer extends IRFunctionPass {

    public InstOptimizer(IRFunction f) {
        super(f);
    }

    private HashMap<Integer, Integer> twoPower = new HashMap<>();

    @Override
    protected void run() {
        for (int i = 0; i <= 31; ++i) {
            twoPower.put(1 << i, i);
        }
        irFunc.blocks.forEach(block -> {
            if (block.terminatorInst instanceof Branch) {
                var br = (Branch) block.terminatorInst;
                var cond = br.condition;
                if (cond instanceof BoolConstant) {
                    if (((BoolConstant) cond).value) {
                        // go to true branch
                        block.removeFromNext(br.falseBranch);
                        block.terminatorInst = new Jump(br.trueBranch);
                        block.nexts.remove(br.falseBranch);
                    } else {
                        block.removeFromNext(br.trueBranch);
                        block.terminatorInst = new Jump(br.falseBranch);
                        block.nexts.remove(br.trueBranch);
                    }
                }
            }
            for (var iter = block.insts.listIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if (inst instanceof Binary) {
                    var bInst = (Binary) inst;
                    if (bInst.operand2 instanceof IntConstant) {
                        var val = ((IntConstant) bInst.operand2).value;
                        if (bInst.inst == Binary.BinInstEnum.mul && twoPower.containsKey(val)) {
                            iter.set(new Binary(Binary.BinInstEnum.shl, bInst.dest,
                                    bInst.operand1, new IntConstant(twoPower.get(val))));
                        } else if (bInst.inst == Binary.BinInstEnum.sdiv && twoPower.containsKey(val)) {
                            iter.set(new Binary(Binary.BinInstEnum.ashr, bInst.dest,
                                    bInst.operand1, new IntConstant(twoPower.get(val))));

                        }
                    }
                }
            }
        });
    }
}
