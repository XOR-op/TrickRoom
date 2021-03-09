package ir.construct;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.Branch;
import ir.instruction.IRInst;
import ir.instruction.Ret;
import ir.operand.GlobalVar;
import ir.operand.Register;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class RegisterTracker {
    private IRFunction irFunc;

    private final HashMap<String, HashSet<IRInst>> defs = new HashMap<>();
    private final HashMap<String, HashSet<IRInst>> uses = new HashMap<>();
    private final HashMap<IRInst, IRBlock> instToBlock = new HashMap<>();

    public RegisterTracker(IRFunction irFunc) {
        this.irFunc = irFunc;
    }

    public void run() {
        defs.clear();
        uses.clear();
        instToBlock.clear();
        Consumer<IRInst> srcProcess = inst -> inst.forEachRegSrc(reg -> {
            if(reg instanceof GlobalVar)return;
            if (!uses.containsKey(reg.identifier())) uses.put(reg.identifier(), new HashSet<>());
            uses.get(reg.identifier()).add(inst);
        });
        Consumer<IRInst> destProcess = inst -> inst.forEachRegDest(reg -> {
            if(reg instanceof GlobalVar)return;
            if (!defs.containsKey(reg.identifier())) defs.put(reg.identifier(), new HashSet<>());
            defs.get(reg.identifier()).add(inst);
        });
        irFunc.blocks.forEach(block -> {
            block.insts.forEach(inst -> {
                instToBlock.put(inst, block);
                srcProcess.accept(inst);
                destProcess.accept(inst);
            });
            srcProcess.accept(block.terminatorInst);
        });
//        assert validate();
    }

    public HashSet<IRInst> queryRegisterUses(String str) {
        assert uses.containsKey(str);
        return uses.get(str);
    }

    public HashSet<IRInst> queryRegisterDefs(String str) {
        assert defs.containsKey(str);
        return defs.get(str);
    }

    public IRBlock queryInstBelongedBlock(IRInst inst) {
        assert instToBlock.containsKey(inst);
        return instToBlock.get(inst);
    }

    private boolean validate(){
        for(var entry:uses.entrySet()){
            if(!defs.containsKey(entry.getKey()))
                throw new IllegalStateException();
        }
        return true;
    }
}
