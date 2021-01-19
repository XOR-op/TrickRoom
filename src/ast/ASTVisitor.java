package ast;

import ast.*;
import exception.UnimplementedError;

public interface ASTVisitor<T>  {
    private T notImplemented() {
        throw new UnimplementedError();
    }
    default T visit(ArrayLiteralNode node) {
        return notImplemented();
    }

    default T visit(AssignmentNode node) {
        return notImplemented();
    }

    default T visit(BinaryExprNode node) {
        return notImplemented();
    }

    default T visit(BreakNode node) {
        return notImplemented();
    }

    default T visit(ClassNode node) {
        return notImplemented();
    }

    default T visit(ConditionalNode node) {
        return notImplemented();
    }

    default T visit(ContinueNode node) {
        return notImplemented();
    }

    default T visit(DeclarationBlockNode node) {
        return notImplemented();
    }

    default T visit(DeclarationNode node) {
        return notImplemented();
    }

    default T visit(ExprStmtNode node) {
        return notImplemented();
    }

    default T visit(FuncCallNode node) {
        return notImplemented();
    }

    default T visit(FunctionNode node) {
        return notImplemented();
    }

    default T visit(IdentifierNode node) {
        return notImplemented();
    }

    default T visit(LiteralNode node) {
        return notImplemented();
    }

    default T visit(LoopNode node) {
        return notImplemented();
    }

    default T visit(PrefixLeftValueNode node) {
        return notImplemented();
    }

    default T visit(MemberNode node) {
        return notImplemented();
    }

    default T visit(NewExprNode node) {
        return notImplemented();
    }

    default T visit(ReturnNode node) {
        return notImplemented();
    }

    default T visit(RootNode node) {
        return notImplemented();
    }

    default T visit(SuiteNode node) {
        return notImplemented();
    }
    default T visit(SubscriptionNode node) {
        return notImplemented();
    }

    default T visit(ThisNode node) {
        return notImplemented();
    }

    default T visit(UnaryExprNode node) {
        return notImplemented();
    }

}
