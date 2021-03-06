package ast.struct;


import ast.ASTVisitor;
import ast.info.CodePosition;
import ast.scope.Scope;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

abstract public class ASTNode {
    public Scope scope;
    public CodePosition coor;
    public abstract Object accept(ASTVisitor visitor);
    public void setPos(ParserRuleContext ctx){coor=new CodePosition(ctx);}
    public void setPos(TerminalNode ctx){coor=new CodePosition(ctx);}
}

