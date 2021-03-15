package optimization.ir;

import ir.IRFunction;
import ir.instruction.Assign;
import ir.instruction.Binary;
import ir.operand.IntConstant;
import misc.pass.IRFunctionPass;

public class ConstantDeducer extends IRFunctionPass {
    public ConstantDeducer(IRFunction f) {
        super(f);
    }

    @Override
    protected void run() {
        irFunc.blocks.forEach(blk -> {
            var iter = blk.insts.listIterator();
            while (iter.hasNext()) {
                var inst = iter.next();
                if (inst instanceof Binary) {
                    var bInst = (Binary) inst;
                    if (bInst.operand1 instanceof IntConstant && bInst.operand2 instanceof IntConstant) {
                        int val1 = ((IntConstant) bInst.operand1).value, val2 = ((IntConstant) bInst.operand2).value, finalResult;
                        switch (bInst.inst) {
                            case add -> finalResult = val1 + val2;
                            case sub -> finalResult = val1 - val2;
                            case mul -> finalResult = val1 * val2;
                            case sdiv -> finalResult = val1 / val2;
                            case srem -> finalResult = val1 % val2;
                            case and -> finalResult = val1 & val2;
                            case or -> finalResult = val1 | val2;
                            case xor -> finalResult = val1 ^ val2;
                            case shl -> finalResult = val1 << val2;
                            case ashr -> finalResult = val1 >> val2;
                            default -> throw new IllegalStateException();
                        }
                        iter.set(new Assign(((Binary) inst).dest, new IntConstant(finalResult)));
                    }
                }
            }
        });
    }
}
