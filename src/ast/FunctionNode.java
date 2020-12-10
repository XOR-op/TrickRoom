package ast;

import compnent.basic.Identifier;
import compnent.basic.Type;
import compnent.scope.Scope;

import java.util.ArrayList;

public class FunctionNode extends ASTNode{
    public Type returnType;
    public Identifier funcId;
    public ArrayList<Identifier> parameters;
    public SuiteNode suite;
    public FunctionNode(String name){
        this.funcId=new Identifier(Type.Func,name);
        parameters=new ArrayList<>();
    }
}
