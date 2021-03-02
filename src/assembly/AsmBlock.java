package assembly;

import assembly.instruction.RVInst;
import assembly.operand.RVRegister;

import java.util.HashSet;
import java.util.LinkedList;

public class AsmBlock {

    public String name;
    public final LinkedList<RVInst> instructions = new LinkedList<>();
    public final HashSet<AsmBlock> prevs = new HashSet<>(), nexts = new HashSet<>();
    public final int loopDepth;

    public HashSet<RVRegister> liveOut;

    public AsmBlock(String name, int loopDepth) {
        this.name = name;
        this.loopDepth = loopDepth;
    }

    public void addInst(RVInst inst) {
        instructions.add(inst);
    }

    public void addPrevBlock(AsmBlock prev) {
        prevs.add(prev);
    }

    public void addNextBlock(AsmBlock next) {
        nexts.add(next);
    }

    public String getName() {
        return name.replace(".","_2E_");
    }

    public String tell() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(":\n");
        instructions.forEach(inst -> builder.append("\t").append(inst.tell()).append('\n'));
        return builder.toString();
    }
}
