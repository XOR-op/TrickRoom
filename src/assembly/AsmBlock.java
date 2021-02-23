package assembly;

import assembly.instruction.RVInst;

import java.util.ArrayList;
import java.util.HashSet;

public class AsmBlock {
    private String name;
    private final ArrayList<RVInst> instructions = new ArrayList<>();
    private final HashSet<AsmBlock> prevs = new HashSet<>(), nexts = new HashSet<>();

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

    public String tell() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(":\n");
        instructions.forEach(inst -> builder.append("\t").append(inst.tell()).append('\n'));
        return builder.toString();
    }
}
