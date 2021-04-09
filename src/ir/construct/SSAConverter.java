package ir.construct;


import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.IRDestedInst;
import ir.instruction.Phi;
import ir.operand.Register;
import misc.pass.IRFunctionPass;

import java.util.*;

public class SSAConverter extends IRFunctionPass {


    private final HashMap<String, Integer> renamingCounter = new HashMap<>();
    private final HashMap<String, Stack<Register>> namingStack = new HashMap<>();
    private final DominanceTracker dominanceTracker;

    public SSAConverter(IRFunction f) {
        super(f);
        dominanceTracker = new DominanceTracker(f);
        dominanceTracker.invoke();
    }

    @Override
    protected void run() {
        phiInsertion();
        variableRenaming();
    }


    public void phiInsertion() {
        irFunc.varDefs.forEach((variable, defsRef) -> {
            var added = new HashSet<IRBlock>();
            var defs = new LinkedList<>(defsRef);
            while (!defs.isEmpty()) {
                IRBlock oneDef = defs.pop();
                for (var frontier : dominanceTracker.dominanceFrontier.get(oneDef)) {
                    if (!added.contains(frontier)) {
                        frontier.appendPhi(new Phi(new Register(irFunc.varType.get(variable), variable)));
                        added.add(frontier);
                        if (!defs.contains(frontier)) {
                            defs.add(frontier);
                        }
                    }
                }
            }
        });
    }

    private Register allocNewRenaming(Register var, HashSet<String> modified) {
        int i = renamingCounter.get(var.getName());
        renamingCounter.put(var.getName(), i + 1);
        var renaming = var.rename(i);
        assert renaming != null;
        // one bb one def
        if (modified.contains(var.getName()))
            namingStack.get(var.getName()).pop();
        else modified.add(var.getName());
        namingStack.get(var.getName()).push(renaming);
        return renaming;
    }

    private Register getRenaming(Register var) {
        if (namingStack.containsKey(var.getName()))
            return namingStack.get(var.getName()).peek();
        else {
            // global or read only
            namingStack.put(var.getName(), new Stack<>());
            namingStack.get(var.getName()).push(var);
            return var;
        }
    }

    public void variableRenaming() {
        irFunc.varDefs.forEach((v, s) -> {
            renamingCounter.put(v, 1);
            var stk = new Stack<Register>();
            stk.push(new Register(irFunc.varType.get(v), v));
            namingStack.put(v, stk);
        });
        irFunc.parameters.forEach(r -> {
            namingStack.get(r.getName()).push(r);
        });
        variableRenaming(irFunc.entryBlock);
    }

    private void variableRenaming(IRBlock bb) {
        var modifiedSet = new HashSet<String>();
        bb.phiCollection.forEach(phi -> {
            // ignore arrayNew phi
            if (phi.namedDest())
                phi.renameDest(allocNewRenaming(phi.dest, modifiedSet));
        });
        bb.insts.forEach(irInst -> {
            irInst.renameOperand(this::getRenaming);
            if (irInst.containsDest() && ((IRDestedInst) irInst).namedDest())
                ((IRDestedInst) irInst).renameDest(r -> allocNewRenaming(r, modifiedSet));
        });
        bb.terminatorInst.renameOperand(this::getRenaming);
        bb.nexts.forEach(nbb -> {
            nbb.phiCollection.forEach(p -> {
                if (p.namedDest())
                    p.append(getRenaming(p.dest), bb);
            });
        });
        // iterate successor
        dominanceTracker.domTree.get(bb).forEach(this::variableRenaming);
        modifiedSet.forEach(v -> {
            assert namingStack.get(v).size() > 1;
        });
        // pop current basic block's modified renaming
        modifiedSet.forEach(v -> namingStack.get(v).pop());
    }


}






