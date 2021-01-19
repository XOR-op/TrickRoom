package ast;

import java.util.ArrayList;

public class DeclarationBlockNode extends StmtNode {
    public ArrayList<DeclarationNode> decls;
    public DeclarationBlockNode(){
        decls=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
