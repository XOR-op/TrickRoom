package ast;

public class ConditionalNode extends ASTNode{
    public ExprNode condExpr;
    public StmtNode trueStat,falseStat;
}
