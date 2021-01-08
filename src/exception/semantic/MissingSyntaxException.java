package exception.semantic;

import ast.ASTNode;

public class MissingSyntaxException extends SemanticException{
    private String syntax;
    public MissingSyntaxException(ASTNode node,String id){
        super(node.coor);
        syntax=id;
    }

    @Override
    public String toString() {
        return "UndeclaredSyntaxException{"+coor.toString() +
                "syntax='" + syntax + '\'' +
                '}';
    }
}
