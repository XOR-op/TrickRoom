package compnent.scope;

import ast.ASTNode;
import compnent.basic.ClassType;
import compnent.basic.FunctionType;
import compnent.basic.Symbol;
import compnent.basic.Type;
import exception.MissingOverrideException;
import exception.semantic.DuplicateSyntaxException;
import exception.semantic.MissingSyntaxException;

import java.util.HashMap;

public class Scope {

    protected Scope upstream;
    protected HashMap<String, Symbol> syntaxTable;

    public Scope(Scope up){
        upstream=up;
        syntaxTable=new HashMap<>();
    }

    public Scope getUpstream() {
        return upstream;
    }

    public Type getVarType(String id, ASTNode node){
        var sym=syntaxTable.get(id);
        if(sym!=null) return sym.getType();
        else if(upstream!=null)return upstream.getVarType(id,node);
        else throw new MissingSyntaxException(node,id);
    }

    public FunctionType getFunction(String func, ASTNode node){
        if(upstream==null)throw new MissingOverrideException();
        return upstream.getFunction(func,node);
    }

    public ClassType getClass(String cls,ASTNode node){
        if(upstream==null)throw new MissingOverrideException();
        return upstream.getClass(cls,node);
    }


    public void registerVar(String id, Type tp,ASTNode node){
        registerVar(new Symbol(tp,id),node);
    }

    public void registerVar(Symbol sym,ASTNode node) {
        checkVarSyntax(sym.getName(),node);
        if (syntaxTable.containsKey(sym.getName()) ) throw new DuplicateSyntaxException(node,sym.getName());
        sym.setScope(this);
        syntaxTable.put(sym.getName(), sym);
    }

    protected void checkClassCollision(String id,ASTNode node){
        upstream.checkClassCollision(id,node);
    }

    protected void checkVarSyntax(String id,ASTNode node){
        if(upstream!=null)upstream.checkVarSyntax(id,node);
    }
    protected void checkFunctionSyntax(String id,ASTNode node){
        if(upstream!=null)upstream.checkVarSyntax(id,node);
    }
}
