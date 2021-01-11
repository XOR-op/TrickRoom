package exception;

public class UnimplementedError extends RuntimeException{
    String info;
    public UnimplementedError(){this("DEFAULT");}
    public UnimplementedError(String s){
        info=s;
    }

    @Override
    public String toString() {
        return "MissingOverrideException{" +
                "info='" + info + '\'' +
                '}';
    }
}
