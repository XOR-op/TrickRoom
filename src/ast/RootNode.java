package ast;

import semantic.ASTVisitor;

import java.util.ArrayList;

public class RootNode implements ASTNode {

    public ArrayList<DeclarationNode> globalVars;
    public ArrayList<FunctionNode> functions;
    public ArrayList<ClassNode> classes;
    public RootNode(){
        globalVars =new ArrayList<>();
        functions =new ArrayList<>();
        classes=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
