package ast.construct;

import ast.ASTVisitor;
import ast.struct.*;


public class ConstantEliminator implements ASTVisitor {
    private final RootNode root;

    public ConstantEliminator(RootNode tree) {
        root = tree;
    }


    @Override
    public Void visit(DeclarationBlockNode node) {
        for (var sub : node.decls)
            visit(sub);
        return null;
    }

    @Override
    public Void visit(DeclarationNode node) {
        if (node.expr != null) node.expr = (ExprNode) node.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(ClassNode node) {
        node.constructorNode.forEach(this::visit);
        node.methodNode.forEach(this::visit);
        return null;
    }

    @Override
    public Void visit(FunctionNode node) {
        visit(node.suiteNode);
        return null;
    }

    @Override
    public Void visit(SuiteNode node) {
        for (var sub : node.statements) {
            sub.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ConditionalNode node) {
        node.condExpr = (ExprNode) node.condExpr.accept(this);
        node.trueStat.accept(this);
        if (node.falseStat != null) {
            node.falseStat.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(LoopNode node) {
        if (node.initDecl != null) visit(node.initDecl);
        if (node.initExpr != null) node.initExpr = (ExprNode) node.initExpr.accept(this);
        if (node.condExpr != null) node.condExpr = (ExprNode) node.condExpr.accept(this);
        if (node.updateExpr != null) node.updateExpr = (ExprNode) node.updateExpr.accept(this);
        node.loopBody.accept(this);
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        if (node.returnExpr != null) node.returnExpr = (ExprNode) node.returnExpr.accept(this);
        return null;
    }

    @Override
    public Void visit(BreakNode node) {
        return null;
    }

    @Override
    public Void visit(ContinueNode node) {
        return null;
    }

    @Override
    public Void visit(ExprStmtNode node) {
        if (node.expr != null)
            node.expr = (ExprNode) node.expr.accept(this);
        return null;
    }

    @Override
    public ExprNode visit(IdentifierNode node) {
        return node;
    }

    @Override
    public ExprNode visit(BinaryExprNode node) {
        node.lhs = (ExprNode) node.lhs.accept(this);
        node.rhs = (ExprNode) node.rhs.accept(this);
        if(node.lhs instanceof LiteralNode&&node.rhs instanceof LiteralNode){
            // todo calculate and eliminate
        }
        return node;
    }

    @Override
    public ExprNode visit(UnaryExprNode node) {
        node.expr = (ExprNode) node.expr.accept(this);
        if(node.expr instanceof LiteralNode){
            // todo eliminate
        }
        return node;
    }

    @Override
    public ExprNode visit(ThisNode node) {
        return node;
    }

    @Override
    public ExprNode visit(MemberNode node) {
        node.object = (ExprNode) node.object.accept(this);
        return node;
    }

    @Override
    public ExprNode visit(NewExprNode node) {
        if (node.arrNew != null) node.arrNew = (ArrayLiteralNode) visit(node.arrNew);
        return node;
    }

    @Override
    public ExprNode visit(LiteralNode node) {
        return node;
    }

    @Override
    public ExprNode visit(FuncCallNode node) {
        if (!(node.callee instanceof IdentifierNode))
            node.callee = (ExprNode) node.callee.accept(this);
        node.arguments.replaceAll(arg -> (ExprNode) arg.accept(this));
        return node;
    }

    @Override
    public Void visit(AssignmentNode node) {
        node.rhs = (ExprNode) node.rhs.accept(this);
        node.lhs = (ExprNode) node.lhs.accept(this);
        return null;
    }

    @Override
    public ExprNode visit(ArrayLiteralNode node) {
        node.dimArr.replaceAll(sub -> (ExprNode) sub.accept(this));
        return node;
    }

    @Override
    public ExprNode visit(SubscriptionNode node) {
        node.lhs = (ExprNode) node.lhs.accept(this);
        node.rhs = (ExprNode) node.rhs.accept(this);
        return node;
    }

    @Override
    public ExprNode visit(PrefixLeftValueNode node) {
        node.expr = (ExprNode) node.expr.accept(this);
        return node;
    }
}