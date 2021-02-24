package assembly.operand;

public abstract class RVOperand {
    public abstract String tell();

    @Override
    public String toString() {
        return tell();
    }
}
