package ast;

import compnent.basic.Identifier;

import java.util.ArrayList;

public class FuncCallNode extends ExprNode{
    public Identifier funcName;
    public ArrayList<ExprNode> arguments;
    public FuncCallNode(String s){
        funcName=new Identifier(s);
        arguments=new ArrayList<>();
    }
}
