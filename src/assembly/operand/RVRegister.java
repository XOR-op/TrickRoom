package assembly.operand;

public abstract class RVRegister extends RVOperand {
    protected RVRegister color;

    public void setColor(RVRegister color) {
        this.color = color;
    }
}
