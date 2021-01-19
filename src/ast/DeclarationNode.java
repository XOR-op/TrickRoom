package ast;

import compnent.basic.Type;

public class DeclarationNode extends StmtNode {
    public Type type;
    public String id;
    public ExprNode expr;
    public DeclarationNode(Type tp,String id){
        this.type=tp;
        this.id=id;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
