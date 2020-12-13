package ast;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs,rhs;
    public String lexerSign; // signs in lexer
    public BinaryExprNode(String sign){lexerSign=sign;}
    public BinaryExprNode(String sign,ExprNode l,ExprNode r){
        lexerSign=sign;
        lhs=l;
        rhs=r;
    }

}
