package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;
import exception.MissingOverrideException;
import exception.semantic.*;
import utils.L;

public class TypeChecker implements ASTVisitor {
    private Scope currentScope;

    private boolean checkIn(ASTNode node) {
        if (node.scope != null) {
            assert node.scope.getUpstream() == currentScope;
            currentScope = node.scope;
//            L.i(currentScope.toString());
            return true;
        } else return false;
    }

    private void checkOut(boolean will) {
        if (will) {
            assert currentScope != null;
//            L.i(currentScope.toString());
            currentScope = currentScope.getUpstream();
        }
    }

    private void check(Type a, Type b, ASTNode node) {
        // check for binary expression
        if (!a.equals(b) || a.equals(TypeConst.Void))
            throw new TypeMismatchException(a, b, node);
    }

    private void checkWithNull(Type a,Type b,ASTNode node){
        if(TypeConst.Null.equals(b)){
            if(a.equals(TypeConst.Void)||a.equals(TypeConst.Int)||a.equals(TypeConst.Bool))
                throw new TypeMismatchException(a,b,node);
        }else check(a,b,node);
    }

    @Override
    public void visit(RootNode node) {
        boolean will=checkIn(node);
        for (var sub : node.nodeList)
            sub.accept(this);
        checkOut(will);
    }

    @Override
    public void visit(ClassNode node) {
        boolean will=checkIn(node);
        for (var sub : node.members) visit(sub);
        for (var sub : node.constructor) visit(sub);
        for (var sub : node.methods) visit(sub);
        checkOut(will);
    }

    @Override
    public void visit(FunctionNode node) {
        boolean will=checkIn(node);
        visit(node.suite);
        checkOut(will);
    }

    @Override
    public void visit(DeclarationNode node) {
        boolean will = checkIn(node);
        if (node.expr != null)
            checkWithNull(node.type, inferType(node.expr), node);
        checkOut(will);
    }

    @Override
    public void visit(ConditionalNode node) {
        Type con;
        if ((con = inferType(node.condExpr)) != TypeConst.Bool)
            throw new TypeMismatchException(con, TypeConst.Bool, node);
//        boolean will = checkIn(node.trueStat);
        node.trueStat.accept(this);
//        checkOut(will);
        if (node.falseStat != null) {
//            will = checkIn(node.falseStat);
            node.falseStat.accept(this);
//            checkOut(will);
        }
    }

    @Override
    public void visit(LoopNode node) {
        boolean will=checkIn(node);
        Type con;
        if (node.initDecl != null) node.initDecl.accept(this);
        if (node.initExpr != null) inferType(node.initExpr);
        if (node.condExpr != null && !(con = inferType(node.condExpr)).equals(TypeConst.Bool))
            throw new TypeMismatchException(con, TypeConst.Bool, node);
        if (node.updateExpr != null) inferType(node.updateExpr);
        node.loopBody.accept(this);
        checkOut(will);
    }

    @Override
    public void visit(ReturnNode node) {
        if(node.returnExpr == null){
            if(!TypeConst.Void.equals(node.correspondingFunction.returnType))
                throw new TypeMismatchException(TypeConst.Void,node.correspondingFunction.returnType,node);
            else return;
        }
        check(inferType(node.returnExpr), node.correspondingFunction.returnType, node);
    }

    @Override
    public void visit(ExprStmtNode node) {
        boolean will = checkIn(node);
        if(node.expr!=null)
            inferType(node.expr);
        checkOut(will);
    }

    @Override
    public void visit(SuiteNode node) {
        boolean will = checkIn(node);
        for (var sub : node.statements) {
            sub.accept(this);
        }
        checkOut(will);
    }

    @Override
    public void visit(BreakNode node) {
        // do nothing
    }

    @Override
    public void visit(ContinueNode node) {
        // do nothing
    }

    @Override
    public void visit(DeclarationBlockNode node) {
        boolean will = checkIn(node);
        for (var sub : node.decls)
            sub.accept(this);
        checkOut(will);
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
        return calcType((ArrayLiteralNode) node);
    }

    private Type calcType(SubscriptionNode node) {
        Type indexType = inferType(node.rhs);
        if (!indexType.equals(TypeConst.Int)) throw new TypeMismatchException(indexType, TypeConst.Int, node);
        Type arrayType = inferType(node.lhs);
        if (arrayType.dimension == 0) throw new NotSubscriptableException(node);
        // return element type
        Type t = arrayType.copy();
        t.dimension -= 1;
        return t;
    }

