package compnent.scope;

import compnent.basic.*;
import exception.semantic.*;

import java.util.HashMap;

public class FileScope extends Scope {
    private HashMap<String, Function> functionTable;
    private HashMap<String, ClassType> classTable;

    public FileScope() {
        super(null);
        functionTable=new HashMap<>();
        classTable=new HashMap<>();
    }

    public void registerFunction(Function func) {
        if (functionTable.get(func.name) != null) throw new DuplicateSyntaxException(func.name);
        functionTable.put(func.name, func);
    }

    public void registerClass(ClassType cls) {
        if (classTable.get(cls.name) != null || syntaxTable.get(cls.name) != null ||
                functionTable.get(cls.name) != null) throw new DuplicateSyntaxException(cls.name);
        classTable.put(cls.name, cls);
    }

    @Override
    public Function getFunction(String func) {
        Function f;
        if ((f = functionTable.get(func)) != null) return f;
        else throw new MissingSyntaxException(func);
    }

    @Override
    protected void checkClassCollision(String id) {
        if(classTable.get(id)!=null)throw new DuplicateSyntaxException(id);
    }
    @Override
    protected void checkVarSyntax(String id) {
        if(classTable.get(id)!=null)throw new DuplicateSyntaxException(id);
    }

    @Override
    protected void checkFunctionSyntax(String id) {
        // method can have the same identifier with static function
        if(classTable.get(id)!=null)throw new DuplicateSyntaxException(id);
    }
}
