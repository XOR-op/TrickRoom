package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class WrongReturn extends SemanticException{
    public WrongReturn(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "WrongReturnException{"+coor.toString()+"}";
    }
}
