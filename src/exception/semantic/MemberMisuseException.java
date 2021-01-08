package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class MemberMisuseException extends SemanticException{
    public MemberMisuseException(ASTNode node) {
        super(node.coor);
    }

    @Override
    public String toString() {
        return "MemberMisuseException{"+coor.toString()+"}";
    }
}
