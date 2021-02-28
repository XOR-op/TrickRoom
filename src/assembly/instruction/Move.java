package assembly.instruction;

import assembly.operand.RVRegister;

public class Move extends Computation{
    public Move(RVRegister rd,RVRegister rs){
        this.rd=rd;
        this.rs1=rs;
    }

    @Override
    public String tell() {
        return "mv "+rd.tell()+", "+rs1.tell();
    }
}
