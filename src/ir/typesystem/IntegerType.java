package ir.typesystem;

public class IntegerType extends IRType{
    private int width;

    public IntegerType(){this(32);}

    public IntegerType(int wid){
        width=wid;
    }

    @Override
    public int size() {
        return width;
    }

    @Override
    public String tell() {
        return "i"+width;
    }
}
