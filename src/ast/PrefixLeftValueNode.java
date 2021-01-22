package ast;

public class PrefixLeftValueNode extends ExprNode{
    public String sign;
    public ExprNode expr;
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
    public PrefixLeftValueNode(String sign,ExprNode expr){
        this.sign=sign;
        this.expr=expr;
    }
}
