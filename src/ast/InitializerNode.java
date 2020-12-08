package ast;

import compnent.basic.Identifier;

import java.util.ArrayList;

public class InitializerNode extends ASTNode{
    public Identifier classId;
    public ArrayList<ExprNode> arguments;
}
