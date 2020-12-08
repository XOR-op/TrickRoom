package ast;

import compnent.basic.Identifier;

import java.util.HashMap;

public class ClassNode extends ASTNode{
    public HashMap<Identifier,LiteralNode > members;
    public HashMap<Identifier,FunctionNode> methods;
    public FunctionNode constructor;
    public Identifier className;

}
