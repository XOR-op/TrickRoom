package ast;

import compnent.basic.Type;
import semantic.ASTVisitor;

import java.util.ArrayList;

public class FunctionNode implements ASTNode {
    public Type returnType;
    public String funcId;
    public ArrayList<DeclarationNode> parameters;
    public SuiteNode suite;
    public FunctionNode(String name){
        this.funcId=name;
        parameters=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
