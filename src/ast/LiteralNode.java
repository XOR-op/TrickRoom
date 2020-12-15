package ast;

import compnent.basic.Type;
import semantic.ASTVisitor;

public class LiteralNode implements ExprNode {
    public Type type;
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
