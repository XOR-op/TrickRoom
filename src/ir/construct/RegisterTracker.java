package ir.construct;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.IRDestedInst;
import ir.instruction.IRInst;
import ir.operand.GlobalVar;
import ir.operand.Register;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class RegisterTracker {
    private IRFunction irFunc;

    public final HashMap<String, HashSet<IRDestedInst>> defs = new HashMap<>();
    public final HashMap<String, HashSet<IRInst>> uses = new HashMap<>();
    public final HashMap<IRInst, IRBlock> instToBlock = new HashMap<>();

    public RegisterTracker(IRFunction irFunc) {
        this.irFunc = irFunc;
    }

    public void run() {
        defs.clear();
        uses.clear();
        instToBlock.clear();
        Consumer<IRInst> srcProcess = inst -> inst.forEachRegSrc(reg -> {
            if (reg instanceof GlobalVar) return;
            if (!uses.containsKey(reg.identifier())) uses.put(reg.identifier(), new HashSet<>());
            uses.get(reg.identifier()).add(inst);
        });
        Consumer<IRDestedInst> destProcess = inst -> inst.forEachRegDest(reg -> {
            if (reg instanceof GlobalVar) return;
            if (!defs.containsKey(reg.identifier())) defs.put(reg.identifier(), new HashSet<>());
            if (!uses.containsKey(reg.identifier())) uses.put(reg.identifier(), new HashSet<>());
            defs.get(reg.identifier()).add(inst);
        });
        irFunc.blocks.forEach(block -> {
            Consumer<IRInst> func = inst -> {
                instToBlock.put(inst, block);
                srcProcess.accept(inst);
                if (inst instanceof IRDestedInst)
                    destProcess.accept((IRDestedInst) inst);
            };
            block.phiCollection.forEach(func);
            block.insts.forEach(func);
            func.accept(block.terminatorInst);
            srcProcess.accept(block.terminatorInst);
        });
    }

    public HashSet<IRInst> queryRegisterUses(Register reg) {
        return queryRegisterUses(reg.identifier());
    }

    public HashSet<IRInst> queryRegisterUses(String str) {
        assert uses.containsKey(str);
        return uses.get(str);
    }

    public IRDestedInst querySingleDef(String str) {
        return defs.get(str).iterator().next();
    }

    public IRDestedInst querySingleDef(Register reg) {
        return querySingleDef(reg.identifier());
    }

    public IRBlock querySingleDefBlock(String str) {
        return instToBlock.get(querySingleDef(str));
    }

    public IRBlock querySingleDefBlock(Register reg) {
        return instToBlock.get(querySingleDef(reg));
    }

    public HashSet<IRDestedInst> queryRegisterDefs(Register reg) {
        return queryRegisterDefs(reg.identifier());
    }

    public HashSet<IRDestedInst> queryRegisterDefs(String str) {
        assert defs.containsKey(str);
        return defs.get(str);
    }

    public IRBlock queryInstBelongedBlock(IRInst inst) {
        assert instToBlock.containsKey(inst);
        return instToBlock.get(inst);
    }

    public Register nameToRegister(String str) {
        // ugly workaround for design flaw
        if (defs.containsKey(str)) return querySingleDef(str).dest;
        else if (uses.containsKey(str) && uses.get(str).size() > 0) {
            AtomicReference<Register> reg = new AtomicReference<>();
            queryRegisterUses(str).iterator().next().forEachRegSrc(src -> {
                if (src.identifier().equals(str)) reg.set(src);
            });
            return reg.get();
        } else {
            for (var reg : irFunc.parameters) {
                if (reg.identifier().equals(str)) return reg;
            }
            throw new IllegalStateException();
        }
    }

    public void updateInstBelonging(IRInst inst, IRBlock newBlock){
        instToBlock.put(inst,newBlock);
    }

    public void removeRegister(Register reg) {
        assert defs.get(reg.identifier()).size() == 1;
        uses.remove(reg.identifier());
        defs.remove(reg.identifier());
    }

    public boolean isParameter(String str) {
        for (var reg : irFunc.parameters) {
            if (reg.identifier().equals(str)) return true;
        }
        return false;
    }

    public boolean isParameter(Register reg) {
        return irFunc.parameters.contains(reg);
    }

    private boolean validate() {
        for (var entry : uses.entrySet()) {
            if (!defs.containsKey(entry.getKey()))
                throw new IllegalStateException();
        }
        return true;
    }

}
