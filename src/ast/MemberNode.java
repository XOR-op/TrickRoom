package ast;

public class MemberNode extends ExprNode{
    public ExprNode object;
    public String member;
    public MemberNode(ExprNode obj,String meb){
        object=obj;
        member=meb;
    }

    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
