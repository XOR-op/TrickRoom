package ir;

import ir.instruction.Branch;
import ir.instruction.IRInst;
import ir.instruction.Jump;
import ir.operand.Register;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BasicBlock {
    private String blockName;
    private Set<BasicBlock> prevs = new HashSet<>(), nexts = new HashSet<>();
    private ArrayList<IRInst> insts = new ArrayList<>();
    private IRInst terminatorInst = null;

    public BasicBlock(String name) {
        blockName = "l."+name;
    }

    public void appendInst(IRInst newInst) {
        assert !hasTerminal();
        insts.add(newInst);
    }

    public void appendNext(BasicBlock next){
        next.prevs.add(this);
        nexts.add(next);
    }
    public void appendNext(BasicBlock next1,BasicBlock next2){
        appendNext(next1);
        appendNext(next2);
    }

    public void setBranchTerminator(Register cond,BasicBlock trueBlock,BasicBlock falseBlock){
        setTerminator(new Branch(cond,trueBlock,falseBlock));
        appendNext(trueBlock,falseBlock);
    }

    public void setJumpTerminator(BasicBlock dst){
        setTerminator(new Jump(dst));
        appendNext(dst);
    }

    public BasicBlock split(String name) {
        /*
         * split this basic block into two blocks
         * the prior contains all instructions of the original one, while the later contains nothing
         */
        var later = new BasicBlock(name);
        later.nexts = this.nexts;
        this.nexts = new HashSet<>();
        this.nexts.add(later);
        later.prevs.add(this);
        return later;
    }

    public void setTerminator(IRInst terInst) {
        assert terminatorInst == null && terInst.isTerminal();
        terminatorInst = terInst;
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
        if(prevs.size()!=0) {
            builder.append("\t\t\t\t\t\t\t; prev: ");
            prevs.forEach(pre -> builder.append('%').append(pre.blockName).append(','));
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append('\n');
        insts.forEach(i -> builder.append('\t').append(i.tell()).append('\n'));
        builder.append('\t').append(terminatorInst.tell()).append("\n");
        return builder.toString();
    }
}
