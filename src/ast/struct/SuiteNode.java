package ast.struct;

import ast.ASTVisitor;

import java.util.ArrayList;

public class SuiteNode extends StmtNode {
    public ArrayList<StmtNode> statements;
    public SuiteNode(){
        statements=new ArrayList<>();
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
