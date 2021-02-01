package ast.struct;

import ast.ASTVisitor;

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
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
