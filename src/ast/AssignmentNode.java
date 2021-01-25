package ast;

public class AssignmentNode extends ExprNode{

    public ExprNode lhs,rhs;
    public AssignmentNode(ExprNode l,ExprNode r){
        lhs=l;
        rhs=r;
    }

    @Override
    public Object accept(ASTVisitor visitor) {return visitor.visit(this);}
}
