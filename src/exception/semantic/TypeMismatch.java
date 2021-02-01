package exception.semantic;
import ast.struct.ASTNode;
import ast.type.Type;

public class TypeMismatch extends SemanticException{
    private String a,b;
//    public TypeMismatchException(){this("");}
    public TypeMismatch(Type tpa, Type tpb, ASTNode node){this(tpa.id,tpb.id,node);}
    public TypeMismatch(String a, String b, ASTNode node){
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
