package ast.construct;

import ast.struct.*;
import ast.type.ArrayObjectType;
import ast.type.Type;
import ast.type.TypeConst;
import ast.compilingInfo.CodePosition;
import exception.UnimplementedError;
import exception.semantic.ParsingException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import parser.MxStarParser;
import parser.MxStarVisitor;

import java.util.ArrayList;

public class ASTBuilder extends AbstractParseTreeVisitor<ASTNode> implements MxStarVisitor<ASTNode> {

    @Override
    public ASTNode visitCode(MxStarParser.CodeContext ctx) {
        var rn = new RootNode();
        rn.setPos(ctx);
        for (var subctx : ctx.children) {
            if (subctx instanceof MxStarParser.FunctionDefContext)
                rn.addFuncNode((FunctionNode) visitFunctionDef((MxStarParser.FunctionDefContext) subctx));
            else if (subctx instanceof MxStarParser.ClassDefContext)
                rn.addClassNode((ClassNode) visitClassDef((MxStarParser.ClassDefContext) subctx));
            else {
                // variable declaration
                iterateDeclExpr(((MxStarParser.DeclarationStatementContext) subctx).declExpr(), rn);
            }
        }
        return rn;
    }

    @Override
    public ASTNode visitFunctionDef(MxStarParser.FunctionDefContext ctx) {
        FunctionNode fn = new FunctionNode(ctx.Identifier().getText());
        fn.setPos(ctx);
        fn.returnType = (ctx.returnType().VOID_KW() != null) ? TypeConst.Void : iterateVarType(ctx.returnType().varType());
        fn.suite = (SuiteNode) visitSuite(ctx.suite());
        fn.parameters = iterateFunctionParamDef(ctx.functionParamDef());
        return fn;
    }


    private ArrayList<DeclarationNode> iterateFunctionParamDef(MxStarParser.FunctionParamDefContext ctx) {
        var re = new ArrayList<DeclarationNode>();
        for (int i = 0; i < ctx.Identifier().size(); ++i) {
            re.add(new DeclarationNode(iterateVarType(ctx.varType(i)), ctx.Identifier(i).getText()));
            re.get(i).setPos(ctx);
        }
        return re;
    }

    @Override
    public ASTNode visitClassDef(MxStarParser.ClassDefContext ctx) {
        ClassNode cn = new ClassNode(ctx.Identifier().getText());
        cn.setPos(ctx);
        for (var sctx : ctx.functionDef())
            cn.methods.add((FunctionNode) visitFunctionDef(sctx));

        for (var sctx : ctx.declarationStatement())
            iterateDeclExpr(sctx.declExpr(), cn.members);

        for (var sctx : ctx.constructorDefinition())
            cn.constructor.add((FunctionNode) visitConstructorDefinition(sctx));

        return cn;
    }


    @Override
    public ASTNode visitConstructorDefinition(MxStarParser.ConstructorDefinitionContext ctx) {
        var fn = new FunctionNode(ctx.Identifier().getText());
        fn.setPos(ctx);
        fn.returnType = TypeConst.Void;
        fn.suite = (SuiteNode) visitSuite(ctx.suite());
        fn.parameters = iterateFunctionParamDef(ctx.functionParamDef());
        return fn;
    }


    @Override
    public ASTNode visitSuite(MxStarParser.SuiteContext ctx) {
        SuiteNode sn = new SuiteNode();
        sn.setPos(ctx);
        for (var sub : ctx.statement()) {
            StmtNode subn = (StmtNode) visitStatement(sub);
            if (subn != null)
                sn.statements.add(subn);
        }
        return sn;
    }

    @Override
    public ASTNode visitStatement(MxStarParser.StatementContext ctx) {
        if (ctx.suite() != null) return visitSuite(ctx.suite());
        else if (ctx.declarationStatement() != null) return visitDeclarationStatement(ctx.declarationStatement());
        else if (ctx.ifStatement() != null) return visitIfStatement(ctx.ifStatement());
        else if (ctx.whileStatement() != null) return visitWhileStatement(ctx.whileStatement());
        else if (ctx.forStatement() != null) return visitForStatement(ctx.forStatement());
        else if (ctx.continueStatement() != null) return visitContinueStatement(ctx.continueStatement());
        else if (ctx.breakStatement() != null) return visitBreakStatement(ctx.breakStatement());
        else if (ctx.returnStatement() != null) return visitReturnStatement(ctx.returnStatement());
        else return visitSimpleStatement(ctx.simpleStatement());
    }

