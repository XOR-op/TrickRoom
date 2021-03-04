package ast.scope;

import ast.Symbol;
import ast.struct.ASTNode;
import ast.type.*;
import ast.exception.*;

import java.util.HashMap;

public class FileScope extends Scope {
    public HashMap<String, FunctionType> functionTable=new HashMap<>();
    public HashMap<String, ClassType> classTable=new HashMap<>();
    public HashMap<String, Symbol> globalVarTable=new HashMap<>();

    public FileScope() {
        super(null);
    }

    public void registerFunction(FunctionType func) {
        if (functionTable.containsKey(func.id)) throw new DuplicateSyntax(func.node,func.id);
        functionTable.put(func.id, func);
    }

    public void registerClass(ClassType cls) {
        if (classTable.containsKey(cls.id)  || syntaxTable.containsKey(cls.id)  ||
                functionTable.containsKey(cls.id) ) throw new DuplicateSyntax(cls.node,cls.id);
        classTable.put(cls.id, cls);
    }

    @Override
    public String getSuffix(String s) {
        return "";
    }

    @Override
    public FunctionType getFunction(String func, ASTNode node) {
        FunctionType f;
        if ((f = functionTable.get(func)) != null) return f;
        else throw new MissingSyntax(node,func);
    }

    @Override
    public ClassType getClass(String cls, ASTNode node) {
        ClassType c;
        if((c=classTable.get(cls))!=null)return c;
        else throw new MissingSyntax(node,cls);
    }

    @Override
    protected void checkClassCollision(String id,ASTNode node) {
        if(classTable.containsKey(id))throw new DuplicateSyntax(node,id);
    }
    @Override
    protected void checkVarSyntax(String id,ASTNode node) {
        if(classTable.get(id)!=null)throw new DuplicateSyntax(node,id);
    }

    @Override
    protected void checkFunctionSyntax(String id,ASTNode node) {
        // method can have the same identifier with static function
        if(classTable.containsKey(id))throw new DuplicateSyntax(node,id);
    }
}
