package exception.semantic;

import ast.struct.ASTNode;

public class MissingSyntax extends SemanticException{
    private String syntax;
    public MissingSyntax(ASTNode node, String id){
        super(node.coor);
        syntax=id;
    }

    @Override
    public String toString() {
        return "UndeclaredSyntaxException{"+coor.toString() +
                "syntax='" + syntax + '\'' +
                '}';
    }
}
