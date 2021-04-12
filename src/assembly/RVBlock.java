package assembly;

import assembly.instruction.Jump;
import assembly.instruction.RVBranch;
import assembly.instruction.RVInst;
import assembly.operand.RVRegister;

import java.util.HashSet;
import java.util.LinkedList;

public class RVBlock {

    public String name;
    public final LinkedList<RVInst> instructions = new LinkedList<>();
    public final HashSet<RVBlock> prevs = new HashSet<>();
    public HashSet<RVBlock> nexts = new HashSet<>();
    public final int loopDepth;

    public HashSet<RVRegister> liveOut;

    public RVBlock(RVFunction baseFunc, String name, int loopDepth) {
        this.name = baseFunc.getName() + name.substring(1);
        this.loopDepth = loopDepth;
    }

    public void addInst(RVInst inst) {
        instructions.add(inst);
    }

    public void addPrevBlock(RVBlock prev) {
        prevs.add(prev);
    }

    public void addNextBlock(RVBlock next) {
        nexts.add(next);
    }

    public String getName() {
        return name.replace(".", "_2E_");
    }

    public String tell() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(":\n");
        instructions.forEach(inst -> builder.append("\t").append(inst.tell()).append('\n'));
        return builder.toString();
    }

    public String tellWithoutLast(RVBlock next) {
        if (instructions.size() == 1 && instructions.get(0) instanceof Jump)
            return tell();
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(":\n");
        for (var iter = instructions.listIterator(); iter.hasNext(); ) {
            var inst = iter.next();
            if (iter.hasNext())
                builder.append("\t").append(inst.tell()).append('\n');
            else {
                if (inst instanceof Jump) {
                    if (!(((Jump) inst).getDest() == next))
                        builder.append("\t").append(inst.tell()).append('\n');
                } else if (inst instanceof RVBranch) {
                    if (((RVBranch) inst).falseDest == next) {
                        builder.append("\t").append(((RVBranch) inst).onlyTrueTell()).append('\n');
                    } else
                        builder.append("\t").append(inst.tell()).append('\n');
                }else
                    builder.append("\t").append(inst.tell()).append('\n');
            }
        }
        return builder.toString();
    }
}
