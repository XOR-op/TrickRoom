package exception.semantic;

import ast.struct.ASTNode;

public class WrongControlStatement extends SemanticException{
    public WrongControlStatement(ASTNode node) {
        super(node.coor);
    }
    @Override
    public String toString() {
        return "WrongControlFlowStatementException{"+coor.toString()+"}";
    }
}
