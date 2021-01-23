package compnent.basic;

public class ArrayType extends Type{
    public int dimension;
    public Type elementType;
    public ArrayType(Type ele, int dimension) {
        super(ele.id);
        elementType=ele;
        this.dimension=dimension;
    }

    @Override
    public Type copy() {
        ArrayType at=new ArrayType(elementType,dimension);
        return at;
    }

    public Type subType(){
        if(dimension>1)return new ArrayType(elementType,dimension-1);
        else return elementType;
    }

    @Override
    public int dim() {
        return dimension;
    }
    
    @Override
    public int size() {
        return elementType.size()*length;
    }

    @Override
    public String tell() {
        return "[ "+ length +" x "+elementType+"]";
    }
}
