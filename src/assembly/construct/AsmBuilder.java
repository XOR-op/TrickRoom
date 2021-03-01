package assembly.construct;

import assembly.AsmBlock;
import assembly.AsmFunction;
import assembly.RVInfo;
import assembly.instruction.*;
import assembly.operand.Imm;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;
import ir.BasicBlock;
import ir.Cst;
import ir.IRFunction;
import ir.IRInfo;
import ir.instruction.*;
import ir.instruction.Branch;
import ir.instruction.Call;
import ir.instruction.Jump;
import ir.operand.*;
import ir.typesystem.PointerType;
import ir.typesystem.StructureType;

import java.util.HashMap;

/*
    delay register restoration to Stage Register Allocation
 */
public class AsmBuilder {
    private IRInfo irInfo;
    private RVInfo rvInfo;
    private AsmBlock curBlock;
    private AsmFunction curFunc;
    private final HashMap<String, VirtualRegister> nameToVirReg = new HashMap<>();

    private final HashMap<IRFunction, AsmFunction> funcMapping = new HashMap<>();

    private class NameGenerator {
        private int n = 0;
        private final String prefix;

        public NameGenerator(String prefix) {
            this.prefix = prefix;
        }

        public VirtualRegister gen() {
            return new VirtualRegister(prefix + (n++));
        }
    }

    private class BlockMapping {
        private HashMap<BasicBlock, AsmBlock> mapping = new HashMap<>();
        private AsmFunction asmFunc;

        public BlockMapping(AsmFunction asmFunc, IRFunction irFunc) {
            this.asmFunc = asmFunc;
            irFunc.blocks.forEach(block -> {
                var curBlk = getBlock(block);
                block.prevs.forEach(p -> curBlk.addPrevBlock(getBlock(p)));
                block.nexts.forEach(p -> curBlk.addNextBlock(getBlock(p)));
            });
            this.asmFunc.setEntry(getBlock(irFunc.entryBlock));
        }

        public AsmBlock getBlock(BasicBlock blk) {
            if (mapping.containsKey(blk)) return mapping.get(blk);
            else {
                var asmBlk = new AsmBlock(blk.getBlockName(), blk.loopDepth);
                mapping.put(blk, asmBlk);
                asmFunc.addBlock(asmBlk);
                return asmBlk;
            }
        }
    }

    private RVInst.WidthType resolveWidth(IROperand operand) {
        return (operand.type.equals(Cst.bool) || operand.type.equals(Cst.byte_t)) ? RVInst.WidthType.b : RVInst.WidthType.w;
    }

    private NameGenerator ng;
    private BlockMapping curBlockMapping;


    public AsmBuilder(IRInfo irInfo) {
        this.irInfo = irInfo;
        rvInfo = new RVInfo(irInfo);
    }

    public RVInfo constructAssembly() {
        irInfo.forEachFunction(f -> {
            if (!f.isBuiltin())
                buildFunction(rvInfo.getFunc(f), f);
        });
        return rvInfo;
    }

    private RVRegister getRegister(IROperand operand) {
        if (operand instanceof Register) {
            var name = ((Register) operand).identifier();
            if (!nameToVirReg.containsKey(name))
                nameToVirReg.put(name, new VirtualRegister(name));
            return nameToVirReg.get(name);
        } else {
            int i = (operand instanceof IntConstant) ? ((IntConstant) operand).value :
                    (((BoolConstant) operand).value ? 1 : 0);
            var inst = new LoadImm(ng.gen(), i);
            curBlock.addInst(inst);
            return inst.rd;
        }
    }

    private RVInst copyToReg(RVRegister target, IROperand op) {
        if (op instanceof Register) return new Move(target, getRegister(op));
        else {
            int val = (op instanceof IntConstant) ? ((IntConstant) op).value : ((((BoolConstant) op).value) ? 1 : 0);
            return new LoadImm(target, val);
        }
    }


    private void buildFunction(AsmFunction asmFunc, IRFunction irFunc) {
        ng = new NameGenerator("asm.virtualReg");
        curBlockMapping = new BlockMapping(asmFunc, irFunc);
        curFunc = asmFunc;
        nameToVirReg.clear();
        // store arguments
        var entry = curBlockMapping.getBlock(irFunc.entryBlock);
        entry.name = irFunc.name;
        for (int i = 0; i < Integer.min(8, asmFunc.parameterCount); ++i) {
            entry.addInst(new Move(getRegister(irFunc.parameters.get(i)), PhysicalRegister.get(10 + i)));
        }
        for (int i = 8; i < asmFunc.parameterCount; ++i) {
            var reg = getRegister(irFunc.parameters.get(i));
            entry.addInst(new LoadData(reg, resolveWidth(irFunc.parameters.get(i)),
                    PhysicalRegister.get("sp"), new Imm(asmFunc.getVarOffset(reg))));
        }
        irFunc.blocks.forEach(b -> buildBlock(curBlockMapping.getBlock(b), b));
        curFunc = null;
    }

