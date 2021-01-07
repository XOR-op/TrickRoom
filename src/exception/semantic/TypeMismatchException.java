package exception.semantic;
import compnent.basic.Type;

public class TypeMismatchException extends SemanticException{
    private String a,b;
//    public TypeMismatchException(){this("");}
    public TypeMismatchException(Type tpa,Type tpb){this(tpa.id,tpb.id);}
    public TypeMismatchException(String a,String b){
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "TypeMismatchException{" +
                "a='" + a + '\'' +
                "b='" + b + '\'' +
                '}';
    }
}
