package exception.semantic;
import ast.ASTNode;
import compnent.basic.Type;

public class TypeMismatchException extends SemanticException{
    private String a,b;
//    public TypeMismatchException(){this("");}
    public TypeMismatchException(Type tpa, Type tpb, ASTNode node){this(tpa.id,tpb.id,node);}
    public TypeMismatchException(String a,String b,ASTNode node){
        super(node.coor);
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "TypeMismatchException{"+coor.toString()+
                "a='" + a + '\'' +
                "b='" + b + '\'' +
                '}';
    }
}
