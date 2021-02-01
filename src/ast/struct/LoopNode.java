package ast.struct;

import ast.ASTVisitor;

public class LoopNode extends StmtNode {
    public ExprNode initExpr,condExpr,updateExpr;
    public DeclarationBlockNode initDecl;
    public StmtNode loopBody;
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
