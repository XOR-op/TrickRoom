package ast;

public class ThisNode extends ExprNode {
    public ThisNode() {
    }

    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
