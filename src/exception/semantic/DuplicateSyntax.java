package exception.semantic;

import ast.struct.ASTNode;

public class DuplicateSyntax extends SemanticException{
    private final String info;
    public DuplicateSyntax(ASTNode node, String s){
        super(node.coor);
        info=s;
    }

    @Override
    public String toString() {
        return "DuplicateSyntaxException{"+coor.toString()+info+"}";
    }
}
