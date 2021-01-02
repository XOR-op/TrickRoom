package semantic;

import ast.*;
import exception.MissingOverrideException;

public class ASTVisitor {
    public void visit(ASTNode node) {
        throw new MissingOverrideException();
    }

    public void visit(ArrayLiteralNode node) {
        throw new MissingOverrideException();
    }

    public void visit(BinaryExprNode node) {
        throw new MissingOverrideException();
    }

    public void visit(BreakNode node) {
        throw new MissingOverrideException();
    }

    public void visit(ClassNode node) {
        throw new MissingOverrideException();
    }

    public void visit(ConditionalNode node) {
        throw new MissingOverrideException();
    }

    public void visit(ContinueNode node) {
        throw new MissingOverrideException();
    }

    public void visit(DeclarationBlockNode node) {
        throw new MissingOverrideException();
    }

    public void visit(DeclarationNode node) {
        throw new MissingOverrideException();
    }

    public void visit(ExprNode node) {
        throw new MissingOverrideException();
    }

    public void visit(ExprStmtNode node) {
        throw new MissingOverrideException();
    }

    public void visit(FuncCallNode node) {
        throw new MissingOverrideException();
    }

    public void visit(FunctionNode node) {
        throw new MissingOverrideException();
    }

    public void visit(IdentifierNode node) {
        throw new MissingOverrideException();
    }

    public void visit(LiteralNode node) {
        throw new MissingOverrideException();
    }

    public void visit(LoopNode node) {
        throw new MissingOverrideException();
    }

    public void visit(NewExprNode node) {
        throw new MissingOverrideException();
    }

    public void visit(ReturnNode node) {
        throw new MissingOverrideException();
    }

    public void visit(RootNode node) {
        throw new MissingOverrideException();
    }

    public void visit(StmtNode node) {
        throw new MissingOverrideException();
    }

    public void visit(SuiteNode node) {
        throw new MissingOverrideException();
    }

    public void visit(UnaryExprNode node) {
        throw new MissingOverrideException();
    }
}
