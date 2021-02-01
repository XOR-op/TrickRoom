package ast.struct;

import ast.ASTVisitor;

import java.util.ArrayList;

public class DeclarationBlockNode extends StmtNode {
    public ArrayList<DeclarationNode> decls;
    public DeclarationBlockNode(){
        decls=new ArrayList<>();
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
