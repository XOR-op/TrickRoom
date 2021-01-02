package compnent.basic;

import compnent.scope.Scope;

public class Symbol {
    private String name;
    private Type type;
    private Scope scope;

    public Symbol(Type type, String name) {
        this.type = type;
        this.name = name;
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
