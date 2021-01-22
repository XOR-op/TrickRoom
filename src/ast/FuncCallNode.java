package ast;

import compnent.basic.FunctionType;

import java.util.ArrayList;

public class FuncCallNode extends ExprNode {
    public ExprNode callee;
    public ArrayList<ExprNode> arguments;
    public FunctionType correspondingFunc;
    public boolean isConstructor;
    public FuncCallNode(ExprNode s,boolean isConstructor){
        callee =s;
        arguments=new ArrayList<>();
        this.isConstructor=isConstructor;
    }
    public FuncCallNode(ExprNode s){this(s,false);}
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
