package ast;

import compnent.basic.Identifier;

import java.util.ArrayList;

public class FuncCallNode {
    public Identifier funcName;
    public ArrayList<ExprNode> arguments;
}
