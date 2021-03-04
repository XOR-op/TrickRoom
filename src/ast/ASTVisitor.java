package ast;

import ast.struct.*;
import misc.UnimplementedError;

public interface ASTVisitor  {
    private Object notImplemented() {
        throw new UnimplementedError();
    }
    default Object visit(ArrayLiteralNode node) {
        return notImplemented();
    }

    default Object visit(AssignmentNode node) {
        return notImplemented();
    }

    default Object visit(BinaryExprNode node) {
        return notImplemented();
    }

    default Object visit(BreakNode node) {
        return notImplemented();
    }

    default Object visit(ClassNode node) {
        return notImplemented();
    }

    default Object visit(ConditionalNode node) {
        return notImplemented();
    }

    default Object visit(ContinueNode node) {
        return notImplemented();
    }

    default Object visit(DeclarationBlockNode node) {
        return notImplemented();
    }

    default Object visit(DeclarationNode node) {
        return notImplemented();
    }

    default Object visit(ExprStmtNode node) {
        return notImplemented();
    }

    default Object visit(FuncCallNode node) {
        return notImplemented();
    }

    default Object visit(FunctionNode node) {
        return notImplemented();
    }

    default Object visit(IdentifierNode node) {
        return notImplemented();
    }

    default Object visit(LiteralNode node) {
        return notImplemented();
    }

    default Object visit(LoopNode node) {
        return notImplemented();
    }

    default Object visit(PrefixLeftValueNode node) {
        return notImplemented();
    }

    default Object visit(MemberNode node) {
        return notImplemented();
    }

    default Object visit(NewExprNode node) {
        return notImplemented();
    }

    default Object visit(ReturnNode node) {
        return notImplemented();
    }

    default Object visit(RootNode node) {
        return notImplemented();
    }

    default Object visit(SuiteNode node) {
        return notImplemented();
    }
    default Object visit(SubscriptionNode node) {
        return notImplemented();
    }

    default Object visit(ThisNode node) {
        return notImplemented();
    }

    default Object visit(UnaryExprNode node) {
        return notImplemented();
    }

}
