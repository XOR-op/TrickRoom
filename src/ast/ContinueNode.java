package ast;

import semantic.ASTVisitor;

public class ContinueNode extends StmtNode {
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