    private Type calcType(AssignmentNode node) {
        // check left value
        if (node.lhs instanceof IdentifierNode
                || node.lhs instanceof MemberNode || node.lhs instanceof SubscriptionNode) {
            Type l = inferType(node.lhs), r = inferType(node.rhs);
            checkWithNull(l, r, node);
            node.type = l;
            return l;
        } else throw new AssignmentException(node);
    }

    private ClassType getObjOfMemberNode(MemberNode node) {
        Type type = inferType(node.object);
        if (!(type instanceof ClassType))
            throw new MemberMisuseException(node);
        return (ClassType) type;
    }

    private Type calcType(MemberNode node) {
        // will only accessed for member variable
        // member method will be handled in calcType(FuncCallNode)
        ClassType ct = getObjOfMemberNode(node);
        Symbol sym;
        if ((sym = ct.memberVars.get(node.member)) == null)
            throw new MissingSyntaxException(node, node.member);
        node.type = sym.getType();
        return node.type;
    }

    private int checkParameters(Function func, FuncCallNode node) {
        if (func.parameters.size() != node.arguments.size()) return 1;
        for (int i = 0; i < func.parameters.size(); ++i) {
            if (!func.parameters.get(i).getType().equals(inferType(node.arguments.get(i))))
                return 2;
        }
        return 0;
    }

    private Type calcType(FuncCallNode node) {
        Function func=null;
        if (node.isConstructor) {
            assert node.callee instanceof IdentifierNode;
            ClassType ct = currentScope.getClass(((IdentifierNode) node.callee).id, node);
            for (var con : ct.constructor) {
                if (checkParameters(con, node) == 0) {
                    func = con;
                    break;
                }
            }
            if (func == null) throw new NoMatchedFunctionException(node);
        } else {
            if (node.callee instanceof IdentifierNode) {
                // global function
                func = currentScope.getFunction(((IdentifierNode) node.callee).id, node);
            }
            else {
                // method
                assert node.callee instanceof MemberNode;
                ClassType ct = getObjOfMemberNode((MemberNode) node.callee);
                if ((func = ct.memberFuncs.get(((MemberNode) node.callee).member)) == null)
                    throw new MissingSyntaxException(node, ((MemberNode) node.callee).member);
            }
            // check types of arguments
            switch (checkParameters(func, node)) {
                case 1 -> throw new WrongParameterSizeException(node);
                case 2 -> throw new NoMatchedFunctionException(node);
                default -> {
                }
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
        return currentScope.getType(node.id, node);
    }

    private Type calcType(ThisNode node) {
        return currentScope.getType("this", node);
    }

    private Type calcType(NewExprNode node) {
        node.type = node.isConstruct ? calcType(node.classNew) : calcType(node.arrNew);
        return node.type;
    }

    private Type calcType(BinaryExprNode node) {
        assert node.lhs!=null&&node.rhs!=null;
        Type lhs = inferType(node.lhs), rhs = inferType(node.rhs);
        check(lhs, rhs, node);
        node.type = lhs;
        if (node.type instanceof Function)
            throw new TypeMismatchException(lhs, rhs, node);
        if (node.type instanceof ClassType && node.type != TypeConst.String)
            throw new UnsupportedBehavior("binary operation on Class is undefined", node);
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
                    throw new TypeMismatchException(lhs, rhs, node);
                break;
            case "&&":
            case "||":
                if (!node.type.equals(TypeConst.Bool))
                    throw new TypeMismatchException(lhs, rhs, node);
                break;
            case "^":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.Bool)))
                    throw new TypeMismatchException(lhs, rhs, node);
                break;
            case ">":
            case "<":
            case ">=":
            case "<=":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.String)))
                    throw new TypeMismatchException(lhs, rhs, node);
                node.type=TypeConst.Bool;
                break;
            case "+":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.String)))
                    throw new TypeMismatchException(lhs, rhs, node);
                break;
            case "==":
            case "!=":
                node.type = TypeConst.Bool;
                break;
            default:
                throw new MissingOverrideException(node.lexerSign);
        }
        return node.type;
    }

    private Type calcType(UnaryExprNode node) {
        node.type = inferType(node.expr);
        switch (node.lexerSign) {
            case "-":
            case "+":
            case "++":
            case "--":
            case "~":
                if (!node.type.equals(TypeConst.Int))
                    throw new TypeMismatchException(node.type, TypeConst.Int, node);
                break;
            case "!":
                if (!node.type.equals(TypeConst.Bool))
                    throw new TypeMismatchException(node.type, TypeConst.Bool, node);
                break;
        }
        return node.type;
    }

    private Type calcType(ArrayLiteralNode node) {
        return node.type;
    }
}
