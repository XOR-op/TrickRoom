package ir.typesystem;

import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import misc.UnimplementedError;

import java.util.ArrayList;
import java.util.StringJoiner;

public class StructureType extends IRType {
    private int size = 0;
    private Integer pointerSize = null;
    public ArrayList<Register> members = new ArrayList<>();
    public String name;

    public StructureType(String name) {
        this.name = name;
    }

    public StructureType addMember(Register mem) {
        // without padding now
        members.add(mem);
        size += Math.min(4, mem.type.size());
        return this;
    }

    public int getMemberOffset(int which) {
        int off = 0;
        for (int idx = 0; idx < which; ++idx) {
            off += Math.min(4, members.get(idx).type.size());
        }
        return off;
    }

    public IntConstant getMemberIndex(String mem) {
        for (int idx = 0; idx < members.size(); ++idx) {
            if (members.get(idx).getName().equals(mem)) return new IntConstant(idx);
        }
        throw new IllegalStateException();
    }

    public void rearrange() {
        assert pointerSize == null;
        ArrayList<Register> pointerReg = new ArrayList<>(), nonPointerReg = new ArrayList<>();
        for (var r : members) {
            if (r.type instanceof PointerType) pointerReg.add(r);
            else nonPointerReg.add(r);
        }
        pointerSize = pointerReg.size();
        pointerReg.addAll(nonPointerReg);
        members = pointerReg;
    }

    public int getPointerSize() {
        assert pointerSize != null && pointerSize >= 0;
        return pointerSize;
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
        return rhs == this;
    }
}
