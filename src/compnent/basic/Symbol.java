package compnent.basic;

import compnent.scope.Scope;

public class Symbol {
    private String name;
    private Type type;
    private Scope scope;
    public String nameAsReg;

    public Symbol(Type type, String name,String nameAsReg) {
        this.type = type;
        this.name = name;
        this.nameAsReg=nameAsReg;
    }

    public boolean matches(Type rhs) {
        return rhs.equals(type);
    }

    public String getName() {
        return name;
    }

    public void setScope(Scope st) {
        scope = st;
    }

    public Scope getScope() {
        return scope;
    }

    public Type getType() {
        return type;
    }
}
