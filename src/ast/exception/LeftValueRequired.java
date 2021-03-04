package ast.exception;

import ast.struct.ASTNode;

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
