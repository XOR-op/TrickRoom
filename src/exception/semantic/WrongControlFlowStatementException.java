package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class WrongControlFlowStatementException extends SemanticException{
    public WrongControlFlowStatementException(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "WrongControlFlowStatementException{"+coor.toString()+"}";
    }
}
