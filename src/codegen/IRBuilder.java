package codegen;

import ast.*;
import compnent.basic.ClassType;
import compnent.basic.TypeConst;
import exception.UnimplementedError;
import ir.BasicBlock;
import ir.Function;
import ir.IRInfo;
import ir.instruction.*;
import ir.operand.BoolConstant;
import ir.operand.IROperand;
import ir.operand.IntConstant;
import ir.operand.Register;
import ir.typesystem.*;

public class IRBuilder implements ASTVisitor {
    private Function currentFunction;
    private BasicBlock currentBlock;
    private IRInfo info;

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
            throw new UnimplementedError();
        } else {
            var binst = Binary.getIntBinOpEnum(node.lexerSign);
            if (binst != null) {
                // binary instruction
                var inst = new Binary(binst, new Register(TypeEnum.int32),
                        (IROperand) node.lhs.accept(this), (IROperand) node.rhs.accept(this)
                );
                currentBlock.appendInst(inst);
                return inst.dest;
            } else {
                // compare instruction
                var cinst = Compare.getCmpOpEnum(node.lexerSign);
                assert cinst != null;
                var inst = new Compare(cinst, new Register(TypeEnum.bool),
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
                    inst = new Binary(Binary.BinInstEnum.sub, new Register(TypeEnum.int32), new IntConstant(0), target);
                }
                case "!" -> {
                    inst = new Binary(Binary.BinInstEnum.xor, new Register(TypeEnum.bool), new BoolConstant(true), target);
                }
                case "~" -> {
                    inst = new Binary(Binary.BinInstEnum.xor, new Register(TypeEnum.int32), new IntConstant(Integer.MAX_VALUE), target);
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
            var inst1 = new Binary(Binary.BinInstEnum.add, new Register(TypeEnum.int32), target, new IntConstant(0));
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
            var call=new Call(new Register(info.resolveType(node.func.returnType)), info.getFunction(node.func));
            node.arguments.forEach(arg->call.push((IROperand) arg.accept(this)));
            return call.dest;
        } else {
            // method where callee is MemberNode
            assert node.callee instanceof MemberNode;

        }
        throw new UnimplementedError();
    }

    @Override
    public Register visit(MemberNode node) {
        // return pointer of member
        // also will called only when accessing member variables
        var inst=new GetElementPtr(new Register(info.resolveType(node.type)),
                (Register)node.object.accept(this),new IntConstant(
                ((StructureType)(info.resolveType(node.object.type))).getMemberOffset(node.member)
        ));
        currentBlock.appendInst(inst);
        return inst.dest;
    }

    @Override
    public Register visit(NewExprNode node) {
        // todo
        /*
        allocate memory on heap
        return pointer to it
         */
        throw new UnimplementedError();
    }

    @Override
    public Register visit(SubscriptionNode node) {
        // perform pointer addition
        var inst = new GetElementPtr(new Register(TypeEnum.int32),
                (Register) node.lhs.accept(this), (IROperand) node.rhs.accept(this));
        currentBlock.appendInst(inst);
        return inst.dest;
    }

    @Override
    public Register visit(IdentifierNode node) {
        // only standalone identifier, member identifier will not be called
        return new Register(info.resolveType(node.type),node.id);
    }

    @Override
    public IROperand visit(LiteralNode node) {
        if(TypeConst.Bool.equals(node.type)) {
            switch (node.content) {
                case "true" -> {
                    return new BoolConstant(true);
                }
                case "false" -> {
                    return new BoolConstant(false);
                }
            }
        }else if(TypeConst.Int.equals(node.type)){
            return new IntConstant(Integer.getInteger(node.content));
        }else if(TypeConst.String.equals(node.type)){
            // todo
        }else {
            // null
            // todo
        }
        throw new UnimplementedError();
    }

    @Override
    public Register visit(AssignmentNode node) {
        if(node.lhs instanceof IdentifierNode){
            // register
            var inst=new Binary(Binary.BinInstEnum.add,(Register) node.lhs.accept(this),(IROperand) node.rhs.accept(this),new IntConstant(0));
            currentBlock.appendInst(inst);
            return inst.dest;
        }else {
            // in memory
            var inst=new Store((IROperand) node.rhs.accept(this),(Register) node.lhs.accept(this));
            currentBlock.appendInst(inst);
            // there should be no successive instruction
            return null;
        }
    }


}







