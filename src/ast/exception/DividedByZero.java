package ast.exception;

import ast.info.CodePosition;
import ast.struct.ASTNode;

public class DividedByZero extends SemanticException{
    public DividedByZero(ASTNode node) {
        super(node.coor);
    }

    @Override
    public String toString() {
        return "DuplicateSyntaxException{"+coor.toString()+"}";
    }
}
