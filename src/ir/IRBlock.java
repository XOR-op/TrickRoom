package ir;

import ir.instruction.*;
import ir.operand.IROperand;
import ir.operand.Register;
import misc.Cst;

import java.util.*;

public class IRBlock {
    public String blockName;
    public LinkedList<IRInst> insts = new LinkedList<>();
    public IRInst terminatorInst = null;
    public Set<Phi> phiCollection = new HashSet<>();
    public Set<IRBlock> prevs = new HashSet<>(), nexts = new HashSet<>();
    //    public Set<String> definition = new HashSet<>();
    public int loopDepth = 0;

    public IRBlock(String name) {
        blockName = "l_" + name;
    }

    public IRBlock appendInst(IRInst newInst) {
        insts.add(newInst);
        return this;
    }

    public void createBetweenPrev(IRBlock prev, IRBlock newBlk) {
        assert prevs.contains(prev);
        prevs.remove(prev);
        prevs.add(newBlk);
        ((Branch) prev.terminatorInst).replaceBranch(this, newBlk);
        prev.nexts.remove(this);
        prev.nexts.add(newBlk);
        newBlk.prevs.add(prev);
        newBlk.setJumpTerminator(this);
    }

    public void setNextBlock(IRBlock next) {
        next.prevs.add(this);
        nexts.add(next);
    }

    public void setNextBlock(IRBlock next1, IRBlock next2) {
        setNextBlock(next1);
        setNextBlock(next2);
    }

    public void setBranchTerminator(IROperand cond, IRBlock trueBlock, IRBlock falseBlock) {
        setTerminator(new Branch(cond, trueBlock, falseBlock));
        setNextBlock(trueBlock, falseBlock);
    }

    public void setJumpTerminator(IRBlock dst) {
        setTerminator(new Jump(dst));
        setNextBlock(dst);
    }

    public void setRetTerminator(IROperand ope) {
        setTerminator(new Ret(ope));
    }

    public void setVoidRetTerminator() {
        setTerminator(Ret.voidRet());
    }

    public void appendPhi(Phi phi) {
        phiCollection.add(phi);
    }

    public void removeInst(IRInst inst) {
        if (inst instanceof Phi) phiCollection.remove(inst);
        else insts.remove(inst);
    }

    public void insertInstFromHead(IRInst inst) {
        this.insts.addFirst(inst);
    }

    private void setTerminator(IRInst terInst) {
        assert terminatorInst == null && terInst.isTerminal();
        terminatorInst = terInst;
    }

    public IRBlock splitBlockWithInsts(LinkedList<IRInst> instsOfNewBlock, int count) {
        var newName = count == 0 ? (Cst.SPLIT_PREFIX + "000" + blockName) :
                (Cst.SPLIT_PREFIX + "0".repeat(Math.max(0, (int) (2 - Math.floor(Math.log10(count)))))
                        + count + blockName.substring(Cst.SPLIT_PREFIX.length() + 5));
        var newBlock = new IRBlock(newName);
        newBlock.insts = instsOfNewBlock;
        nexts.forEach(successor -> {
            successor.prevs.remove(this);
            successor.prevs.add(newBlock);
            successor.phiCollection.forEach(phi -> {
                phi.arguments.forEach(s -> {
                    if (s.block == this)
                        s.block = newBlock;
                });
            });
        });
        newBlock.nexts = nexts;
        nexts = new HashSet<>();
        newBlock.terminatorInst = terminatorInst;
        terminatorInst = null;
        return newBlock;
    }

    public void removeFromNext(IRBlock next) {
        for (var phiIter = next.phiCollection.iterator(); phiIter.hasNext(); ) {
            var phi = phiIter.next();
            phi.arguments.removeIf(source -> source.block == this);
            if (phi.arguments.size() == 1) {
                phiIter.remove();
                next.insertInstFromHead(new Assign(phi.dest, phi.arguments.get(0).val));
            }
        }
        next.prevs.remove(this);
    }

    public String getBlockName() {
        return blockName;
    }

    public boolean hasTerminal() {
        return terminatorInst != null;
    }

    public String tell() {
        var builder = new StringBuilder();
        builder.append(blockName).append(':');
        if (prevs.size() != 0) {
            builder.append("\t\t\t\t\t\t\t; prev: ");
            prevs.forEach(pre -> builder.append('%').append(pre.blockName).append(','));
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append('\n');
        phiCollection.forEach(p -> builder.append('\t').append(p.tell()).append('\n'));
        insts.forEach(i -> builder.append('\t').append(i.tell()).append('\n'));
        builder.append('\t').append(terminatorInst.tell()).append("\n");
        return builder.toString();
    }

    @Override
    public String toString() {
        return blockName;
    }

    public boolean selfTest() {
        if (terminatorInst instanceof Jump) {
            assert nexts.size() == 1 && nexts.contains(((Jump) terminatorInst).target);
        } else if (terminatorInst instanceof Branch) {
            assert nexts.size() == 2 && nexts.contains(((Branch) terminatorInst).trueBranch) && nexts.contains(((Branch) terminatorInst).falseBranch);
        }
        prevs.forEach(p->{
            assert p.nexts.contains(this);
        });
        return true;
    }
}
