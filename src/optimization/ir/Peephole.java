package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Binary;
import ir.instruction.Branch;
import ir.instruction.Jump;
import ir.operand.*;
import misc.pass.IRFunctionPass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Peephole extends IRFunctionPass {

    public Peephole(IRFunction f) {
        super(f);
    }

    private HashMap<Integer, Integer> twoPower = new HashMap<>();

    private final static HashSet<Binary.BinInstEnum> algebraicData = new HashSet<>();

    static {
        algebraicData.add(Binary.BinInstEnum.mul);
        algebraicData.add(Binary.BinInstEnum.add);
        algebraicData.add(Binary.BinInstEnum.or);
        algebraicData.add(Binary.BinInstEnum.and);
        algebraicData.add(Binary.BinInstEnum.xor);
        algebraicData.add(Binary.BinInstEnum.shl);
        algebraicData.add(Binary.BinInstEnum.ashr);
    }

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
            instReplace(block);
            algebraicAssociation(block);
        });
    }

    private void instReplace(IRBlock block) {
        for (var iter = block.insts.listIterator(); iter.hasNext(); ) {
            var inst = iter.next();
            if (inst instanceof Binary) {
                var bInst = (Binary) inst;
                if (bInst.operand2 instanceof IntConstant) {
                    var val = ((IntConstant) bInst.operand2).value;
                    if (bInst.op == Binary.BinInstEnum.mul && twoPower.containsKey(val)) {
                        iter.set(new Binary(Binary.BinInstEnum.shl, bInst.dest,
                                bInst.operand1, new IntConstant(twoPower.get(val))));
                    } else if (bInst.op == Binary.BinInstEnum.sdiv && twoPower.containsKey(val)) {
                        iter.set(new Binary(Binary.BinInstEnum.ashr, bInst.dest,
                                bInst.operand1, new IntConstant(twoPower.get(val))));

                    }
                }
            }
        }
    }

    private boolean samePriority(Binary.BinInstEnum op1, Binary.BinInstEnum op2) {
        return op1 == op2 && algebraicData.contains(op1);
    }

    private Binary calculate(Register dest, Register reg, Binary.BinInstEnum op1, IRConstant val1, Binary.BinInstEnum op2, IRConstant val2) {
        if (val1 instanceof IntConstant) {
            IntConstant val;
            int ival1 = ((IntConstant) val1).value, ival2 = ((IntConstant) val2).value;
            switch (op1) {
                case add, shl, ashr -> val = new IntConstant(ival1 + ival2);
                case mul -> val = new IntConstant(ival1 * ival2);
                case or -> val = new IntConstant(ival1 | ival2);
                case and -> val = new IntConstant(ival1 & ival2);
                case xor -> val = new IntConstant(ival1 ^ ival2);
                default -> throw new IllegalStateException();
            }
            return new Binary(op1, dest, reg, val);
        } else {
            assert val1 instanceof BoolConstant;
            BoolConstant val;
            boolean bval1 = ((BoolConstant) val1).value, bval2 = ((BoolConstant) val2).value;
            switch (op1) {
                case or -> val = new BoolConstant(bval1 | bval2);
                case and -> val = new BoolConstant(bval1 & bval2);
                case xor -> val = new BoolConstant(bval1 ^ bval2);
                default -> throw new IllegalStateException();
            }
            return new Binary(op1, dest, reg, val);
        }
    }

    private void algebraicAssociation(IRBlock block) {
        Binary last = null;
        for (var iter = block.insts.listIterator(); iter.hasNext(); ) {
            var inst = iter.next();
            if (inst instanceof Binary) {
                var bInst = (Binary) inst;
                if (last == null) {
                    if (algebraicData.contains(bInst.op) && bInst.operand2 instanceof IRConstant
                            && !(bInst.operand2 instanceof UndefConstant)) last = bInst;
                } else {
                    if (bInst.operand2 instanceof IRConstant && !(bInst.operand2 instanceof UndefConstant)) {
                        assert last.operand1 instanceof Register && bInst.operand1 instanceof Register;
                        if (samePriority(last.op, bInst.op) && (last.dest).sameIdentifier(bInst.operand1)) {
                            bInst = calculate(bInst.dest, (Register) last.operand1, last.op, (IRConstant) last.operand2, bInst.op, (IRConstant) bInst.operand2);
                            iter.set(bInst);
                        }
                        last = bInst;
                    } else last = null;
                }
            } else last = null;
        }
    }
}
