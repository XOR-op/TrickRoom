package ast;

public class IdentifierNode extends ExprNode {
    public String id;
    public IdentifierNode(String name){
        id=name;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
