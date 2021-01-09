package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class LeftValueException extends SemanticException{

    public LeftValueException(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "LeftValueException{"+coor.toString() +
                '}';
    }
}
