package ast;

public class ThisNode extends ExprNode {
    public ThisNode(){}

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
