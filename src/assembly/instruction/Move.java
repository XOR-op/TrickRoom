package assembly.instruction;

import assembly.operand.RVRegister;

public class Move extends Computation{
    private RVRegister rd,rs;
    public Move(RVRegister rd,RVRegister rs){
        this.rd=rd;
        this.rs=rs;
    }

    @Override
    public String tell() {
        return "mv "+rd+", "+rs;
    }
}
