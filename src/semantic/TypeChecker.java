package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;
import exception.MissingOverrideException;
import exception.semantic.*;

public class TypeChecker implements ASTVisitor {
    private Scope currentScope;

    private boolean checkIn(ASTNode node) {
        assert node.scope != null|| node instanceof SuiteNode;
        assert node.scope.getUpstream() == currentScope;
        if(node.scope!=null) {
            currentScope = node.scope;
            return true;
        }else return false;
    }

    private void checkOut() {
        assert currentScope.getUpstream() != null;
        currentScope = currentScope.getUpstream();
    }
    private void checkOut(boolean will){
        if(will)checkOut();
    }

    private void check(Type a, Type b,ASTNode node) {
        if (!a.equals(b) || a.equals(TypeConst.Void)) throw new TypeMismatchException(a, b,node);
    }

    @Override
    public void visit(RootNode node) {
        for (var sub : node.nodeList)
            sub.accept(this);
    }

    @Override
    public void visit(ClassNode node) {
        checkIn(node);
        for(var sub:node.members)visit(sub);
        for(var sub:node.constructor)visit(sub);
        for(var sub:node.methods)visit(sub);
        checkOut();
    }

    @Override
    public void visit(FunctionNode node) {
        checkIn(node);
        visit(node.suite);
        checkOut();
    }

    @Override
    public void visit(DeclarationNode node) {
        boolean will=checkIn(node);
        if (node.expr != null)
            check(node.type, inferType(node.expr),node);
        checkOut(will);
    }

    @Override
    public void visit(ConditionalNode node) {
        Type con;
        if ((con = inferType(node.condExpr)) != TypeConst.Bool)
            throw new TypeMismatchException(con, TypeConst.Bool,node);
        boolean will=checkIn(node.trueStat);
        node.trueStat.accept(this);
        checkOut(will);
        if(node.falseStat!=null) {
            will = checkIn(node.falseStat);
            node.falseStat.accept(this);
            checkOut(will);
        }
    }

    @Override
    public void visit(LoopNode node) {
        checkIn(node);
        Type con;
        if (node.initDecl != null) node.initDecl.accept(this);
        if (node.initExpr != null) inferType(node.initExpr);
        if (node.condExpr != null && !(con = inferType(node.condExpr)).equals(TypeConst.Bool))
            throw new TypeMismatchException(con, TypeConst.Bool,node);
        if (node.updateExpr != null) node.updateExpr.accept(this);
        node.loopBody.accept(this);
        checkOut();
    }

    @Override
    public void visit(ReturnNode node) {
        Type tp = node.returnExpr == null ? TypeConst.Void : inferType(node.returnExpr);
        check(tp, node.correspondingFunction.returnType,node);
    }

    @Override
    public void visit(ExprStmtNode node) {
        boolean will=checkIn(node);
        inferType(node.expr);
        checkOut(will);
    }

    @Override
    public void visit(SuiteNode node) {
        boolean will=checkIn(node);
        for (var sub : node.statements) {
            sub.accept(this);
        }
        checkOut(will);
    }

    @Override
    public void visit(DeclarationBlockNode node) {
        boolean will=checkIn(node);
        for(var sub:node.decls)
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
        return calcType((ArrayLiteralNode) node);
    }

    private Type calcType(AssignmentNode node) {
        // check left value
        if (node.lhs instanceof IdentifierNode
                || node.lhs instanceof MemberNode || node.lhs instanceof SubscriptionNode) {
            Type l = inferType(node.lhs), r = inferType(node.rhs);
            check(l, r,node);
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
        ClassType ct = getObjOfMemberNode(node);
        Type tp;
        if ((tp = ct.memberFuncs.get(node.member)) == null)
            throw new MissingSyntaxException(node,node.member);
        node.type = tp;
        return tp;
    }

    private int checkParameters(Function func,FuncCallNode node){
        if (func.parameters.size() != node.arguments.size()) return 1;
        for (int i = 0; i < func.parameters.size(); ++i) {
            if (!func.parameters.get(i).getType().equals(inferType(node.arguments.get(i))))
                return 2;
        }
        return 0;
    }

    private Type calcType(FuncCallNode node) {
        Function func=null;
        if(node.isConstructor){
            assert node.callee instanceof IdentifierNode;
            ClassType ct=currentScope.getClass(((IdentifierNode) node.callee).id,node);
            for(var con:ct.constructor){
                if(checkParameters(con,node)==0){
                    func=con;
                    break;
                }
            }
            if(func==null)throw new NoMatchedFunctionException(node);
        }
        else {
            if (node.callee instanceof IdentifierNode)
                func = currentScope.getFunction(((IdentifierNode) node.callee).id,node);
            else {
                assert node.callee instanceof MemberNode;
                ClassType ct = getObjOfMemberNode((MemberNode) node.callee);
                if ((func = ct.memberFuncs.get(((MemberNode) node.callee).member)) == null)
                    throw new MissingSyntaxException(node,((MemberNode) node.callee).member);
            }
            // check types of arguments
            switch (checkParameters(func, node)){
                case 1->throw new WrongParameterSizeException(node);
                case 2->throw new NoMatchedFunctionException(node);
                default->{}
            }
        }
        node.type = func.node.returnType;
        node.correspondingFunc=func;
        return node.type;
    }

    private Type calcType(LiteralNode node) {
        return node.type;
    }

    private Type calcType(IdentifierNode node) {
        return currentScope.getType(node.id,node);
    }

    private Type calcType(ThisNode node) {
        return currentScope.getType("this",node);
    }

    private Type calcType(NewExprNode node) {
        node.type = node.isConstruct ? calcType(node.classNew) : calcType(node.arrNew);
        return node.type;
    }

    private Type calcType(BinaryExprNode node) {
        Type lhs = inferType(node.lhs), rhs = inferType(node.rhs);
        check(lhs, rhs,node);
        node.type = lhs;
        if (node.type instanceof Function)
            throw new TypeMismatchException(lhs, rhs,node);
        if (node.type instanceof ClassType && node.type != TypeConst.String)
            throw new UnsupportedBehavior("binary operation on Class is undefined",node);
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
                    throw new TypeMismatchException(lhs, rhs,node);
                break;
            case "&&":
            case "||":
                if (!node.type.equals(TypeConst.Bool))
                    throw new TypeMismatchException(lhs, rhs,node);
                break;
            case "^":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.Bool)))
                    throw new TypeMismatchException(lhs, rhs,node);
                break;
            case ">":
            case "<":
            case ">=":
            case "<=":
            case "+":
                if (!(node.type.equals(TypeConst.Int) || node.type.equals(TypeConst.String)))
                    throw new TypeMismatchException(lhs, rhs,node);
                break;
            case "==":
            case "!=":
                node.type=TypeConst.Bool;
                break;
            default:
                throw new MissingOverrideException();
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
                    throw new TypeMismatchException(node.type, TypeConst.Int,node);
                break;
            case "!":
                if (!node.type.equals(TypeConst.Bool))
                    throw new TypeMismatchException(node.type, TypeConst.Bool,node);
                break;
        }
        return node.type;
    }

    private Type calcType(ArrayLiteralNode node) {
        return node.type;
    }
}
