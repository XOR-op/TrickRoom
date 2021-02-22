package assembly.construct;

import assembly.AsmBlock;
import assembly.AsmFunction;
import assembly.instruction.*;
import assembly.operand.Imm;
import assembly.operand.PhysicalRegister;
import assembly.operand.RVRegister;
import assembly.operand.VirtualRegister;
import ir.BasicBlock;
import ir.Cst;
import ir.Function;
import ir.IRInfo;
import ir.instruction.*;
import ir.instruction.Call;
import ir.operand.BoolConstant;
import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import ir.typesystem.PointerType;
import ir.typesystem.StructureType;

public class AsmBuilder {
    private final IRInfo ir;
    private AsmBlock curBlock;
    private AsmFunction curFunc;

    private class NameGenerator {
        private int n = 0;
        private String prefix;

        public NameGenerator(String prefix) {
            this.prefix = prefix;
        }

        public VirtualRegister gen() {
            return new VirtualRegister(prefix + (n++));
        }
    }

    private NameGenerator ng;

    public AsmBuilder(IRInfo irInfo) {
        ir = irInfo;
    }

    private void buildFunction(AsmFunction asmFunc, Function irFunc) {
        ng = new NameGenerator("asm.virtualReg");
    }

    private void buildBlock(AsmBlock asmBlock, BasicBlock irBlock) {
        irBlock.insts.forEach(irInst -> {
            if (!(irInst instanceof Compare) || irInst != irBlock.insts.getLast())
                buildInst(irInst);
        });

    }

    private void buildInst(IRInst inst) {
        if (inst instanceof Alloca) buildAlloca((Alloca) inst);
        else if (inst instanceof Assign) buildAssign((Assign) inst);
        else if (inst instanceof Binary) buildBinary((Binary) inst);
        else if (inst instanceof BitCast) buildBitCast((BitCast) inst);
        else if (inst instanceof Call) buildCall((Call) inst);
        else if (inst instanceof Compare) buildCompare((Compare) inst);
        else if (inst instanceof GetElementPtr) buildGetElementPtr((GetElementPtr) inst);
        else if (inst instanceof Load) buildLoad((Load) inst);
        else if (inst instanceof Store) buildStore((Store) inst);
        else throw new IllegalStateException();
    }

    private RVRegister getRegister(IROperand operand) {
        if (operand instanceof Register) {
            return new VirtualRegister(((Register) operand).identifier());
        } else {
            int i = (operand instanceof IntConstant) ? ((IntConstant) operand).value :
                    (((BoolConstant) operand).value ? 1 : 0);
            var inst = new LoadImm(ng.gen(), i);
            curBlock.addInst(inst);
            return inst.rd;
        }
    }

    private void buildAlloca(Alloca inst) {
        // todo
    }

    private void buildAssign(Assign inst) {
        curBlock.addInst(new Computation(getRegister(inst.dest), Computation.CompType.add, getRegister(inst.src), new Imm(0)));
    }

    private void buildBinary(Binary inst) {
        assert !(inst.operand1 instanceof IntConstant && inst.operand2 instanceof IntConstant);
        var ct = Computation.getCompType(inst.inst);
        var rd = new VirtualRegister(inst.dest);
        VirtualRegister rs1 = null, rs2 = null;
        Imm imm = null;
        if (inst.operand1 instanceof IntConstant) {
            switch (ct) {
                case div, rem, sll, sra -> {
                    rs1 = (VirtualRegister) getRegister(inst.operand1);
                    rs2 = new VirtualRegister((Register) inst.operand2);
                }
                default -> {
                    rs1 = new VirtualRegister((Register) inst.operand2);
                    imm = new Imm(((IntConstant) inst.operand1).value);
                }
            }
        } else {
            assert inst.operand1 instanceof Register;
            rs1 = new VirtualRegister((Register) inst.operand1);
            if (inst.operand2 instanceof Register)
                rs2 = new VirtualRegister((Register) inst.operand2);
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
        // todo
    }

    private void buildCompare(Compare inst) {
        var rs1 = getRegister(inst.operand1);
        var rs2 = getRegister(inst.operand2);
        // todo
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
        var loading = new LoadData(getRegister(inst.dest), inst.dest.type.equals(Cst.byte_t) ? RVInst.WidthType.b : RVInst.WidthType.w,
                getRegister(inst.address), new Imm(0));
        curBlock.addInst(loading);
    }

    private void buildStore(Store inst) {
        var storing = new StoreData(inst.source.type.equals(Cst.byte_t) ? RVInst.WidthType.b : RVInst.WidthType.w,
                getRegister(inst.address), getRegister(inst.source), new Imm(0));
        curBlock.addInst(storing);
    }
}
