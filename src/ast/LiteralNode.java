package ast;

import compnent.basic.Type;

public class LiteralNode extends ExprNode{
    public Type type;
    public String content;
    public LiteralNode(Type type,String content){
        this.type=type;
        this.content=content;
    }
}
