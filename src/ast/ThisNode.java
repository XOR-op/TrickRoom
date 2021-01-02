package ast;

import semantic.ASTVisitor;

public class ThisNode extends ExprNode {
    public ThisNode(){}

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
