package compnent.basic;

public class ArrayObjectType extends Type{
    public int dimension;
    public Type elementType;
    public ArrayObjectType(Type ele, int dimension) {
        super(ele.id);
        elementType=ele;
        this.dimension=dimension;
    }

    @Override
    public Type copy() {
        ArrayObjectType at=new ArrayObjectType(elementType,dimension);
        return at;
    }

    public Type subType(){
        if(dimension>1)return new ArrayObjectType(elementType,dimension-1);
        else return elementType;
    }

    @Override
    public int dim() {
        return dimension;
    }
    
}
