package semantic;

import ast.*;
import exception.UnimplementedError;

public interface ASTVisitor  {
    private void notImplemented() {
        throw new UnimplementedError();
    }
    default void visit(ArrayLiteralNode node) {
        notImplemented();
    }

    default void visit(AssignmentNode node) {
        notImplemented();
    }

    default void visit(BinaryExprNode node) {
        notImplemented();
    }

    default void visit(BreakNode node) {
        notImplemented();
    }

    default void visit(ClassNode node) {
        notImplemented();
    }

    default void visit(ConditionalNode node) {
        notImplemented();
    }

    default void visit(ContinueNode node) {
        notImplemented();
    }

    default void visit(DeclarationBlockNode node) {
        notImplemented();
    }

    default void visit(DeclarationNode node) {
        notImplemented();
    }

    default void visit(ExprStmtNode node) {
        notImplemented();
    }

    default void visit(FuncCallNode node) {
        notImplemented();
    }

    default void visit(FunctionNode node) {
        notImplemented();
    }

    default void visit(IdentifierNode node) {
        notImplemented();
    }

    default void visit(LiteralNode node) {
        notImplemented();
    }

    default void visit(LoopNode node) {
        notImplemented();
    }

    default void visit(PrefixLeftValueNode node) {
        notImplemented();
    }

    default void visit(MemberNode node) {
        notImplemented();
    }

    default void visit(NewExprNode node) {
        notImplemented();
    }

    default void visit(ReturnNode node) {
        notImplemented();
    }

    default void visit(RootNode node) {
        notImplemented();
    }

    default void visit(SuiteNode node) {
        notImplemented();
    }
    default void visit(SubscriptionNode node) {
        notImplemented();
    }

    default void visit(ThisNode node) {
        notImplemented();
    }

    default void visit(UnaryExprNode node) {
        notImplemented();
    }

}
