package exception;

public class MissingOverrideException extends RuntimeException{
    String info;
    public MissingOverrideException(){this("DEFAULT");}
    public MissingOverrideException(String s){
        info=s;
    }

    @Override
    public String toString() {
        return "MissingOverrideException{" +
                "info='" + info + '\'' +
                '}';
    }
}
