package ast;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs,rhs;
    public int lexerSign; // signs in lexer
}
