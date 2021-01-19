package ast;

public class AssignmentNode extends ExprNode{

    public ExprNode lhs,rhs;
    public AssignmentNode(ExprNode l,ExprNode r){
        lhs=l;
        rhs=r;
    }

    @Override
    public void accept(ASTVisitor visitor) {visitor.visit(this);}
}
