package ast;

public class LoopNode extends StmtNode {
    public ExprNode initExpr,condExpr,updateExpr;
    public DeclarationBlockNode initDecl;
    public StmtNode loopBody;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
