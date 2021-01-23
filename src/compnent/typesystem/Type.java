package compnent.basic;

public abstract class Type {

    public String id;

    public Type(String name) {
        this.id=name;
    }

    public boolean equals(Type rhs) {
        return this.id.equals(rhs.id) &&this.dim()==rhs.dim();
    }
    public Type copy(){
        return new Type(id);
    }
    public int dim(){return 0;}
    public boolean isArray(){return this.dim()!=0;}
    public abstract String tell();
    public abstract int size();

    @Override
    public String toString() {
        return tell();
    }
}
