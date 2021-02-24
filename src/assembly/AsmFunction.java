package assembly;

import ir.IRFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class AsmFunction {
    private String name;
    public final int parameterCount;
    public ArrayList<AsmBlock> blocks = new ArrayList<>();
    public AsmBlock entry;
    public int stackOffset = 0;
    public final HashMap<String, Integer> varOffset = new HashMap<>();

    public boolean isBuiltin() {
        return isBuiltin;
    }

    private final boolean isBuiltin;

    public AsmFunction(IRFunction irFunc, boolean isBuiltin) {
        name = irFunc.name;
        this.isBuiltin = isBuiltin;
        parameterCount = irFunc.parameters.size();
    }

    public AsmFunction(IRFunction irFunction) {
        this(irFunction, false);
    }

    public AsmFunction addBlock(AsmBlock block) {
        blocks.add(block);
        return this;
    }

    public void setEntry(AsmBlock entry) {
        this.entry = entry;
    }

    public String tell() {
        StringBuilder builder = new StringBuilder();
        // todo metadata
        builder.append("\t.globl\t").append(name).append("\n");
        builder.append("\t.type\t").append(name).append(", @function\n");
        blocks.forEach(block -> builder.append(block.tell()));
        builder.append("\t.size\t").append(name).append(", .-").append(name).append("\n");
        return builder.toString();
    }


}
