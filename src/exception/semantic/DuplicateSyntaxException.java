package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class DuplicateSyntaxException extends SemanticException{
    private final String info;
    public DuplicateSyntaxException(ASTNode node,String s){
        super(node.coor);
        info=s;
    }

    @Override
    public String toString() {
        return "DuplicateSyntaxException{"+coor.toString()+info+"}";
    }
}
