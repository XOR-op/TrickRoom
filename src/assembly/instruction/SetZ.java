package assembly.instruction;

import assembly.operand.RVRegister;

public class SetZ extends Computation{

    public enum SetType{ seqz,snez,sltz,sgtz}

    private SetType st;

    public SetZ(RVRegister rd,SetType st,RVRegister rs){
        this.rd=rd;
        this.rs1=rs;
        this.st=st;
    }

    @Override
    public String tell() {
        return st+" "+rd+", "+rs1;
    }
}
