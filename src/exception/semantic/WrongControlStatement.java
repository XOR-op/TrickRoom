package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class WrongControlStatement extends SemanticException{
    public WrongControlStatement(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "WrongControlFlowStatementException{"+coor.toString()+"}";
    }
}
