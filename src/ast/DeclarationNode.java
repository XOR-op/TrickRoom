package ast;

import compnent.basic.Identifier;

public class DeclarationNode extends ExprNode{
    public Identifier id;
    public ExprNode expr;
    public DeclarationNode(String id){
        this.id=new Identifier(id);
        expr=null;
    }
}
