package assembly.instruction;

import assembly.operand.RVRegister;

public class Move extends Computation implements Comparable {
    public Move(RVRegister rd, RVRegister rs) {
        super(rd, CompType.SPECIFIC, rs, null, null);
    }

    public RVRegister getRd() {
        return rd;
    }

    public RVRegister getRs() {
        return rs1;
    }

    @Override
    public String tell() {
        return "mv " + rd.tell() + ", " + rs1.tell();
    }

    @Override
    public int compareTo(Object o) {
//        return (rd.toString().compareTo(((Move) o).rd.toString())) << 16 + (rs1.toString().compareTo(((Move) o).rs1.toString()) & 0xffff);
        return o.hashCode()-this.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
