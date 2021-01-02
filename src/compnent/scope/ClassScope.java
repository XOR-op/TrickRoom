package compnent.scope;

import compnent.basic.ClassType;
import compnent.basic.Function;
import exception.semantic.DuplicateSyntaxException;
import exception.semantic.MissingSyntaxException;

import java.util.HashMap;

public class ClassScope extends Scope{
    private HashMap<String, Function> methodTable;
    ClassType classType;
    public ClassScope(Scope up, ClassType cls){
        super(up);
        methodTable=new HashMap<>();
        classType=cls;
    }

    public void registerMethod(Function func) {
        checkFunctionSyntax(func.name);
        if (methodTable.get(func.name) != null) throw new DuplicateSyntaxException(func.name);
        methodTable.put(func.name, func);
    }
    @Override
    public Function getFunction(String func) {
        Function f;
        if ((f = methodTable.get(func)) != null) return f;
        else return upstream.getFunction(func);
    }

}
