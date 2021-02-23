package assembly;

import ir.IRFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class AsmFunction {
    private String name;
    public ArrayList<AsmBlock> blocks = new ArrayList<>();
    public AsmBlock entry;
    public int stackOffset=0;
    public final HashMap<String,Integer> varOffset=new HashMap<>();

    public AsmFunction(IRFunction irFunc) {
        // todo
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
        blocks.forEach(block -> builder.append(block.tell()));
        return builder.toString();
    }


}
