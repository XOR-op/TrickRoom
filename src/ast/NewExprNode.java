package ast;

public class NewExprNode extends ExprNode{
    public FuncCallNode classNew;
    public ArrayLiteralNode arrNew;
    public boolean isFuncCall;
    public NewExprNode(boolean isCall){
        this.isFuncCall=isCall;
        if(isCall)arrNew=null;
        else classNew=null;
    }
}
