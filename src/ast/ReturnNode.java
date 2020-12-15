package ast;

import semantic.ASTVisitor;

public class ReturnNode implements StmtNode {
    public ExprNode returnExpr;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
