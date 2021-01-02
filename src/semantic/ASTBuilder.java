package semantic;

import ast.*;
import compnent.basic.Type;
import exception.semantic.UnUsedVisitorException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import parser.MxStarParser;
import parser.MxStarVisitor;

import java.util.ArrayList;

public class ASTBuilder extends AbstractParseTreeVisitor<ASTNode> implements MxStarVisitor<ASTNode> {

    @Override
    public ASTNode visitCode(MxStarParser.CodeContext ctx) {
        var rn = new RootNode();
        for (var subctx:ctx.children){
            if(subctx instanceof MxStarParser.FunctionDefContext)
                rn.addFuncNode((FunctionNode) visitFunctionDef((MxStarParser.FunctionDefContext) subctx));
            else if(subctx instanceof MxStarParser.ClassDefContext)
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
        fn.returnType = (ctx.returnType().VOID_KW() != null) ? Type.Void : iterateVarType(ctx.returnType().varType());
        fn.suite = (SuiteNode) visitSuite(ctx.suite());
        fn.parameters = iterateFunctionParamDef(ctx.functionParamDef());
        return fn;
    }


    private ArrayList<DeclarationNode> iterateFunctionParamDef(MxStarParser.FunctionParamDefContext ctx) {
        var re = new ArrayList<DeclarationNode>();
        for (int i = 0; i < ctx.Identifier().size(); ++i) {
            re.add(new DeclarationNode(iterateVarType(ctx.varType(i)), ctx.Identifier(i).getText()));
        }
        return re;
    }

    @Override
    public ASTNode visitClassDef(MxStarParser.ClassDefContext ctx) {
        ClassNode cn = new ClassNode(ctx.Identifier().getText());
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
        fn.returnType = Type.Void;
        fn.suite = (SuiteNode) visitSuite(ctx.suite());
        fn.parameters = iterateFunctionParamDef(ctx.functionParamDef());
        return fn;
    }


    @Override
    public ASTNode visitSuite(MxStarParser.SuiteContext ctx) {
        SuiteNode sn = new SuiteNode();
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
        cn.condExpr = (ExprNode) visit(ctx.expression());
        cn.trueStat = (StmtNode) visitStatement(ctx.trueStat);
        if (ctx.falseStat != null) cn.falseStat = (StmtNode) visitStatement(ctx.falseStat);
        return cn;
    }

    @Override
    public ASTNode visitWhileStatement(MxStarParser.WhileStatementContext ctx) {
        LoopNode ln = new LoopNode();
        ln.initExpr = null;
        ln.initDecl=null;
        ln.condExpr = (ExprNode) visit(ctx.expression());
        ln.updateExpr = null;
        ln.loopBody = ctx.statement() == null ? null : (StmtNode) visitStatement(ctx.statement());
        return ln;
    }

    @Override
    public ASTNode visitForStatement(MxStarParser.ForStatementContext ctx) {
        LoopNode ln = new LoopNode();
        if(ctx.initExp!=null){
            if(ctx.initExp.declExpr()!=null){
                ln.initDecl=new DeclarationBlockNode();
                ln.initExpr=null;
                iterateDeclExpr(ctx.initExp.declExpr(),ln.initDecl.decls);
            }else {
                // normal expression
                ln.initDecl=null;
                ln.initExpr= (ExprNode) visit(ctx.initExp.expression());
            }
        }
        ln.condExpr = ctx.condExp == null ? null : (ExprNode) visit(ctx.condExp);
        ln.updateExpr = ctx.updExp == null ? null : (ExprNode) visit(ctx.updExp);
        ln.loopBody = ctx.statement() == null ? null : (StmtNode) visitStatement(ctx.statement());
        return ln;
    }

    @Override
    public ASTNode visitContinueStatement(MxStarParser.ContinueStatementContext ctx) {
        return new ContinueNode();
    }

    @Override
    public ASTNode visitBreakStatement(MxStarParser.BreakStatementContext ctx) {
        return new BreakNode();
    }

    @Override
    public ASTNode visitReturnStatement(MxStarParser.ReturnStatementContext ctx) {
        ReturnNode rn = new ReturnNode();
        rn.returnExpr = (ctx.expression() != null) ? (ExprNode) visit(ctx.expression()) : null;
        return rn;
    }

    @Override
    public ASTNode visitSimpleStatement(MxStarParser.SimpleStatementContext ctx) {
        return ctx.expression() == null ? null : new ExprStmtNode((ExprNode) visit(ctx.expression()));
    }

    @Override
    public ASTNode visitDeclarationStatement(MxStarParser.DeclarationStatementContext ctx) {
        var n = new DeclarationBlockNode();
        iterateDeclExpr(ctx.declExpr(), n.decls);
        return n;
    }

    @Override
    public ASTNode visitAtomExp(MxStarParser.AtomExpContext ctx) {
        if (ctx.Identifier() != null) {
            return new IdentifierNode(ctx.Identifier().getText());
        } else if (ctx.constant() != null) {
            return visitConstant(ctx.constant());
        } else {
            return visit(ctx.expression());
        }
    }

    @Override
    public ASTNode visitNewExpr(MxStarParser.NewExprContext ctx) {
        var nn = new NewExprNode(ctx.arrayLiteral() == null);
        if (nn.isFuncCall) {
            nn.classNew = ctx.Identifier() != null ?
                    new FuncCallNode(ctx.Identifier().getText()) :
                    (FuncCallNode) visitFuncCall(ctx.funcCall());
        } else {
            nn.arrNew = (ArrayLiteralNode) visitArrayLiteral(ctx.arrayLiteral());
        }
        return nn;
    }

    @Override
    public ASTNode visitIndexExpr(MxStarParser.IndexExprContext ctx) {
        return new BinaryExprNode("[", (ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)));
    }

