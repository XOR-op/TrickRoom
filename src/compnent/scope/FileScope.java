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
        if (functionTable.containsKey(func.id)) throw new DuplicateSyntaxException(func.id);
        functionTable.put(func.id, func);
    }

    public void registerClass(ClassType cls) {
        if (classTable.containsKey(cls.id)  || syntaxTable.containsKey(cls.id)  ||
                functionTable.containsKey(cls.id) ) throw new DuplicateSyntaxException(cls.id);
        classTable.put(cls.id, cls);
    }

    @Override
    public Function getFunction(String func) {
        Function f;
        if ((f = functionTable.get(func)) != null) return f;
        else throw new MissingSyntaxException(func);
    }

    @Override
    public ClassType getClass(String cls) {
        ClassType c;
        if((c=classTable.get(cls))!=null)return c;
        else throw new MissingSyntaxException(cls);
    }

    @Override
    protected void checkClassCollision(String id) {
        if(classTable.containsKey(id))throw new DuplicateSyntaxException(id);
    }
    @Override
    protected void checkVarSyntax(String id) {
        if(classTable.get(id)!=null)throw new DuplicateSyntaxException(id);
    }

    @Override
    protected void checkFunctionSyntax(String id) {
        // method can have the same identifier with static function
        if(classTable.containsKey(id))throw new DuplicateSyntaxException(id);
    }
}
