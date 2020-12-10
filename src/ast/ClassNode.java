package ast;

import compnent.basic.Identifier;
import compnent.basic.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassNode extends ASTNode{
    public ArrayList<DeclarationNode> members;
    public ArrayList<FunctionNode> methods;
    public ArrayList<FunctionNode> constructor;
    public Identifier className;
    public ClassNode(String name){
        className=new Identifier(Type.Class,name);
        constructor=new ArrayList<>();
        members=new ArrayList<>();
        methods=new ArrayList<>();
    }

}
