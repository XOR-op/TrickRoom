package ast;

public class SubscriptionNode extends ExprNode{
    public ExprNode lhs,rhs;
    public SubscriptionNode(ExprNode l,ExprNode r){
        lhs=l;
        rhs=r;
    }

    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
