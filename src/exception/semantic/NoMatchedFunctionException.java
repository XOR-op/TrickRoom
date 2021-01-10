package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class NoMatchedFunctionException extends SemanticException{
    private String info;
    public NoMatchedFunctionException(ASTNode n) {
        this(n,"");
    }

    public NoMatchedFunctionException(ASTNode n,String info){
        super(n.coor);
        this.info=info;
    }
    @Override
    public String toString() {
        return "NoMatchedFunctionException{"+coor.toString()+(info.equals("")?"":" info:"+info)+"}";
    }
}
