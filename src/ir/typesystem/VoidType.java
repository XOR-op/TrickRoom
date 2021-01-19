package ir.typesystem;

import exception.UnimplementedError;

public class VoidType extends IRType{
    @Override
    public int size() {
        throw new UnimplementedError();
    }

    @Override
    public String tell() {
        return "void";
    }
}
