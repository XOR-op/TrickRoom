package ast;

import semantic.ASTVisitor;

public class BreakNode extends StmtNode {
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
