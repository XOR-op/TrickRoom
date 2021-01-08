package exception.semantic;

import compnent.info.CodePosition;

public class SemanticException extends RuntimeException{
    public CodePosition coor;
    public SemanticException(CodePosition c){coor=c;}
    @Override
    public String toString() {
        return coor.toString();
    }
}
