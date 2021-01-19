package ast;

public class ReturnNode extends StmtNode {
    public ExprNode returnExpr;
    public FunctionNode correspondingFunction;
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
