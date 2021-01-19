package ir;

import ir.instruction.IRInst;

import java.util.ArrayList;

public class BasicBlock {
    public ArrayList<BasicBlock> prevs,nexts;
    public ArrayList<IRInst> insts;

}
