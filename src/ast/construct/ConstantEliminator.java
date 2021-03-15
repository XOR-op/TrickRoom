package ast.construct;

import ast.ASTVisitor;
import ast.struct.*;
import ast.type.TypeConst;
import misc.Cst;


public class ConstantEliminator implements ASTVisitor {
    private final RootNode root;

    public ConstantEliminator(RootNode tree) {
        root = tree;
    }

    public void run() {
        root.accept(this);
    }

    @Override
    public Object visit(RootNode node) {
        node.functions.forEach(this::visit);
        node.classes.forEach(this::visit);
        return null;
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
        if (node.lhs instanceof LiteralNode && node.rhs instanceof LiteralNode) {
            if (node.lhs.type.equals(TypeConst.String)) {
                String lhs = ((LiteralNode) node.lhs).content, rhs = ((LiteralNode) node.rhs).content;
                boolean val;
                switch (node.lexerSign) {
                    case Cst.EQUAL -> val = lhs.equals(rhs);
                    case Cst.NOT_EQ -> val = !lhs.equals(rhs);
                    case Cst.LESS -> val = lhs.compareTo(rhs) < 0;
                    case Cst.LESS_EQ -> val = lhs.compareTo(rhs) <= 0;
                    case Cst.GREATER -> val = lhs.compareTo(rhs) > 0;
                    case Cst.GREATER_EQ -> val = lhs.compareTo(rhs) >= 0;
                    case Cst.ADD -> {
                        return new LiteralNode(TypeConst.String, lhs.concat(rhs));
                    }
                    default -> throw new IllegalStateException();
                }
                return new LiteralNode(TypeConst.Bool, Boolean.toString(val));
            } else if (node.lhs.type.equals(TypeConst.Int)) {
                int lhs = Integer.parseInt(((LiteralNode) node.lhs).content),
                        rhs = Integer.parseInt(((LiteralNode) node.rhs).content);
                switch (node.lexerSign) {
                    case Cst.ADD, Cst.MINUS, Cst.MUL, Cst.DIV, Cst.MOD, Cst.RIGHT_SHIFT,
                            Cst.LEFT_SHIFT, Cst.AND_ARI, Cst.OR_ARI, Cst.XOR_ARI -> {
                        // arithmetic operations
                        int finalVal;
                        switch (node.lexerSign) {
                            case Cst.ADD -> finalVal = lhs + rhs;
                            case Cst.MINUS -> finalVal = lhs - rhs;
                            case Cst.MUL -> finalVal = lhs * rhs;
                            case Cst.DIV -> {
                                if (rhs == 0)
                                    return node; // ignore
                                finalVal = lhs / rhs;
                            }
                            case Cst.MOD -> finalVal = lhs % rhs;
                            case Cst.RIGHT_SHIFT -> finalVal = lhs >> rhs;
                            case Cst.LEFT_SHIFT -> finalVal = lhs << rhs;
                            case Cst.AND_ARI -> finalVal = lhs & rhs;
                            case Cst.OR_ARI -> finalVal = lhs | rhs;
                            case Cst.XOR_ARI -> finalVal = lhs ^ rhs;
                            default -> throw new IllegalStateException();
                        }
                        return new LiteralNode(TypeConst.Int, Integer.toString(finalVal));
                    }
                    default -> {
                        // relational operations
                        boolean val;
                        switch (node.lexerSign) {
                            case Cst.GREATER -> val = lhs > rhs;
                            case Cst.LESS -> val = lhs < rhs;
                            case Cst.GREATER_EQ -> val = lhs >= rhs;
                            case Cst.LESS_EQ -> val = lhs <= rhs;
                            case Cst.EQUAL -> val = lhs == rhs;
                            case Cst.NOT_EQ -> val = lhs != rhs;
                            default -> throw new IllegalStateException();
                        }
                        return new LiteralNode(TypeConst.Bool, Boolean.toString(val));
                    }
                }
            } else {
                assert node.lhs.type.equals(TypeConst.Bool);
                boolean lhs = Boolean.parseBoolean(((LiteralNode) node.lhs).content), rhs = Boolean.parseBoolean(((LiteralNode) node.rhs).content);
                boolean val;
                switch (node.lexerSign) {
                    case Cst.EQUAL -> val = lhs == rhs;
                    case Cst.NOT_EQ -> val = lhs != rhs;
                    case Cst.AND_LOGIC, Cst.AND_ARI -> val = lhs & rhs;
                    case Cst.OR_LOGIC, Cst.OR_ARI -> val = lhs | rhs;
                    case Cst.XOR_ARI -> val = lhs ^ rhs;
                    default -> throw new IllegalStateException();
                }
                return new LiteralNode(TypeConst.Bool, Boolean.toString(val));
            }
        }
        return node;
    }

    @Override
    public ExprNode visit(UnaryExprNode node) {
        node.expr = (ExprNode) node.expr.accept(this);
        if (node.lexerSign.equals(Cst.ADD)) return node.expr;
        if (node.expr instanceof LiteralNode) {
            switch (node.lexerSign) {
                case Cst.MINUS -> {
                    assert node.type.equals(TypeConst.Int);
                    return new LiteralNode(TypeConst.Int, Integer.toString(-Integer.parseInt(((LiteralNode) node.expr).content)));
                }
                case Cst.NOT_ARI -> {
                    assert node.type.equals(TypeConst.Int);
                    return new LiteralNode(TypeConst.Int, Integer.toString(~Integer.parseInt(((LiteralNode) node.expr).content)));
                }
                case Cst.NOT_LOGIC -> {
                    return new LiteralNode(TypeConst.Bool, Boolean.toString(!Boolean.parseBoolean(((LiteralNode) node.expr).content)));
                }
                default -> throw new IllegalStateException();
            }
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
    public ExprNode visit(AssignmentNode node) {
        node.rhs = (ExprNode) node.rhs.accept(this);
        node.lhs = (ExprNode) node.lhs.accept(this);
        return node;
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