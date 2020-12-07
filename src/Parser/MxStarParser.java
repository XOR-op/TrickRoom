package Parser;// Generated from Grammar/MxStar.g4 by ANTLR 4.9
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxStarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Constant=1, DecInteger=2, String=3, LineComment=4, BlockComment=5, WhiteSpace=6, 
		PLUS=7, MINUS=8, MUL=9, DIV=10, MOD=11, GREATER=12, GREATER_EQ=13, LESS=14, 
		LESS_EQ=15, NOT_EQ=16, EQUAL=17, AND_LOGIC=18, OR_LOGIC=19, NOT_LOGIC=20, 
		RIGHT_SHIFT=21, LEFT_SHIFT=22, AND_ARI=23, OR_ARI=24, XOR_ARI=25, NOT_ARI=26, 
		ASSIGNMENT=27, SELF_PLUS=28, SELF_MINUS=29, DOT=30, L_PARENTNESS=31, R_PARENTNESS=32, 
		L_BRACKET=33, R_BRACKET=34, L_BRACE=35, R_BRACE=36, SPACE=37, COMMA=38, 
		SEMICOLON=39, LINE_BREAK=40, TAB=41, INT_KW=42, BOOL_KW=43, STRING_KW=44, 
		NULL_KW=45, VOID_KW=46, TRUE_KW=47, FALSE_KW=48, IF_KW=49, ELSE_KW=50, 
		FOR_KW=51, WHILE_KW=52, BREAK_KW=53, CONTINUE_KW=54, RETURN_KW=55, NEW_KW=56, 
		CLASS_KW=57, THIS_KW=58, ESCAPE_DB_QUOTATION=59, ESCAPE_BACKSLASH=60, 
		Identifier=61;
	public static final int
		RULE_code = 0, RULE_functionDef = 1, RULE_functionParamDef = 2, RULE_functionParam = 3, 
		RULE_classDef = 4, RULE_memberDeclaration = 5, RULE_constructorDefinition = 6, 
		RULE_expressionList = 7, RULE_suite = 8, RULE_statement = 9, RULE_ifStatement = 10, 
		RULE_whileStatement = 11, RULE_forStatement = 12, RULE_continueStatement = 13, 
		RULE_breakStatement = 14, RULE_returnStatement = 15, RULE_simpleStatement = 16, 
		RULE_declarationStatement = 17, RULE_atomExp = 18, RULE_expression = 19, 
		RULE_funcCall = 20, RULE_indexAccess = 21, RULE_memberAccess = 22, RULE_exprOrDecl = 23, 
		RULE_declExpr = 24, RULE_newExpression = 25, RULE_arrayLiteral = 26, RULE_varDeclaration = 27, 
		RULE_varType = 28, RULE_returnType = 29, RULE_unaryOp = 30, RULE_multiplicativeOp = 31, 
		RULE_additiveOp = 32, RULE_shiftOp = 33, RULE_relationalCmpOp = 34, RULE_equalityCmpOp = 35;
	private static String[] makeRuleNames() {
		return new String[] {
			"code", "functionDef", "functionParamDef", "functionParam", "classDef", 
			"memberDeclaration", "constructorDefinition", "expressionList", "suite", 
			"statement", "ifStatement", "whileStatement", "forStatement", "continueStatement", 
			"breakStatement", "returnStatement", "simpleStatement", "declarationStatement", 
			"atomExp", "expression", "funcCall", "indexAccess", "memberAccess", "exprOrDecl", 
			"declExpr", "newExpression", "arrayLiteral", "varDeclaration", "varType", 
			"returnType", "unaryOp", "multiplicativeOp", "additiveOp", "shiftOp", 
			"relationalCmpOp", "equalityCmpOp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, "'+'", "'-'", "'*'", "'/'", 
			"'%'", "'>'", "'>='", "'<'", "'<='", "'!='", "'=='", "'&&'", "'||'", 
			"'!'", "'>>'", "'<<'", "'&'", "'|'", "'^'", "'~'", "'='", "'++'", "'--'", 
			"'.'", "'('", "')'", "'['", "']'", "'{'", "'}'", "' '", "','", "';'", 
			"'\n'", "'\t'", "'int'", "'bool'", "'string'", "'null'", "'void'", "'true'", 
			"'false'", "'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", 
			"'return'", "'new'", "'class'", "'this'", "'\\\"'", "'\\\\'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Constant", "DecInteger", "String", "LineComment", "BlockComment", 
			"WhiteSpace", "PLUS", "MINUS", "MUL", "DIV", "MOD", "GREATER", "GREATER_EQ", 
			"LESS", "LESS_EQ", "NOT_EQ", "EQUAL", "AND_LOGIC", "OR_LOGIC", "NOT_LOGIC", 
			"RIGHT_SHIFT", "LEFT_SHIFT", "AND_ARI", "OR_ARI", "XOR_ARI", "NOT_ARI", 
			"ASSIGNMENT", "SELF_PLUS", "SELF_MINUS", "DOT", "L_PARENTNESS", "R_PARENTNESS", 
			"L_BRACKET", "R_BRACKET", "L_BRACE", "R_BRACE", "SPACE", "COMMA", "SEMICOLON", 
			"LINE_BREAK", "TAB", "INT_KW", "BOOL_KW", "STRING_KW", "NULL_KW", "VOID_KW", 
			"TRUE_KW", "FALSE_KW", "IF_KW", "ELSE_KW", "FOR_KW", "WHILE_KW", "BREAK_KW", 
			"CONTINUE_KW", "RETURN_KW", "NEW_KW", "CLASS_KW", "THIS_KW", "ESCAPE_DB_QUOTATION", 
			"ESCAPE_BACKSLASH", "Identifier"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MxStar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxStarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CodeContext extends ParserRuleContext {
		public List<ClassDefContext> classDef() {
			return getRuleContexts(ClassDefContext.class);
		}
		public ClassDefContext classDef(int i) {
			return getRuleContext(ClassDefContext.class,i);
		}
		public List<DeclarationStatementContext> declarationStatement() {
			return getRuleContexts(DeclarationStatementContext.class);
		}
		public DeclarationStatementContext declarationStatement(int i) {
			return getRuleContext(DeclarationStatementContext.class,i);
		}
		public List<FunctionDefContext> functionDef() {
			return getRuleContexts(FunctionDefContext.class);
		}
		public FunctionDefContext functionDef(int i) {
			return getRuleContext(FunctionDefContext.class,i);
		}
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_code);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << VOID_KW) | (1L << CLASS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(75);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(72);
					classDef();
					}
					break;
				case 2:
					{
					setState(73);
					declarationStatement();
					}
					break;
				case 3:
					{
					setState(74);
					functionDef();
					}
					break;
				}
				}
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefContext extends ParserRuleContext {
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public FunctionParamDefContext functionParamDef() {
			return getRuleContext(FunctionParamDefContext.class,0);
		}
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public FunctionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFunctionDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefContext functionDef() throws RecognitionException {
		FunctionDefContext _localctx = new FunctionDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_functionDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			returnType();
			setState(81);
			match(Identifier);
			setState(82);
			match(L_PARENTNESS);
			setState(83);
			functionParamDef();
			setState(84);
			match(R_PARENTNESS);
			setState(85);
			suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParamDefContext extends ParserRuleContext {
		public List<VarTypeContext> varType() {
			return getRuleContexts(VarTypeContext.class);
		}
		public VarTypeContext varType(int i) {
			return getRuleContext(VarTypeContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(MxStarParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxStarParser.Identifier, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxStarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxStarParser.COMMA, i);
		}
		public FunctionParamDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParamDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFunctionParamDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParamDefContext functionParamDef() throws RecognitionException {
		FunctionParamDefContext _localctx = new FunctionParamDefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_functionParamDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << Identifier))) != 0)) {
				{
				setState(87);
				varType(0);
				setState(88);
				match(Identifier);
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(89);
					match(COMMA);
					setState(90);
					varType(0);
					setState(91);
					match(Identifier);
					}
					}
					setState(97);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParamContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(MxStarParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxStarParser.Identifier, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxStarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxStarParser.COMMA, i);
		}
		public FunctionParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParam; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFunctionParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParamContext functionParam() throws RecognitionException {
		FunctionParamContext _localctx = new FunctionParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functionParam);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(100);
				match(Identifier);
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(101);
					match(COMMA);
					setState(102);
					match(Identifier);
					}
					}
					setState(107);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDefContext extends ParserRuleContext {
		public TerminalNode CLASS_KW() { return getToken(MxStarParser.CLASS_KW, 0); }
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode L_BRACE() { return getToken(MxStarParser.L_BRACE, 0); }
		public TerminalNode R_BRACE() { return getToken(MxStarParser.R_BRACE, 0); }
		public TerminalNode SEMICOLON() { return getToken(MxStarParser.SEMICOLON, 0); }
		public List<MemberDeclarationContext> memberDeclaration() {
			return getRuleContexts(MemberDeclarationContext.class);
		}
		public MemberDeclarationContext memberDeclaration(int i) {
			return getRuleContext(MemberDeclarationContext.class,i);
		}
		public List<FunctionDefContext> functionDef() {
			return getRuleContexts(FunctionDefContext.class);
		}
		public FunctionDefContext functionDef(int i) {
			return getRuleContext(FunctionDefContext.class,i);
		}
		public List<ConstructorDefinitionContext> constructorDefinition() {
			return getRuleContexts(ConstructorDefinitionContext.class);
		}
		public ConstructorDefinitionContext constructorDefinition(int i) {
			return getRuleContext(ConstructorDefinitionContext.class,i);
		}
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(CLASS_KW);
			setState(111);
			match(Identifier);
			setState(112);
			match(L_BRACE);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << VOID_KW) | (1L << Identifier))) != 0)) {
				{
				setState(116);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(113);
					memberDeclaration();
					}
					break;
				case 2:
					{
					setState(114);
					functionDef();
					}
					break;
				case 3:
					{
					setState(115);
					constructorDefinition();
					}
					break;
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
			match(R_BRACE);
			setState(122);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclarationContext extends ParserRuleContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<TerminalNode> Identifier() { return getTokens(MxStarParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxStarParser.Identifier, i);
		}
		public TerminalNode SEMICOLON() { return getToken(MxStarParser.SEMICOLON, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MxStarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxStarParser.COMMA, i);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_memberDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			varType(0);
			setState(125);
			match(Identifier);
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(126);
				match(COMMA);
				setState(127);
				match(Identifier);
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(133);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDefinitionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public FunctionParamContext functionParam() {
			return getRuleContext(FunctionParamContext.class,0);
		}
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public ConstructorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitConstructorDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDefinitionContext constructorDefinition() throws RecognitionException {
		ConstructorDefinitionContext _localctx = new ConstructorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constructorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(Identifier);
			setState(136);
			match(L_PARENTNESS);
			setState(137);
			functionParam();
			setState(138);
			match(R_PARENTNESS);
			setState(139);
			suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxStarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxStarParser.COMMA, i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			expression(0);
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(142);
				match(COMMA);
				setState(143);
				expression(0);
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SuiteContext extends ParserRuleContext {
		public TerminalNode L_BRACE() { return getToken(MxStarParser.L_BRACE, 0); }
		public TerminalNode R_BRACE() { return getToken(MxStarParser.R_BRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suite; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteContext suite() throws RecognitionException {
		SuiteContext _localctx = new SuiteContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_suite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(L_BRACE);
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << L_BRACE) | (1L << SEMICOLON) | (1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << IF_KW) | (1L << FOR_KW) | (1L << WHILE_KW) | (1L << BREAK_KW) | (1L << CONTINUE_KW) | (1L << RETURN_KW) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
				{
				{
				setState(150);
				statement();
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(156);
			match(R_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public DeclarationStatementContext declarationStatement() {
			return getRuleContext(DeclarationStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_statement);
		try {
			setState(167);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(158);
				suite();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				declarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(160);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(161);
				whileStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(162);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(163);
				continueStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(164);
				breakStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(165);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(166);
				simpleStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public StatementContext trueStat;
		public StatementContext falseStat;
		public TerminalNode IF_KW() { return getToken(MxStarParser.IF_KW, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE_KW() { return getToken(MxStarParser.ELSE_KW, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			match(IF_KW);
			setState(170);
			match(L_PARENTNESS);
			setState(171);
			expression(0);
			setState(172);
			match(R_PARENTNESS);
			setState(173);
			((IfStatementContext)_localctx).trueStat = statement();
			setState(176);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(174);
				match(ELSE_KW);
				setState(175);
				((IfStatementContext)_localctx).falseStat = statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE_KW() { return getToken(MxStarParser.WHILE_KW, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(WHILE_KW);
			setState(179);
			match(L_PARENTNESS);
			setState(180);
			expression(0);
			setState(181);
			match(R_PARENTNESS);
			setState(182);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public ExprOrDeclContext initExp;
		public ExpressionContext condExp;
		public ExpressionContext updExp;
		public TerminalNode FOR_KW() { return getToken(MxStarParser.FOR_KW, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(MxStarParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(MxStarParser.SEMICOLON, i);
		}
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ExprOrDeclContext exprOrDecl() {
			return getRuleContext(ExprOrDeclContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			match(FOR_KW);
			setState(185);
			match(L_PARENTNESS);
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
				{
				setState(186);
				((ForStatementContext)_localctx).initExp = exprOrDecl();
				}
			}

			setState(189);
			match(SEMICOLON);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
				{
				setState(190);
				((ForStatementContext)_localctx).condExp = expression(0);
				}
			}

			setState(193);
			match(SEMICOLON);
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
				{
				setState(194);
				((ForStatementContext)_localctx).updExp = expression(0);
				}
			}

			setState(197);
			match(R_PARENTNESS);
			setState(198);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE_KW() { return getToken(MxStarParser.CONTINUE_KW, 0); }
		public TerminalNode SEMICOLON() { return getToken(MxStarParser.SEMICOLON, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(CONTINUE_KW);
			setState(201);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK_KW() { return getToken(MxStarParser.BREAK_KW, 0); }
		public TerminalNode SEMICOLON() { return getToken(MxStarParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(BREAK_KW);
			setState(204);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN_KW() { return getToken(MxStarParser.RETURN_KW, 0); }
		public TerminalNode SEMICOLON() { return getToken(MxStarParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(RETURN_KW);
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
				{
				setState(207);
				expression(0);
				}
			}

			setState(210);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleStatementContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(MxStarParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitSimpleStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_simpleStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
				{
				setState(212);
				expression(0);
				}
			}

			setState(215);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationStatementContext extends ParserRuleContext {
		public DeclExprContext declExpr() {
			return getRuleContext(DeclExprContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(MxStarParser.SEMICOLON, 0); }
		public DeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitDeclarationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationStatementContext declarationStatement() throws RecognitionException {
		DeclarationStatementContext _localctx = new DeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_declarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			declExpr();
			setState(218);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomExpContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode Constant() { return getToken(MxStarParser.Constant, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public AtomExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAtomExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomExpContext atomExp() throws RecognitionException {
		AtomExpContext _localctx = new AtomExpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_atomExp);
		try {
			setState(226);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(220);
				match(Identifier);
				}
				break;
			case Constant:
				enterOuterAlt(_localctx, 2);
				{
				setState(221);
				match(Constant);
				}
				break;
			case L_PARENTNESS:
				enterOuterAlt(_localctx, 3);
				{
				setState(222);
				match(L_PARENTNESS);
				setState(223);
				expression(0);
				setState(224);
				match(R_PARENTNESS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public UnaryOpContext prefix;
		public Token suffix;
		public AtomExpContext atomExp() {
			return getRuleContext(AtomExpContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public UnaryOpContext unaryOp() {
			return getRuleContext(UnaryOpContext.class,0);
		}
		public MultiplicativeOpContext multiplicativeOp() {
			return getRuleContext(MultiplicativeOpContext.class,0);
		}
		public AdditiveOpContext additiveOp() {
			return getRuleContext(AdditiveOpContext.class,0);
		}
		public RelationalCmpOpContext relationalCmpOp() {
			return getRuleContext(RelationalCmpOpContext.class,0);
		}
		public EqualityCmpOpContext equalityCmpOp() {
			return getRuleContext(EqualityCmpOpContext.class,0);
		}
		public ShiftOpContext shiftOp() {
			return getRuleContext(ShiftOpContext.class,0);
		}
		public TerminalNode AND_ARI() { return getToken(MxStarParser.AND_ARI, 0); }
		public TerminalNode XOR_ARI() { return getToken(MxStarParser.XOR_ARI, 0); }
		public TerminalNode OR_ARI() { return getToken(MxStarParser.OR_ARI, 0); }
		public TerminalNode AND_LOGIC() { return getToken(MxStarParser.AND_LOGIC, 0); }
		public TerminalNode OR_LOGIC() { return getToken(MxStarParser.OR_LOGIC, 0); }
		public TerminalNode ASSIGNMENT() { return getToken(MxStarParser.ASSIGNMENT, 0); }
		public IndexAccessContext indexAccess() {
			return getRuleContext(IndexAccessContext.class,0);
		}
		public MemberAccessContext memberAccess() {
			return getRuleContext(MemberAccessContext.class,0);
		}
		public TerminalNode SELF_PLUS() { return getToken(MxStarParser.SELF_PLUS, 0); }
		public TerminalNode SELF_MINUS() { return getToken(MxStarParser.SELF_MINUS, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(229);
				atomExp();
				}
				break;
			case 2:
				{
				setState(230);
				funcCall();
				}
				break;
			case 3:
				{
				setState(231);
				newExpression();
				}
				break;
			case 4:
				{
				setState(232);
				((ExpressionContext)_localctx).prefix = unaryOp();
				setState(233);
				expression(12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(283);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(281);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(237);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(238);
						multiplicativeOp();
						setState(239);
						expression(12);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(241);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(242);
						additiveOp();
						setState(243);
						expression(11);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(245);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(246);
						relationalCmpOp();
						setState(247);
						expression(10);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(249);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(250);
						equalityCmpOp();
						setState(251);
						expression(9);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(253);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(254);
						shiftOp();
						setState(255);
						expression(8);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(257);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(258);
						match(AND_ARI);
						setState(259);
						expression(7);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(260);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(261);
						match(XOR_ARI);
						setState(262);
						expression(6);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(263);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(264);
						match(OR_ARI);
						setState(265);
						expression(5);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(266);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(267);
						match(AND_LOGIC);
						setState(268);
						expression(4);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(269);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(270);
						match(OR_LOGIC);
						setState(271);
						expression(3);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(272);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(273);
						match(ASSIGNMENT);
						setState(274);
						expression(1);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(275);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(276);
						indexAccess();
						}
						break;
					case 13:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(277);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(278);
						memberAccess();
						}
						break;
					case 14:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(279);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(280);
						((ExpressionContext)_localctx).suffix = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==SELF_PLUS || _la==SELF_MINUS) ) {
							((ExpressionContext)_localctx).suffix = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(285);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FuncCallContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			match(Identifier);
			setState(287);
			match(L_PARENTNESS);
			setState(289);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
				{
				setState(288);
				expressionList();
				}
			}

			setState(291);
			match(R_PARENTNESS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexAccessContext extends ParserRuleContext {
		public TerminalNode L_BRACKET() { return getToken(MxStarParser.L_BRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode R_BRACKET() { return getToken(MxStarParser.R_BRACKET, 0); }
		public IndexAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexAccess; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitIndexAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexAccessContext indexAccess() throws RecognitionException {
		IndexAccessContext _localctx = new IndexAccessContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_indexAccess);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			match(L_BRACKET);
			setState(294);
			expression(0);
			setState(295);
			match(R_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberAccessContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(MxStarParser.DOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MemberAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberAccess; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitMemberAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberAccessContext memberAccess() throws RecognitionException {
		MemberAccessContext _localctx = new MemberAccessContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_memberAccess);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			match(DOT);
			setState(298);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprOrDeclContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclExprContext declExpr() {
			return getRuleContext(DeclExprContext.class,0);
		}
		public ExprOrDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprOrDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprOrDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprOrDeclContext exprOrDecl() throws RecognitionException {
		ExprOrDeclContext _localctx = new ExprOrDeclContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_exprOrDecl);
		try {
			setState(302);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				declExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclExprContext extends ParserRuleContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxStarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxStarParser.COMMA, i);
		}
		public DeclExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitDeclExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclExprContext declExpr() throws RecognitionException {
		DeclExprContext _localctx = new DeclExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_declExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			varType(0);
			setState(305);
			varDeclaration();
			setState(310);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(306);
				match(COMMA);
				setState(307);
				varDeclaration();
				}
				}
				setState(312);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewExpressionContext extends ParserRuleContext {
		public TerminalNode NEW_KW() { return getToken(MxStarParser.NEW_KW, 0); }
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitNewExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_newExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(NEW_KW);
			setState(317);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(314);
				arrayLiteral();
				}
				break;
			case 2:
				{
				setState(315);
				funcCall();
				}
				break;
			case 3:
				{
				setState(316);
				match(Identifier);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayLiteralContext extends ParserRuleContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<TerminalNode> L_BRACKET() { return getTokens(MxStarParser.L_BRACKET); }
		public TerminalNode L_BRACKET(int i) {
			return getToken(MxStarParser.L_BRACKET, i);
		}
		public List<TerminalNode> R_BRACKET() { return getTokens(MxStarParser.R_BRACKET); }
		public TerminalNode R_BRACKET(int i) {
			return getToken(MxStarParser.R_BRACKET, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitArrayLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiteralContext arrayLiteral() throws RecognitionException {
		ArrayLiteralContext _localctx = new ArrayLiteralContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_arrayLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			varType(0);
			setState(325); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(320);
					match(L_BRACKET);
					setState(322);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Constant) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NEW_KW) | (1L << Identifier))) != 0)) {
						{
						setState(321);
						expression(0);
						}
					}

					setState(324);
					match(R_BRACKET);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(327); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode ASSIGNMENT() { return getToken(MxStarParser.ASSIGNMENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_varDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			match(Identifier);
			setState(332);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGNMENT) {
				{
				setState(330);
				match(ASSIGNMENT);
				setState(331);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarTypeContext extends ParserRuleContext {
		public TerminalNode INT_KW() { return getToken(MxStarParser.INT_KW, 0); }
		public TerminalNode STRING_KW() { return getToken(MxStarParser.STRING_KW, 0); }
		public TerminalNode BOOL_KW() { return getToken(MxStarParser.BOOL_KW, 0); }
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public TerminalNode L_BRACKET() { return getToken(MxStarParser.L_BRACKET, 0); }
		public TerminalNode R_BRACKET() { return getToken(MxStarParser.R_BRACKET, 0); }
		public VarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarTypeContext varType() throws RecognitionException {
		return varType(0);
	}

	private VarTypeContext varType(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		VarTypeContext _localctx = new VarTypeContext(_ctx, _parentState);
		VarTypeContext _prevctx = _localctx;
		int _startState = 56;
		enterRecursionRule(_localctx, 56, RULE_varType, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KW:
				{
				setState(335);
				match(INT_KW);
				}
				break;
			case STRING_KW:
				{
				setState(336);
				match(STRING_KW);
				}
				break;
			case BOOL_KW:
				{
				setState(337);
				match(BOOL_KW);
				}
				break;
			case Identifier:
				{
				setState(338);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(346);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VarTypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_varType);
					setState(341);
					if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
					setState(342);
					match(L_BRACKET);
					setState(343);
					match(R_BRACKET);
					}
					} 
				}
				setState(348);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ReturnTypeContext extends ParserRuleContext {
		public TerminalNode VOID_KW() { return getToken(MxStarParser.VOID_KW, 0); }
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitReturnType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_returnType);
		try {
			setState(351);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID_KW:
				enterOuterAlt(_localctx, 1);
				{
				setState(349);
				match(VOID_KW);
				}
				break;
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(350);
				varType(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOpContext extends ParserRuleContext {
		public TerminalNode SELF_PLUS() { return getToken(MxStarParser.SELF_PLUS, 0); }
		public TerminalNode SELF_MINUS() { return getToken(MxStarParser.SELF_MINUS, 0); }
		public TerminalNode PLUS() { return getToken(MxStarParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MxStarParser.MINUS, 0); }
		public TerminalNode NOT_LOGIC() { return getToken(MxStarParser.NOT_LOGIC, 0); }
		public TerminalNode NOT_ARI() { return getToken(MxStarParser.NOT_ARI, 0); }
		public UnaryOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitUnaryOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOpContext unaryOp() throws RecognitionException {
		UnaryOpContext _localctx = new UnaryOpContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_unaryOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeOpContext extends ParserRuleContext {
		public TerminalNode MUL() { return getToken(MxStarParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(MxStarParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(MxStarParser.MOD, 0); }
		public MultiplicativeOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitMultiplicativeOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeOpContext multiplicativeOp() throws RecognitionException {
		MultiplicativeOpContext _localctx = new MultiplicativeOpContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_multiplicativeOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveOpContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(MxStarParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MxStarParser.MINUS, 0); }
		public AdditiveOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAdditiveOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveOpContext additiveOp() throws RecognitionException {
		AdditiveOpContext _localctx = new AdditiveOpContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_additiveOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShiftOpContext extends ParserRuleContext {
		public TerminalNode LEFT_SHIFT() { return getToken(MxStarParser.LEFT_SHIFT, 0); }
		public TerminalNode RIGHT_SHIFT() { return getToken(MxStarParser.RIGHT_SHIFT, 0); }
		public ShiftOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitShiftOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftOpContext shiftOp() throws RecognitionException {
		ShiftOpContext _localctx = new ShiftOpContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_shiftOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
			_la = _input.LA(1);
			if ( !(_la==RIGHT_SHIFT || _la==LEFT_SHIFT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalCmpOpContext extends ParserRuleContext {
		public TerminalNode LESS() { return getToken(MxStarParser.LESS, 0); }
		public TerminalNode LESS_EQ() { return getToken(MxStarParser.LESS_EQ, 0); }
		public TerminalNode GREATER() { return getToken(MxStarParser.GREATER, 0); }
		public TerminalNode GREATER_EQ() { return getToken(MxStarParser.GREATER_EQ, 0); }
		public RelationalCmpOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalCmpOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitRelationalCmpOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalCmpOpContext relationalCmpOp() throws RecognitionException {
		RelationalCmpOpContext _localctx = new RelationalCmpOpContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_relationalCmpOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GREATER) | (1L << GREATER_EQ) | (1L << LESS) | (1L << LESS_EQ))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityCmpOpContext extends ParserRuleContext {
		public TerminalNode EQUAL() { return getToken(MxStarParser.EQUAL, 0); }
		public TerminalNode NOT_EQ() { return getToken(MxStarParser.NOT_EQ, 0); }
		public EqualityCmpOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityCmpOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitEqualityCmpOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityCmpOpContext equalityCmpOp() throws RecognitionException {
		EqualityCmpOpContext _localctx = new EqualityCmpOpContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_equalityCmpOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			_la = _input.LA(1);
			if ( !(_la==NOT_EQ || _la==EQUAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 28:
			return varType_sempred((VarTypeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		case 11:
			return precpred(_ctx, 16);
		case 12:
			return precpred(_ctx, 15);
		case 13:
			return precpred(_ctx, 13);
		}
		return true;
	}
	private boolean varType_sempred(VarTypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3?\u0170\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\3\2\3\2\7\2N\n\2\f\2\16\2Q\13\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4`\n\4\f\4\16\4c\13"+
		"\4\5\4e\n\4\3\5\3\5\3\5\7\5j\n\5\f\5\16\5m\13\5\5\5o\n\5\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\7\6w\n\6\f\6\16\6z\13\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u0083"+
		"\n\7\f\7\16\7\u0086\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\7"+
		"\t\u0093\n\t\f\t\16\t\u0096\13\t\3\n\3\n\7\n\u009a\n\n\f\n\16\n\u009d"+
		"\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00aa"+
		"\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00b3\n\f\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\5\16\u00be\n\16\3\16\3\16\5\16\u00c2\n\16\3\16\3\16"+
		"\5\16\u00c6\n\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21"+
		"\5\21\u00d3\n\21\3\21\3\21\3\22\5\22\u00d8\n\22\3\22\3\22\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00e5\n\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u00ee\n\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\7\25\u011c\n\25\f\25\16\25\u011f"+
		"\13\25\3\26\3\26\3\26\5\26\u0124\n\26\3\26\3\26\3\27\3\27\3\27\3\27\3"+
		"\30\3\30\3\30\3\31\3\31\5\31\u0131\n\31\3\32\3\32\3\32\3\32\7\32\u0137"+
		"\n\32\f\32\16\32\u013a\13\32\3\33\3\33\3\33\3\33\5\33\u0140\n\33\3\34"+
		"\3\34\3\34\5\34\u0145\n\34\3\34\6\34\u0148\n\34\r\34\16\34\u0149\3\35"+
		"\3\35\3\35\5\35\u014f\n\35\3\36\3\36\3\36\3\36\3\36\5\36\u0156\n\36\3"+
		"\36\3\36\3\36\7\36\u015b\n\36\f\36\16\36\u015e\13\36\3\37\3\37\5\37\u0162"+
		"\n\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3%\2\4(:&\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH\2\t\3\2\36\37\6\2"+
		"\t\n\26\26\34\34\36\37\3\2\13\r\3\2\t\n\3\2\27\30\3\2\16\21\3\2\22\23"+
		"\2\u0186\2O\3\2\2\2\4R\3\2\2\2\6d\3\2\2\2\bn\3\2\2\2\np\3\2\2\2\f~\3\2"+
		"\2\2\16\u0089\3\2\2\2\20\u008f\3\2\2\2\22\u0097\3\2\2\2\24\u00a9\3\2\2"+
		"\2\26\u00ab\3\2\2\2\30\u00b4\3\2\2\2\32\u00ba\3\2\2\2\34\u00ca\3\2\2\2"+
		"\36\u00cd\3\2\2\2 \u00d0\3\2\2\2\"\u00d7\3\2\2\2$\u00db\3\2\2\2&\u00e4"+
		"\3\2\2\2(\u00ed\3\2\2\2*\u0120\3\2\2\2,\u0127\3\2\2\2.\u012b\3\2\2\2\60"+
		"\u0130\3\2\2\2\62\u0132\3\2\2\2\64\u013b\3\2\2\2\66\u0141\3\2\2\28\u014b"+
		"\3\2\2\2:\u0155\3\2\2\2<\u0161\3\2\2\2>\u0163\3\2\2\2@\u0165\3\2\2\2B"+
		"\u0167\3\2\2\2D\u0169\3\2\2\2F\u016b\3\2\2\2H\u016d\3\2\2\2JN\5\n\6\2"+
		"KN\5$\23\2LN\5\4\3\2MJ\3\2\2\2MK\3\2\2\2ML\3\2\2\2NQ\3\2\2\2OM\3\2\2\2"+
		"OP\3\2\2\2P\3\3\2\2\2QO\3\2\2\2RS\5<\37\2ST\7?\2\2TU\7!\2\2UV\5\6\4\2"+
		"VW\7\"\2\2WX\5\22\n\2X\5\3\2\2\2YZ\5:\36\2Za\7?\2\2[\\\7(\2\2\\]\5:\36"+
		"\2]^\7?\2\2^`\3\2\2\2_[\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2be\3\2\2"+
		"\2ca\3\2\2\2dY\3\2\2\2de\3\2\2\2e\7\3\2\2\2fk\7?\2\2gh\7(\2\2hj\7?\2\2"+
		"ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2lo\3\2\2\2mk\3\2\2\2nf\3\2\2\2"+
		"no\3\2\2\2o\t\3\2\2\2pq\7;\2\2qr\7?\2\2rx\7%\2\2sw\5\f\7\2tw\5\4\3\2u"+
		"w\5\16\b\2vs\3\2\2\2vt\3\2\2\2vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2"+
		"y{\3\2\2\2zx\3\2\2\2{|\7&\2\2|}\7)\2\2}\13\3\2\2\2~\177\5:\36\2\177\u0084"+
		"\7?\2\2\u0080\u0081\7(\2\2\u0081\u0083\7?\2\2\u0082\u0080\3\2\2\2\u0083"+
		"\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0087\3\2"+
		"\2\2\u0086\u0084\3\2\2\2\u0087\u0088\7)\2\2\u0088\r\3\2\2\2\u0089\u008a"+
		"\7?\2\2\u008a\u008b\7!\2\2\u008b\u008c\5\b\5\2\u008c\u008d\7\"\2\2\u008d"+
		"\u008e\5\22\n\2\u008e\17\3\2\2\2\u008f\u0094\5(\25\2\u0090\u0091\7(\2"+
		"\2\u0091\u0093\5(\25\2\u0092\u0090\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092"+
		"\3\2\2\2\u0094\u0095\3\2\2\2\u0095\21\3\2\2\2\u0096\u0094\3\2\2\2\u0097"+
		"\u009b\7%\2\2\u0098\u009a\5\24\13\2\u0099\u0098\3\2\2\2\u009a\u009d\3"+
		"\2\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009e\3\2\2\2\u009d"+
		"\u009b\3\2\2\2\u009e\u009f\7&\2\2\u009f\23\3\2\2\2\u00a0\u00aa\5\22\n"+
		"\2\u00a1\u00aa\5$\23\2\u00a2\u00aa\5\26\f\2\u00a3\u00aa\5\30\r\2\u00a4"+
		"\u00aa\5\32\16\2\u00a5\u00aa\5\34\17\2\u00a6\u00aa\5\36\20\2\u00a7\u00aa"+
		"\5 \21\2\u00a8\u00aa\5\"\22\2\u00a9\u00a0\3\2\2\2\u00a9\u00a1\3\2\2\2"+
		"\u00a9\u00a2\3\2\2\2\u00a9\u00a3\3\2\2\2\u00a9\u00a4\3\2\2\2\u00a9\u00a5"+
		"\3\2\2\2\u00a9\u00a6\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00a8\3\2\2\2\u00aa"+
		"\25\3\2\2\2\u00ab\u00ac\7\63\2\2\u00ac\u00ad\7!\2\2\u00ad\u00ae\5(\25"+
		"\2\u00ae\u00af\7\"\2\2\u00af\u00b2\5\24\13\2\u00b0\u00b1\7\64\2\2\u00b1"+
		"\u00b3\5\24\13\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\27\3\2"+
		"\2\2\u00b4\u00b5\7\66\2\2\u00b5\u00b6\7!\2\2\u00b6\u00b7\5(\25\2\u00b7"+
		"\u00b8\7\"\2\2\u00b8\u00b9\5\24\13\2\u00b9\31\3\2\2\2\u00ba\u00bb\7\65"+
		"\2\2\u00bb\u00bd\7!\2\2\u00bc\u00be\5\60\31\2\u00bd\u00bc\3\2\2\2\u00bd"+
		"\u00be\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c1\7)\2\2\u00c0\u00c2\5(\25"+
		"\2\u00c1\u00c0\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c5"+
		"\7)\2\2\u00c4\u00c6\5(\25\2\u00c5\u00c4\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\u00c7\3\2\2\2\u00c7\u00c8\7\"\2\2\u00c8\u00c9\5\24\13\2\u00c9\33\3\2"+
		"\2\2\u00ca\u00cb\78\2\2\u00cb\u00cc\7)\2\2\u00cc\35\3\2\2\2\u00cd\u00ce"+
		"\7\67\2\2\u00ce\u00cf\7)\2\2\u00cf\37\3\2\2\2\u00d0\u00d2\79\2\2\u00d1"+
		"\u00d3\5(\25\2\u00d2\u00d1\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4\3\2"+
		"\2\2\u00d4\u00d5\7)\2\2\u00d5!\3\2\2\2\u00d6\u00d8\5(\25\2\u00d7\u00d6"+
		"\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\7)\2\2\u00da"+
		"#\3\2\2\2\u00db\u00dc\5\62\32\2\u00dc\u00dd\7)\2\2\u00dd%\3\2\2\2\u00de"+
		"\u00e5\7?\2\2\u00df\u00e5\7\3\2\2\u00e0\u00e1\7!\2\2\u00e1\u00e2\5(\25"+
		"\2\u00e2\u00e3\7\"\2\2\u00e3\u00e5\3\2\2\2\u00e4\u00de\3\2\2\2\u00e4\u00df"+
		"\3\2\2\2\u00e4\u00e0\3\2\2\2\u00e5\'\3\2\2\2\u00e6\u00e7\b\25\1\2\u00e7"+
		"\u00ee\5&\24\2\u00e8\u00ee\5*\26\2\u00e9\u00ee\5\64\33\2\u00ea\u00eb\5"+
		"> \2\u00eb\u00ec\5(\25\16\u00ec\u00ee\3\2\2\2\u00ed\u00e6\3\2\2\2\u00ed"+
		"\u00e8\3\2\2\2\u00ed\u00e9\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ee\u011d\3\2"+
		"\2\2\u00ef\u00f0\f\r\2\2\u00f0\u00f1\5@!\2\u00f1\u00f2\5(\25\16\u00f2"+
		"\u011c\3\2\2\2\u00f3\u00f4\f\f\2\2\u00f4\u00f5\5B\"\2\u00f5\u00f6\5(\25"+
		"\r\u00f6\u011c\3\2\2\2\u00f7\u00f8\f\13\2\2\u00f8\u00f9\5F$\2\u00f9\u00fa"+
		"\5(\25\f\u00fa\u011c\3\2\2\2\u00fb\u00fc\f\n\2\2\u00fc\u00fd\5H%\2\u00fd"+
		"\u00fe\5(\25\13\u00fe\u011c\3\2\2\2\u00ff\u0100\f\t\2\2\u0100\u0101\5"+
		"D#\2\u0101\u0102\5(\25\n\u0102\u011c\3\2\2\2\u0103\u0104\f\b\2\2\u0104"+
		"\u0105\7\31\2\2\u0105\u011c\5(\25\t\u0106\u0107\f\7\2\2\u0107\u0108\7"+
		"\33\2\2\u0108\u011c\5(\25\b\u0109\u010a\f\6\2\2\u010a\u010b\7\32\2\2\u010b"+
		"\u011c\5(\25\7\u010c\u010d\f\5\2\2\u010d\u010e\7\24\2\2\u010e\u011c\5"+
		"(\25\6\u010f\u0110\f\4\2\2\u0110\u0111\7\25\2\2\u0111\u011c\5(\25\5\u0112"+
		"\u0113\f\3\2\2\u0113\u0114\7\35\2\2\u0114\u011c\5(\25\3\u0115\u0116\f"+
		"\22\2\2\u0116\u011c\5,\27\2\u0117\u0118\f\21\2\2\u0118\u011c\5.\30\2\u0119"+
		"\u011a\f\17\2\2\u011a\u011c\t\2\2\2\u011b\u00ef\3\2\2\2\u011b\u00f3\3"+
		"\2\2\2\u011b\u00f7\3\2\2\2\u011b\u00fb\3\2\2\2\u011b\u00ff\3\2\2\2\u011b"+
		"\u0103\3\2\2\2\u011b\u0106\3\2\2\2\u011b\u0109\3\2\2\2\u011b\u010c\3\2"+
		"\2\2\u011b\u010f\3\2\2\2\u011b\u0112\3\2\2\2\u011b\u0115\3\2\2\2\u011b"+
		"\u0117\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2"+
		"\2\2\u011d\u011e\3\2\2\2\u011e)\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0121"+
		"\7?\2\2\u0121\u0123\7!\2\2\u0122\u0124\5\20\t\2\u0123\u0122\3\2\2\2\u0123"+
		"\u0124\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0126\7\"\2\2\u0126+\3\2\2\2"+
		"\u0127\u0128\7#\2\2\u0128\u0129\5(\25\2\u0129\u012a\7$\2\2\u012a-\3\2"+
		"\2\2\u012b\u012c\7 \2\2\u012c\u012d\5(\25\2\u012d/\3\2\2\2\u012e\u0131"+
		"\5(\25\2\u012f\u0131\5\62\32\2\u0130\u012e\3\2\2\2\u0130\u012f\3\2\2\2"+
		"\u0131\61\3\2\2\2\u0132\u0133\5:\36\2\u0133\u0138\58\35\2\u0134\u0135"+
		"\7(\2\2\u0135\u0137\58\35\2\u0136\u0134\3\2\2\2\u0137\u013a\3\2\2\2\u0138"+
		"\u0136\3\2\2\2\u0138\u0139\3\2\2\2\u0139\63\3\2\2\2\u013a\u0138\3\2\2"+
		"\2\u013b\u013f\7:\2\2\u013c\u0140\5\66\34\2\u013d\u0140\5*\26\2\u013e"+
		"\u0140\7?\2\2\u013f\u013c\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u013e\3\2"+
		"\2\2\u0140\65\3\2\2\2\u0141\u0147\5:\36\2\u0142\u0144\7#\2\2\u0143\u0145"+
		"\5(\25\2\u0144\u0143\3\2\2\2\u0144\u0145\3\2\2\2\u0145\u0146\3\2\2\2\u0146"+
		"\u0148\7$\2\2\u0147\u0142\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u0147\3\2"+
		"\2\2\u0149\u014a\3\2\2\2\u014a\67\3\2\2\2\u014b\u014e\7?\2\2\u014c\u014d"+
		"\7\35\2\2\u014d\u014f\5(\25\2\u014e\u014c\3\2\2\2\u014e\u014f\3\2\2\2"+
		"\u014f9\3\2\2\2\u0150\u0151\b\36\1\2\u0151\u0156\7,\2\2\u0152\u0156\7"+
		".\2\2\u0153\u0156\7-\2\2\u0154\u0156\7?\2\2\u0155\u0150\3\2\2\2\u0155"+
		"\u0152\3\2\2\2\u0155\u0153\3\2\2\2\u0155\u0154\3\2\2\2\u0156\u015c\3\2"+
		"\2\2\u0157\u0158\f\7\2\2\u0158\u0159\7#\2\2\u0159\u015b\7$\2\2\u015a\u0157"+
		"\3\2\2\2\u015b\u015e\3\2\2\2\u015c\u015a\3\2\2\2\u015c\u015d\3\2\2\2\u015d"+
		";\3\2\2\2\u015e\u015c\3\2\2\2\u015f\u0162\7\60\2\2\u0160\u0162\5:\36\2"+
		"\u0161\u015f\3\2\2\2\u0161\u0160\3\2\2\2\u0162=\3\2\2\2\u0163\u0164\t"+
		"\3\2\2\u0164?\3\2\2\2\u0165\u0166\t\4\2\2\u0166A\3\2\2\2\u0167\u0168\t"+
		"\5\2\2\u0168C\3\2\2\2\u0169\u016a\t\6\2\2\u016aE\3\2\2\2\u016b\u016c\t"+
		"\7\2\2\u016cG\3\2\2\2\u016d\u016e\t\b\2\2\u016eI\3\2\2\2\"MOadknvx\u0084"+
		"\u0094\u009b\u00a9\u00b2\u00bd\u00c1\u00c5\u00d2\u00d7\u00e4\u00ed\u011b"+
		"\u011d\u0123\u0130\u0138\u013f\u0144\u0149\u014e\u0155\u015c\u0161";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}