package compnent.basic;

public class Type {
    public static final Type Int = new Type("int", 0, true);
    public static final Type String = new Type("string", 0, true);
    public static final Type Bool = new Type("bool", 0, true);
    public static final Type Null = new Type("null", 0, true);
    public static final Type Void = new Type("void", 0, true);
    public static final Type Func = new Type("function", 0, true);
    public static final Type Class = new Type("class", 0, true);

    public String id;
    public int dimension; // array dimension; 0 means scalar
    private boolean reserved;

    private Type(String name, int dimension, boolean reserved) {
        this.id = name;
        this.dimension = dimension;
        this.reserved = reserved;
    }

    public Type(String name, int dimension) {
        this(name, dimension, false);
    }

    public Type(String name) {
        this(name, 0);
    }

    public boolean equals(Type rhs) {
        return this.reserved == rhs.reserved && this.id.equals(rhs.id) && this.dimension == rhs.dimension;
    }
}
