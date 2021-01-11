package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class NotSubscriptable extends SemanticException{

    public NotSubscriptable(ASTNode node) {
        super(node.coor);
    }

    @Override
    public String toString() {
        return "NotSubscriptableException "+coor.toString();
    }
}
