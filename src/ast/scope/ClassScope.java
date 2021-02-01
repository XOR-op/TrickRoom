package ast.scope;

import ast.struct.ASTNode;
import ast.type.ClassType;
import ast.type.FunctionType;
import exception.semantic.DuplicateSyntax;

import java.util.HashMap;

public class ClassScope extends Scope{
    private HashMap<String, FunctionType> methodTable;
    ClassType classType;
    public ClassScope(Scope up, ClassType cls){
        super(up);
        methodTable=new HashMap<>();
        classType=cls;
    }

    public void registerMethod(FunctionType func, ASTNode node) {
        checkFunctionSyntax(func.id,node);
        if (methodTable.containsKey(func.id) ) throw new DuplicateSyntax(node,func.id);
        methodTable.put(func.id, func);
    }
    @Override
    public FunctionType getFunction(String func, ASTNode node) {
        FunctionType f;
        if ((f = methodTable.get(func)) != null) return f;
        else return upstream.getFunction(func,node);
    }

}
