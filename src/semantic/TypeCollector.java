package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;
import exception.UnimplementedError;
import exception.semantic.*;

public class TypeCollector implements ASTVisitor<Void> {
    private Scope currentScope;

    private boolean checkIn(ASTNode node) {
        if (node.scope != null) {
            currentScope = node.scope;
            return true;
        } else return false;
    }

    private void checkOut(boolean will) {
        if (will) {
            assert currentScope != null;
            currentScope = currentScope.getUpstream();
        }
    }

    private void check(Type a, Type b, ASTNode node) {
        // check for binary expression
        if (!a.equals(b) || a.equals(TypeConst.Void))
            throw new TypeMismatch(a, b, node);
    }

    private void checkWithNull(Type a, Type b, ASTNode node) {
        if (TypeConst.Null.equals(b)) {
            if ((a.equals(TypeConst.Void) || a.equals(TypeConst.Int) || a.equals(TypeConst.Bool)) && !a.isArray())
                throw new TypeMismatch(a, b, node);
        } else if (TypeConst.Null.equals(a)) {
            if ((b.equals(TypeConst.Void) || b.equals(TypeConst.Int) || b.equals(TypeConst.Bool)) && !a.isArray())
                throw new TypeMismatch(a, b, node);
        } else check(a, b, node);
    }

    @Override
    public Void visit(RootNode node) {
        boolean will = checkIn(node);
        FunctionType main;
        if (!((main = currentScope.getFunction("main", node)) != null
                && main.returnType.equals(TypeConst.Int) && main.parameters.isEmpty()))
            throw new MissingSyntax(node, "main function");
        for (var sub : node.nodeList)
            sub.accept(this);
        checkOut(will);
        return null;
    }

    @Override
    public Void visit(ClassNode node) {
        boolean will = checkIn(node);
        node.members.forEach(this::visit);
        node.constructor.forEach(this::visit);
        node.methods.forEach(this::visit);
        checkOut(will);
        return null;
    }

    @Override
    public Void visit(FunctionNode node) {
        boolean will = checkIn(node);
        visit(node.suite);
        checkOut(will);
        return null;
    }

    @Override
    public Void visit(DeclarationNode node) {
        boolean will = checkIn(node);
        if (node.expr != null)
            checkWithNull(node.type, inferType(node.expr), node);
        checkOut(will);
        return null;
    }

