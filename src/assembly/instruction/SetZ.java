package assembly.instruction;

import assembly.operand.RVRegister;

public class SetZ extends Computation{

    public enum SetType{ seqz,snez,sltz,sgtz}

    private SetType st;

    public SetZ(RVRegister rd,SetType st,RVRegister rs){
        super(rd,CompType.SPECIFIC,rs,null,null);
        this.st=st;
    }

    @Override
    public String tell() {
        return st+" "+rd.tell()+", "+rs1.tell();
    }
}
