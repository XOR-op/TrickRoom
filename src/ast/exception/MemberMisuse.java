package ast.exception;

import ast.struct.ASTNode;

public class MemberMisuse extends SemanticException{
    public MemberMisuse(ASTNode node) {
        super(node.coor);
    }

    @Override
    public String toString() {
        return "MemberMisuseException{"+coor.toString()+"}";
    }
}
