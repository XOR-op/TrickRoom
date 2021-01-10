package compnent.scope;

import ast.ASTNode;
import compnent.basic.*;
import exception.semantic.*;

import java.util.HashMap;

public class FileScope extends Scope {
    private HashMap<String, FunctionType> functionTable;
    private HashMap<String, ClassType> classTable;

    public FileScope() {
        super(null);
        functionTable=new HashMap<>();
        classTable=new HashMap<>();
    }

    public void registerFunction(FunctionType func) {
        if (functionTable.containsKey(func.id)) throw new DuplicateSyntaxException(func.node,func.id);
        functionTable.put(func.id, func);
    }

    public void registerClass(ClassType cls) {
        if (classTable.containsKey(cls.id)  || syntaxTable.containsKey(cls.id)  ||
                functionTable.containsKey(cls.id) ) throw new DuplicateSyntaxException(cls.node,cls.id);
        classTable.put(cls.id, cls);
    }

    @Override
    public FunctionType getFunction(String func, ASTNode node) {
        FunctionType f;
        if ((f = functionTable.get(func)) != null) return f;
        else throw new MissingSyntaxException(node,func);
    }

    @Override
    public ClassType getClass(String cls, ASTNode node) {
        ClassType c;
        if((c=classTable.get(cls))!=null)return c;
        else throw new MissingSyntaxException(node,cls);
    }

    @Override
    protected void checkClassCollision(String id,ASTNode node) {
        if(classTable.containsKey(id))throw new DuplicateSyntaxException(node,id);
    }
    @Override
    protected void checkVarSyntax(String id,ASTNode node) {
        if(classTable.get(id)!=null)throw new DuplicateSyntaxException(node,id);
    }

    @Override
    protected void checkFunctionSyntax(String id,ASTNode node) {
        // method can have the same identifier with static function
        if(classTable.containsKey(id))throw new DuplicateSyntaxException(node,id);
    }
}
