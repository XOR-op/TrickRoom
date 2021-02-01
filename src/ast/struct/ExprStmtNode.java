package ast.struct;

import ast.ASTVisitor;

public class ExprStmtNode extends StmtNode {
    public ExprNode expr;
    public ExprStmtNode(ExprNode e){
        expr=e;
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
