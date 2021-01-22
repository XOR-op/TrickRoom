package ast;

import java.util.ArrayList;

public class RootNode extends ASTNode {

    public ArrayList<DeclarationNode> globalVars;
    public ArrayList<FunctionNode> functions;
    public ArrayList<ClassNode> classes;
    public ArrayList<ASTNode> nodeList;
    public RootNode(){
        globalVars =new ArrayList<>();
        functions =new ArrayList<>();
        classes=new ArrayList<>();
        nodeList=new ArrayList<>();
    }

    public void addDeclNode(DeclarationNode nd){
        globalVars.add(nd);
        nodeList.add(nd);
    }

    public void addFuncNode(FunctionNode nd){
        functions.add(nd);
        nodeList.add(nd);
    }

    public void addClassNode(ClassNode nd){
        classes.add(nd);
        nodeList.add(nd);
    }

    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
