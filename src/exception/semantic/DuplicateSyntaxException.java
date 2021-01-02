package exception.semantic;

public class DuplicateSyntaxException extends SemanticException{
    private final String info;
    public DuplicateSyntaxException(String s){
        info=s;
    }

    @Override
    public String toString() {
        return info;
    }
}
