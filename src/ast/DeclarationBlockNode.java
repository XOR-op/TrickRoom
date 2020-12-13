package ast;

import java.util.ArrayList;

public class DeclarationBlockNode extends StmtNode{
    ArrayList<DeclarationNode> decls;
    public DeclarationBlockNode(){
        decls=new ArrayList<>();
    }
}
