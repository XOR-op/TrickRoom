package ast;

import semantic.ASTVisitor;

public class PrefixLeftValueNode extends ExprNode{
    public String sign;
    public ExprNode expr;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    public PrefixLeftValueNode(String sign,ExprNode expr){
        this.sign=sign;
        this.expr=expr;
    }
}
