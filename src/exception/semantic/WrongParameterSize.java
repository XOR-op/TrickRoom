package exception.semantic;

import ast.ASTNode;

public class WrongParameterSize extends NoMatchedFunction {

    public WrongParameterSize(ASTNode n) {
        super(n);
    }
    @Override
    public String toString() {
        return "WrongParameterSizeException{"+coor.toString()+"}";
    }
}
