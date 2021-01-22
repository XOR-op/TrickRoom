package ast;

public class ConditionalNode extends StmtNode {
    public ExprNode condExpr;
    public StmtNode trueStat,falseStat;
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
