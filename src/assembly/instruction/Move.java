package assembly.instruction;

import assembly.operand.RVRegister;

public class Move extends Computation{
    public Move(RVRegister rd,RVRegister rs){
        super(rd,CompType.SPECIFIC,rs,null,null);
    }

    @Override
    public String tell() {
        return "mv "+rd.tell()+", "+rs1.tell();
    }
}
