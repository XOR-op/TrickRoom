package ast.struct;

import ast.ASTVisitor;
import ast.type.ClassType;

import java.util.ArrayList;

public class ClassNode extends ASTNode {
    public ClassType cls;
    public ArrayList<DeclarationNode> memberNode;
    public ArrayList<FunctionNode> methodNode;
    public ArrayList<FunctionNode> constructorNode;
    public ClassNode(String name){
        cls=new ClassType(this);
        cls.id=name;
        constructorNode =new ArrayList<>();
        memberNode =new ArrayList<>();
        methodNode =new ArrayList<>();
    }

    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
