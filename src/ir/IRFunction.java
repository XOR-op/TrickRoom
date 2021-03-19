package ir;

import ir.instruction.Branch;
import ir.instruction.Jump;
import ir.instruction.Phi;
import ir.instruction.Ret;
import ir.operand.Register;
import ir.typesystem.IRType;
import misc.Cst;

import java.util.*;

public class IRFunction {
    public String name;
    public IRType retTy;
    public LinkedList<IRBlock> blocks = new LinkedList<>();
    public ArrayList<Register> parameters = new ArrayList<>();
    // only before optimize
    public Map<String, HashSet<IRBlock>> varDefs = new HashMap<>();
    public Map<String, IRType> varType = new HashMap<>();
    public IRBlock entryBlock, exitBlock;
    public ArrayList<IRBlock> returnBlocks = new ArrayList<>();
    public Set<IRFunction> invokedFunctions = new HashSet<>();
    private final boolean isBuiltin;

    public IRFunction(String name, IRType returnType, boolean isBuiltin) {
        this.name = name;
        this.isBuiltin = isBuiltin;
        retTy = returnType;
        entryBlock = new IRBlock("entry", 0);
        exitBlock = new IRBlock("exit", 0);
        blocks.add(entryBlock);
    }

    public IRFunction(String name, IRType returnType) {
        this(name, returnType, false);
    }

    public IRFunction addParam(Register reg) {
        parameters.add(reg);
        varDefs.put(reg.getName(), new HashSet<>());
        varType.put(reg.getName(), reg.type);
        return this;
    }

    public void addReturn(IRBlock bb) {
        returnBlocks.add(bb);
    }

    public IRFunction addParam(IRType ty, String name) {
        return addParam(new Register(ty, name));
    }

    public void defineVar(Register reg, IRBlock bb) {
        // define and update Defs(var)
//        bb.defVariable(reg);
        varDefs.get(reg.getName()).add(bb);
    }

    public void declareVar(Register reg) {
        var newSet = new HashSet<IRBlock>();
        varDefs.put(reg.getName(), newSet);
        varType.put(reg.getName(), reg.type);
    }

    public IRFunction addBlock(IRBlock bl) {
        blocks.add(bl);
        return this;
    }

    public String toDeclaration() {
        var builder = new StringBuilder();
        var argJoiner = new StringJoiner(",", "(", ")");
        parameters.forEach(p -> argJoiner.add(p.type.tell()));
        builder.append("declare ").append(retTy.tell()).append(" @").append(name).append(argJoiner.toString()).append(" \n");
        return builder.toString();
    }

    public String tell() {
        var builder = new StringBuilder();
        var argJoiner = new StringJoiner(",", "(", ")");
        parameters.forEach(p -> argJoiner.add(p.type.tell() + " " + p.tell()));
        builder.append("define ").append(retTy.tell()).append(" @").append(name).append(argJoiner.toString()).append(" #0 {\n");
        blocks.forEach(b -> builder.append(b.tell()));
        return builder.append("}\n").toString();
    }

    public boolean isBuiltin() {
        return isBuiltin;
    }

    // (entryBlock:IRBlock, exitBlock:IRBlock, blockSet:HashSet<IRBlock>)
    public Object[] inlineClone(int serial) {
        HashMap<IRBlock, IRBlock> originToNew = new HashMap<>();
        String prefix = Cst.inlinePrefix(name, serial);
        blocks.forEach(b -> originToNew.put(b, new IRBlock(prefix + b.blockName, b.loopDepth)));
        for (var pair : originToNew.entrySet()) {
            var origin = pair.getKey();
            var substitute = pair.getValue();
            origin.nexts.forEach(nb -> substitute.nexts.add(originToNew.get(nb)));
            origin.prevs.forEach(pb -> substitute.prevs.add(originToNew.get(pb)));
            origin.insts.forEach(inst -> substitute.appendInst(inst.copy(prefix)));
            // phi
            origin.phiCollection.forEach(phi -> {
                Phi newPhi = new Phi(phi.dest.copy(prefix));
                phi.arguments.forEach(source -> {
                    newPhi.append(source.val.copy(prefix), originToNew.get(source.block));
                });
                substitute.appendPhi(newPhi);
            });
            // terminal
            if (origin.terminatorInst instanceof Ret) {
                substitute.terminatorInst = (origin.terminatorInst).copy(prefix);
            } else if (origin.terminatorInst instanceof Jump) {
                var oldJump = (Jump) origin.terminatorInst;
                substitute.terminatorInst = oldJump.copy(originToNew.get(oldJump.target));
            } else {
                assert origin.terminatorInst instanceof Branch;
                var oldBr = (Branch) origin.terminatorInst;
                substitute.terminatorInst = oldBr.copy(prefix, originToNew.get(oldBr.trueBranch), originToNew.get(oldBr.falseBranch));
            }
        }
        return new Object[]{originToNew.get(entryBlock), originToNew.get(exitBlock), new HashSet<>(originToNew.values())};
    }

}
