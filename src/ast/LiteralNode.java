package ast;

import compnent.basic.Type;

public class LiteralNode extends ExprNode {
    public String content;
    public LiteralNode(Type type,String content){
        this.type=type;
        this.content=content;
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
