package codegen;

import ast.*;
import compnent.basic.ClassType;
import exception.UnimplementedError;
import ir.BasicBlock;
import ir.Function;
import ir.instruction.Binary;
import ir.instruction.Compare;
import ir.instruction.GetElementPtr;
import ir.operand.BoolConstant;
import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import ir.typesystem.BoolType;
import ir.typesystem.IntegerType;

public class IRBuilder implements ASTVisitor {
    private Function currentFunction;
    private BasicBlock currentBlock;

    @Override
    public Object visit(RootNode node) {
        node.classes.forEach(cls -> cls.accept(this));
        node.functions.forEach(func -> func.accept(this));
        // todo
        return null;
    }


    @Override
    public Register visit(BinaryExprNode node) {
        if (node.lhs.type instanceof ClassType) {
            // implicit function
            // todo
        } else {
            var binst = Binary.getIntBinOpEnum(node.lexerSign);
            if (binst != null) {
                // binary instruction
                var inst = new Binary(binst, new Register(new IntegerType()),
                        (IROperand) node.lhs.accept(this), (IROperand) node.rhs.accept(this)
                );
                currentBlock.appendInst(inst);
                return inst.dest;
            } else {
                // compare instruction
                var cinst = Compare.getCmpOpEnum(node.lexerSign);
                assert cinst != null;
                var inst = new Compare(cinst, new Register(new BoolType()),
                        (IROperand) node.lhs.accept(this), (IROperand) node.rhs.accept(this));
                currentBlock.appendInst(inst);
                return (Register) inst.dest;
            }
        }
    }

    @Override
    public Register visit(UnaryExprNode node) {
        var target = (Register) node.expr.accept(this);
        Binary inst;
        if (node.isPrefix) {
            // !/~/-/+
            switch (node.lexerSign) {
                case "+" -> {
                    // no instruction
                    return target;
                }
                case "-" -> {
                    inst = new Binary(Binary.BinInstEnum.sub, new Register(new IntegerType()), new IntConstant(0), target);
                }
                case "!" -> {
                    inst = new Binary(Binary.BinInstEnum.xor, new Register(new BoolType()), new BoolConstant(true), target);
                }
                case "~" -> {
                    inst = new Binary(Binary.BinInstEnum.xor, new Register(new IntegerType()), new IntConstant(Integer.MAX_VALUE), target);
                }
                default -> {
                    throw new UnimplementedError();
                }
            }
            currentBlock.appendInst(inst);
            return inst.dest;
        } else {
            // must be suffix ++/--
            // record original value
            var inst1 = new Binary(Binary.BinInstEnum.add, new Register(new IntegerType()), target, new IntConstant(0));
            // incremental operation
            var inst2 = new Binary(node.lexerSign.equals("++") ? Binary.BinInstEnum.add : Binary.BinInstEnum.sub, target, target, new IntConstant(1));
            currentBlock.appendInst(inst1);
            currentBlock.appendInst(inst2);
            return inst1.dest;
        }
    }

    @Override
    public Register visit(PrefixLeftValueNode node) {
        var target = (Register) node.expr.accept(this);
        var inst = new Binary(node.sign.equals("+") ? Binary.BinInstEnum.add : Binary.BinInstEnum.sub, target, target, new IntConstant(1));
        currentBlock.appendInst(inst);
        return inst.dest;
    }

    @Override
    public Register visit(FuncCallNode node) {
        if (node.func.isGlobal()) {

        } else {
            // method
        }
    }

    @Override
    public Register visit(MemberNode node) {
        // todo
        /*
        1) get pointer
        2) load
        3) return? # left value?
         */
    }

    @Override
    public Register visit(NewExprNode node) {
        // todo
        /*
        allocate memory on heap
        return pointer to it
         */
    }

    @Override
    public Register visit(SubscriptionNode node) {
        // perform pointer addition
        var inst = new GetElementPtr(new Register(new IntegerType()),
                (Register) node.lhs.accept(this), (IROperand) node.rhs.accept(this));
        currentBlock.appendInst(inst);
        return inst.dest;
    }

    @Override
    public Object visit(IdentifierNode node) {

    }
}







