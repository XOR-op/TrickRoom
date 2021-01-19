package ast;

public class ContinueNode extends StmtNode {
    public LoopNode correspondingLoop;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
