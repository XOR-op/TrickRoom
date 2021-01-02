package ast;

import semantic.ASTVisitor;

import java.util.ArrayList;

public class FuncCallNode extends ExprNode {
    public String funcName;
    public ArrayList<ExprNode> arguments;
    public FuncCallNode(String s){
        funcName=s;
        arguments=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
