package assembly;

import assembly.instruction.ControlFlowInst;
import assembly.instruction.Jump;
import assembly.instruction.RVBranch;
import assembly.instruction.Return;
import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;
import ir.IRFunction;

import java.util.*;

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
//        assert !varOffset.containsKey(reg);
        if(varOffset.containsKey(reg))return; // reg as >8 arguments
        stackOffset -= 4;
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

    /*
    private HashMap<RVBlock, RVBlock> blockSort() {
        HashMap<RVBlock, RVBlock> nextMap = new HashMap<>();
        HashMap<RVBlock, Integer> count = new HashMap<>();
        Stack<RVBlock> dfsStack = new Stack<>();
        for (var block : blocks) {
            if (block.instructions.peekLast() instanceof Jump) {
                nextMap.put(block, ((Jump) block.instructions.peekLast()).getDest());
            }
        }
        for (var entry : nextMap.entrySet()) {
            if (!count.containsKey(entry.getKey())) {
                var target = entry.getValue();
                while (!count.containsKey(target) && target != null) {
                    dfsStack.push(target);
                    target = nextMap.get(target);
                }
                int i = target == null ? 1 : count.get(target) + 1;
                for (; !dfsStack.isEmpty(); ++i) {
                    count.put(dfsStack.pop(), i);
                }
                count.put(entry.getKey(), 2);
            }
        }
        HashMap<RVBlock, RVBlock> retValue = new HashMap<>();
    }*/

    private ArrayList<RVBlock> dfsSort() {
        HashSet<RVBlock> visited = new HashSet<>();
        Stack<RVBlock> dfsStack = new Stack<>();
        ArrayList<RVBlock> list = new ArrayList<>();
        dfsStack.push(entry);
        while (!dfsStack.isEmpty()) {
            var block = dfsStack.pop();
            if (visited.contains(block)) continue;
            visited.add(block);
            list.add(block);
            while (block.instructions.peekLast() instanceof Jump) {
                var tblock = ((Jump) block.instructions.peekLast()).getDest();
                if (!visited.contains(tblock)) {
                    visited.add(tblock);
                    list.add(tblock);
                    block = tblock;
                } else {
                    // back edge
                    block = null;
                    break;
                }
            }
            if (block != null) {
                if (block.instructions.peekLast() instanceof RVBranch) {
                    var branch = (RVBranch) block.instructions.peekLast();
                    dfsStack.push(branch.trueDest);
                    dfsStack.push(branch.falseDest);
                } else {
                    assert block.instructions.peekLast() instanceof Return;
                    if (!visited.contains(block)) {
                        visited.add(block);
                        list.add(block);
                    }
                }
            }
        }
        return list;
    }

    public String tell() {
        entry.name = name;
        StringBuilder builder = new StringBuilder();
        builder.append("\t.globl\t").append(getName()).append("\n");
        builder.append("\t.type\t").append(getName()).append(", @function\n");
        var list = dfsSort();
        for (int i = 0; i < list.size() - 1; ++i) {
            builder.append(list.get(i).tellWithoutLast(list.get(i + 1)));
        }
        builder.append(list.get(list.size() - 1).tell());
        builder.append("\t.size\t").append(getName()).append(", .-").append(getName()).append("\n");
        return builder.toString();
    }

    public String getName() {
        return name.replace(".", "_2E_");
    }

}
