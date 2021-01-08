package compnent.scope;

import ast.ASTNode;
import compnent.basic.ClassType;
import compnent.basic.Function;
import exception.semantic.DuplicateSyntaxException;

import java.util.HashMap;

public class ClassScope extends Scope{
    private HashMap<String, Function> methodTable;
    ClassType classType;
    public ClassScope(Scope up, ClassType cls){
        super(up);
        methodTable=new HashMap<>();
        classType=cls;
    }

    public void registerMethod(Function func,ASTNode node) {
        checkFunctionSyntax(func.id,node);
        if (methodTable.containsKey(func.id) ) throw new DuplicateSyntaxException(node,func.id);
        methodTable.put(func.id, func);
    }
    @Override
    public Function getFunction(String func, ASTNode node) {
        Function f;
        if ((f = methodTable.get(func)) != null) return f;
        else return upstream.getFunction(func,node);
    }

}
