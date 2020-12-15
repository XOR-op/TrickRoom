package ast;

import semantic.ASTVisitor;

public class ConditionalNode implements StmtNode {
    public ExprNode condExpr;
    public StmtNode trueStat,falseStat;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
