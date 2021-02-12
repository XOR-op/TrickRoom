package ir.construct;

import ast.ASTVisitor;
import ast.struct.*;
import ast.type.ClassType;
import ast.type.ArrayObjectType;
import ast.type.TypeConst;
import ast.scope.FileScope;
import ir.BasicBlock;
import ir.Cst;
import ir.Function;
import ir.IRInfo;
import ir.instruction.*;
import ir.operand.*;
import ir.typesystem.*;

import java.util.ArrayList;
import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    private ClassType curClass;
    private Function curFunc = null;
    private BasicBlock curBlock = null;
    private final Stack<BasicBlock> UpdBlockStack = new Stack<>();
    private final Stack<BasicBlock> AfterLoopStack = new Stack<>();
    private final IRInfo info;
    private final RootNode root;
    private int blockSuffix = 0;


    public IRBuilder(RootNode rootNode) {
        root = rootNode;
        info = new IRInfo((FileScope) rootNode.scope);
    }

    public IRInfo constructIR() {
        visit(root);
        return info;
    }

    @Override
    public Object visit(RootNode node) {
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
            var inst = new Call(new Register(Cst.bool), f);
            inst.push((IROperand) node.lhs.accept(this)).push((IROperand) node.rhs.accept(this));
            curBlock.appendInst(inst);
            return inst.dest;
        } else if (node.lhs.type.isArray() || node.rhs.type.isArray()) {
            // array pointer cmp
            assert (node.lhs.type.isArray() && node.rhs.type.equals(TypeConst.Null)) ||
                    (node.rhs.type.isArray() && node.lhs.type.equals(TypeConst.Null));
            IROperand operand = (IROperand) (node.lhs.type.isArray() ? node.lhs.accept(this) : node.rhs.accept(this));
            var cast = new BitCast(new Register(PointerType.nullptr()), operand);
            var cmp = new Compare(Compare.getCmpOpEnum(node.lexerSign), cast.dest, new NullptrConstant(), new Register(Cst.bool));
            curBlock.appendInst(cast);
            curBlock.appendInst(cmp);
            return cmp.dest;
        } else {
            var binst = Binary.getIntBinOpEnum(node.lexerSign);
            if (binst != null) {
                // binary instruction
                var inst = new Binary(binst, new Register(Cst.int32),
                        (IROperand) node.lhs.accept(this), (IROperand) node.rhs.accept(this)
                );
                curBlock.appendInst(inst);
                return inst.dest;
            } else {
                // compare instruction
                var cinst = Compare.getCmpOpEnum(node.lexerSign);
                assert cinst != null;
                var inst = new Compare(cinst, new Register(Cst.bool),
                        (IROperand) node.lhs.accept(this), (IROperand) node.rhs.accept(this));
                curBlock.appendInst(inst);
                return inst.dest;
            }
        }
    }

    @Override
    public Register visit(UnaryExprNode node) {
        Binary inst;
        if (node.isPrefix) {
            // !/~/-/+
            var target = (Register) node.expr.accept(this);
            switch (node.lexerSign) {
                case "+" -> {
                    return target;
                }
                case "-" -> inst = new Binary(Binary.BinInstEnum.sub, new Register(Cst.int32), new IntConstant(0), target);
                case "!" -> inst = new Binary(Binary.BinInstEnum.xor, new Register(Cst.bool), new BoolConstant(true), target);
                case "~" -> inst = new Binary(Binary.BinInstEnum.xor, new Register(Cst.int32), new IntConstant(Integer.MAX_VALUE), target);
                default -> throw new IllegalStateException(node.lexerSign);
            }
            curBlock.appendInst(inst);
            return inst.dest;
        } else {
            // must be suffix ++/--
            if (directlyAccessible(node.expr)) {
                var target = (Register) node.expr.accept(this);
                var move = new Assign(new Register(Cst.int32), target);
                var incremental = new Binary(node.lexerSign.equals("++") ? Binary.BinInstEnum.add : Binary.BinInstEnum.sub, target, target, new IntConstant(1));
                curFunc.defineVar(target, curBlock);
                curBlock.appendInst(move).appendInst(incremental);
                return move.dest;
            } else {
                var ptr = locate(node.expr);
                var load = new Load(new Register(Cst.int32), ptr);
                var incre = new Binary(Binary.BinInstEnum.add, new Register(Cst.int32), load.dest, new IntConstant(1));
                var store = new Store(incre.dest, ptr);
                curBlock.appendInst(load).appendInst(incre).appendInst(store);
                return load.dest;
            }
        }
    }

    @Override
    public Register visit(PrefixLeftValueNode node) {
        if (directlyAccessible(node.expr)) {
            var target = (Register) node.expr.accept(this);
            var inst = new Binary(node.sign.equals("++") ? Binary.BinInstEnum.add : Binary.BinInstEnum.sub, target, target, new IntConstant(1));
            curFunc.defineVar(target, curBlock);
            curBlock.appendInst(inst);
            return inst.dest;
        } else {
            var ptr = locate(node.expr);
            var load = new Load(new Register(Cst.int32), ptr);
            var incre = new Binary(Binary.BinInstEnum.add, new Register(Cst.int32), load.dest, new IntConstant(1));
            var store = new Store(incre.dest, ptr);
            curBlock.appendInst(load).appendInst(incre).appendInst(store);
            return incre.dest;
        }
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
                call = new Call(new Register(Cst.int32), info.getArraySize());
                call.push((IROperand) node.callee.accept(this));
            } else {
                ExprNode object = ((MemberNode) node.callee).object;
                Function f = object.type.equals(TypeConst.String) ?
                        info.getStringMethod(((MemberNode) node.callee).member) :
                        info.getClassMethod(object.type.id, node.func.id);
                call = new Call(new Register(f.retTy), f);
                call.push((IROperand) object.accept(this));
                node.arguments.forEach(arg -> call.push((IROperand) arg.accept(this)));
            }
        }
        curBlock.appendInst(call);
        return call.dest;
    }

    @Override
    public Register visit(MemberNode node) {
        // will called only when accessing member variables
        var indexing = new GetElementPtr(new Register(info.resolveType(node.type)), (Register) node.object.accept(this), new IntConstant(0),
                ((StructureType) (info.resolveType(node.object.type))).getMemberIndex(node.member)
        );
        var loading = new Load(new Register(info.resolveType(node.type)), indexing.dest);
        curBlock.appendInst(indexing).appendInst(loading);
        return loading.dest;
    }

    @Override
    public Register visit(SubscriptionNode node) {
        // perform pointer addition and load
        var inst = new GetElementPtr(new Register(new PointerType(info.resolveType(node.type))),
                (Register) node.lhs.accept(this), (IROperand) node.rhs.accept(this), new IntConstant(0));
        var loading = new Load(new Register(info.resolveType(node.type)), inst.dest);
        curBlock.appendInst(inst).appendInst(loading);
        return loading.dest;
    }

    private Register arrayInitialize(int level, ArrayList<IROperand> dims, IRType elementType) {
        // n-dim nested loop
        // if using stack, it may be more efficient by eliminate repeated calculation of size
        var alloc = new Call(new Register(new PointerType(Cst.byte_t)), info.getFunction(Cst.MALLOC));
        IRDestedInst arrayWidth = new Binary(Binary.BinInstEnum.mul, new Register(Cst.int32),
                dims.get(level), new IntConstant(elementType.size()));
        IRDestedInst calcSize = new Binary(Binary.BinInstEnum.add, new Register(Cst.int32),
                new IntConstant(Cst.int32.size()), arrayWidth.dest);
        alloc.push(calcSize.dest);
        // calculate real array address
        IRDestedInst trueArrayAddress = new GetElementPtr(new Register(new PointerType(Cst.byte_t)), alloc.dest,
                new IntConstant(0), new IntConstant(4));
        IRDestedInst returnCast = new BitCast(new Register(new PointerType(elementType)), trueArrayAddress.dest);
        // store size
        IRDestedInst cast = new BitCast(new Register(new PointerType(Cst.int32)), alloc.dest);
        IRInst storeSize = new Store(dims.get(level), cast.dest);
        curBlock.appendInst(alloc).appendInst(arrayWidth).appendInst(calcSize);
        curBlock.appendInst(cast).appendInst(storeSize);
        curBlock.appendInst(trueArrayAddress).appendInst(returnCast);
        if (level < dims.size() - 1) {
            // initialize subarray
            var beforeLoop = curBlock;
            var cond = new BasicBlock("cond");
            var loopBody = new BasicBlock("body");
            var afterLoop = new BasicBlock("after");
            IRDestedInst incrVar = new Assign(new Register(new PointerType(elementType)), returnCast.dest);
            IRDestedInst endAddr = new GetElementPtr(new Register(new PointerType(elementType)),
                    returnCast.dest, dims.get(level), new IntConstant(0));
            IRDestedInst cmpAddr = new Compare(Compare.CmpEnum.ne, new Register(Cst.bool), incrVar.dest, endAddr.dest);
            beforeLoop.appendInst(incrVar).appendInst(endAddr);
            beforeLoop.setJumpTerminator(cond);
            cond.appendInst(cmpAddr).setBranchTerminator(cmpAddr.dest, loopBody, afterLoop);
            curBlock = loopBody;
            arrayInitialize(level + 1, dims, ((PointerType) elementType).subType());
            curBlock.appendInst(new GetElementPtr(incrVar.dest, incrVar.dest, new IntConstant(1), new IntConstant(0)));
            curBlock.setJumpTerminator(cond);
            curBlock = afterLoop;
        } // we assume no initialization is needed for the most internal elements
        return returnCast.dest;
    }

    @Override
    public Register visit(NewExprNode node) {
        //  return pointer to newed object
        // allocate heap with i8* returned
        if (node.isClass) {
            var alloc = new Call(new Register(new PointerType(Cst.byte_t)), info.getFunction(Cst.MALLOC));
            var classType = info.resolveType(node.classNew.type);
            alloc.push(new IntConstant(info.resolveClass((ClassType) node.classNew.type).size()));
            var cast = new BitCast(new Register(classType), alloc.dest);
            var constructCall = new Call(new Register(classType),
                    info.getClassMethod(node.classNew.type.id, node.classNew.func.id));
            constructCall.push(cast.dest);
            node.classNew.arguments.forEach(
                    exprNode -> constructCall.push((IROperand) exprNode.accept(this))
            );
            curBlock.appendInst(alloc).appendInst(cast).appendInst(constructCall);
            return cast.dest;
        } else {
            // array [i32, ty]* with ty* returned
            var elementType = info.resolveType(node.arrNew.type.subType());
            var arrDimension = new ArrayList<IROperand>();
            node.arrNew.dimArr.forEach(d -> {
                if (d != null)
                    arrDimension.add((IROperand) d.accept(this));
            });
            return arrayInitialize(0, arrDimension, elementType);
        }
    }

    private GetElementPtr calculateImplicitThis(IdentifierNode node) {
        return new GetElementPtr(new Register(new PointerType(info.resolveType(node.type))), visit(new ThisNode()),
                new IntConstant(0), info.resolveClass(curClass).getMemberIndex(node.id));
    }

    @Override
    public Register visit(IdentifierNode node) {
        // only standalone identifier, member identifier will not be called
        // left-value implicit this identifier will be processed in visit(AssignmentNode)
        var inferType = info.resolveType(node.type);
        if (node.sym.implicitThis()) {
            var getPtr = calculateImplicitThis(node);
            var load = new Load(new Register(inferType), getPtr.dest);
            curBlock.appendInst(getPtr);
            curBlock.appendInst(load);
            return load.dest;
        } else if (node.sym.isGlobal()) {
            var load = new Load(new Register(inferType), new GlobalVar(new PointerType(inferType), node.sym.nameAsReg));
            curBlock.appendInst(load);
            return load.dest;
        } else {
            return new Register(inferType, node.sym.nameAsReg);
        }
    }

    private boolean directlyAccessible(ExprNode node) {
        return node instanceof IdentifierNode &&
                !((IdentifierNode) node).sym.isGlobal() &&
                !((IdentifierNode) node).sym.implicitThis();
    }

    private Register locate(ExprNode node) {
        // always return pointer to a left value
        var inferType = info.resolveType(node.type);
        if (node instanceof IdentifierNode) {
            if (((IdentifierNode) node).sym.implicitThis()) {
                var getPtr = calculateImplicitThis((IdentifierNode) node);
                curBlock.appendInst(getPtr);
                return getPtr.dest;
            } else {
                assert ((IdentifierNode) node).sym.isGlobal();
                return new GlobalVar(new PointerType(inferType), ((IdentifierNode) node).sym.nameAsReg);
            }
        } else if (node instanceof SubscriptionNode) {
            var inst = new GetElementPtr(new Register(new PointerType(inferType)),
                    (Register) ((SubscriptionNode) node).lhs.accept(this),
                    (IROperand) ((SubscriptionNode) node).rhs.accept(this), new IntConstant(0));
            curBlock.appendInst(inst);
            return inst.dest;
        } else {
            assert node instanceof MemberNode;
            var indexing = new GetElementPtr(new Register(inferType), (IROperand) ((MemberNode) node).object.accept(this),
                    new IntConstant(0), ((StructureType) (info.resolveType(((MemberNode) node).object.type)))
                    .getMemberIndex(((MemberNode) node).member));
            curBlock.appendInst(indexing);
            return indexing.dest;
        }
    }


    @Override
    public Register visit(AssignmentNode node) {
        IROperand rhs = (IROperand) node.rhs.accept(this);
        if (directlyAccessible(node.lhs)) {
            Register lhs = (Register) node.lhs.accept(this);
            if (rhs instanceof Register) {
                ((Register) rhs).replaceWith(lhs.name);
                return lhs;
            } else {
                var inst = new Assign(lhs, rhs);
                curFunc.defineVar(lhs, curBlock);
                curBlock.appendInst(inst);
                return inst.dest;
            }
        } else {
            Register ptr = locate(node.lhs);
            var inst = new Store(rhs, ptr);
            curBlock.appendInst(inst);
            return null;
        }
    }

    @Override
    public Object visit(FunctionNode node) {
        Register.reset(1);
        blockSuffix = 1;
        curFunc = curClass == null ? info.getFunction(node.funcId) : info.getClassMethod(curClass.id, node.funcId);
        assert curFunc != null;
        curBlock = curFunc.entryBlock;
        node.suite.accept(this);
        if (curFunc.returnBlocks.isEmpty()) {
            curBlock.setRetTerminator(new Register(new PointerType(curFunc.retTy), "this"));
            curFunc.exitBlock = curBlock;
        } else if (curFunc.returnBlocks.size() == 1) {
            curFunc.exitBlock = curFunc.returnBlocks.get(0);
        } else {
            var exit = new BasicBlock("exit");
            var returnValue = new Register(new PointerType(curFunc.retTy), Cst.RETURN_VAL);
            curFunc.addAlloca(returnValue);
            var loading=new Load(new Register(curFunc.retTy), returnValue);
            exit.appendInst(loading).setRetTerminator(loading.dest);
            curFunc.returnBlocks.forEach(b -> {
                IROperand ope = ((Ret) b.terminatorInst).value;
                b.terminatorInst = null;
                b.appendInst(new Store(ope, returnValue));
                b.setJumpTerminator(exit);
            });
            curFunc.addBlock(exit);
            curFunc.exitBlock = exit;
        }
        new SSAConverter(curFunc).SSAConstruct();
        curFunc = null;
        return null;
    }

    @Override
    public Object visit(DeclarationNode node) {
        var varReg = new Register(info.resolveType(node.sym.getType()), node.sym.nameAsReg);
        curFunc.declareVar(varReg);
        if (node.expr != null) {
            curFunc.defineVar(varReg, curBlock);
            var expr = (IROperand) node.expr.accept(this);
            if (expr instanceof Register) {
                ((Register) expr).replaceWith(varReg.name);
                return expr;
            } else {
                var inst = new Assign(varReg, expr);
                curBlock.appendInst(inst);
                return inst.dest;
            }
        }
        return null;
    }


    @Override
    public Object visit(ClassNode node) {
        curClass = node.cls;
        node.methods.forEach(m -> m.accept(this));
        node.constructor.forEach(c -> c.accept(this));
        curClass = null;
        return null;
    }


    @Override
    public Object visit(LoopNode node) {
        /*
         * todo loop CFG can be further optimized
         */
        if (node.initExpr != null) node.initExpr.accept(this);
        else if (node.initDecl != null) node.initDecl.accept(this);
        boolean hasCond = node.condExpr != null;
        boolean hasUpdate = node.updateExpr != null;
        // blocks
        var beforeLoop = curBlock;
        var conditionBlock = new BasicBlock("cond" + blockSuffix);
        var loopBody = new BasicBlock("body" + blockSuffix);
        var updateBlock = new BasicBlock("upd" + blockSuffix);
        var afterLoop = new BasicBlock("after" + blockSuffix);
        AfterLoopStack.push(afterLoop);
        UpdBlockStack.push(updateBlock);
        beforeLoop.setJumpTerminator(conditionBlock);
        curFunc.addBlock(conditionBlock).addBlock(loopBody).addBlock(updateBlock).addBlock(afterLoop);
        // condition block
        curBlock = conditionBlock;
        if (hasCond) {
            Register cond = (Register) node.condExpr.accept(this);
            curBlock.setBranchTerminator(cond, loopBody, afterLoop);
        } else {
            curBlock.setJumpTerminator(loopBody);
        }
        // body
        curBlock = loopBody;
        node.loopBody.accept(this);
        if (!curBlock.hasTerminal())
            curBlock.setJumpTerminator(updateBlock);

        // update
        curBlock = updateBlock;
        if (hasUpdate) {
            node.updateExpr.accept(this);
        }
        curBlock.setJumpTerminator(conditionBlock);
        // done
        curBlock = afterLoop;
        UpdBlockStack.pop();
        AfterLoopStack.pop();
        blockSuffix++;
        return null;
    }

    @Override
    public Object visit(ConditionalNode node) {
        // process branch and generate blocks
        Register cond = (Register) node.condExpr.accept(this);
        var beforeBranch = curBlock;
        var trueBlock = new BasicBlock("true" + blockSuffix);
        var afterBranch = new BasicBlock("after" + blockSuffix);
        boolean hasFalse = node.falseStat != null;
        var falseBlock = hasFalse ? new BasicBlock("false" + blockSuffix) : null;
        beforeBranch.setBranchTerminator(cond, trueBlock, hasFalse ? falseBlock : afterBranch);

        curFunc.addBlock(trueBlock);
        if (hasFalse) curFunc.addBlock(falseBlock);
        curFunc.addBlock(afterBranch);
        // visit instructions
        curBlock = trueBlock;
        node.trueStat.accept(this);
        if (!curBlock.hasTerminal())
            curBlock.setJumpTerminator(afterBranch);
        if (hasFalse) {
            curBlock = falseBlock;
            node.falseStat.accept(this);
            if (!curBlock.hasTerminal())
                curBlock.setJumpTerminator(afterBranch);
        }
        curBlock = afterBranch;
        blockSuffix++;
        return null;
    }

    @Override
    public Object visit(ReturnNode node) {
        curFunc.addReturn(curBlock);
        curBlock.setRetTerminator((IROperand) node.returnExpr.accept(this));
        return null;
    }

    @Override
    public Object visit(ExprStmtNode node) {
        node.expr.accept(this);
        return null;
    }

    @Override
    public Object visit(BreakNode node) {
        curBlock.setJumpTerminator(AfterLoopStack.peek());
        return null;
    }

    @Override
    public Object visit(ContinueNode node) {
        curBlock.setJumpTerminator(UpdBlockStack.peek());
        return null;
    }

    @Override
    public Object visit(DeclarationBlockNode node) {
        node.decls.forEach(d -> d.accept(this));
        return null;
    }

    @Override
    public Register visit(ThisNode node) {
        return new Register(info.resolveType(curClass), "this");
    }

    @Override
    public Object visit(SuiteNode node) {
        node.statements.forEach(s -> s.accept(this));
        return null;
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
                default -> throw new IllegalStateException();
            }
        } else if (TypeConst.Int.equals(node.type)) {
            return new IntConstant(Integer.parseInt(node.content));
        } else if (TypeConst.String.equals(node.type)) {
            return new Register(Cst.str, info.registerStrLiteral(node.content));
        } else {
            return new NullptrConstant();
        }
    }

    @Override
    public Register visit(ArrayLiteralNode node) {
        throw new IllegalStateException();
    }
}







