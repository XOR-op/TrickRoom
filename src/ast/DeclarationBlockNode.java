package ast;

import semantic.ASTVisitor;

import java.util.ArrayList;

public class DeclarationBlockNode implements StmtNode {
    public ArrayList<DeclarationNode> decls;
    public DeclarationBlockNode(){
        decls=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
