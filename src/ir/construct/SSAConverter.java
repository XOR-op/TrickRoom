package ir.construct;


import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.IRDestedInst;
import ir.instruction.Phi;
import ir.operand.Register;
import misc.pass.IRFunctionPass;

import java.util.*;

public class SSAConverter extends IRFunctionPass {
    // dominance
    private final HashMap<IRBlock, Integer> order = new HashMap<>();
    private final ArrayList<IRBlock> blocksByOrder = new ArrayList<>();
    private final HashMap<IRBlock, IRBlock> iDoms = new HashMap<>();
    private final HashMap<IRBlock, HashSet<IRBlock>> domTree = new HashMap<>();
    private final HashMap<IRBlock, ArrayList<IRBlock>> dominanceFrontier = new HashMap<>();

    private final int maxOrder;

    private final HashMap<String, Integer> renamingCounter = new HashMap<>();
    private final HashMap<String, Stack<Register>> namingStack = new HashMap<>();

    public SSAConverter(IRFunction f) {
        super(f);
        maxOrder = f.blocks.size();
        reversePostorder();
        calculateDom();
    }

    @Override
    protected void run() {
        calcDominanceFrontier();
        phiInsertion();
        variableRenaming();
    }

    private void reversePostorder() {
        reversePostorder(irFunc.entryBlock, new HashSet<>());
        Collections.reverse(blocksByOrder);
    }

    private void reversePostorder(IRBlock blk, HashSet<IRBlock> sets) {
        sets.add(blk);
        for (var b : blk.nexts) {
            if (!sets.contains(b)) reversePostorder(b, sets);
        }
        blocksByOrder.add(blk);
        order.put(blk, maxOrder - blocksByOrder.size());
    }

    private IRBlock intersect(IRBlock i, IRBlock j) {
        while (i != j) {
            while (order.get(i) > order.get(j)) {
                assert iDoms.get(i) != null;
                i = iDoms.get(i);
            }
            while (order.get(j) > order.get(i)) {
                assert iDoms.get(j) != null;
                j = iDoms.get(j);
            }
        }
        return i;
    }

    public void calculateDom() {
        irFunc.blocks.forEach(b -> {
            domTree.put(b, new HashSet<>());
        });
        blocksByOrder.forEach(b -> iDoms.put(b, null));
        iDoms.put(irFunc.entryBlock, irFunc.entryBlock);
        for (boolean flag = true; flag; ) {
            flag = false;
            for (int cur = 1; cur < blocksByOrder.size(); ++cur) {
                var curBlock = blocksByOrder.get(cur);
                var iter = curBlock.prevs.iterator();
                IRBlock newIDom = iter.next();
                while (iDoms.get(newIDom) == null) newIDom = iter.next(); // pick first processed one
                while (iter.hasNext()) {
                    var onePrev = iter.next();
                    if (iDoms.get(onePrev) != null)
                        newIDom = intersect(onePrev, newIDom);
                }
                if (iDoms.get(curBlock) != newIDom) {
                    iDoms.put(curBlock, newIDom);
                    flag = true;
                }
            }
        }
        iDoms.forEach((b, prev) -> {
            if (b != prev)
                domTree.get(prev).add(b);
        });
    }

    public void calcDominanceFrontier() {
        irFunc.blocks.forEach(b -> dominanceFrontier.put(b, new ArrayList<>()));
        for (var block : blocksByOrder) {
            if (block.prevs.size() > 1) {
                for (var prev : block.prevs) {
                    for (var cur = prev; cur != iDoms.get(block); cur = iDoms.get(cur))
                        dominanceFrontier.get(cur).add(block);
                }
            }
        }
    }

    public void phiInsertion() {
        irFunc.varDefs.forEach((variable, defsRef) -> {
            var added = new HashSet<IRBlock>();
            var defs = new LinkedList<>(defsRef);
            while (!defs.isEmpty()) {
                IRBlock oneDef = defs.pop();
                for (var frontier : dominanceFrontier.get(oneDef)) {
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
        domTree.get(bb).forEach(this::variableRenaming);
        modifiedSet.forEach(v -> {
            assert namingStack.get(v).size() > 1;
        });
        // pop current basic block's modified renaming
        modifiedSet.forEach(v -> namingStack.get(v).pop());
    }

}






