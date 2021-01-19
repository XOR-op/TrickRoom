package ast;

import compnent.basic.Type;

public class LiteralNode extends ExprNode {
    public String content;
    public LiteralNode(Type type,String content){
        this.type=type;
        this.content=content;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
