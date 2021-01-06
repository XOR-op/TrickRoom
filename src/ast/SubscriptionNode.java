package ast;

import semantic.ASTVisitor;

public class SubscriptionNode extends ExprNode{
    public ExprNode lhs,rhs;
    public SubscriptionNode(ExprNode l,ExprNode r){
        lhs=l;
        rhs=r;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
