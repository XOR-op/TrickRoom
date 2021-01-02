package ast;

import semantic.ASTVisitor;

public class ReturnNode extends StmtNode {
    public ExprNode returnExpr;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
