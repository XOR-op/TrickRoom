package ir.typesystem;

public class BoolType extends IRType{
    @Override
    public int size() {
        return 8;
    }

    @Override
    public String tell() {
        return "i1";
    }
}