    @Override
    public ASTNode visitIfStatement(MxStarParser.IfStatementContext ctx) {
        var cn = new ConditionalNode();
        cn.setPos(ctx);
        cn.condExpr = (ExprNode) visit(ctx.expression());
        cn.trueStat = (StmtNode) visitStatement(ctx.trueStat);
        if (ctx.falseStat != null) cn.falseStat = (StmtNode) visitStatement(ctx.falseStat);
        return cn;
    }

    @Override
    public ASTNode visitWhileStatement(MxStarParser.WhileStatementContext ctx) {
        LoopNode ln = new LoopNode();
        ln.setPos(ctx);
        ln.initExpr = null;
        ln.initDecl = null;
        ln.condExpr = (ExprNode) visit(ctx.expression());
        ln.updateExpr = null;
        ln.loopBody = ctx.statement() == null ? null : (StmtNode) visitStatement(ctx.statement());
        return ln;
    }

    @Override
    public ASTNode visitForStatement(MxStarParser.ForStatementContext ctx) {
        LoopNode ln = new LoopNode();
        ln.setPos(ctx);
        if (ctx.initExp != null) {
            if (ctx.initExp.declExpr() != null) {
                ln.initDecl = new DeclarationBlockNode();
                ln.initExpr = null;
                iterateDeclExpr(ctx.initExp.declExpr(), ln.initDecl.decls);
            } else {
                // normal expression
                ln.initDecl = null;
                ln.initExpr = (ExprNode) visit(ctx.initExp.expression());
            }
        }
        ln.condExpr = ctx.condExp == null ? null : (ExprNode) visit(ctx.condExp);
        ln.updateExpr = ctx.updExp == null ? null : (ExprNode) visit(ctx.updExp);
        ln.loopBody = ctx.statement() == null ? null : (StmtNode) visitStatement(ctx.statement());
        return ln;
    }

    @Override
    public ASTNode visitContinueStatement(MxStarParser.ContinueStatementContext ctx) {

        var node = new ContinueNode();
        node.setPos(ctx);
        return node;
    }

    @Override
    public ASTNode visitBreakStatement(MxStarParser.BreakStatementContext ctx) {
        var node = new BreakNode();
        node.setPos(ctx);
        return node;
    }

    @Override
    public ASTNode visitReturnStatement(MxStarParser.ReturnStatementContext ctx) {
        ReturnNode rn = new ReturnNode();
        rn.setPos(ctx);
        rn.returnExpr = (ctx.expression() != null) ? (ExprNode) visit(ctx.expression()) : null;
        return rn;
    }

    @Override
    public ASTNode visitSimpleStatement(MxStarParser.SimpleStatementContext ctx) {
        return ctx.expression() == null ? new ExprStmtNode(null) : new ExprStmtNode((ExprNode) visit(ctx.expression()));
    }

    @Override
    public ASTNode visitDeclarationStatement(MxStarParser.DeclarationStatementContext ctx) {
        var n = new DeclarationBlockNode();
        n.setPos(ctx);
        iterateDeclExpr(ctx.declExpr(), n.decls);
        return n;
    }

    @Override
    public ASTNode visitAtomExp(MxStarParser.AtomExpContext ctx) {
        if (ctx.Identifier() != null) {
            var node = new IdentifierNode(ctx.Identifier().getText());
            node.setPos(ctx);
            return node;
        } else if (ctx.constant() != null) {
            return visitConstant(ctx.constant());
        } else {
            return visit(ctx.expression());
        }
    }

