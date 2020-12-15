package ast;


import semantic.ASTVisitor;

 public interface ASTNode {
    void accept(ASTVisitor visitor);
}

