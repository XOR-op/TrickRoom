package ast;

import semantic.ASTVisitor;

import java.util.ArrayList;

public class FuncCallNode extends ExprNode {
    public ExprNode callee;
    public ArrayList<ExprNode> arguments;
    public FuncCallNode(ExprNode s){
        callee =s;
        arguments=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
