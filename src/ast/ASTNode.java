package ast;


import compnent.scope.Scope;
import semantic.ASTVisitor;

abstract public class ASTNode {
    public Scope scope;
    public abstract void accept(ASTVisitor visitor);
}

