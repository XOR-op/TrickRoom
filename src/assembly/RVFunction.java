package assembly;

import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;
import ir.IRFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class RVFunction {
    public String name;
    public final int parameterCount;
    public ArrayList<RVBlock> blocks = new ArrayList<>();
    public RVBlock entry;

    private int stackOffset = 0;
    private int curOffset = 0;
    private final HashMap<RVRegister, Integer> varOffset = new HashMap<>();
    public final HashMap<String, VirtualRegister> nameToVirReg = new HashMap<>();

    public boolean isBuiltin() {
        return isBuiltin;
    }

    private final boolean isBuiltin;

    public RVFunction(IRFunction irFunc, boolean isBuiltin) {
        name = irFunc.name;
        this.isBuiltin = isBuiltin;
        parameterCount = irFunc.parameters.size();
        for (int i = 8; i < parameterCount; ++i) {
            var reg = new VirtualRegister(irFunc.parameters.get(i));
            nameToVirReg.put(irFunc.parameters.get(i).getName(), reg);
        }
    }

    public RVFunction(IRFunction irFunction) {
        this(irFunction, false);
    }

    public RVFunction addBlock(RVBlock block) {
        blocks.add(block);
        return this;
    }

    public void setEntry(RVBlock entry) {
        this.entry = entry;
    }

    public void addVarOnStack(RVRegister reg) {
        assert !varOffset.containsKey(reg);
        stackOffset -= 4;
//        L.l("Stack:"+reg.toString()+"@"+System.identityHashCode(reg));
        varOffset.put(reg, stackOffset);
    }

    public int getVarOffset(RVRegister reg) {
        assert varOffset.containsKey(reg);
        return varOffset.get(reg);
    }

    public int getStackOffset() {
        var so = -stackOffset;
        return so % 16 == 0 ? so : (so / 16) * 16 + 16;
    }

    public String tell() {
        entry.name = name;
        StringBuilder builder = new StringBuilder();
        builder.append("\t.globl\t").append(getName()).append("\n");
        builder.append("\t.type\t").append(getName()).append(", @function\n");
        blocks.forEach(block -> builder.append(block.tell()));
        builder.append("\t.size\t").append(getName()).append(", .-").append(getName()).append("\n");
        return builder.toString();
    }

    public String getName() {
        return name.replace(".", "_2E_");
    }

}