    @Override
    public Void visit(ConditionalNode node) {
        Type con;
        if (!(con = inferType(node.condExpr)).equals(TypeConst.Bool))
            throw new TypeMismatch(con, TypeConst.Bool, node);
        node.trueStat.accept(this);
        if (node.falseStat != null) {
            node.falseStat.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(LoopNode node) {
        boolean will = checkIn(node);
        Type con;
        if (node.initDecl != null) node.initDecl.accept(this);
        if (node.initExpr != null) inferType(node.initExpr);
        if (node.condExpr != null && !(con = inferType(node.condExpr)).equals(TypeConst.Bool))
            throw new TypeMismatch(con, TypeConst.Bool, node);
        if (node.updateExpr != null) inferType(node.updateExpr);
        node.loopBody.accept(this);
        checkOut(will);
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        if (node.returnExpr == null) {
            if (!TypeConst.Void.equals(node.correspondingFunction.returnType))
                throw new TypeMismatch(TypeConst.Void, node.correspondingFunction.returnType, node);
            else return null;
        }
        checkWithNull(inferType(node.returnExpr), node.correspondingFunction.returnType, node);
        return null;
    }

    @Override
    public Void visit(ExprStmtNode node) {
        boolean will = checkIn(node);
        if (node.expr != null)
            inferType(node.expr);
        checkOut(will);
        return null;
    }

    @Override
    public Void visit(SuiteNode node) {
        boolean will = checkIn(node);
        node.statements.forEach(sub -> sub.accept(this));
        checkOut(will);
        return null;
    }

    @Override
    public Void visit(BreakNode node) {
        // do nothing
        return null;
    }

    @Override
    public Void visit(ContinueNode node) {
        // do nothing
        return null;
    }

    @Override
    public Void visit(DeclarationBlockNode node) {
        boolean will = checkIn(node);
        node.decls.forEach(sub -> sub.accept(this));
        checkOut(will);
        return null;
    }

    private Type inferType(ExprNode node) {
        if (node instanceof AssignmentNode) return calcType((AssignmentNode) node);
        if (node instanceof MemberNode) return calcType((MemberNode) node);
        if (node instanceof FuncCallNode) return calcType((FuncCallNode) node);
        if (node instanceof LiteralNode) return calcType((LiteralNode) node);
        if (node instanceof IdentifierNode) return calcType((IdentifierNode) node);
        if (node instanceof ThisNode) return calcType((ThisNode) node);
        if (node instanceof NewExprNode) return calcType((NewExprNode) node);
        if (node instanceof BinaryExprNode) return calcType((BinaryExprNode) node);
        if (node instanceof UnaryExprNode) return calcType((UnaryExprNode) node);
        // if(node instanceof ArrayLiteralNode)
        if (node instanceof SubscriptionNode) return calcType((SubscriptionNode) node);
        if (node instanceof PrefixLeftValueNode) return calcType((PrefixLeftValueNode) node);
        assert node instanceof ArrayLiteralNode;
        return calcType((ArrayLiteralNode) node);
    }


    private boolean isLeftValue(ASTNode node) {
        return node instanceof IdentifierNode || node instanceof MemberNode || node instanceof SubscriptionNode || node instanceof PrefixLeftValueNode;
    }

    private Type calcType(PrefixLeftValueNode node) {
        if (!isLeftValue(node.expr))
            throw new LeftValueRequired(node.expr);
        Type tp = inferType(node.expr);
        if (!tp.equals(TypeConst.Int))
            throw new TypeMismatch(tp, TypeConst.Int, node);
        node.type = tp;
        return tp;
    }

    private Type calcType(SubscriptionNode node) {
        Type indexType = inferType(node.rhs);
        if (!indexType.equals(TypeConst.Int)) throw new TypeMismatch(indexType, TypeConst.Int, node);
        Type arrayType = inferType(node.lhs);
        if (!arrayType.isArray())
            throw new NotSubscriptable(node);
        // return element type
        node.type = ((ArrayType) arrayType).subType();
        return node.type;
    }

    private Type calcType(AssignmentNode node) {
        // check left value
        if (isLeftValue(node.lhs)) {
            Type l = inferType(node.lhs), r = inferType(node.rhs);
            checkWithNull(l, r, node);
            node.type = l;
            return l;
        } else throw new LeftValueRequired(node);
    }

    private ClassType getObjOfMemberNode(MemberNode node) {
        Type type = inferType(node.object);
        if (!(type instanceof ClassType))
            throw new MemberMisuse(node);
        return (ClassType) type;
    }

    private Type calcType(MemberNode node) {
        // will only accessed for member variable
        // member method will be handled in calcType(FuncCallNode)
        ClassType ct = getObjOfMemberNode(node);
        Symbol sym;
        if ((sym = ct.memberVars.get(node.member)) == null)
            throw new MissingSyntax(node, node.member);
        node.type = sym.getType();
        return node.type;
    }

    private int checkParameters(FunctionType func, FuncCallNode node) {
        if (func.parameters.size() != node.arguments.size()) return 1;
        for (int i = 0; i < func.parameters.size(); ++i) {
            try {
                checkWithNull(func.parameters.get(i).getType(), inferType(node.arguments.get(i)), node);
            } catch (TypeMismatch e) {
                return 2;
            }
        }
        return 0;
    }

    private Type calcType(FuncCallNode node) {
        FunctionType func = null;
        if (node.isConstructor) {
            assert node.callee instanceof IdentifierNode;
            ClassType ct = currentScope.getClass(((IdentifierNode) node.callee).id, node);
            for (var con : ct.constructor) {
                if (checkParameters(con, node) == 0) {
                    func = con;
                    break;
                }
            }
            if (func == null) throw new NoMatchedFunction(node);
        } else {
            if (node.callee instanceof IdentifierNode) {
                // global function
                func = currentScope.getFunction(((IdentifierNode) node.callee).id, node);
            } else {
                // method
                assert node.callee instanceof MemberNode;
                // special judge for array.size()
                Type type = inferType(((MemberNode) node.callee).object);
                if (type instanceof ClassType) {
                    // find if there exists such method
                    ClassType ct = getObjOfMemberNode((MemberNode) node.callee);
                    if ((func = ct.memberFuncs.get(((MemberNode) node.callee).member)) == null)
                        throw new MissingSyntax(node, ((MemberNode) node.callee).member);
                } else if (type.isArray()) {
                    if (!((MemberNode) node.callee).member.equals("size"))
                        throw new MissingSyntax(node, ((MemberNode) node.callee).member);
                    else func = FunctionType.arraySize;
                } else
                    throw new MemberMisuse(node);
            }
            // check types of arguments
            switch (checkParameters(func, node)) {
                case 1:
                    throw new WrongParameterSize(node);
                case 2:
                    throw new NoMatchedFunction(node);
                default:
            }
        }
        node.type = func.returnType;
        node.correspondingFunc = func;
        return node.type;
    }

    private Type calcType(LiteralNode node) {
        return node.type;
    }

    private Type calcType(IdentifierNode node) {
        return node.type;
    }

    private Type calcType(ThisNode node) {
        return node.type;
    }

    private Type calcType(NewExprNode node) {
        node.type = node.isConstruct ? calcType(node.classNew) : calcType(node.arrNew);
        return node.type;
    }

    private Type calcType(BinaryExprNode node) {
        assert node.lhs != null && node.rhs != null;
        Type lhs = inferType(node.lhs), rhs = inferType(node.rhs);
        if (node.lexerSign.equals("==") || node.lexerSign.equals("!=")) {
            checkWithNull(lhs, rhs, node);
        } else {
            check(lhs, rhs, node);
            if (lhs instanceof ClassType && !lhs.equals(TypeConst.String))
                throw new UnsupportedBehavior("binary operation on Class is undefined", node);
            if (lhs instanceof FunctionType)
                throw new TypeMismatch(lhs, rhs, node);
        }
        // lhs to avoid null
        node.type = lhs;
        switch (node.lexerSign) {
            case "-":
            case "*":
            case "/":
            case "%":
            case ">>":
            case "<<":
            case "&":
            case "|":
                if (!node.type.equals(TypeConst.Int))
                    throw new TypeMismatch(lhs, rhs, node);
                break;
            case "&&":
            case "||":
                if (!node.type.equals(TypeConst.Bool))
                    throw new TypeMismatch(lhs, rhs, node);
                break;
            case "^":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.Bool)))
                    throw new TypeMismatch(lhs, rhs, node);
                break;
            case ">":
            case "<":
            case ">=":
            case "<=":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.String)))
                    throw new TypeMismatch(lhs, rhs, node);
                node.type = TypeConst.Bool;
                break;
            case "+":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.String)))
                    throw new TypeMismatch(lhs, rhs, node);
                break;
            case "==":
            case "!=":
                node.type = TypeConst.Bool;
                break;
            default:
                throw new UnimplementedError(node.lexerSign);
        }
        return node.type;
    }

    private Type calcType(UnaryExprNode node) {
        node.type = inferType(node.expr);
        switch (node.lexerSign) {
            case "-":
            case "+":
            case "~":
                if (!node.type.equals(TypeConst.Int))
                    throw new TypeMismatch(node.type, TypeConst.Int, node);
                break;
            case "!":
                if (!node.type.equals(TypeConst.Bool))
                    throw new TypeMismatch(node.type, TypeConst.Bool, node);
                break;
            case "++":
            case "--":
                // suffix ++/--
                if (!node.type.equals(TypeConst.Int))
                    throw new TypeMismatch(node.type, TypeConst.Int, node);
                if (!isLeftValue(node.expr))
                    throw new LeftValueRequired(node);
                break;
        }
        return node.type;
    }

    private Type calcType(ArrayLiteralNode node) {
        Type tp;
        for (var sub : node.dimArr) {
            tp = inferType(sub);
            if (!tp.equals(TypeConst.Int))
                throw new TypeMismatch(tp, TypeConst.Int, sub);
        }
        return node.type;
    }
}
