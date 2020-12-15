package semantic;
import ast.*;

public interface ASTVisitor {
    void visit(ASTNode node);
    void visit(ArrayLiteralNode node);
    void visit(BinaryExprNode node);
    void visit(BreakNode node);
    void visit(ClassNode node);
    void visit(ConditionalNode node);
    void visit(ContinueNode node);
    void visit(DeclarationBlockNode node);
    void visit(DeclarationNode node);
    void visit(ExprNode node);
    void visit(ExprStmtNode node);
    void visit(FuncCallNode node);
    void visit(FunctionNode node);
    void visit(IdentifierNode node);
    void visit(LiteralNode node);
    void visit(LoopNode node);
    void visit(NewExprNode node);
    void visit(ReturnNode node);
    void visit(RootNode node);
    void visit(StmtNode node);
    void visit(SuiteNode node);
    void visit(UnaryExprNode node);
}
