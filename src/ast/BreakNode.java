package ast;

public class BreakNode extends StmtNode {
    public LoopNode correspondingLoop;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
