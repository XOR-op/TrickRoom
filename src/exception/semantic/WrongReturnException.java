package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class WrongReturnException extends SemanticException{
    public WrongReturnException(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "WrongReturnException{"+coor.toString()+"}";
    }
}
