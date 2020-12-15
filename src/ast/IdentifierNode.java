package ast;

import semantic.ASTVisitor;

public class IdentifierNode implements ExprNode {
    public String id;
    public IdentifierNode(String name){
        id=name;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
