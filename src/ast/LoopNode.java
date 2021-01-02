package ast;

import semantic.ASTVisitor;

public class LoopNode extends StmtNode {
    public ExprNode initExpr,condExpr,updateExpr;
    public StmtNode loopBody;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
