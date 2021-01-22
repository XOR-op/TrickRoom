package ast;

public class IdentifierNode extends ExprNode {
    public String id;
    public IdentifierNode(String name){
        id=name;
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
