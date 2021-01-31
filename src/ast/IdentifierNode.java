package ast;

import compnent.basic.Symbol;

public class IdentifierNode extends ExprNode {
    public String id;
    public Symbol sym;
    public IdentifierNode(String name){
        id=name;
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
