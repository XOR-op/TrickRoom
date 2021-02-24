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
    public boolean equals(RVRegister rhs){
        return (rhs instanceof VirtualRegister)&&((VirtualRegister) rhs).name.equals(name);
    }

    @Override
    public String tell() {
        return name;
//        throw new IllegalStateException();
    }
}
