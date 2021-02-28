package assembly;

import assembly.construct.GraphRegisterAllocator;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;
import ir.IRFunction;
import ir.IRInfo;
import ir.typesystem.PointerType;
import ir.typesystem.StructureType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class RVInfo {
    private final HashMap<String, AsmFunction> funcCollection = new HashMap<>();
    private final IRInfo irInfo;

    public RVInfo(IRInfo irInfo) {
        this.irInfo = irInfo;
        irInfo.forEachFunction(f -> {
            if (!f.isBuiltin())
                funcCollection.put(f.name, new AsmFunction(f));
            else
                funcCollection.put(f.name, new AsmFunction(f, true));
        });
    }

    public AsmFunction getFunc(IRFunction irFunc) {
        return funcCollection.get(irFunc.name);
    }

    public void forEachFunction(Consumer<AsmFunction> consumer) {
        funcCollection.forEach((k, f) -> consumer.accept(f));
    }

    public void registerAllocate() {
        funcCollection.forEach((k, v) -> {
            if(!v.isBuiltin())
                new GraphRegisterAllocator(v).run();
        });
    }

    public String tell() {
        StringBuilder builder = new StringBuilder();
        forEachFunction(f -> {
            if (!f.isBuiltin())
                builder.append(f.tell());
        });
        return builder.toString();
    }

    private static HashSet<RVRegister> callerSave = null, calleeSave = null;

    public static HashSet<RVRegister> getCallerSave() {
        if (callerSave == null) {
            callerSave = new HashSet<>();
            callerSave.add(PhysicalRegister.get("ra"));
            for (int i = 5; i <= 7; ++i) callerSave.add(PhysicalRegister.get(i));
            for (int i = 11; i <= 17; ++i) callerSave.add(PhysicalRegister.get(i));
            for (int i = 28; i <= 31; ++i) callerSave.add(PhysicalRegister.get(i));
        }
        return callerSave;
    }

    public static HashSet<RVRegister> getCalleeSave() {
        if (calleeSave == null) {
            calleeSave = new HashSet<>();
            calleeSave.add(PhysicalRegister.get("sp"));
            for (int i = 8; i <= 9; ++i) calleeSave.add(PhysicalRegister.get(i));
            for (int i = 18; i <= 27; ++i) calleeSave.add(PhysicalRegister.get(i));
        }
        return calleeSave;
    }
}
