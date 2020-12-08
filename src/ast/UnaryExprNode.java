package ast;

public class UnaryExprNode extends ExprNode{
    public ExprNode expr;
    public int lexerSign;
    public boolean isPrefix;
}
