package exception.semantic;

public class MissingSyntaxException extends SemanticException{
    private String syntax;
    public MissingSyntaxException(String id){
        syntax=id;
    }

    @Override
    public String toString() {
        return "UndeclaredSyntaxException{" +
                "syntax='" + syntax + '\'' +
                '}';
    }
}
