package compnent.basic;

public class Type {

    public String id;
    public int dimension; // array dimension; 0 means scalar
    private final boolean reserved;

    protected Type(String name, int dimension, boolean reserved) {
        this.id = name;
        this.dimension = dimension;
        this.reserved = reserved;
    }

    public Type(String name, int dimension) {
        this(name, dimension, false);
        assert !(name.equals("int") || name.equals("string") || name.equals("bool") || name.equals("null") || name.equals("void") || name.equals("function"));
    }

    public Type(String name) {
        this(name, 0);
    }

    public boolean equals(Type rhs) {
        return this.reserved == rhs.reserved && this.id.equals(rhs.id) && this.dimension == rhs.dimension;
    }
}
