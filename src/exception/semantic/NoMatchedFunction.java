package exception.semantic;

import ast.ASTNode;

public class NoMatchedFunction extends SemanticException{
    private String info;
    public NoMatchedFunction(ASTNode n) {
        this(n,"");
    }

    public NoMatchedFunction(ASTNode n, String info){
        super(n.coor);
        this.info=info;
    }
    @Override
    public String toString() {
        return "NoMatchedFunctionException{"+coor.toString()+(info.equals("")?"":" info:"+info)+"}";
    }
}
