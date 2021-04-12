package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.construct.RegisterTracker;
import ir.instruction.*;
import ir.operand.*;
import misc.DividedByZero;
import misc.pass.IRFunctionPass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class SCCP extends IRFunctionPass {
    private static class LatticeStat {
        public enum Stat {
            unsure, constant, multiple
        }

        public IRConstant constant = null;
        public Stat stat;

        public LatticeStat(Stat stat) {
            this.stat = stat;
        }

        public LatticeStat(IRConstant cst) {
            this.stat = Stat.constant;
            this.constant = cst;
        }

        public LatticeStat operate(LatticeStat rhs) {
            if (stat == Stat.multiple) return this;
            else if (rhs.stat == Stat.multiple) return rhs;
            return rhs.stat == Stat.unsure ? this : (stat == Stat.unsure ? rhs :
                    (constant.sameConst(rhs.constant) ? this : new LatticeStat(Stat.multiple)));
        }

        public LatticeStat operate(IRConstant rhs) {
            if (stat == Stat.multiple) return this;
            return stat == Stat.unsure ? new LatticeStat(rhs) : (rhs.sameConst(constant) ? this : new LatticeStat(Stat.multiple));
        }
    }

    private final HashSet<IRBlock> reachableBlock = new HashSet<>();
    private final RegisterTracker tracker;
    private final HashMap<String, LatticeStat> lattice = new HashMap<>();
    private final LinkedList<String> registerUpdateQueue = new LinkedList<>();
    private final LinkedList<IRBlock> blockUpdateQueue = new LinkedList<>();
    private final LinkedList<Phi> phiUpdateQueue = new LinkedList<>();
    private final HashMap<IRBlock, HashSet<Phi>> nextPhi = new HashMap<>();

    private void setLattice(Register reg, LatticeStat.Stat stat) {
        lattice.get(reg.identifier()).stat = stat;
    }

    private LatticeStat getLattice(Register reg) {
        return lattice.get(reg.identifier());
    }

    public SCCP(IRFunction f) {
        super(f);
        tracker = new RegisterTracker(f);
        tracker.run();
        irFunc.blocks.forEach(b -> nextPhi.put(b, new HashSet<>()));
        irFunc.blocks.forEach(b -> b.phiCollection.forEach(phi -> {
            phi.arguments.forEach(s -> {
                nextPhi.get(s.block).add(phi);
            });
        }));
    }

    @Override
    protected void run() {
        irFunc.parameters.forEach(r -> lattice.put(r.identifier(), new LatticeStat(LatticeStat.Stat.multiple)));
        tracker.defs.forEach((r, v) -> {
            lattice.put(r, new LatticeStat(LatticeStat.Stat.unsure));
        });
        reachableBlock.add(irFunc.entryBlock);
        blockUpdateQueue.add(irFunc.entryBlock);
        while (!(registerUpdateQueue.isEmpty() && blockUpdateQueue.isEmpty() && phiUpdateQueue.isEmpty())) {
            if (!registerUpdateQueue.isEmpty()) {
                var ele = registerUpdateQueue.pop();
                tracker.uses.get(ele).forEach(inst -> {
                    if (reachableBlock.contains(tracker.queryInstBelongedBlock(inst))) {
                        if (inst instanceof Binary) visitBinary((Binary) inst);
                        else if (inst instanceof Assign) visitAssign((Assign) inst);
                        else if (inst instanceof Compare) visitCompare((Compare) inst);
                        else if (inst instanceof Phi) visitPhi((Phi) inst);
                        else if (inst instanceof Branch) visitBranch((Branch) inst);
                        else if (inst instanceof BitCast) visitBitCast((BitCast) inst);
                        else visitOthers(inst);
                    } else if (inst instanceof Phi) {
                        partiallyVisitPhi((Phi) inst);
                    }
                });
            }
            if (!blockUpdateQueue.isEmpty()) {
                var ele = blockUpdateQueue.pop();
                visitBlock(ele);
            }
            if (!phiUpdateQueue.isEmpty()) {
                var phi = phiUpdateQueue.pop();
                visitPhi(phi);
            }
        }
        postProcessing();
    }

    private void visitBlock(IRBlock block) {
        // one block will be evaluated once
        block.phiCollection.forEach(this::visitPhi);
        block.insts.forEach(inst -> {
            if (inst instanceof Binary) visitBinary((Binary) inst);
            else if (inst instanceof Assign) visitAssign((Assign) inst);
            else if (inst instanceof Compare) visitCompare((Compare) inst);
            else if (inst instanceof BitCast) visitBitCast((BitCast) inst);
            else visitOthers(inst);
        });
        if (block.terminatorInst instanceof Branch) {
            Branch br = (Branch) block.terminatorInst;
            visitBranch(br);
        } else if (block.terminatorInst instanceof Jump) {
            var dest = ((Jump) block.terminatorInst).getTarget();
            if (!reachableBlock.contains(dest)) {
                reachableBlock.add(dest);
                blockUpdateQueue.add(dest);
            }
        }
        phiUpdateQueue.addAll(nextPhi.get(block));
    }

    private IRConstant getConst(IROperand operand) {
        return operand instanceof IRConstant ? (IRConstant) operand :
                (getLattice((Register) operand).stat == LatticeStat.Stat.constant ? getLattice((Register) operand).constant : null);
    }

    private boolean ifMultiple(IROperand operand) {
        return operand instanceof Register && !(operand instanceof GlobalVar) && getLattice((Register) operand).stat == LatticeStat.Stat.multiple;
    }

    private void visitBranch(Branch inst) {
        if (ifMultiple(inst.condition)) {
            if (!reachableBlock.contains(inst.trueBranch)) {
                reachableBlock.add(inst.trueBranch);
                blockUpdateQueue.add(inst.trueBranch);
            }
            if (!reachableBlock.contains(inst.falseBranch)) {
                reachableBlock.add(inst.falseBranch);
                blockUpdateQueue.add(inst.falseBranch);
            }
        } else {
            BoolConstant v = (BoolConstant) getConst(inst.condition);
            if (v != null) {
                if (v.value) {
                    if (!reachableBlock.contains(inst.trueBranch)) {
                        reachableBlock.add(inst.trueBranch);
                        blockUpdateQueue.add(inst.trueBranch);
                    }
                } else {
                    if (!reachableBlock.contains(inst.falseBranch)) {
                        reachableBlock.add(inst.falseBranch);
                        blockUpdateQueue.add(inst.falseBranch);
                    }
                }
            }
        }
    }

    private void visitBinary(Binary inst) {
        if (ifMultiple(inst.operand1) || ifMultiple(inst.operand2)) {
            if (getLattice(inst.dest).stat != LatticeStat.Stat.multiple) {
                setLattice(inst.dest, LatticeStat.Stat.multiple);
                registerUpdateQueue.add(inst.dest.identifier());
            }
        } else {
            IRConstant v1 = getConst(inst.operand1), v2 = getConst(inst.operand2);
            var lattice = getLattice(inst.dest);
            if (v1 != null && v2 != null && lattice.stat == LatticeStat.Stat.unsure) {
                try {
                    IRConstant result = Binary.evaluateConstant(inst.op, v1, v2);
                    lattice.stat = LatticeStat.Stat.constant;
                    lattice.constant = result;
                } catch (DividedByZero dividedByZero) {
                    lattice.stat = LatticeStat.Stat.multiple;
                }
                registerUpdateQueue.add(inst.dest.identifier());
            }
        }
    }

    private void visitCompare(Compare inst) {
        if (ifMultiple(inst.operand1) || ifMultiple(inst.operand2)) {
            if (getLattice(inst.dest).stat != LatticeStat.Stat.multiple) {
                setLattice(inst.dest, LatticeStat.Stat.multiple);
                registerUpdateQueue.add(inst.dest.identifier());
            }
        } else {
            IRConstant v1 = getConst(inst.operand1), v2 = getConst(inst.operand2);
            var lattice = getLattice(inst.dest);
            if (v1 != null && v2 != null && lattice.stat == LatticeStat.Stat.unsure) {
                IRConstant result = Compare.evaluateConstant(inst.type, v1, v2);
                lattice.stat = LatticeStat.Stat.constant;
                lattice.constant = result;
                registerUpdateQueue.add(inst.dest.identifier());
            }
        }
    }

    private void visitAssign(Assign inst) {
        visitAssignAndBitCast(inst.dest, inst.src);
    }

    private void visitBitCast(BitCast inst) {
        visitAssignAndBitCast(inst.dest, inst.from);
    }

    private void visitAssignAndBitCast(Register dest, IROperand src) {
        if (src instanceof UndefConstant) {
            lattice.put(dest.identifier(), new LatticeStat(LatticeStat.Stat.multiple));
            registerUpdateQueue.add(dest.identifier());
            return;
        }
        if (ifMultiple(src)) {
            if (getLattice(dest).stat != LatticeStat.Stat.multiple) {
                setLattice(dest, LatticeStat.Stat.multiple);
                registerUpdateQueue.add(dest.identifier());
            }
        } else {
            IRConstant v = getConst(src);
            var lattice = getLattice(dest);
            if (v != null && lattice.stat == LatticeStat.Stat.unsure) {
                lattice.stat = LatticeStat.Stat.constant;
                lattice.constant = v;
                registerUpdateQueue.add(dest.identifier());
            }
        }
    }

    private LatticeStat iteratePhi(Phi phi) {
        LatticeStat init = new LatticeStat(LatticeStat.Stat.unsure);
        for (var sou : phi.arguments) {
            if (reachableBlock.contains(sou.block)) {
                if (sou.val instanceof IRConstant) init = init.operate((IRConstant) sou.val);
                else init = init.operate(getLattice((Register) sou.val));
            }
        }
        return init;
    }

    private void visitPhi(Phi phi) {
        var conjunction = iteratePhi(phi);
        var latt = getLattice(phi.dest);
        if (conjunction.stat == LatticeStat.Stat.multiple) {
            if (latt.stat != conjunction.stat) {
                latt.stat = LatticeStat.Stat.multiple;
                registerUpdateQueue.add(phi.dest.identifier());
            }
        } else partiallyVisitPhi(phi, conjunction);
    }

    private void partiallyVisitPhi(Phi phi, LatticeStat conjunction) {
        if (conjunction.stat == LatticeStat.Stat.constant) {
            var latt = getLattice(phi.dest);
            if (latt.stat == LatticeStat.Stat.unsure) {
                latt.stat = LatticeStat.Stat.constant;
                latt.constant = conjunction.constant;
                registerUpdateQueue.add(phi.dest.identifier());
            }
        }
    }

    private void partiallyVisitPhi(Phi phi) {
        partiallyVisitPhi(phi, iteratePhi(phi));
    }

    private void visitOthers(IRInst i) {
        if (!i.containsDest()) return;
        var inst = (IRDestedInst) i;
        if (getLattice(inst.dest).stat == LatticeStat.Stat.unsure && (inst instanceof Load || inst instanceof Call || inst instanceof GetElementPtr)) {
            lattice.put(inst.dest.identifier(), new LatticeStat(LatticeStat.Stat.multiple));
            registerUpdateQueue.add(inst.dest.identifier());
        }
    }

    private void postProcessing() {
        assert reachableBlock.contains(irFunc.exitBlock);
        lattice.forEach((reg, l) -> {
            if (l.stat == LatticeStat.Stat.constant) {
                tracker.queryRegisterUses(reg).forEach(inst -> {
                    inst.replaceRegisterWithOperand(l.constant, tracker.querySingleDef(reg).dest);
                });
                var def = tracker.queryRegisterDefs(reg).iterator().next();
                if (!def.hasSideEffect())
                    tracker.queryInstBelongedBlock(def).insts.remove(def);
            }
        });
        // remove unreachable block
        for (var iter = irFunc.blocks.iterator(); iter.hasNext(); ) {
            var block = iter.next();
            if (!reachableBlock.contains(block)) {
                iter.remove();
                block.prevs.forEach(b -> {
                    if (b.terminatorInst instanceof Branch) {
                        var tmp = (Branch) b.terminatorInst;
                        b.nexts.remove(block);
                        b.terminatorInst = null;
                        b.setJumpTerminator(block == tmp.trueBranch ? tmp.falseBranch : tmp.trueBranch);
                    }
                });
                block.nexts.forEach(block::removeFromNext);
            }
        }
    }
}
