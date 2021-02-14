package ast.scope;

import ast.struct.ASTNode;
import ast.type.ClassType;
import ast.type.FunctionType;
import ast.Symbol;
import ast.type.Type;
import exception.UnimplementedError;
import exception.semantic.DuplicateSyntax;
import exception.semantic.MissingSyntax;

import java.util.HashMap;

public class Scope {

    protected Scope upstream;
    protected HashMap<String, Symbol> syntaxTable;
    protected int level;

    public Scope(Scope up){
        upstream=up;
        syntaxTable=new HashMap<>();
        level= up==null?0:up.level+1;
    }

    public String getSuffix(String s) {
        return upstream.getSuffix(s);
    }

    public Scope getUpstream() {
        return upstream;
    }

    public Type getVarType(String id, ASTNode node){
        return getVarSymbol(id,node).getType();
    }
    public Symbol getVarSymbol(String id,ASTNode node){
        var sym=syntaxTable.get(id);
        if(sym!=null) return sym;
        else if(upstream!=null)return upstream.getVarSymbol(id,node);
        else throw new MissingSyntax(node,id);
    }

    public FunctionType getFunction(String func, ASTNode node){
        if(upstream==null)throw new UnimplementedError();
        return upstream.getFunction(func,node);
    }

    public ClassType getClass(String cls,ASTNode node){
        if(upstream==null)throw new UnimplementedError();
        return upstream.getClass(cls,node);
    }


    public void registerVar(String id, Type tp,String nameAsReg,ASTNode node){
        registerVar(new Symbol(tp,id,nameAsReg),node);
    }

    public void registerVar(Symbol sym,ASTNode node) {
        checkVarSyntax(sym.getName(),node);
        if (syntaxTable.containsKey(sym.getName()) ) throw new DuplicateSyntax(node,sym.getName());
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
