package ast.struct;

import ast.ASTVisitor;

public class BreakNode extends StmtNode {
    public LoopNode correspondingLoop;
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
