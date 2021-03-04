package assembly.construct;

import assembly.AsmBlock;
import assembly.AsmFunction;
import assembly.RVInfo;
import assembly.instruction.*;
import assembly.operand.*;
import ir.BasicBlock;
import misc.Cst;
import ir.IRFunction;
import ir.IRInfo;
import ir.instruction.*;
import ir.instruction.Branch;
import ir.instruction.Call;
import ir.instruction.Jump;
import ir.operand.*;
import ir.typesystem.PointerType;
import ir.typesystem.StructureType;
import misc.Cst;

import java.util.HashMap;

public class AsmBuilder {
    private IRInfo irInfo;
    private RVInfo rvInfo;
    private AsmBlock curBlock;
    private AsmFunction curFunc;

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
                var asmBlk = new AsmBlock(asmFunc, blk.getBlockName(), blk.loopDepth);
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
            buildFunction(rvInfo.getFunc(f), f);
        });
        return rvInfo;
    }

    private RVRegister getRegister(IROperand operand) {
        if (operand instanceof Register) {
            if (operand instanceof GlobalVar) {
                var addrReg = ng.gen();
                var valReg = ng.gen();
                curBlock.addInst(new Lui(addrReg, new AddrImm(AddrImm.Part.hi, operand)));
                curBlock.addInst(new LoadMem(valReg, resolveWidth(operand),
                        addrReg, new AddrImm(AddrImm.Part.lo, operand)));
                return valReg;
            } else {
                var name = ((Register) operand).identifier();
                if (!curFunc.nameToVirReg.containsKey(name))
                    curFunc.nameToVirReg.put(name, new VirtualRegister(name));
                return curFunc.nameToVirReg.get(name);
            }
        } else {
            int i = (operand instanceof IntConstant) ? ((IntConstant) operand).value :
                    ((operand instanceof NullptrConstant) ? 0 : (((BoolConstant) operand).value ? 1 : 0));
            var inst = new LoadImm(ng.gen(), i);
            curBlock.addInst(inst);
            return inst.rd;
        }
    }

    private RVRegister getRegister(String name) {
        if (!curFunc.nameToVirReg.containsKey(name))
            curFunc.nameToVirReg.put(name, new VirtualRegister(name));
        return curFunc.nameToVirReg.get(name);
    }

    private void copyToReg(RVRegister target, IROperand op) {
        if (op instanceof Register) {
            curBlock.addInst(new Move(target, getRegister(op)));
            return;
        } else {
            int val;
            if (op instanceof IntConstant) {
                val = ((IntConstant) op).value;
            } else if (op instanceof NullptrConstant) {
                val = 0;
            } else if (op instanceof BoolConstant) {
                val = ((BoolConstant) op).value ? 1 : 0;
            } else {
                assert op instanceof StringConstant;
                var reg = ng.gen();
                curBlock.addInst(new Lui(reg, new AddrImm(AddrImm.Part.hi, op)));
                curBlock.addInst(new Computation(reg, Computation.CompType.add, reg, new AddrImm(AddrImm.Part.lo, op)));
                return;
            }
            curBlock.addInst(new LoadImm(target, val));
        }
    }


    private void buildFunction(AsmFunction asmFunc, IRFunction irFunc) {
        ng = new NameGenerator(Cst.NAME_GENERATE_PREFIX);
        curBlockMapping = new BlockMapping(asmFunc, irFunc);
        curFunc = asmFunc;
        var entry = curBlockMapping.getBlock(irFunc.entryBlock);
        // load arguments
        entry.name = irFunc.name;
        for (int i = 0; i < Integer.min(8, asmFunc.parameterCount); ++i) {
            entry.addInst(new Move(getRegister(irFunc.parameters.get(i)), PhysicalRegister.get(10 + i)));
        }
        for (int i = 8; i < asmFunc.parameterCount; ++i) {
            var reg = getRegister(irFunc.parameters.get(i));
            curFunc.addVarOnStack(reg);
            entry.addInst(new LoadMem(reg, resolveWidth(irFunc.parameters.get(i)),
                    PhysicalRegister.get("sp"), new VirtualImm(4 * (i - 8))));
        }
        // callee save
        RVInfo.getCalleeSave().forEach(reg -> {
            entry.addInst(new Move(getRegister(Cst.RESERVE_PREFIX + reg.tell()), reg));
        });
        entry.addInst(new Move(getRegister(Cst.RESERVE_PREFIX + PhysicalRegister.get("ra")), PhysicalRegister.get("ra")));
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
            Compare irCmp = (!irBlock.insts.isEmpty() && irBlock.insts.getLast() instanceof Compare)
                    ? ((Compare) irBlock.insts.getLast()) : null;
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
                copyToReg(PhysicalRegister.get("a0"), val);
            }
            // restoration
            RVInfo.getCalleeSave().forEach(reg -> {
                curBlock.addInst(new Move(reg, getRegister(Cst.RESERVE_PREFIX + reg.tell())));
            });
            curBlock.addInst(new Move(PhysicalRegister.get("ra"), getRegister(Cst.RESERVE_PREFIX + PhysicalRegister.get("ra"))));
            curBlock.addInst(new Return());
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
        if (!(inst.dest instanceof GlobalVar) && !(inst.src instanceof StringConstant)) {
            copyToReg(getRegister(inst.dest), inst.src);
            return;
        }
        RVRegister srcReg;
        if (inst.src instanceof StringConstant) {
            // corner condition for immutable string
            srcReg = ng.gen();
            curBlock.addInst(new Lui(srcReg, new AddrImm(AddrImm.Part.hi, inst.src)));
            curBlock.addInst(new Computation(srcReg, Computation.CompType.add, srcReg,
                    new AddrImm(AddrImm.Part.lo, inst.src)));
        } else {
            srcReg = getRegister(inst.src);
        }
        if (inst.dest instanceof GlobalVar) {
            var addrReg = ng.gen();
            curBlock.addInst(new Lui(addrReg, new AddrImm(AddrImm.Part.hi, inst.dest)));
            curBlock.addInst(new StoreMem(resolveWidth(inst.dest), addrReg,
                    srcReg, new AddrImm(AddrImm.Part.lo, inst.dest)));
        } else
            curBlock.addInst(new Move(getRegister(inst.dest), srcReg));
    }

    private void buildBinary(Binary inst) {
        // should be optimized
        assert !(inst.operand1 instanceof IntConstant && inst.operand2 instanceof IntConstant);
        var ct = Computation.getCompType(inst.inst);
        var rd = getRegister(inst.dest);
        RVRegister rs1, rs2 = null;
        Integer imm = null;
        if (inst.operand1 instanceof IntConstant) {
            switch (ct) {
                case div, rem, sll, sra, sub -> {
                    rs1 = getRegister(inst.operand1);
                    rs2 = getRegister(inst.operand2);
                }
                default -> {
                    // commutative
                    rs1 = getRegister(inst.operand2);
                    imm = ((IntConstant) inst.operand1).value;
                }
            }
        } else if (inst.operand1 instanceof BoolConstant) {
            rs1 = getRegister(inst.operand2);
            imm = ((BoolConstant) inst.operand1).value ? 1 : 0;
        } else {
            assert inst.operand1 instanceof Register;
            rs1 = getRegister(inst.operand1);
            if (inst.operand2 instanceof Register)
                rs2 = getRegister(inst.operand2);
            else imm = ((IntConstant) inst.operand2).value;
        }
        if (imm != null && RVInfo.hasHigh(imm)) {
            // check if exceed 12-bits
            rs2 = ng.gen();
            curBlock.addInst(new LoadImm(rs2, imm));
            imm = null;
        }
        // rs1 now guaranteed
        if (imm == null)
            curBlock.addInst(new Computation(rd, ct, rs1, rs2));
        else {
            switch (ct) {
                case add, slt, sll, sra, xor, or, and -> curBlock.addInst(new Computation(rd, ct, rs1, new Imm(imm)));
                case sub -> curBlock.addInst(new Computation(rd, Computation.CompType.add, rs1, new Imm(-imm)));
                default -> {
                    var loadToReg = new LoadImm(ng.gen(), imm);
                    curBlock.addInst(loadToReg);
                    curBlock.addInst(new Computation(rd, ct, rs1, loadToReg.rd));
                }
            }
        }
    }

    private void buildBitCast(BitCast inst) {
        curBlock.addInst(new Computation(getRegister(inst.dest), Computation.CompType.add, getRegister(inst.from), new Imm(0)));
    }

    private void buildCall(Call inst) {
        for (int i = 0; i < Integer.min(8, inst.args.size()); ++i) {
            var operand = inst.args.get(i);
            if (operand instanceof StringConstant || operand instanceof GlobalVar) {
                var dest = PhysicalRegister.get("a" + i);
                curBlock.addInst(new Lui(dest, new AddrImm(AddrImm.Part.hi, operand)));
                curBlock.addInst(new Computation(dest, Computation.CompType.add,
                        dest, new AddrImm(AddrImm.Part.lo, operand)));
            } else
                curBlock.addInst(new Move(PhysicalRegister.get("a" + i), getRegister(operand)));
        }
        if (inst.args.size() > 8) {
            // todo special judge to fill the 16-align hole
            // store to the sp
            int curOff = 0;
            curBlock.addInst(new Computation(PhysicalRegister.get("sp"), Computation.CompType.add,
                    PhysicalRegister.get("sp"), new Imm(-4 * (inst.args.size() - 8))));
            for (int i = 8; i < inst.args.size(); ++i) {
                curBlock.addInst(new StoreMem(resolveWidth(inst.args.get(i)), PhysicalRegister.get("sp"),
                        getRegister(inst.args.get(i)), new Imm(curOff)));
                curOff += 4;
            }
            curBlock.addInst(new RVCall(rvInfo.getFunc(inst.function)));
            if (inst.containsDest()) {
                curBlock.addInst(new Move(getRegister(inst.dest), PhysicalRegister.get("s0")));
            }
        }

        curBlock.addInst(new RVCall(rvInfo.getFunc(inst.function)));

        if (inst.args.size() > 8) {
            // restore sp
            curBlock.addInst(new Computation(PhysicalRegister.get("sp"), Computation.CompType.add,
                    PhysicalRegister.get("sp"), new Imm(4 * (inst.args.size() - 8))));
        }
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

    private int twoPower(int x) {
        int r = 0;
        for (; x != 1; r++) {
            assert x % 2 == 0;
            x /= 2;
        }
        return r;
    }


    private void buildGetElementPtr(GetElementPtr inst) {
        var base = getRegister(inst.base);
        var tp = ((PointerType) inst.base.type).subType();
        var offset = inst.offset == null ? -1 : ((StructureType) tp).getMemberOffset(inst.offset.value);
        if (inst.indexing instanceof IntConstant) {
            int i = tp.size() * ((IntConstant) inst.indexing).value;
            if (inst.offset != null)
                i += offset;
            var calcAddr = new Computation(getRegister(inst.dest), Computation.CompType.add, base, new Imm(i));
            curBlock.addInst(calcAddr);
        } else {
            var calcBase = new Computation(ng.gen(), Computation.CompType.sll,
                    getRegister(inst.indexing), new Imm(twoPower(((PointerType) inst.base.type).subType().size())));
            curBlock.addInst(calcBase);
            if (inst.offset == null) {
                var addBase = new Computation(getRegister(inst.dest), Computation.CompType.add, base, calcBase.rd);
                curBlock.addInst(addBase);
            } else {
                var addBase = new Computation(ng.gen(), Computation.CompType.add, base, calcBase.rd);
                var addOffset = new Computation(getRegister(inst.dest), Computation.CompType.add, addBase.rd,
                        new Imm(offset));
                curBlock.addInst(addBase);
                curBlock.addInst(addOffset);
            }
        }
    }

    private void buildLoad(Load inst) {
        if (inst.address instanceof GlobalVar) {
            var addrReg = ng.gen();
            curBlock.addInst(new Lui(addrReg, new AddrImm(AddrImm.Part.hi, inst.address)));
            curBlock.addInst(new LoadMem(getRegister(inst.dest), resolveWidth(inst.dest),
                    addrReg, new AddrImm(AddrImm.Part.lo, inst.address)));

        } else {
            var loading = new LoadMem(getRegister(inst.dest), resolveWidth(inst.dest),
                    getRegister(inst.address), new Imm(0));
            curBlock.addInst(loading);
        }
    }

    private void buildStore(Store inst) {
        RVRegister srcReg;
        if (inst.source instanceof StringConstant) {
            srcReg = ng.gen();
            curBlock.addInst(new Lui(srcReg, new AddrImm(AddrImm.Part.hi, inst.source)));
            curBlock.addInst(new Computation(srcReg, Computation.CompType.add,
                    srcReg, new AddrImm(AddrImm.Part.lo, inst.source)));
        } else srcReg = getRegister(inst.source);
        if (inst.address instanceof GlobalVar) {
            var addrReg = ng.gen();
            curBlock.addInst(new Lui(addrReg, new AddrImm(AddrImm.Part.hi, inst.address)));
            curBlock.addInst(new StoreMem(resolveWidth(inst.source),
                    addrReg, srcReg, new AddrImm(AddrImm.Part.lo, inst.address)));
        } else {
            var storing = new StoreMem(resolveWidth(inst.source),
                    getRegister(inst.address), srcReg, new Imm(0));
            curBlock.addInst(storing);
        }
    }
}
