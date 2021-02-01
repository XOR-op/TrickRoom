package ast.struct;

import ast.ASTVisitor;
import ast.Symbol;
import ast.type.Type;

public class DeclarationNode extends StmtNode {
    public Type type;
    public String id;
    public ExprNode expr;
    public Symbol sym;
    public DeclarationNode(Type tp,String id){
        this.type=tp;
        this.id=id;
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
