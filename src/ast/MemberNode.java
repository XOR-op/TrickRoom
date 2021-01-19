package ast;

public class MemberNode extends ExprNode{
    public ExprNode object;
    public String member;
    public MemberNode(ExprNode obj,String meb){
        object=obj;
        member=meb;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
