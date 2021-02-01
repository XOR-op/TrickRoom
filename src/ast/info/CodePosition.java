package ast.info;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class CodePosition {
    public final int row,column;
    public CodePosition(int r,int c){
        row=r;
        column=c;
    }
    public CodePosition(Token token){
        this(token.getLine(),token.getCharPositionInLine());
    }
    public CodePosition(TerminalNode node){
        this(node.getSymbol());
    }
    public CodePosition(ParserRuleContext ctx){
        this(ctx.start);
    }

    @Override
    public String toString() {
        return "at (" + row + ", " + column+") ";
    }
}
