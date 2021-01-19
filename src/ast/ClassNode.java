package ast;

import compnent.basic.ClassType;

import java.util.ArrayList;

public class ClassNode extends ASTNode {
    public ClassType cls;
    public ArrayList<DeclarationNode> members;
    public ArrayList<FunctionNode> methods;
    public ArrayList<FunctionNode> constructor;
    public ClassNode(String name){
        cls=new ClassType(this);
        cls.id=name;
        constructor=new ArrayList<>();
        members=new ArrayList<>();
        methods=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
