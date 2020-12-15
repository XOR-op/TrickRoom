package ast;

import semantic.ASTVisitor;

public class BreakNode implements StmtNode {
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
