package ast;

import java.util.ArrayList;

class RootNode extends ASTNode {

    public ArrayList<DeclarationNode> globalVars;
    public ArrayList<FunctionNode> functions;
    public ArrayList<ClassNode> classes;
    public RootNode(){
        globalVars =new ArrayList<>();
        functions =new ArrayList<>();
        classes=new ArrayList<>();
    }
}
