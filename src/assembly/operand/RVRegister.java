package assembly.operand;

import java.util.Objects;

public abstract class RVRegister extends RVOperand implements Comparable {
    protected RVRegister color;
    protected String name;

    public void setColor(RVRegister color) {
        this.color = color;
    }

    public RVRegister getColor() {
        return color;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((RVRegister) o).name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RVRegister that = (RVRegister) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
