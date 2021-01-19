package ir.typesystem;

import java.util.ArrayList;
import java.util.StringJoiner;

public class StructureType extends IRType{
    private int size;
    public ArrayList<IRType> members;
    public String name;

    public StructureType(String name){
        this.name=name;
        members=new ArrayList<>();
        size=0;
    }

    public StructureType addMember(IRType tp){
        // without padding now
        members.add(tp);
        size+=tp.size();
        return this;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String tell() {
        StringJoiner s=new StringJoiner(", ","<{ "," }>");
        members.forEach(tp->s.add(tp.tell()));
        return s.toString();
    }
}
