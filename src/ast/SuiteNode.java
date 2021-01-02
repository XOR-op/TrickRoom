package ast;

import semantic.ASTVisitor;

import java.util.ArrayList;

public class SuiteNode extends StmtNode {
    public ArrayList<StmtNode> statements;
    public SuiteNode(){
        statements=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
