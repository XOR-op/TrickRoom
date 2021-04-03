package assembly;

import assembly.construct.GraphRegisterAllocator;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;
import ir.instruction.Call;
import misc.Cst;
import ir.IRFunction;
import ir.IRInfo;
import optimization.assembly.RedundantOptimizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.function.Consumer;

public class RVInfo {
    private final HashMap<String, RVFunction> funcCollection = new HashMap<>();
    private final HashMap<String, String> strData = new HashMap<>();
    private final HashMap<String, Integer> globalToSize = new HashMap<>();
    private final IRInfo irInfo;
    private final HashSet<IRFunction> reachable=new HashSet<>();

    public RVInfo(IRInfo irInfo) {
        this.irInfo = irInfo;
//        buildReachable();
        irInfo.forEachFunction(reachable::add);
        // add
        irInfo.forEachFunctionIncludingBuiltin(f -> {
            if (!f.isBuiltin()) {
                if(reachable.contains(f))
                    funcCollection.put(f.name, new RVFunction(f));
            }
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

    private void buildReachable(){
        var queue=new HashSet<IRFunction>();
        var main=irInfo.getMain();
        reachable.add(main);
        queue.add(main);
        while (!queue.isEmpty()){
            var iter=queue.iterator();
            var f=iter.next();
            iter.remove();
            f.blocks.forEach(b->{
                b.insts.forEach(inst->{
                    if(inst instanceof Call){
                        var dest=((Call) inst).function;
                        if(!reachable.contains(dest))
                            queue.add(dest);
                        reachable.add(dest);
                    }
                });
            });
        }
    }

    public boolean isReachableFunc(IRFunction irF){return reachable.contains(irF);}

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

    public static boolean isShortImm(int val) {
        // fit in sign-extended 12-bit
        return ((val & 0xfffff800) == 0)||((val|0x7ff)==0xffffffff);
    }
}
