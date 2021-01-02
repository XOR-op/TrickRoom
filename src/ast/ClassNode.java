package ast;

import semantic.ASTVisitor;

import java.util.ArrayList;

public class ClassNode extends ASTNode {
    public String className;
    public ArrayList<DeclarationNode> members;
    public ArrayList<FunctionNode> methods;
    public ArrayList<FunctionNode> constructor;
    public ClassNode(String name){
        className=name;
        constructor=new ArrayList<>();
        members=new ArrayList<>();
        methods=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
