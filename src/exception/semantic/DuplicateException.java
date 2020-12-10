package exception.semantic;

public class DuplicateException extends SemanticException{
    private final String info;
    public DuplicateException(String s){
        info=s;
    }

    @Override
    public String toString() {
        return info;
    }
}
