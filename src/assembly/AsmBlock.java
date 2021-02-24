package assembly;

import assembly.instruction.RVInst;
import assembly.operand.RVRegister;

import java.util.HashSet;
import java.util.LinkedList;

public class AsmBlock {

    public String name;
    public final LinkedList<RVInst> instructions = new LinkedList<>();
    public final HashSet<AsmBlock> prevs = new HashSet<>(), nexts = new HashSet<>();

    public HashSet<RVRegister> liveOut;

    public AsmBlock(String name) {
        this.name = name;
    }

    public void addInst(RVInst inst) {
        instructions.add(inst);
    }

    public void addPrev(AsmBlock prev) {
        prevs.add(prev);
    }

    public void addNext(AsmBlock next) {
        nexts.add(next);
    }

    public String getName() {
        return name;
    }

    public String tell() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(":\n");
        instructions.forEach(inst -> builder.append("\t").append(inst.tell()).append('\n'));
        return builder.toString();
    }
}
