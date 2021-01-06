package ast;

import semantic.ASTVisitor;

public class NewExprNode extends ExprNode {
    public FuncCallNode classNew;
    public ArrayLiteralNode arrNew;
    public boolean isConstruct;
    public NewExprNode(boolean isCall){
        this.isConstruct =isCall;
        if(isCall)arrNew=null;
        else classNew=null;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
