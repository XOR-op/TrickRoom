package exception.semantic;

import ast.ASTNode;

public class WrongParameterSizeException extends NoMatchedFunctionException {

    public WrongParameterSizeException(ASTNode n) {
        super(n);
    }
    @Override
    public String toString() {
        return "WrongParameterSizeException{"+coor.toString()+"}";
    }
}
