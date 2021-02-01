package ast.struct;

import ast.ASTVisitor;

public class UnaryExprNode extends ExprNode {
    public ExprNode expr;
    public String lexerSign;
    public boolean isPrefix;

    public UnaryExprNode(boolean isPrefix, String lexerSign, ExprNode expr) {
        this.lexerSign = lexerSign;
        this.isPrefix = isPrefix;
        this.expr = expr;
    }

    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
