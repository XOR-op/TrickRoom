package exception.semantic;

import ast.info.CodePosition;

public class ParsingException extends SemanticException{
    public ParsingException(CodePosition c) {
        super(c);
    }

    @Override
    public String toString() {
        return "ParsingException "+coor.toString();
    }
}
