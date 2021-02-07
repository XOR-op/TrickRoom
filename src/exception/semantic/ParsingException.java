package exception.semantic;

import ast.compilingInfo.CodePosition;

public class ParsingException extends SemanticException{
    public ParsingException(CodePosition c) {
        super(c);
    }

    @Override
    public String toString() {
        return "ParsingException "+coor.toString();
    }
}
