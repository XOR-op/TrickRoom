package assembly.operand;

import ir.operand.Register;

public class VirtualRegister extends RVRegister {

    public VirtualRegister(String name){
        this.name=name;
    }

    public VirtualRegister(Register irReg){
        name=irReg.identifier();
    }

    @Override
    public String tell() {
        assert color instanceof PhysicalRegister;
//        return L.ANSI_PURPLE+name+L.ANSI_RESET+":"+color.tell()+"\t";
        return color.tell();
    }

    @Override
    public String toString() {
        return color==null?name:color.tell();
    }
}
