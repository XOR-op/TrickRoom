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
    private final HashMap<BasicBlock, Integer> order =new HashMap<>();
    private final ArrayList<BasicBlock> blocksByOrder=new ArrayList<>();
    private final HashMap<BasicBlock,BasicBlock> iDoms=new HashMap<>();
    private final HashMap<BasicBlock,HashSet<BasicBlock>> domTree=new HashMap<>();
    private final HashMap<BasicBlock,ArrayList<BasicBlock>> dominanceFrontier=new HashMap<>();

    private final int maxOrder;

    private HashMap<String,Integer> renamingCounter=new HashMap<>();
    private HashMap<String,Stack<Register>> namingStack=new HashMap<>();

    public SSAConverter(Function f) {
        func = f;
        maxOrder = f.blocks.size();
        func.definedVariables.forEach((v,s)->{
            renamingCounter.put(v.name,1);
            namingStack.put(v.name,new Stack<>());
        });
        func.blocks.forEach(b->{
            domTree.put(b,new HashSet<>());
            dominanceFrontier.put(b,new ArrayList<>());
        });
        reversePostorder(f.entryBlock);
        calculateDom();
    }

    private Register newRenaming(Register var){
        var v=renamingCounter.get(var.name);
        renamingCounter.put(var.name,v+1);
        return var.rename(v);
    }

    private void reversePostorder(BasicBlock blk){
        for(var b:blk.nexts){
            if(!order.containsKey(b))reversePostorder(b);
        }
        blocksByOrder.add(blk);
        order.put(blk,maxOrder-blocksByOrder.size());
    }

    private BasicBlock intersect(BasicBlock i,BasicBlock j){
        while (!order.get(i).equals(order.get(j))){
            while (order.get(i)>order.get(j)){
                i=iDoms.get(i);
            }
            while (order.get(j)>order.get(i)){
                j=iDoms.get(j);
            }
        }
        return i;
    }

    public void calculateDom(){
        blocksByOrder.forEach(b->iDoms.put(b,null));
        iDoms.put(func.entryBlock, func.entryBlock);
        for(boolean flag=true;flag;){
            flag=false;
            for(int cur=1;cur<blocksByOrder.size();++cur){
                var curBlock=blocksByOrder.get(cur);
                var iter=curBlock.prevs.iterator();
                var newIDom=iter.next(); // pick random one
                while (iter.hasNext()){
                    var prev=iter.next();
                    if(iDoms.get(prev)!=null)
                        newIDom=intersect(prev,newIDom);
                }
                if(iDoms.get(curBlock)!=newIDom){
                    iDoms.put(curBlock,newIDom);
                    flag=true;
                }
            }
        }
        iDoms.forEach((b,prev)-> {
            if(b!=prev)
                domTree.get(prev).add(b);
        });
    }

    public void calcDominanceFrontier(){
        for(var block:blocksByOrder){
            if(block.prevs.size()>1){
                for(var prev:block.prevs){
                    for (var cur=prev;cur!=iDoms.get(block);cur=iDoms.get(cur))
                        dominanceFrontier.get(cur).add(block);
                }
            }
        }
    }

    public void phiInsertion(){
        func.definedVariables.forEach((variable,defsRef)->{
            var added=new HashSet<BasicBlock>();
            var defs=new LinkedList<>(defsRef);
            while (!defs.isEmpty()){
                BasicBlock oneDef=defs.pop();
                for(var frontier:dominanceFrontier.get(oneDef)){
                    if(!added.contains(frontier)){
                        frontier.appendPhi(new Phi(variable));
                        added.add(frontier);
                        if(!defs.contains(frontier)){
                            defs.add(frontier);
                        }
                    }
                }
            }
        });
    }

    private Register allocNewRenaming(Register var,HashSet<String> modified){
        int i=renamingCounter.get(var.name);
        renamingCounter.put(var.name,i+1);
        var renaming=var.rename(i);
        if(modified.contains(var.name))
            namingStack.get(var.name).pop();
        else modified.add(var.name);
        namingStack.get(var.name).push(renaming);
        return renaming;
    }

    private Register getRenaming(Register var){
        return namingStack.get(var.name).peek();
    }

    public void variableRenaming(BasicBlock bb){
        var modifiedSet=new HashSet<String>();
        bb.phiCollection.forEach(phi->{
            phi.renameDest(allocNewRenaming(phi.dest,modifiedSet));
        });
        bb.insts.forEach(irInst -> {
            irInst.renameOperand(this::getRenaming);
            if(irInst instanceof IRDestedInst && ((IRDestedInst) irInst).namedDest())
                ((IRDestedInst) irInst).renameDest(r->allocNewRenaming(r,modifiedSet));
        });
        bb.nexts.forEach(nbb->{
            nbb.phiCollection.forEach(p->{
                p.append(getRenaming(p.dest));
            });
        });
        // iterate successor
        domTree.get(bb).forEach(this::variableRenaming);
        // pop current basic block's modified renaming
        modifiedSet.forEach(v->namingStack.get(v).pop());
    }

}






