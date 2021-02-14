package ast;

import ast.struct.ExprNode;
import ast.type.Type;
import ast.scope.Scope;

public class Symbol {
    private String name;
    private Type type;
    private Scope scope;
    private boolean isGlobal;
    private boolean implicitThis;
    public String nameAsReg;
    public ExprNode initExpr;

    public Symbol(Type type, String name, String nameAsReg, boolean isGlobal, boolean implicitThis, ExprNode initExpr) {
        this.type = type;
        this.name = name;
        this.nameAsReg = nameAsReg;
        this.isGlobal = isGlobal;
        this.initExpr = initExpr;
        this.implicitThis = implicitThis;
    }

    public Symbol(Type type, String name, String nameAsReg) {
        this(type, name, nameAsReg, false, false, null);
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

    public boolean isGlobal() {
        return isGlobal;
    }

    public boolean implicitThis() {
        return implicitThis;
    }
}
