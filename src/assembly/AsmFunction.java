package assembly;

import assembly.operand.RVRegister;
import ir.IRFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class AsmFunction {
    public String name;
    public final int parameterCount;
    public ArrayList<AsmBlock> blocks = new ArrayList<>();
    public AsmBlock entry;
    private int stackOffset = 0;
    private final HashMap<RVRegister, Integer> varOffset = new HashMap<>();

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

    public int addVarOnStack(RVRegister reg) {
        varOffset.put(reg, stackOffset);
        stackOffset += 4;
        return stackOffset - 4;
    }

    public int getVarOffset(RVRegister reg) {
        assert varOffset.containsKey(reg);
        return varOffset.get(reg);
    }

    public String tell() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t.globl\t").append(getName()).append("\n");
        builder.append("\t.type\t").append(getName()).append(", @function\n");
        blocks.forEach(block -> builder.append(block.tell()));
        builder.append("\t.size\t").append(getName()).append(", .-").append(getName()).append("\n\n");
        return builder.toString();
    }

    public String getName() {
        return name.replace(".", "_2E_");
    }

}
