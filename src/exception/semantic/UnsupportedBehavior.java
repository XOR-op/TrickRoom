package exception.semantic;

public class UnsupportedBehavior extends SemanticException{
    private String info;
    public UnsupportedBehavior(){this("");}
    public UnsupportedBehavior(String s){this.info=s;}

    @Override
    public String toString() {
        return "UnsupportedBehavior{" +
                "info='" + info + '\'' +
                '}';
    }
}
