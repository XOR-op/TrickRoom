package ast;

import semantic.ASTVisitor;

public class UnaryExprNode implements ExprNode {
    public ExprNode expr;
    public String lexerSign;
    public boolean isPrefix;
    public UnaryExprNode(boolean isPrefix,String lexerSign,ExprNode expr){
        this.lexerSign=lexerSign;
        this.isPrefix=isPrefix;
        this.expr=expr;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
