package ast.struct;

import ast.ASTVisitor;
import ast.type.Type;

import java.util.ArrayList;

public class FunctionNode extends ASTNode {
    public Type returnType;
    public String funcId;
    public ArrayList<DeclarationNode> parameterNode;
    public SuiteNode suiteNode;
    public FunctionNode(String name){
        this.funcId=name;
        parameterNode =new ArrayList<>();
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
