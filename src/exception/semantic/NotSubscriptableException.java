package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class NotSubscriptableException extends SemanticException{

    public NotSubscriptableException(ASTNode node) {
        super(node.coor);
    }

    @Override
    public String toString() {
        return "NotSubscriptableException "+coor.toString();
    }
}
