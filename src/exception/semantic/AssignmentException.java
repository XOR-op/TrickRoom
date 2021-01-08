package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class AssignmentException extends SemanticException{

    public AssignmentException(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "AssignmentException{"+coor.toString() +
                '}';
    }
}
