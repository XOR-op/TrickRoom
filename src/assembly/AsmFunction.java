package assembly;

import java.util.ArrayList;

public class AsmFunction {
    private String name;
    private ArrayList<AsmBlock> blocks = new ArrayList<>();

    public AsmFunction(ir.Function irFunc) {
        // todo
    }

    public AsmFunction addBlock(AsmBlock block){
        blocks.add(block);
        return this;
    }

    public String tell(){
        StringBuilder builder=new StringBuilder();
        // todo metadata
        blocks.forEach(block->builder.append(block.tell()));
        return builder.toString();
    }



}
