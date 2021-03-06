package ir.typesystem;

import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import misc.UnimplementedError;

import java.util.ArrayList;
import java.util.StringJoiner;

public class StructureType extends IRType {
    private int size;
    public ArrayList<Register> members;
    public String name;

    public StructureType(String name) {
        this.name = name;
        members = new ArrayList<>();
        size = 0;
    }

    public StructureType addMember(Register mem) {
        // without padding now
        members.add(mem);
        size += mem.type.size();
        return this;
    }

    public int getMemberOffset(int which) {
        int off = 0;
        for (int idx = 0; idx < which; ++idx) {
            off += members.get(idx).type.size();
        }
        return off;
    }

    public IntConstant getMemberIndex(String mem){
        for (int idx = 0; idx < members.size() ; ++idx) {
            if(members.get(idx).name.equals(mem))return new IntConstant(idx);
        }
        throw new IllegalStateException();
    }

    @Override
    public int size() {
        return size;
    }

    public String isWhat() {
        StringJoiner s = new StringJoiner(", ", "type <{ ", " }>");
        members.forEach(tp -> s.add(tp.type.tell()));
        return s.toString();
    }

    @Override
    public String tell() {
        return "%struct." + name;
    }

    @Override
    public IROperand defaultValue() {
        throw new IllegalStateException();
    }

    @Override
    public boolean matches(IRType rhs) {
        throw new UnimplementedError();
    }
}
