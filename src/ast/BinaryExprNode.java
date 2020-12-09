package ast;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs,rhs;
    public String lexerSign; // signs in lexer
    public BinaryExprNode(String sign){lexerSign=sign;}
}
