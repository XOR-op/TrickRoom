package compnent.basic;

public class Type {
    private static final Type Int = new Type("int");

    public static Type Int() {
        return Int;
    }

    private static final Type String = new Type("string");

    public static Type String() {
        return String;
    }

    private static final Type Bool = new Type("bool");

    public static Type Bool() {
        return Bool;
    }

    private String id;
    private int dimension; // array dimension; 0 means scalar

    public Type(String name,int dimension) {
        this.id = name;
        this.dimension=dimension;
    }
    public Type(String name){
        this(name,0);
    }

    public boolean equals(Type rhs) {
        return this.id.equals(rhs.id) && this.dimension == rhs.dimension;
    }
}
