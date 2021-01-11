package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class MemberMisuse extends SemanticException{
    public MemberMisuse(ASTNode node) {
        super(node.coor);
    }

    @Override
    public String toString() {
        return "MemberMisuseException{"+coor.toString()+"}";
    }
}
