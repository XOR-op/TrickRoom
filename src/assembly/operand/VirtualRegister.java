package assembly.operand;

import ir.operand.Register;

public class VirtualRegister extends RVRegister {

    private String name;

    public VirtualRegister(String name){
        this.name=name;
    }

    public VirtualRegister(Register irReg){
        name=irReg.identifier();
    }

    @Override
    public String tell() {
        assert color instanceof PhysicalRegister;
        return color.tell();
    }

    @Override
    public String toString() {
        return color==null?name:color.tell();
    }
}
