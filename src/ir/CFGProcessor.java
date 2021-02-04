package ir;


import java.util.ArrayList;
import java.util.HashMap;

public class CFGProcessor {
    // dominance
    private Function func;
    private HashMap<BasicBlock, Integer> order =new HashMap<>();
    private ArrayList<BasicBlock> blocksByOrder=new ArrayList<>();
    private HashMap<BasicBlock,BasicBlock> iDoms=new HashMap<>();

    private final int maxOrder;


    private void reversePostorder(BasicBlock blk){
        for(var b:blk.nexts){
            if(!order.containsKey(b))reversePostorder(b);
        }
        blocksByOrder.add(blk);
        order.put(blk,maxOrder-blocksByOrder.size());
    }

    public CFGProcessor(Function f){
        func =f;
        maxOrder=f.blocks.size();
        reversePostorder(f.entryBlock);
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
    }

    public void calcDominanceFrontier(){
        for(var block:blocksByOrder){
            if(block.prevs.size()>1){
                for(var prev:block.prevs){
                    for (var cur=prev;cur!=iDoms.get(block);cur=iDoms.get(cur))
                        cur.dominanceFrontier.add(block);
                }
            }
        }
    }

}