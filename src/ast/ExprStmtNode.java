package ast;

public class ExprStmtNode extends StmtNode{
    public ExprNode expr;
    public ExprStmtNode(ExprNode e){
        expr=e;
    }
}
