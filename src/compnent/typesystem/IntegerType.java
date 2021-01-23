package compnent.basic;

public class IntegerType extends Type{
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
}
