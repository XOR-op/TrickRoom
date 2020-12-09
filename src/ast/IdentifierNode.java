package ast;

import compnent.basic.Identifier;

public class IdentifierNode extends ExprNode{
    public Identifier id;
    public IdentifierNode(String name){
        id=new Identifier(name);
    }
}
