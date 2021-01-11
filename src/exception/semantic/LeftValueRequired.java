package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class LeftValueRequired extends SemanticException{

    public LeftValueRequired(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "LeftValueException{"+coor.toString() +
                '}';
    }
}
