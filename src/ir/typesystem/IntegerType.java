package ir.typesystem;

public class IntegerType extends IRType{
    @Override
    public int size() {
        return 32;
    }

    @Override
    public String tell() {
        return "i32";
    }
}
