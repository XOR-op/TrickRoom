package ir.construct;


import ir.BasicBlock;
import ir.Function;
import ir.instruction.IRDestedInst;
import ir.instruction.Phi;
import ir.operand.Register;

import java.util.*;

public class SSAConverter {
    // dominance
    private final Function func;
    private final HashMap<BasicBlock, Integer> order = new HashMap<>();
    private final ArrayList<BasicBlock> blocksByOrder = new ArrayList<>();
    private final HashMap<BasicBlock, BasicBlock> iDoms = new HashMap<>();
    private final HashMap<BasicBlock, HashSet<BasicBlock>> domTree = new HashMap<>();
    private final HashMap<BasicBlock, ArrayList<BasicBlock>> dominanceFrontier = new HashMap<>();

    private final int maxOrder;

    private final HashMap<String, Integer> renamingCounter = new HashMap<>();
    private final HashMap<String, Stack<Register>> namingStack = new HashMap<>();

    public SSAConverter(Function f) {
        func = f;
        maxOrder = f.blocks.size();
        reversePostorder();
        calculateDom();
    }

    public void SSAConstruct() {
        calcDominanceFrontier();
        phiInsertion();
        variableRenaming();
    }

    private void reversePostorder() {
        reversePostorder(func.entryBlock, new HashSet<>());
        Collections.reverse(blocksByOrder);
    }

    private void reversePostorder(BasicBlock blk, HashSet<BasicBlock> sets) {
        sets.add(blk);
        for (var b : blk.nexts) {
            if (!sets.contains(b)) reversePostorder(b, sets);
        }
        blocksByOrder.add(blk);
        order.put(blk, maxOrder - blocksByOrder.size());
    }

    private BasicBlock intersect(BasicBlock i, BasicBlock j) {
        while (i!=j) {
            while (order.get(i) > order.get(j)) {
                assert iDoms.get(i)!=null;
                i = iDoms.get(i);
            }
            while (order.get(j) > order.get(i)) {
                assert iDoms.get(j)!=null;
                j = iDoms.get(j);
            }
        }
        return i;
    }

    public void calculateDom() {
        func.blocks.forEach(b -> {
            domTree.put(b, new HashSet<>());
        });
        blocksByOrder.forEach(b -> iDoms.put(b, null));
        iDoms.put(func.entryBlock, func.entryBlock);
        for (boolean flag = true; flag; ) {
            flag = false;
            for (int cur = 1; cur < blocksByOrder.size(); ++cur) {
                var curBlock = blocksByOrder.get(cur);
                var iter = curBlock.prevs.iterator();
                BasicBlock newIDom=iter.next();
                while (iDoms.get(newIDom)==null)newIDom=iter.next(); // pick first processed one
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
        func.blocks.forEach(b -> dominanceFrontier.put(b, new ArrayList<>()));
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
        func.varDefs.forEach((variable, defsRef) -> {
            var added = new HashSet<BasicBlock>();
            var defs = new LinkedList<>(defsRef);
            while (!defs.isEmpty()) {
                BasicBlock oneDef = defs.pop();
                for (var frontier : dominanceFrontier.get(oneDef)) {
                    if (!added.contains(frontier)) {
                        frontier.appendPhi(new Phi(new Register(func.varType.get(variable), variable)));
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
        int i = renamingCounter.get(var.name);
        renamingCounter.put(var.name, i + 1);
        var renaming = var.rename(i);
        if (modified.contains(var.name))
            namingStack.get(var.name).pop();
        else modified.add(var.name);
        namingStack.get(var.name).push(renaming);
        return renaming;
    }

    private Register getRenaming(Register var) {
        if (namingStack.containsKey(var.name))
            return namingStack.get(var.name).peek();
        else {
            // global or read only
            namingStack.put(var.name, new Stack<>());
            namingStack.get(var.name).push(var);
            return var;
        }
    }
    public void variableRenaming(){
        func.varDefs.forEach((v, s) -> {
            renamingCounter.put(v, 1);
            namingStack.put(v, new Stack<>());
        });
        func.parameters.forEach(r -> {
            namingStack.get(r.name).push(r);
        });
        variableRenaming(func.entryBlock);
    }

    private void variableRenaming(BasicBlock bb) {
        var modifiedSet = new HashSet<String>();
        bb.phiCollection.forEach(phi -> {
            phi.renameDest(allocNewRenaming(phi.dest, modifiedSet));
        });
        bb.insts.forEach(irInst -> {
            irInst.renameOperand(this::getRenaming);
            if (irInst instanceof IRDestedInst && ((IRDestedInst) irInst).namedDest())
                ((IRDestedInst) irInst).renameDest(r -> allocNewRenaming(r, modifiedSet));
        });
        bb.terminatorInst.renameOperand(this::getRenaming);
        bb.nexts.forEach(nbb -> {
            nbb.phiCollection.forEach(p -> {
                p.append(getRenaming(p.dest),bb);
            });
        });
        // iterate successor
        domTree.get(bb).forEach(this::variableRenaming);
        // pop current basic block's modified renaming
        modifiedSet.forEach(v -> namingStack.get(v).pop());
    }

}






