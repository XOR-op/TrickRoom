package ast;

import semantic.ASTVisitor;

public class ExprStmtNode implements StmtNode {
    public ExprNode expr;
    public ExprStmtNode(ExprNode e){
        expr=e;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
