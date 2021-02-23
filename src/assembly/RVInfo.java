package assembly;

import ir.IRFunction;
import ir.IRInfo;
import ir.typesystem.PointerType;
import ir.typesystem.StructureType;

import java.util.HashMap;

public class RVInfo {
    private final HashMap<String, AsmFunction> funcCollection = new HashMap<>();
    private final IRInfo irInfo;

    public RVInfo(IRInfo irInfo) {
        this.irInfo = irInfo;
    }

    public AsmFunction getFunc(IRFunction irFunc){
        return funcCollection.get(irFunc.name);
    }

}
