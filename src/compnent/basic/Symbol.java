package compnent.basic;

import syntaxTable.SyntaxTable;

public class Symbol {
    private String name;


    private Type type;


    private SyntaxTable scope;
    public Symbol(Type type,String name){
        this.type=type;
        this.name=name;
    }

    public boolean matches(Type rhs) {
        return rhs.equals(type);
    }

    public String getName() {
        return name;
    }

    public void setScope(SyntaxTable st) {
        scope = st;
    }

    public SyntaxTable getScope() {
        return scope;
    }
    public Type getType() {
        return type;
    }
}
