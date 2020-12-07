package Parser;// Generated from Grammar/MxStar.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxStarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxStarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxStarParser#code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(MxStarParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDef(MxStarParser.FunctionDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionParamDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParamDef(MxStarParser.FunctionParamDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParam(MxStarParser.FunctionParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxStarParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(MxStarParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#constructorDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDefinition(MxStarParser.ConstructorDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(MxStarParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MxStarParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxStarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MxStarParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MxStarParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MxStarParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(MxStarParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(MxStarParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MxStarParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#simpleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStatement(MxStarParser.SimpleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#declarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationStatement(MxStarParser.DeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#atomExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExp(MxStarParser.AtomExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MxStarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(MxStarParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#indexAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexAccess(MxStarParser.IndexAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#memberAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccess(MxStarParser.MemberAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#exprOrDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprOrDecl(MxStarParser.ExprOrDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclExpr(MxStarParser.DeclExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpression(MxStarParser.NewExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#arrayLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiteral(MxStarParser.ArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(MxStarParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(MxStarParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(MxStarParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#unaryOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOp(MxStarParser.UnaryOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#multiplicativeOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeOp(MxStarParser.MultiplicativeOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#additiveOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveOp(MxStarParser.AdditiveOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#shiftOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftOp(MxStarParser.ShiftOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#relationalCmpOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalCmpOp(MxStarParser.RelationalCmpOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#equalityCmpOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityCmpOp(MxStarParser.EqualityCmpOpContext ctx);
}