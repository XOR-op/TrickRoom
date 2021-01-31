package codegen;

import ast.*;
import compnent.basic.ClassType;
import compnent.basic.ArrayObjectType;
import compnent.basic.TypeConst;
import exception.UnimplementedError;
import ir.BasicBlock;
import ir.Function;
import ir.IRInfo;
import ir.instruction.*;
import ir.operand.*;
import ir.typesystem.*;

import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    private Function currentFunction = null;
    private BasicBlock currentBlock = null;
    private Stack<BasicBlock> currentLoopUpdBlock = new Stack<>();
    private Stack<BasicBlock> currentAfterLoopBlock = new Stack<>();
    private IRInfo info;
    private int scopeLevel = 0;
    private int counter = 0;

    private String translateIdentifier(String name) {
//        return currentFunction.name+"."+scopeLevel+"."+name;
        return "var." + scopeLevel + "." + name;
    }

    private String generateBlockName() {
        return Integer.toString(counter++);
    }

    @Override
    public Object visit(RootNode node) {
        node.globalVars.forEach(var -> {
            //todo
        });
        node.classes.forEach(cls -> cls.accept(this));
        node.functions.forEach(func -> func.accept(this));
        return null;
    }


    @Override
    public Register visit(BinaryExprNode node) {
        if (node.lhs.type.equals(TypeConst.String) || node.rhs.type.equals(TypeConst.String)) {
            // implicit function
            Function f;
            switch (node.lexerSign) {
                case "==" -> f = info.getStringMethod("eq");

                case "!=" -> f = info.getStringMethod("neq");

                case "<" -> f = info.getStringMethod("lt");

                case ">" -> f = info.getStringMethod("gt");

                case "<=" -> f = info.getStringMethod("le");

                case ">=" -> f = info.getStringMethod("ge");

                default -> throw new IllegalStateException("Unexpected value: " + node.lexerSign);
            }
            var inst = new Call(new Register(TypeEnum.bool), f);
            inst.push((IROperand) node.lhs.accept(this)).push((IROperand) node.rhs.accept(this));
            currentBlock.appendInst(inst);
            return inst.dest;
        } else if (node.lhs.type.isArray() || node.rhs.type.isArray()) {
            // todo null cmp
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
                case "-" -> inst = new Binary(Binary.BinInstEnum.sub, new Register(TypeEnum.int32), new IntConstant(0), target);

                case "!" -> inst = new Binary(Binary.BinInstEnum.xor, new Register(TypeEnum.bool), new BoolConstant(true), target);

                case "~" -> inst = new Binary(Binary.BinInstEnum.xor, new Register(TypeEnum.int32), new IntConstant(Integer.MAX_VALUE), target);

                default -> throw new IllegalStateException(node.lexerSign);

            }
            currentBlock.appendInst(inst);
            return inst.dest;
        } else {
            // must be suffix ++/--
            // save original value
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
        Call call;
        if (node.func.isGlobal()) {
            call = new Call(new Register(info.resolveType(node.func.returnType)), info.getFunction(node.func.id));
            node.arguments.forEach(arg -> call.push((IROperand) arg.accept(this)));
        } else {
            // method where callee is MemberNode
            assert node.callee instanceof MemberNode;
            if (node.callee.type instanceof ArrayObjectType) {
                // todo array.size()
                call = new Call(new Register(TypeEnum.int32), info.getArraySize());
                call.push((IROperand) node.callee.accept(this));
            } else {
                Function f = node.callee.type.equals(TypeConst.String) ?
                        info.getStringMethod(((MemberNode) node.callee).member) :
                        info.getClassMethod((ClassType) ((MemberNode) node.callee).object.type, node.func);
                call = new Call(new Register(f.retTy), f);
                call.push((IROperand) node.callee.accept(this));
                node.arguments.forEach(arg -> call.push((IROperand) arg.accept(this)));
            }
        }
        currentBlock.appendInst(call);
        return call.dest;
    }

    @Override
    public Register visit(MemberNode node) {
        // return pointer of member
        // also will called only when accessing member variables
        var inst = new GetElementPtr(new Register(info.resolveType(node.type)),
                (Register) node.object.accept(this), new IntConstant(
                ((StructureType) (info.resolveType(node.object.type))).getMemberOffset(node.member)
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
        return new Register(info.resolveType(node.type), translateIdentifier(node.id));
    }

    @Override
    public IROperand visit(LiteralNode node) {
        if (TypeConst.Bool.equals(node.type)) {
            switch (node.content) {
                case "true" -> {
                    return new BoolConstant(true);
                }
                case "false" -> {
                    return new BoolConstant(false);
                }
            }
        } else if (TypeConst.Int.equals(node.type)) {
            return new IntConstant(Integer.getInteger(node.content));
        } else if (TypeConst.String.equals(node.type)) {
            return new Register(TypeEnum.str, info.registerStrLiteral(node.content));
        } else {
            return new NullptrConstant();
        }
        // todo
    }

    @Override
    public Register visit(AssignmentNode node) {
        if (node.lhs instanceof IdentifierNode) {
            // register
            var inst = new Binary(Binary.BinInstEnum.add, (Register) node.lhs.accept(this), (IROperand) node.rhs.accept(this), new IntConstant(0));
            currentBlock.appendInst(inst);
            return inst.dest;
        } else {
            // in memory
            var inst = new Store((IROperand) node.rhs.accept(this), (Register) node.lhs.accept(this));
            currentBlock.appendInst(inst);
            // there should be no successive instruction
            return null;
        }
    }

    @Override
    public Object visit(FunctionNode node) {
        Register.reset();
        counter = 1;
        currentFunction = info.getFunction(node.funcId);
        currentBlock = currentFunction.blocks.get(0);
        node.suite.accept(this);
        return null;
    }

    @Override
    public Object visit(LoopNode node) {
        if (node.initExpr != null) node.initExpr.accept(this);
        else if (node.initDecl != null) node.initDecl.accept(this);
        boolean hasCond = node.condExpr != null;
        boolean hasUpdate = node.updateExpr != null;
        // blocks
        var beforeLoop = currentBlock;
        var conditionBlock = beforeLoop.split(generateBlockName());
        var loopBody = conditionBlock.split(generateBlockName());
        var updateBlock = loopBody.split(generateBlockName());
        var afterLoop = updateBlock.split(generateBlockName());
        currentAfterLoopBlock.push(afterLoop);
        currentLoopUpdBlock.push(updateBlock);
        beforeLoop.setTerminator(new Jump(conditionBlock));
        // condition block
        currentBlock = conditionBlock;
        if (hasCond) {
            Register cond = (Register) node.condExpr.accept(this);
            currentBlock.setTerminator(new Branch(cond, loopBody, afterLoop));
        } else {
            currentBlock.setTerminator(new Jump(loopBody));
        }
        // body
        currentBlock = loopBody;
        node.loopBody.accept(this);
        if (!currentBlock.hasTerminal())
            currentBlock.setTerminator(new Jump(updateBlock));
        // update
        currentBlock = updateBlock;
        if (hasUpdate) {
            node.updateExpr.accept(this);
        }
        currentBlock.setTerminator(new Jump(conditionBlock));
        // done
        currentBlock = afterLoop;
        currentLoopUpdBlock.pop();
        currentAfterLoopBlock.pop();
        return null;
    }

    @Override
    public Object visit(ConditionalNode node) {
        // process branch and generate blocks
        Register cond = (Register) node.condExpr.accept(this);
        var beforeBranch = currentBlock;
        var trueBlock = currentBlock.split(generateBlockName());
        var afterBranch = trueBlock.split(generateBlockName());
        boolean hasFalse = node.falseStat != null;
        var falseBlock = hasFalse ? trueBlock.split(generateBlockName()) : null;
        beforeBranch.setTerminator(new Branch(cond, trueBlock, hasFalse ? falseBlock : afterBranch));
        // visit instructions
        currentBlock = trueBlock;
        node.trueStat.accept(this);
        if (!currentBlock.hasTerminal())
            currentBlock.setTerminator(new Jump(afterBranch));
        if (hasFalse) {
            currentBlock = falseBlock;
            node.falseStat.accept(this);
            if (!currentBlock.hasTerminal())
                currentBlock.setTerminator(new Jump(afterBranch));
        }
        currentBlock = afterBranch;
        return null;
    }

    @Override
    public Object visit(ExprStmtNode node) {
        node.expr.accept(this);
        return null;
    }

    @Override
    public Object visit(BreakNode node) {
        currentBlock.setTerminator(new Jump(currentAfterLoopBlock.peek()));
        return null;
    }

    @Override
    public Object visit(ContinueNode node) {
        currentBlock.setTerminator(new Jump(currentLoopUpdBlock.peek()));
        return null;
    }

    @Override
    public Object visit(DeclarationBlockNode node) {
        node.decls.forEach(d->d.accept(this));
        return null;
    }

    @Override
    public Object visit(DeclarationNode node) {
        if(node.expr!=null){
            var varReg=new Register(info.resolveType(node.sym.getType()),translateIdentifier(node.id));
            var inst = new Binary(Binary.BinInstEnum.add, varReg, (IROperand) node.expr.accept(this), new IntConstant(0));
            currentBlock.appendInst(inst);
            return inst.dest;
        }
        return null;
    }


}







