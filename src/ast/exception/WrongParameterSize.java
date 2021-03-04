package ast.exception;

import ast.struct.ASTNode;

public class WrongParameterSize extends NoMatchedFunction {

    public WrongParameterSize(ASTNode n) {
        super(n);
    }
    @Override
    public String toString() {
        return "WrongParameterSizeException{"+coor.toString()+"}";
    }
}
