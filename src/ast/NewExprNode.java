package ast;

public class NewExprNode extends ExprNode{
    public FuncCallNode classNew;
    public ArrayLiteralNode arrNew;
    public boolean isFuncCall;
}
