package compnent.basic;

public class Type {

    public String id;
//    public int dimension; // array dimension; 0 means scalar
//    public Type(String name, int dimension) {
//        this.id = name;
//        this.dimension = dimension;
//    }

    public Type(String name) {
        this.id=name;
//        this(name, 0);
    }

    public boolean equals(Type rhs) {
//        return this.id.equals(rhs.id) && this.dimension == rhs.dimension;
        return this.id.equals(rhs.id) &&this.dim()==rhs.dim();
    }
    public Type copy(){
        return new Type(id);
    }
    public int dim(){return 0;}
    public boolean isArray(){return this.dim()!=0;}
}
