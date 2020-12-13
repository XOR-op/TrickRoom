package ast;

public class ConditionalNode extends StmtNode{
    public ExprNode condExpr;
    public StmtNode trueStat,falseStat;
}
