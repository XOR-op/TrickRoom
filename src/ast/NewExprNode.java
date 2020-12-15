package ast;

import semantic.ASTVisitor;

public class NewExprNode implements ExprNode {
    public FuncCallNode classNew;
    public ArrayLiteralNode arrNew;
    public boolean isFuncCall;
    public NewExprNode(boolean isCall){
        this.isFuncCall=isCall;
        if(isCall)arrNew=null;
        else classNew=null;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
