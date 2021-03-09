package assembly;

import assembly.construct.GraphRegisterAllocator;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;
import misc.Cst;
import ir.IRFunction;
import ir.IRInfo;
import optimization.assembly.RedundantOptimizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class RVInfo {
    private final HashMap<String, RVFunction> funcCollection = new HashMap<>();
    private final HashMap<String, String> strData = new HashMap<>();
    private final HashMap<String, Integer> globalToSize = new HashMap<>();
    private final IRInfo irInfo;

    public RVInfo(IRInfo irInfo) {
        this.irInfo = irInfo;
        irInfo.forEachFunctionIncludingBuiltin(f -> {
            if (!f.isBuiltin())
                funcCollection.put(f.name, new RVFunction(f));
            else
                funcCollection.put(f.name, new RVFunction(f, true));
        });
        irInfo.getStringLiteral().forEach((k, v) -> {
            strData.put(v.name, v.value);
        });
        irInfo.getGlobalVars().forEach((k, v) -> {
            globalToSize.put(v.getName(), v.type.size());
        });
    }

    public RVFunction getFunc(IRFunction irFunc) {
        return funcCollection.get(irFunc.name);
    }

    public void forEachFunction(Consumer<RVFunction> consumer) {
        funcCollection.forEach((k, f) -> consumer.accept(f));
    }

    public void preOptimize() {
        funcCollection.forEach((k, func) -> {
            if (!func.isBuiltin())
                new RedundantOptimizer(func).invoke();
        });
    }

    public void registerAllocate() {
        funcCollection.forEach((k, func) -> {
            if (!func.isBuiltin()) {
                new GraphRegisterAllocator(func).run();
                new RedundantOptimizer(func).invoke();
            }
        });
    }

    public void renameMain() {
        forEachFunction(f -> {
            if (f.name.equals("main")) {
                f.name = "real.main";
                f.entry.name = "real.main";
            } else if (f.name.equals(Cst.INIT)) {
                f.name = "main";
                f.entry.name = "main";
            }
        });
    }

    public String tell() {
        StringBuilder builder = new StringBuilder();
        Consumer<String> rawStr = s -> builder.append("\t").append(s).append("\n");
        // .rodata
        if (!strData.isEmpty()) {
            rawStr.accept(".rodata");
            strData.forEach((k, v) -> {
                String converted = v.replace("\\", "\\\\")
                        .replace("\n", "\\n")
                        .replace("\"", "\\\"")
                        .replace("\t", "\\t");
                rawStr.accept(".align 2");
                builder.append(k).append(":\n");
                builder.append("\t.string \"").append(converted).append("\"\n");
            });
            builder.append("\n");
        }
        // .text
        rawStr.accept(".text");
        rawStr.accept(".align 2");
        forEachFunction(f -> {
            if (!f.isBuiltin())
                builder.append(f.tell()).append("\n");
        });
        // .bss
        if (!globalToSize.isEmpty()) {
            rawStr.accept(".bss");
            rawStr.accept(".align 2");
            globalToSize.forEach((name, size) -> {
                builder.append("\t.globl\t").append(name).append("\n");
                builder.append("\t.type\t").append(name).append(", @object\n");
                builder.append("\t.size\t").append(name).append(", ").append(size).append("\n");
                builder.append(name).append(":\n");
                builder.append("\t.zero\t").append(size).append("\n");
            });
        }
        return builder.toString();
    }

    private static HashSet<RVRegister> callerSave = null, calleeSave = null;

    public static HashSet<RVRegister> getCallerSave() {
        if (callerSave == null) {
            callerSave = new HashSet<>();
            callerSave.add(PhysicalRegister.get("ra"));
            for (int i = 5; i <= 7; ++i) callerSave.add(PhysicalRegister.get(i));
            for (int i = 10; i <= 17; ++i) callerSave.add(PhysicalRegister.get(i));
            for (int i = 28; i <= 31; ++i) callerSave.add(PhysicalRegister.get(i));
        }
        return callerSave;
    }

    public static HashSet<RVRegister> getCalleeSave() {
        if (calleeSave == null) {
            calleeSave = new HashSet<>();
            // not add sp because sp is restored by addi
            for (int i = 8; i <= 9; ++i) calleeSave.add(PhysicalRegister.get(i));
            for (int i = 18; i <= 27; ++i) calleeSave.add(PhysicalRegister.get(i));
        }
        return calleeSave;
    }

    public static boolean hasHigh(int val) {
        return (val & 0xfffff000) != 0;
    }
}
