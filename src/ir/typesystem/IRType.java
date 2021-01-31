package ir.typesystem;

public abstract class IRType {
    public abstract int size();
    public abstract String tell();

    @Override
    public String toString() {
        return tell();
    }
}