    private void buildBlock(AsmBlock asmBlock, BasicBlock irBlock) {
        curBlock = asmBlock;
        irBlock.insts.forEach(irInst -> {
            if (!((irInst instanceof Compare) && irBlock.terminatorInst instanceof Branch && irInst != irBlock.insts.getLast()))
                buildInst(irInst);
        });
        // build terminal instruction
        if (irBlock.terminatorInst instanceof Branch) {
            var irBranch = (Branch) irBlock.terminatorInst;
            Compare irCmp = (irBlock.insts.getLast() instanceof Compare) ? ((Compare) irBlock.insts.getLast()) : null;
            if (irCmp != null && irCmp.dest == irBranch.condition) {
                // coalesce cmp and branch
                RVInst.RelaType rt;
                switch (((Compare) irBlock.insts.getLast()).type) {
                    case eq -> rt = RVInst.RelaType.eq;
                    case ne -> rt = RVInst.RelaType.ne;
                    case sle -> rt = RVInst.RelaType.le;
                    case sge -> rt = RVInst.RelaType.ge;
                    case slt -> rt = RVInst.RelaType.lt;
                    case sgt -> rt = RVInst.RelaType.gt;
                    default -> throw new IllegalStateException();
                }
                curBlock.addInst(new RVBranch(rt, false, getRegister(irCmp.operand1), getRegister(irCmp.operand2),
                        curBlockMapping.getBlock(irBranch.trueBranch), curBlockMapping.getBlock(irBranch.falseBranch)));
            } else {
                curBlock.addInst(new RVBranch(RVInst.RelaType.ne, false, getRegister(irBranch.condition), PhysicalRegister.Zero(),
                        curBlockMapping.getBlock(irBranch.trueBranch), curBlockMapping.getBlock(irBranch.falseBranch)));
            }
        } else if (irBlock.terminatorInst instanceof Jump) {
            curBlock.addInst(new assembly.instruction.Jump(curBlockMapping.getBlock(((Jump) irBlock.terminatorInst).getTarget())));
        } else {
            assert irBlock.terminatorInst instanceof Ret;
            var val = ((Ret) irBlock.terminatorInst).value;
            if (val != null) {
                curBlock.addInst(copyToReg(PhysicalRegister.get("a0"), val));
            }
            curBlock.addInst(new Return());
            // notice: no restoration here currently
        }
        curBlock = null;
    }

    private void buildInst(IRInst inst) {
        if (inst instanceof Assign) buildAssign((Assign) inst);
        else if (inst instanceof Binary) buildBinary((Binary) inst);
        else if (inst instanceof BitCast) buildBitCast((BitCast) inst);
        else if (inst instanceof Call) buildCall((Call) inst);
        else if (inst instanceof Compare) buildCompare((Compare) inst);
        else if (inst instanceof GetElementPtr) buildGetElementPtr((GetElementPtr) inst);
        else if (inst instanceof Load) buildLoad((Load) inst);
        else if (inst instanceof Store) buildStore((Store) inst);
        else throw new IllegalStateException(((Object) inst).toString());
    }


    private void buildAssign(Assign inst) {
        if (inst.src instanceof UndefConstant) return;
        curBlock.addInst(copyToReg(getRegister(inst.dest), inst.src));
    }

    private void buildBinary(Binary inst) {
        // should be optimized
        assert !(inst.operand1 instanceof IntConstant && inst.operand2 instanceof IntConstant);
        var ct = Computation.getCompType(inst.inst);
        var rd = getRegister(inst.dest);
        RVRegister rs1, rs2 = null;
        Imm imm = null;
        if (inst.operand1 instanceof IntConstant) {
            switch (ct) {
                case div, rem, sll, sra -> {
                    rs1 = getRegister(inst.operand1);
                    rs2 = getRegister(inst.operand2);
                }
                default -> {
                    rs1 = getRegister(inst.operand2);
                    imm = new Imm(((IntConstant) inst.operand1).value);
                }
            }
        } else {
            assert inst.operand1 instanceof Register;
            rs1 = getRegister(inst.operand1);
            if (inst.operand2 instanceof Register)
                rs2 = getRegister(inst.operand2);
            else imm = new Imm(((IntConstant) inst.operand2).value);
        }
        if (imm == null)
            curBlock.addInst(new Computation(rd, ct, rs1, rs2));
        else
            curBlock.addInst(new Computation(rd, ct, rs1, imm));
    }

