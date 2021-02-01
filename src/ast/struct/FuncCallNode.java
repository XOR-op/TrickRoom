package ast.struct;

import ast.ASTVisitor;
import ast.type.FunctionType;

import java.util.ArrayList;

public class FuncCallNode extends ExprNode {
    public ExprNode callee; // anything before (
    public ArrayList<ExprNode> arguments;
    public FunctionType func;
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

    public boolean isGlobal(){
        return (callee instanceof IdentifierNode)&&!isConstructor;
    }
}