    @Override
    public ASTNode visitPrefixExpr(MxStarParser.PrefixExprContext ctx) {
        return new UnaryExprNode(true, ctx.prefix.getText(), (ExprNode) visit(ctx.expression()));
    }

    @Override
    public ASTNode visitFuncExpr(MxStarParser.FuncExprContext ctx) {
        return visitFuncCall(ctx.funcCall());
    }

    @Override
    public ASTNode visitMemberExpr(MxStarParser.MemberExprContext ctx) {
        return new BinaryExprNode(".", (ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)));
    }

    @Override
    public ASTNode visitSuffixExpr(MxStarParser.SuffixExprContext ctx) {
        return new UnaryExprNode(false, ctx.suffix.getText(), (ExprNode) visit(ctx.expression()));
    }

    @Override
    public ASTNode visitAtomExpr(MxStarParser.AtomExprContext ctx) {
        return visitAtomExp(ctx.atomExp());
    }

    @Override
    public ASTNode visitBinaryExpr(MxStarParser.BinaryExprContext ctx) {
        String sign = null;
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
        return new BinaryExprNode(sign, (ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)));

    }

    @Override
    public ASTNode visitAssignExpr(MxStarParser.AssignExprContext ctx) {
        return new BinaryExprNode("=", (ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)));
    }

    @Override
    public ASTNode visitFuncCall(MxStarParser.FuncCallContext ctx) {
        var fcn = new FuncCallNode(ctx.Identifier().getText());
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
            if (ref.STRING_KW() != null) type = Type.String;
            else if (ref.INT_KW() != null) type = Type.Int;
            else type = Type.Bool;
        } else type = Type.Class;
        var aln = new ArrayLiteralNode(type, ctx.L_BRACKET().size());
        if (ctx.expression() != null)
            for (var exp : ctx.expression())
                aln.dimArr.add((ExprNode) visit(exp));
        return aln;
    }

    @Override
    public ASTNode visitVarDeclaration(MxStarParser.VarDeclarationContext ctx) {
        var dn = new DeclarationNode(null, ctx.Identifier().getText());
        if (ctx.expression() != null) dn.expr = (ExprNode) visit(ctx.expression());
        return dn;
    }


    private Type iterateVarType(MxStarParser.VarTypeContext ctx) {
        if (ctx.varType() != null) {
            Type tp = iterateVarType(ctx.varType());
            tp.dimension = ctx.L_BRACKET().size();
            return tp;
        } else if (ctx.builtinType() != null) {
            if (ctx.builtinType().INT_KW() != null) return Type.Int;
            else if (ctx.builtinType().STRING_KW() != null) return Type.String;
            else return Type.Bool;
        } else return new Type(ctx.Identifier().getText());
    }


    @Override
    public ASTNode visitConstant(MxStarParser.ConstantContext ctx) {
        if (ctx.DecInteger() != null) {
            return new LiteralNode(Type.Int, ctx.getText());
        } else if (ctx.String() != null) {
            return new LiteralNode(Type.String, ctx.getText());
        } else if (ctx.TRUE_KW() != null || ctx.FALSE_KW() != null) {
            return new LiteralNode(Type.Bool, ctx.getText());
        } else {
            return new ThisNode();
        }
    }

    /*
     * NOT USED
     *
     * */

    @Override
    public ASTNode visitExprOrDecl(MxStarParser.ExprOrDeclContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitDeclExpr(MxStarParser.DeclExprContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitExpressionList(MxStarParser.ExpressionListContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitVarType(MxStarParser.VarTypeContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitReturnType(MxStarParser.ReturnTypeContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitFunctionParamDef(MxStarParser.FunctionParamDefContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitBuiltinType(MxStarParser.BuiltinTypeContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitUnaryOp(MxStarParser.UnaryOpContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitMultiplicativeOp(MxStarParser.MultiplicativeOpContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitAdditiveOp(MxStarParser.AdditiveOpContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitShiftOp(MxStarParser.ShiftOpContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitRelationalCmpOp(MxStarParser.RelationalCmpOpContext ctx) {
        throw new UnUsedVisitorException();
    }

    @Override
    public ASTNode visitEqualityCmpOp(MxStarParser.EqualityCmpOpContext ctx) {
        throw new UnUsedVisitorException();
    }
}