    @Override
    public ASTNode visitNewExpr(MxStarParser.NewExprContext ctx) {
        if(ctx.arraySemanticError()!=null)throw new ParsingException(new CodePosition(ctx));
        var nn = new NewExprNode(ctx.arrayLiteral() == null);
        nn.setPos(ctx);
        if (nn.isClass) {
            // implicitly-invoked constructor or the opposite
            nn.classNew = ctx.Identifier() != null ?
                    new FuncCallNode(new IdentifierNode(ctx.Identifier().getText()), true) :
                    (FuncCallNode) visitConstructorCall(ctx.constructorCall());
            nn.classNew.setPos(ctx);
            nn.classNew.isConstructor = true;
        } else {
            nn.arrNew = (ArrayLiteralNode) visitArrayLiteral(ctx.arrayLiteral());
        }
        return nn;
    }

    @Override
    public ASTNode visitIndexExpr(MxStarParser.IndexExprContext ctx) {
        var node = new SubscriptionNode((ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)));
        node.setPos(ctx);
        return node;
    }

    @Override
    public ASTNode visitPrefixExpr(MxStarParser.PrefixExprContext ctx) {
        ExprNode node;
        String op=ctx.prefix.getText();
        if(op.equals("++")||op.equals("--")){
            node= new PrefixLeftValueNode(op, (ExprNode) visit(ctx.expression()));
        }else {
            node = new UnaryExprNode(true, op, (ExprNode) visit(ctx.expression()));
        }
        node.setPos(ctx);
        return node;
    }

    @Override
    public ASTNode visitFuncExpr(MxStarParser.FuncExprContext ctx) {
        var fcn = new FuncCallNode((ExprNode) visit(ctx.expression()));
        fcn.setPos(ctx);
        if (ctx.expressionList() != null)
            for (var exp : ctx.expressionList().expression())
                fcn.arguments.add((ExprNode) visit(exp));
        return fcn;
    }

    @Override
    public ASTNode visitMemberExpr(MxStarParser.MemberExprContext ctx) {
        var node = new MemberNode((ExprNode) visit(ctx.expression()), ctx.Identifier().getText());
        node.setPos(ctx);
        return node;
    }

    @Override
    public ASTNode visitSuffixExpr(MxStarParser.SuffixExprContext ctx) {
        var node = new UnaryExprNode(false, ctx.suffix.getText(), (ExprNode) visit(ctx.expression()));
        node.setPos(ctx);
        return node;
    }

    @Override
    public ASTNode visitAtomExpr(MxStarParser.AtomExprContext ctx) {
        return visitAtomExp(ctx.atomExp());
    }

    @Override
    public ASTNode visitBinaryExpr(MxStarParser.BinaryExprContext ctx) {
        String sign;
        if (ctx.multiplicativeOp() != null)
            sign = ctx.multiplicativeOp().getText();
        else if (ctx.additiveOp() != null)
            sign = ctx.additiveOp().getText();
        else if (ctx.relationalCmpOp() != null)
            sign = ctx.relationalCmpOp().getText();
        else if (ctx.equalityCmpOp() != null)
            sign = ctx.equalityCmpOp().getText();
        else if (ctx.shiftOp() != null)
            sign = ctx.shiftOp().getText();
        else if (ctx.AND_ARI() != null)
            sign = ctx.AND_ARI().getText();
        else if (ctx.XOR_ARI() != null)
            sign = ctx.XOR_ARI().getText();
        else if (ctx.OR_ARI() != null)
            sign = ctx.OR_ARI().getText();
        else if (ctx.AND_LOGIC() != null)
            sign = ctx.AND_LOGIC().getText();
        else if (ctx.OR_LOGIC() != null)
            sign = ctx.OR_LOGIC().getText();
        else throw new UnimplementedError();
        var node = new BinaryExprNode(sign, (ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)));
        node.setPos(ctx);
        return node;

    }

    @Override
    public ASTNode visitAssignExpr(MxStarParser.AssignExprContext ctx) {
        var node = new AssignmentNode((ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)));
        node.setPos(ctx);
        return node;
    }

    @Override
    public ASTNode visitConstructorCall(MxStarParser.ConstructorCallContext ctx) {
        var fcn = new FuncCallNode(new IdentifierNode(ctx.Identifier().getText()));
        fcn.setPos(ctx);
        if (ctx.expressionList() != null)
            for (var exp : ctx.expressionList().expression())
                fcn.arguments.add((ExprNode) visit(exp));
        return fcn;
    }

    // RootNode calls for sequential order
    private void iterateDeclExpr(MxStarParser.DeclExprContext ctx, RootNode node) {
        Type type = iterateVarType(ctx.varType());
        for (var declCtx : ctx.varDeclaration()) {
            var entry = (DeclarationNode) visitVarDeclaration(declCtx);
            entry.type = type;
            node.addDeclNode(entry);
        }
    }

    // ClassNode not and declaration statement not
    private void iterateDeclExpr(MxStarParser.DeclExprContext ctx, ArrayList<DeclarationNode> list) {
        Type type = iterateVarType(ctx.varType());
        for (var declCtx : ctx.varDeclaration()) {
            var entry = (DeclarationNode) visitVarDeclaration(declCtx);
            entry.type = type;
            list.add(entry);
        }
    }

    @Override
    public ASTNode visitArrayLiteral(MxStarParser.ArrayLiteralContext ctx) {
        Type type;
        if (ctx.builtinType() != null) {
            var ref = ctx.builtinType();
            if (ref.STRING_KW() != null) type = TypeConst.String;
            else if (ref.INT_KW() != null) type = TypeConst.Int;
            else type = TypeConst.Bool;
        } else {
            // type will be converted to ClassType later
            type = new Type(ctx.Identifier().getText());
        }
        var aln = new ArrayLiteralNode(new ArrayObjectType(new Type(type.id), ctx.L_BRACKET().size()));
        aln.setPos(ctx);
        if (ctx.expression() != null)
            for (var exp : ctx.expression())
                aln.dimArr.add((ExprNode) visit(exp));
        return aln;
    }

    @Override
    public ASTNode visitVarDeclaration(MxStarParser.VarDeclarationContext ctx) {
        var dn = new DeclarationNode(null, ctx.Identifier().getText());
        dn.setPos(ctx);
        if (ctx.expression() != null) dn.expr = (ExprNode) visit(ctx.expression());
        return dn;
    }


    private Type iterateVarType(MxStarParser.VarTypeContext ctx) {
        if (ctx.varType() != null) {
            return new ArrayObjectType(iterateVarType(ctx.varType()),ctx.L_BRACKET().size());
        } else if (ctx.builtinType() != null) {
            if (ctx.builtinType().INT_KW() != null) return TypeConst.Int;
            else if (ctx.builtinType().STRING_KW() != null) return TypeConst.String;
            else return TypeConst.Bool;
        } else return new Type(ctx.Identifier().getText());
    }


    @Override
    public ASTNode visitConstant(MxStarParser.ConstantContext ctx) {
        Type tp;
        if (ctx.DecInteger() != null) {
            tp = TypeConst.Int;
        } else if (ctx.String() != null) {
            tp = TypeConst.String;
        } else if (ctx.TRUE_KW() != null || ctx.FALSE_KW() != null) {
            tp = TypeConst.Bool;
        } else if (ctx.NULL_KW() != null) {
            tp = TypeConst.Null;
        } else {
            var node = new ThisNode();
            node.setPos(ctx);
            return node;

        }
        var node = new LiteralNode(tp, ctx.getText());
        node.setPos(ctx);
        return node;
    }

    /*
     * NOT USED
     *
     * */

    private ASTNode notImplemented() {
        throw new UnimplementedError();
    }

    @Override
    public ASTNode visitExprOrDecl(MxStarParser.ExprOrDeclContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitDeclExpr(MxStarParser.DeclExprContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitArraySemanticError(MxStarParser.ArraySemanticErrorContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitExpressionList(MxStarParser.ExpressionListContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitVarType(MxStarParser.VarTypeContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitReturnType(MxStarParser.ReturnTypeContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitFunctionParamDef(MxStarParser.FunctionParamDefContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitBuiltinType(MxStarParser.BuiltinTypeContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitUnaryOp(MxStarParser.UnaryOpContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitMultiplicativeOp(MxStarParser.MultiplicativeOpContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitAdditiveOp(MxStarParser.AdditiveOpContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitShiftOp(MxStarParser.ShiftOpContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitRelationalCmpOp(MxStarParser.RelationalCmpOpContext ctx) {
        return notImplemented();
    }

    @Override
    public ASTNode visitEqualityCmpOp(MxStarParser.EqualityCmpOpContext ctx) {
        return notImplemented();
    }
}
