package assembly;

import ir.IRInfo;
import ir.typesystem.PointerType;
import ir.typesystem.StructureType;

import java.util.HashMap;

public class RVInfo {
    private final HashMap<String, AsmFunction> funcColl = new HashMap<>();
    private final IRInfo irInfo;

    public RVInfo(IRInfo irInfo) {
        this.irInfo = irInfo;
    }

}
