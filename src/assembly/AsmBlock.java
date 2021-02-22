package assembly;

import assembly.instruction.RVInst;

import java.util.ArrayList;

public class AsmBlock {
    private String name;
    private ArrayList<RVInst> instructions=new ArrayList<>();

    public AsmBlock(String name){
        this.name=name;
    }

    public void addInst(RVInst inst){
        instructions.add(inst);
    }

    public String tell(){
        StringBuilder builder=new StringBuilder();
        builder.append(name).append(":\n");
        instructions.forEach(inst->builder.append("\t").append(inst.tell()).append('\n'));
        return builder.toString();
    }
}
