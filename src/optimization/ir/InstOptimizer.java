package optimization.ir;

import ir.IRFunction;
import ir.instruction.Branch;
import ir.instruction.Jump;
import ir.operand.BoolConstant;
import misc.pass.IRFunctionPass;

public class InstOptimizer extends IRFunctionPass {

    public InstOptimizer(IRFunction f) {
        super(f);
    }

    @Override
    protected void run() {
        irFunc.blocks.forEach(block -> {
            if(block.terminatorInst instanceof Branch){
                var br=(Branch) block.terminatorInst;
                var cond=br.condition;
                if(cond instanceof BoolConstant){
                    if(((BoolConstant) cond).value){
                        // go to true branch
                        block.removeFromNext(br.falseBranch);
                        block.terminatorInst=new Jump(br.trueBranch);
                        block.nexts.remove(br.falseBranch);
                    }else {
                        block.removeFromNext(br.trueBranch);
                        block.terminatorInst=new Jump(br.falseBranch);
                        block.nexts.remove(br.trueBranch);
                    }
                }
            }
        });
    }
}
