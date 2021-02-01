package ast.struct;

import ast.ASTVisitor;
import ast.type.Type;

import java.util.ArrayList;

public class FunctionNode extends ASTNode {
    public Type returnType;
    public String funcId;
    public ArrayList<DeclarationNode> parameters;
    public SuiteNode suite;
    public FunctionNode(String name){
        this.funcId=name;
        parameters=new ArrayList<>();
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
