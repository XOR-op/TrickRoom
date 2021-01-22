package ast;

public class ReturnNode extends StmtNode {
    public ExprNode returnExpr;
    public FunctionNode correspondingFunction;
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
