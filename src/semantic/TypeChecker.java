package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;
import exception.semantic.AssignmentException;
import exception.semantic.MemberMisuseException;
import exception.semantic.WrongParameterSizeException;
import exception.semantic.TypeMismatchException;

public class TypeChecker implements ASTVisitor {
    private Scope currentScope;

    private void checkIn(ASTNode node) {
        if (node.scope != null) {
            assert node.scope.getUpstream() == currentScope;
            currentScope = node.scope;
        }
    }

    private void checkOut() {
        assert currentScope.getUpstream() != null;
        currentScope = currentScope.getUpstream();
    }

    private void check(Type a, Type b) {
        if (!a.equals(b)||a.equals(Type.Void)) throw new TypeMismatchException();
    }

    @Override
    public void visit(RootNode node) {
        for (var sub : node.nodeList)
            sub.accept(this);
    }

    @Override
    public void visit(DeclarationNode node) {
        checkIn(node);
        if (node.expr != null)
            check(node.type, inferType(node.expr));
        checkOut();
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
            Type l=inferType(node.lhs), r=inferType(node.rhs);
            check(l,r);
            node.type=l;
            return l;
        } else throw new AssignmentException();
    }

    private Type calcType(MemberNode node) {
        Type type=inferType(node.object);
        if(!type.isClass)throw new MemberMisuseException();
        ClassType cls=currentScope.getClass(type.id);

    }

    private Type calcType(FuncCallNode node) {
        Function func=currentScope.getFunction(node.funcName);
        // check types of arguments
        if(func.parameters.size()!=node.arguments.size())throw new WrongParameterSizeException();
        for(int i=0;i<func.parameters.size();++i){
            if(!func.parameters.get(i).getType().equals(inferType(node.arguments.get(i))))
                throw new WrongParameterSizeException();
        }
        node.type=func.node.returnType;
        return node.type;
    }

    private Type calcType(LiteralNode node) {
        return node.type;
    }

    private Type calcType(IdentifierNode node) {
        return currentScope.getType(node.id);
    }

    private Type calcType(ThisNode node) {
        return currentScope.getType("this");
    }

    private Type calcType(NewExprNode node) {
        node.type=node.isConstruct ?calcType(node.classNew):calcType(node.arrNew);
        return node.type;
    }

    private Type calcType(BinaryExprNode node) {
        Type lhs = inferType(node.lhs), rhs = inferType(node.rhs);
        check(lhs, rhs);
        node.type=lhs;
        return lhs;
    }

    private Type calcType(UnaryExprNode node) {
        node.type=inferType(node.expr);
        // todo specific type check
        return node.type;
    }

    private Type calcType(ArrayLiteralNode node) {
        return node.type;
    }
}
