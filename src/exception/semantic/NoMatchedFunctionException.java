package exception.semantic;

import ast.ASTNode;
import compnent.info.CodePosition;

public class NoMatchedFunctionException extends SemanticException{
    public NoMatchedFunctionException(ASTNode n) {
        super(n.coor);
    }

    @Override
    public String toString() {
        return "NoMatchedFunctionException{"+coor.toString()+"}";
    }
}
