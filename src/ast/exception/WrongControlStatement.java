package ast.exception;

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