    private void buildBitCast(BitCast inst) {
        curBlock.addInst(new Computation(getRegister(inst.dest), Computation.CompType.add, getRegister(inst.from), new Imm(0)));
    }

    private void buildCall(Call inst) {
        // todo backup
        for (int i = 0; i < Integer.min(inst.args.size(), 8); ++i) {
            var op = inst.args.get(i);
            var target = PhysicalRegister.get(10 + i);
            curBlock.addInst(copyToReg(target, op));
        }
        if (inst.args.size() >= 8) {
            // store to the sp
            int curOff = 0;
            for (int i = 8; i < inst.args.size(); ++i) {
                curBlock.addInst(new StoreData(resolveWidth(inst.args.get(i)), PhysicalRegister.get("sp"),
                        getRegister(inst.args.get(i)), new Imm(curOff)));
                curOff += 4;
            }
            curBlock.addInst(new RVCall(rvInfo.getFunc(inst.function)));
            if (inst.containsDest()) {
                curBlock.addInst(new Move(getRegister(inst.dest), PhysicalRegister.get("s0")));
            }
        }
        curBlock.addInst(new RVCall(rvInfo.getFunc(inst.function)));
        if (inst.containsDest())
            curBlock.addInst(new Move(getRegister(inst.dest), PhysicalRegister.get("a0")));
    }

    private void buildCompare(Compare inst) {
        var rs1 = getRegister(inst.operand1);
        var rs2 = getRegister(inst.operand2);
        switch (inst.type) {
            case slt -> curBlock.addInst(new Computation(getRegister(inst.dest), Computation.CompType.slt, rs1, rs2));
            case sgt -> curBlock.addInst(new Computation(getRegister(inst.dest), Computation.CompType.slt, rs2, rs1));
            case eq -> {
                var xor = new Computation(ng.gen(), Computation.CompType.xor, rs1, rs2);
                curBlock.addInst(xor);
                curBlock.addInst(new SetZ(getRegister(inst.dest), SetZ.SetType.seqz, xor.rd));
            }
            case ne -> {
                var xor = new Computation(ng.gen(), Computation.CompType.xor, rs1, rs2);
                curBlock.addInst(xor);
                curBlock.addInst(new SetZ(getRegister(inst.dest), SetZ.SetType.snez, xor.rd));
            }
            case sle -> {
                var gt = new Computation(ng.gen(), Computation.CompType.slt, rs2, rs1);
                curBlock.addInst(gt);
                curBlock.addInst(new Computation(getRegister(inst.dest), Computation.CompType.xor, gt.rd, new Imm(1)));
            }
            case sge -> {
                var lt = new Computation(ng.gen(), Computation.CompType.slt, rs1, rs2);
                curBlock.addInst(lt);
                curBlock.addInst(new Computation(getRegister(inst.dest), Computation.CompType.xor, lt.rd, new Imm(1)));
            }
            default -> throw new IllegalStateException();
        }
    }

    private void buildGetElementPtr(GetElementPtr inst) {
        var base = getRegister(inst.base);
        if (inst.indexing instanceof IntConstant) {
            var tp = ((PointerType) inst.base.type).subType();
            int i = tp.size() * ((IntConstant) inst.indexing).value;
            if (inst.offset != null) {
                i += ((StructureType) tp).getMemberOffset(inst.offset.value);
            }
            var calcAddr = new Computation(getRegister(inst.dest), Computation.CompType.add, base, new Imm(i));
            curBlock.addInst(calcAddr);
        } else {
            if (inst.offset == null) {
                var addBase = new Computation(getRegister(inst.dest), Computation.CompType.add, base, getRegister(inst.indexing));
                curBlock.addInst(addBase);
            } else {
                var addBase = new Computation(ng.gen(), Computation.CompType.add, base, getRegister(inst.indexing));
                var addOffset = new Computation(getRegister(inst.dest), Computation.CompType.add, addBase.rd, new Imm(inst.offset.value));
                curBlock.addInst(addBase);
                curBlock.addInst(addOffset);
            }
        }
    }

    private void buildLoad(Load inst) {
        var loading = new LoadData(getRegister(inst.dest), resolveWidth(inst.dest),
                getRegister(inst.address), new Imm(0));
        curBlock.addInst(loading);
    }

    private void buildStore(Store inst) {
        var storing = new StoreData(resolveWidth(inst.source),
                getRegister(inst.address), getRegister(inst.source), new Imm(0));
        curBlock.addInst(storing);
    }
}
