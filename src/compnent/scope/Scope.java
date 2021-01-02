package compnent.scope;

import compnent.basic.Function;
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

    public Type getType(String id){
        var sym=syntaxTable.get(id);
        if(sym!=null) return sym.getType();
        else if(upstream!=null)return upstream.getType(id);
        else throw new MissingSyntaxException(id);
    }

    public Function getFunction(String func){
        if(upstream==null)throw new MissingOverrideException();
        return upstream.getFunction(func);
    }


    public void registerVar(String id, Type tp){
        registerVar(new Symbol(tp,id));
    }

    public void registerVar(Symbol sym) {
        checkVarSyntax(sym.getName());
        if (syntaxTable.containsKey(sym.getName()) ) throw new DuplicateSyntaxException(sym.getName());
        sym.setScope(this);
        syntaxTable.put(sym.getName(), sym);
    }

    protected void checkClassCollision(String id){
        upstream.checkClassCollision(id);
    }

    protected void checkVarSyntax(String id){
        if(upstream!=null)upstream.checkVarSyntax(id);
    }
    protected void checkFunctionSyntax(String id){
        if(upstream!=null)upstream.checkVarSyntax(id);
    }
}
