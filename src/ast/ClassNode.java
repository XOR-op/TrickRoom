package ast;

import semantic.ASTVisitor;

import java.util.ArrayList;

public class ClassNode implements ASTNode {
    public ArrayList<DeclarationNode> members;
    public ArrayList<FunctionNode> methods;
    public ArrayList<FunctionNode> constructor;
    public String className;
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
