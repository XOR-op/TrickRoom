package ir.construct;

import ast.ASTVisitor;
import ast.scope.Scope;
import ast.struct.*;
import ast.type.ClassType;
import ast.type.FunctionType;
import ast.type.TypeConst;
import ast.scope.FileScope;
import ir.IRBlock;
import misc.Cst;
import ir.IRFunction;
import ir.IRInfo;
import ir.instruction.*;
import ir.operand.*;
import ir.typesystem.*;
import misc.UnimplementedError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    private class IRScope {
        private Stack<HashSet<Register>> currentAvailable = new Stack<>();

        public IRScope() {
            pushScope();
        }

        public void pushScope() {
            currentAvailable.push(new HashSet<>());
        }

        public void popScope() {
            assert !currentAvailable.isEmpty();
            currentAvailable.pop();
        }

        public void addDecl(Register reg) {
            currentAvailable.peek().add(reg);
        }

        public boolean needHint() {
            return currentAvailable.stream().mapToInt(HashSet::size).sum() != 0;
        }

        public VaArg generateHint() {
            // collect local root node for gc
            assert info.getFunction(Cst.GC_HINT) != null;
            var hint = new VaArg(null, info.getFunction(Cst.GC_HINT));
            var set = new HashSet<Register>();
            currentAvailable.forEach(set::addAll);
            hint.push(new IntConstant(set.size()));
            set.forEach(hint::push);
            return hint;
        }

        public Call Unhint() {
            assert info.getFunction(Cst.GC_UNHINT) != null;
            var f = new Call(null, info.getFunction(Cst.GC_UNHINT));
            f.push(new IntConstant(currentAvailable.stream().mapToInt(HashSet::size).sum()));
            return f;
        }
    }

    private ClassType curClass;
    private IRFunction curFunc = null;
    private IRBlock curBlock = null;
    private IRScope curScope = null;
    private final Stack<IRBlock> UpdBlockStack = new Stack<>();
    private final Stack<IRBlock> AfterLoopStack = new Stack<>();
    private final IRInfo info;
    private final RootNode root;
    private int blockSuffix = 0;
    private int loopSuffix = 0;
    private int heapSize;

    public IRBuilder(RootNode rootNode, int heapSize) {
        root = rootNode;
        this.heapSize = heapSize;
        info = new IRInfo((FileScope) rootNode.scope);
    }

    public IRInfo constructIR() {
        visit(root);
        return info;
    }

    @Override
    public Object visit(RootNode node) {
        // global init
        curFunc = new IRFunction(Cst.INIT, Cst.int32);
        info.setInit(curFunc);
        curBlock = curFunc.entryBlock;
        curBlock.appendInst(new Call(null, info.getFunction(Cst.GC_INIT)).push(new IntConstant(heapSize)));
        var staticHint=new Call(null,info.getFunction(Cst.GC_STATIC_HINT));
        staticHint.push(new IntConstant(info.globalVars.size()));
        curBlock.appendInst(staticHint);
        info.globalVars.forEach((k,v)->staticHint.push(v));
        node.globalVars.forEach(decl -> {
            if (decl.expr != null) {
                var varReg = new GlobalVar(new PointerType(info.resolveType(decl.sym.getType())), decl.sym.nameAsReg);
                var expr = (IROperand) decl.expr.accept(this);
                var store = new Store(expr, varReg);
                curBlock.appendInst(store);
            }
        });
        var callMain = new Call(new Register(Cst.int32), info.getFunction("main"));
        curBlock.appendInst(callMain);
        curBlock.appendInst(new Call(null,info.getFunction(Cst.GC_RECLAIM)));
        curBlock.setRetTerminator(callMain.dest);
        curFunc.exitBlock = curBlock;
        curFunc.addInvoked(callMain.function);
        curFunc = null;
        node.classes.forEach(cls -> cls.accept(this));
        node.functions.forEach(func -> func.accept(this));
        return null;
    }

    private IRBlock prevAndLogicalSecond;
    private Register prevAndCondVar;

    @Override
    public IROperand visit(BinaryExprNode node) {
        if (node.lhs.type.equals(TypeConst.String) || node.rhs.type.equals(TypeConst.String)) {
            // implicit function
            IRFunction f;
            switch (node.lexerSign) {
                case Cst.EQUAL -> f = info.getStringMethod("eq");
                case Cst.NOT_EQ -> f = info.getStringMethod("ne");
                case Cst.LESS -> f = info.getStringMethod("lt");
                case Cst.GREATER -> f = info.getStringMethod("gt");
                case Cst.LESS_EQ -> f = info.getStringMethod("le");
                case Cst.GREATER_EQ -> f = info.getStringMethod("ge");
                case Cst.ADD -> f = info.getStringMethod("concat");
                default -> throw new IllegalStateException("Unexpected value: " + node.lexerSign);
            }
            var inst = new Call(new Register(node.lexerSign.equals(Cst.ADD) ? Cst.str : Cst.bool), f);
            inst.push((IROperand) node.lhs.accept(this)).push((IROperand) node.rhs.accept(this));
            curBlock.appendInst(inst);
            return inst.dest;
        } else if (node.lhs.type.isArray() || node.rhs.type.isArray()) {
            // array pointer cmp
            IROperand operand = (IROperand) (node.rhs instanceof LiteralNode ? node.lhs.accept(this) : node.rhs.accept(this));
            var cmp = new Compare(Compare.getCmpOpEnum(node.lexerSign), new Register(Cst.bool), operand, new NullptrConstant((PointerType) operand.type));
            curBlock.appendInst(cmp);
            return cmp.dest;
        } else {
            var binst = Binary.getIntBinOpEnum(node.lexerSign);
            if (binst != null) {
                // short circuit
                switch (binst) {
                    case logic_and -> {
                        IRBlock second = new IRBlock("and_second" + blockSuffix);
                        curFunc.addBlock(second);
                        if (node.lhs instanceof BinaryExprNode && ((BinaryExprNode) node.lhs).lexerSign.equals(Cst.AND_LOGIC)) {
                            blockSuffix++;
                            node.lhs.accept(this);
                            assert prevAndLogicalSecond != null && prevAndCondVar != null;
                            assert prevAndLogicalSecond.nexts.contains(curBlock);
                            var allAfter = curBlock;
                            prevAndLogicalSecond.terminatorInst = null;
                            prevAndLogicalSecond.setBranchTerminator(prevAndCondVar, second, allAfter);

                            curBlock = second;
                            regAssign(prevAndCondVar, (IROperand) node.rhs.accept(this));
                            curFunc.defineVar(prevAndCondVar, curBlock);
                            curBlock.setJumpTerminator(allAfter);

                            curBlock = allAfter;
                            prevAndLogicalSecond = second;
                            return prevAndCondVar;
                        } else {
                            IRBlock after = new IRBlock("and_after" + blockSuffix);
                            curFunc.addBlock(after);
                            var condReg = new Register(Cst.bool, Cst.SHORT_CIRCUIT_COND + "and" + blockSuffix);
                            curFunc.declareVar(condReg);
                            curFunc.entryBlock.insertInstFromHead(new Assign(condReg, new UndefConstant(condReg.type)));
                            blockSuffix++;

                            regAssign(condReg, (IROperand) node.lhs.accept(this));
                            curFunc.defineVar(condReg, curBlock);
                            curBlock.setBranchTerminator(condReg, second, after);

                            curBlock = second;
                            regAssign(condReg, (IROperand) node.rhs.accept(this));
                            curFunc.defineVar(condReg, curBlock);
                            curBlock.setJumpTerminator(after);

                            prevAndLogicalSecond = curBlock;
                            curBlock = after;
                            prevAndCondVar = condReg;
                            return condReg;
                        }
                    }
                    case logic_or -> {
                        IRBlock second = new IRBlock("or_second" + blockSuffix);
                        curFunc.addBlock(second);
                        IRBlock after = new IRBlock("or_after" + blockSuffix);
                        curFunc.addBlock(after);
                        var condReg = new Register(Cst.bool, Cst.SHORT_CIRCUIT_COND + "or" + blockSuffix);
                        curFunc.declareVar(condReg);
                        curFunc.entryBlock.insertInstFromHead(new Assign(condReg, new UndefConstant(condReg.type)));
                        blockSuffix++;

                        regAssign(condReg, (IROperand) node.lhs.accept(this));
                        curFunc.defineVar(condReg, curBlock);
                        curBlock.setBranchTerminator(condReg, after, second);

                        curBlock = second;
                        regAssign(condReg, (IROperand) node.rhs.accept(this));
                        curFunc.defineVar(condReg, curBlock);
                        curBlock.setJumpTerminator(after);

                        curBlock = after;
                        return condReg;
                    }
                    default -> {
                        // binary instruction
                        var inst = new Binary(binst, new Register(info.resolveType(node.type)),
                                (IROperand) node.lhs.accept(this), (IROperand) node.rhs.accept(this)
                        );
                        curBlock.appendInst(inst);
                        return inst.dest;
                    }
                }

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
    public IROperand visit(UnaryExprNode node) {
        Binary inst;
        if (node.isPrefix) {
            // !/~/-/+
            var target = (IROperand) node.expr.accept(this);
            switch (node.lexerSign) {
                case Cst.ADD -> {
                    return target;
                }
                case Cst.MINUS -> inst = new Binary(Binary.BinInstEnum.sub, new Register(Cst.int32), new IntConstant(0), target);
                case Cst.NOT_LOGIC -> inst = new Binary(Binary.BinInstEnum.xor, new Register(Cst.bool), new BoolConstant(true), target);
                case Cst.NOT_ARI -> inst = new Binary(Binary.BinInstEnum.xor, new Register(Cst.int32), new IntConstant(-1), target);
                default -> throw new IllegalStateException(node.lexerSign);
            }
            curBlock.appendInst(inst);
            return inst.dest;
        } else {
            // must be suffix ++/--
            if (directlyAccessible(node.expr)) {
                var target = (Register) node.expr.accept(this);
                var move = new Assign(new Register(Cst.int32), target);
                var incremental = new Binary(node.lexerSign.equals("++") ? Binary.BinInstEnum.add : Binary.BinInstEnum.sub,
                        target, target, new IntConstant(1));
                curFunc.defineVar(target, curBlock);
                curBlock.appendInst(move).appendInst(incremental);
                return move.dest;
            } else
                return unarySelfOp(node.expr, node.lexerSign, false);
        }
    }

    private Register unarySelfOp(ExprNode expr, String lexerSign, boolean isPrefix) {
        var ptr = locate(expr);
        var load = new Load(new Register(Cst.int32), ptr);
        var incre = new Binary(lexerSign.equals(Cst.SELF_INCRE) ? Binary.BinInstEnum.add : Binary.BinInstEnum.sub,
                new Register(Cst.int32), load.dest, new IntConstant(1));
        var store = new Store(incre.dest, ptr);
        curBlock.appendInst(load).appendInst(incre).appendInst(store);
        return isPrefix ? incre.dest : load.dest;
    }

    @Override
    public Register visit(PrefixLeftValueNode node) {
        if (directlyAccessible(node.expr)) {
            var target = (Register) node.expr.accept(this);
            var inst = new Binary(node.sign.equals(Cst.SELF_INCRE) ? Binary.BinInstEnum.add : Binary.BinInstEnum.sub,
                    target, target, new IntConstant(1));
            curFunc.defineVar(target, curBlock);
            curBlock.appendInst(inst);
            return inst.dest;
        } else
            return unarySelfOp(node.expr, node.sign, true);
    }

    private Register withType(IRType ty) {
        return ty.matches(Cst.void_t) ? null : new Register(ty);
    }

    @Override
    public Register visit(FuncCallNode node) {
        Call call;
        if (node.func == FunctionType.arraySize) {
            // inline array.size()
            assert node.callee instanceof MemberNode;
            var ptr = (Register) ((MemberNode) node.callee).object.accept(this);
            if (!((PointerType) ptr.type).subType().matches(Cst.int32)) {
                var cast = new BitCast(new Register(new PointerType(Cst.int32)), ptr);
                curBlock.appendInst(cast);
                ptr = cast.dest;
            }
            var indexing = new GetElementPtr(new Register(ptr.type), ptr, new IntConstant(-1));
            var load = new Load(new Register(Cst.int32), indexing.dest);
            var masking = new Binary(Binary.BinInstEnum.and, new Register(Cst.int32), load.dest, new IntConstant(0x3fffffff));
            curBlock.appendInst(indexing).appendInst(load).appendInst(masking);
            return masking.dest;
        } else {
            if (node.func.isGlobal()) {
                call = new Call(withType(info.resolveType(node.func.returnType)), info.getFunction(node.func.id));
                node.arguments.forEach(arg -> call.push((IROperand) arg.accept(this)));
            } else {
                if (node.callee instanceof IdentifierNode) {
                    // implicit this
                    IRFunction f = info.getClassMethod(curClass.id, ((IdentifierNode) node.callee).id);
                    call = new Call(withType(f.retTy), f);
                    call.push(visit(new ThisNode()));
                    node.arguments.forEach(arg -> call.push((IROperand) arg.accept(this)));
                } else {
                    // method where callee is MemberNode
                    assert node.callee instanceof MemberNode;
                    ExprNode object = ((MemberNode) node.callee).object;
                    IRFunction f = object.type.equals(TypeConst.String) ?
                            info.getStringMethod(((MemberNode) node.callee).member) :
                            info.getClassMethod(object.type.id, node.func.id);
                    call = new Call(withType(f.retTy), f);
                    call.push((IROperand) object.accept(this));
                    node.arguments.forEach(arg -> call.push((IROperand) arg.accept(this)));
                }
            }
            curFunc.addInvoked(call.function);
            curBlock.appendInst(call);
            return call.dest;
        }
    }

    @Override
    public Register visit(MemberNode node) {
        // will called only when accessing member variables
        var indexing = new GetElementPtr(new Register(new PointerType(info.resolveType(node.type))), (Register) node.object.accept(this), new IntConstant(0),
                ((info.resolveClass((ClassType) node.object.type))).getMemberIndex(node.member)
        );
        var loading = new Load(new Register(info.resolveType(node.type)), indexing.dest);
        curBlock.appendInst(indexing).appendInst(loading);
        return loading.dest;
    }

    @Override
    public Register visit(SubscriptionNode node) {
        // perform pointer addition and load
        var inst = new GetElementPtr(new Register(new PointerType(info.resolveType(node.type))),
                (Register) node.lhs.accept(this), (IROperand) node.rhs.accept(this));
        var loading = new Load(new Register(info.resolveType(node.type)), inst.dest);
        curBlock.appendInst(inst).appendInst(loading);
        return loading.dest;
    }

    private Register arrayInitialize(int level, ArrayList<IROperand> dims, IRType elementType) {
        // n-dim nested loop
        // if using stack, it may be more efficient by eliminate repeated calculation of size
        var alloc = new Call(new Register(new PointerType(Cst.byte_t)), info.getFunction(Cst.GC_ARRAY_MALLOC));
        alloc.push(dims.get(level)).push(new IntConstant(elementType.size()));

        IRDestedInst returnCast = new BitCast(new Register(new PointerType(elementType)), alloc.dest);

        curBlock.appendInst(alloc).appendInst(returnCast);

        if (level < dims.size() - 1 || elementType instanceof PointerType) {
            var loopedAddr = new Register(new PointerType(elementType), Cst.LOOP_INCRE_NAME + loopSuffix++);
            IRDestedInst copyAddr = new Assign(loopedAddr, returnCast.dest);
            curFunc.declareVar(loopedAddr);
            curFunc.defineVar(loopedAddr, curBlock);
            curBlock.appendInst(copyAddr);
            // initialize subarray
            var cond = new IRBlock("imp_cond" + blockSuffix);
            var loopBody = new IRBlock("imp_body" + blockSuffix);
            var afterLoop = new IRBlock("imp_after" + blockSuffix);
            curFunc.addBlock(cond).addBlock(loopBody).addBlock(afterLoop);
            blockSuffix++;
            IRDestedInst endAddr = new GetElementPtr(new Register(new PointerType(elementType)),
                    returnCast.dest, dims.get(level));
            IRDestedInst cmpAddr = new Compare(Compare.CmpEnum.ne, new Register(Cst.bool), loopedAddr, endAddr.dest);
            IRDestedInst incrPtr = new GetElementPtr(loopedAddr, loopedAddr, new IntConstant(1));

            curBlock.appendInst(endAddr);
            curBlock.setJumpTerminator(cond);
            cond.appendInst(cmpAddr).setBranchTerminator(cmpAddr.dest, loopBody, afterLoop);
            curBlock = loopBody;

            IROperand initDone = (level < dims.size() - 1) ?
                    arrayInitialize(level + 1, dims, ((PointerType) elementType).subType())
                    : new NullptrConstant((PointerType) elementType);

            curBlock.appendInst(new Store(initDone, loopedAddr));
            curBlock.appendInst(incrPtr);
            curFunc.defineVar(loopedAddr, curBlock);
            curBlock.setJumpTerminator(cond);
            curBlock = afterLoop;
        } // we assume no initialization is needed for the most internal elements except pointer
        return returnCast.dest;
    }

    @Override
    public Register visit(NewExprNode node) {
        // return pointer to newed object
        // allocate heap with i8* returned
        if (node.isClass) {
            var alloc = new Call(new Register(new PointerType(Cst.byte_t)), info.getFunction(Cst.GC_STRUCT_MALLOC));
            // struct object
            var structType = info.resolveClass((ClassType) node.classNew.type);
            alloc.push(new IntConstant(structType.size())).push(new IntConstant(structType.getPointerSize()));

            // the in fact pointer type
            var classType = info.resolveType(node.classNew.type);
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
                    (IROperand) ((SubscriptionNode) node).rhs.accept(this));
            curBlock.appendInst(inst);
            return inst.dest;
        } else {
            assert node instanceof MemberNode;
            var mNode = (MemberNode) node;
            var indexing = new GetElementPtr(new Register(new PointerType(inferType)), (IROperand) mNode.object.accept(this),
                    new IntConstant(0), info.resolveClass((ClassType) (mNode.object.type)).getMemberIndex(mNode.member));
            curBlock.appendInst(indexing);
            return indexing.dest;
        }
    }

    private void regAssign(Register lhs, IROperand rhs) {
        curFunc.defineVar(lhs, curBlock);
        if (rhs instanceof Register && ((Register) rhs).isAnonymous()) {
            ((Register) rhs).replaceWith(lhs.getName());
        } else {
            var inst = new Assign(lhs, rhs);
            curBlock.appendInst(inst);
        }
    }


    @Override
    public Object visit(AssignmentNode node) {
        IROperand rhs = (IROperand) node.rhs.accept(this);
        if (directlyAccessible(node.lhs)) {
            Register lhs = (Register) node.lhs.accept(this);
            regAssign(lhs, rhs);
        } else {
            Register ptr = locate(node.lhs);
            var inst = new Store(rhs, ptr);
            curBlock.appendInst(inst);
        }
        return null;
    }

    @Override
    public Object visit(FunctionNode node) {
        Register.reset(1);
        blockSuffix = 1;
        loopSuffix = 1;
        curFunc = curClass == null ? info.getFunction(node.funcId) : info.getClassMethod(curClass.id, node.funcId);
        assert curFunc != null;
        curBlock = curFunc.entryBlock;
        curScope = new IRScope();

        node.suiteNode.accept(this);

        if (curFunc.retTy.matches(Cst.void_t)) {
            if (!curBlock.hasTerminal()) {
                // implicit return
                curFunc.returnBlocks.add(curBlock);
                curBlock.setVoidRetTerminator();
            }
            if (curFunc.returnBlocks.size() == 1) {
                curFunc.exitBlock = curFunc.returnBlocks.get(0);
            } else {
                var exit = new IRBlock("exit");
                exit.setVoidRetTerminator();
                curFunc.returnBlocks.forEach(b -> {
                    b.terminatorInst = null;
                    b.setJumpTerminator(exit);
                });
                curFunc.addBlock(exit);
                curFunc.exitBlock = exit;
            }
        } else {
            if (curFunc.returnBlocks.isEmpty()) {
                if (node.funcId.equals("main")) {
                    curBlock.setRetTerminator(new IntConstant(0));
                } else {
                    // for constructor
                    // need further checking
                    curBlock.setRetTerminator(new Register(new PointerType(curFunc.retTy), "this"));
                }
                curFunc.exitBlock = curBlock;
            } else {
                if (!curBlock.hasTerminal()) {
                    // ugly workaround for those like after-block after a while-true loop
                    if (curFunc.retTy.matches(Cst.int32))
                        curBlock.setRetTerminator(new IntConstant(0));
                    else if (curFunc.retTy.matches(Cst.bool))
                        curBlock.setRetTerminator(new BoolConstant(false));
                    else if (curFunc.retTy.matches(Cst.str)) {
                        var p = info.registerStrLiteral("");
                        curBlock.setRetTerminator(p);
                    } else {
                        assert curFunc.retTy instanceof PointerType;
                        curBlock.setRetTerminator(new NullptrConstant((PointerType) curFunc.retTy));
                    }
                }
                if (curFunc.returnBlocks.size() == 1) {
                    curFunc.exitBlock = curFunc.returnBlocks.get(0);
                } else {
                    var exit = new IRBlock("exit");
                    var returnValue = new Register(curFunc.retTy, Cst.RETURN_VAL);
                    curFunc.entryBlock.insertInstFromHead(new Assign(returnValue, new UndefConstant(returnValue.type)));
                    curFunc.declareVar(returnValue);
                    exit.setRetTerminator(returnValue);
                    curFunc.returnBlocks.forEach(b -> {
                        IROperand ope = ((Ret) b.terminatorInst).value;
                        b.terminatorInst = null;
                        b.appendInst(new Assign(returnValue, ope));
                        curFunc.defineVar(returnValue, b);
                        b.setJumpTerminator(exit);
                    });
                    curFunc.addBlock(exit);
                    curFunc.exitBlock = exit;
                }
            }
        }
        for (boolean removeFlag = true; removeFlag; ) {
            removeFlag = false;
            var iter = curFunc.blocks.iterator();
            while (iter.hasNext()) {
                var bb = iter.next();
                if (bb.prevs.isEmpty() && bb != curFunc.entryBlock) {
                    removeFlag = true;
                    bb.nexts.forEach(n -> n.prevs.remove(bb));
                    iter.remove();
                }
            }
        }

        curFunc = null;
        curScope = null;
        return null;
    }

    @Override
    public Object visit(DeclarationNode node) {
        var varReg = new Register(info.resolveType(node.sym.getType()), node.sym.nameAsReg);
        curFunc.declareVar(varReg);
        curFunc.entryBlock.insertInstFromHead(new Assign(varReg, new UndefConstant(varReg.type)));
        if (node.expr != null) {
            var expr = (IROperand) node.expr.accept(this);
            regAssign(varReg, expr);
        } else if (varReg.type instanceof PointerType) {
            // initialize with null
            regAssign(varReg, new NullptrConstant((PointerType) varReg.type));
        }
        if (varReg.type instanceof PointerType)
            curScope.addDecl(varReg);
        return null;
    }


    @Override
    public Object visit(ClassNode node) {
        curClass = node.cls;
        node.methodNode.forEach(m -> m.accept(this));
        node.constructorNode.forEach(c -> c.accept(this));
        curClass = null;
        return null;
    }


    @Override
    public Object visit(LoopNode node) {
        curScope.pushScope();
        if (node.initExpr != null) node.initExpr.accept(this);
        else if (node.initDecl != null) node.initDecl.accept(this);
        boolean hasCond = node.condExpr != null;
        boolean hasUpdate = node.updateExpr != null;
        // blocks
        var beforeLoop = curBlock;
        var conditionBlock = new IRBlock("cond" + blockSuffix);
        var loopBody = new IRBlock("body" + blockSuffix);
        var updateBlock = new IRBlock("upd" + blockSuffix);
        var afterLoop = new IRBlock("after" + blockSuffix);
        blockSuffix++;
        AfterLoopStack.push(afterLoop);
        UpdBlockStack.push(updateBlock);
        beforeLoop.setJumpTerminator(conditionBlock);
        curFunc.addBlock(conditionBlock).addBlock(loopBody).addBlock(updateBlock).addBlock(afterLoop);
        // condition block
        curBlock = conditionBlock;
        if (hasCond) {
            IROperand cond = (IROperand) node.condExpr.accept(this);
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
        curScope.popScope();
        return null;
    }

    @Override
    public Object visit(ConditionalNode node) {
        // process branch and generate blocks
        IROperand cond = (IROperand) node.condExpr.accept(this);
        var beforeBranch = curBlock;
        var trueBlock = new IRBlock("true" + blockSuffix);
        var afterBranch = new IRBlock("after" + blockSuffix);
        boolean hasFalse = node.falseStat != null;
        var falseBlock = hasFalse ? new IRBlock("false" + blockSuffix) : null;
        blockSuffix++;
        beforeBranch.setBranchTerminator(cond, trueBlock, hasFalse ? falseBlock : afterBranch);
        curFunc.addBlock(trueBlock);
        if (hasFalse) curFunc.addBlock(falseBlock);
        curFunc.addBlock(afterBranch);
        // visit instructions
        curBlock = trueBlock;
        curScope.pushScope();
        node.trueStat.accept(this);
        curScope.popScope();
        if (!curBlock.hasTerminal())
            curBlock.setJumpTerminator(afterBranch);
        if (hasFalse) {
            curBlock = falseBlock;
            curScope.pushScope();
            node.falseStat.accept(this);
            curScope.popScope();
            if (!curBlock.hasTerminal())
                curBlock.setJumpTerminator(afterBranch);
        }
        curBlock = afterBranch;
        return null;
    }

    @Override
    public Object visit(ReturnNode node) {
        if (node.returnExpr == null)
            curBlock.setVoidRetTerminator();
        else {
            // due to side effect of accept, two statements cannot be merged
            var val = (IROperand) node.returnExpr.accept(this);
            curBlock.setRetTerminator(val);
        }
        curFunc.addReturn(curBlock);
        return null;
    }

    @Override
    public Object visit(ExprStmtNode node) {
        if (node.expr != null)
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
        for (var sub : node.statements) {
            if (sub instanceof SuiteNode) {
                curScope.pushScope();
                sub.accept(this);
                curScope.popScope();
            } else
                sub.accept(this);
        }
        return null;
    }

    @Override
    public IRConstant visit(LiteralNode node) {
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
            return info.registerStrLiteral(node.content);
        } else {
            return new NullptrConstant((PointerType) info.resolveType(node.type));
        }
    }

    @Override
    public Register visit(ArrayLiteralNode node) {
        throw new IllegalStateException();
    }
}







