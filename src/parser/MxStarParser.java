package parser;// Generated from MxStar.g4 by ANTLR 4.9
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
		DecInteger=1, String=2, LineComment=3, BlockComment=4, WhiteSpace=5, PLUS=6, 
		MINUS=7, MUL=8, DIV=9, MOD=10, GREATER=11, GREATER_EQ=12, LESS=13, LESS_EQ=14, 
		NOT_EQ=15, EQUAL=16, AND_LOGIC=17, OR_LOGIC=18, NOT_LOGIC=19, RIGHT_SHIFT=20, 
		LEFT_SHIFT=21, AND_ARI=22, OR_ARI=23, XOR_ARI=24, NOT_ARI=25, ASSIGNMENT=26, 
		SELF_PLUS=27, SELF_MINUS=28, DOT=29, L_PARENTNESS=30, R_PARENTNESS=31, 
		L_BRACKET=32, R_BRACKET=33, L_BRACE=34, R_BRACE=35, SPACE=36, COMMA=37, 
		SEMICOLON=38, LINE_BREAK=39, TAB=40, INT_KW=41, BOOL_KW=42, STRING_KW=43, 
		NULL_KW=44, VOID_KW=45, TRUE_KW=46, FALSE_KW=47, IF_KW=48, ELSE_KW=49, 
		FOR_KW=50, WHILE_KW=51, BREAK_KW=52, CONTINUE_KW=53, RETURN_KW=54, NEW_KW=55, 
		CLASS_KW=56, THIS_KW=57, ESCAPE_DB_QUOTATION=58, ESCAPE_BACKSLASH=59, 
		Identifier=60;
	public static final int
		RULE_code = 0, RULE_functionDef = 1, RULE_functionParamDef = 2, RULE_classDef = 3, 
		RULE_constructorDefinition = 4, RULE_expressionList = 5, RULE_suite = 6, 
		RULE_statement = 7, RULE_ifStatement = 8, RULE_whileStatement = 9, RULE_forStatement = 10, 
		RULE_continueStatement = 11, RULE_breakStatement = 12, RULE_returnStatement = 13, 
		RULE_simpleStatement = 14, RULE_declarationStatement = 15, RULE_atomExp = 16, 
		RULE_expression = 17, RULE_constructorCall = 18, RULE_exprOrDecl = 19, 
		RULE_declExpr = 20, RULE_arraySemanticError = 21, RULE_arrayLiteral = 22, 
		RULE_varDeclaration = 23, RULE_varType = 24, RULE_returnType = 25, RULE_builtinType = 26, 
		RULE_constant = 27, RULE_unaryOp = 28, RULE_multiplicativeOp = 29, RULE_additiveOp = 30, 
		RULE_shiftOp = 31, RULE_relationalCmpOp = 32, RULE_equalityCmpOp = 33;
	private static String[] makeRuleNames() {
		return new String[] {
			"code", "functionDef", "functionParamDef", "classDef", "constructorDefinition", 
			"expressionList", "suite", "statement", "ifStatement", "whileStatement", 
			"forStatement", "continueStatement", "breakStatement", "returnStatement", 
			"simpleStatement", "declarationStatement", "atomExp", "expression", "constructorCall", 
			"exprOrDecl", "declExpr", "arraySemanticError", "arrayLiteral", "varDeclaration", 
			"varType", "returnType", "builtinType", "constant", "unaryOp", "multiplicativeOp", 
			"additiveOp", "shiftOp", "relationalCmpOp", "equalityCmpOp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'>'", "'>='", "'<'", "'<='", "'!='", "'=='", "'&&'", "'||'", "'!'", 
			"'>>'", "'<<'", "'&'", "'|'", "'^'", "'~'", "'='", "'++'", "'--'", "'.'", 
			"'('", "')'", "'['", "']'", "'{'", "'}'", "' '", "','", "';'", "'\n'", 
			"'\t'", "'int'", "'bool'", "'string'", "'null'", "'void'", "'true'", 
			"'false'", "'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", 
			"'return'", "'new'", "'class'", "'this'", "'\\\"'", "'\\\\'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "DecInteger", "String", "LineComment", "BlockComment", "WhiteSpace", 
			"PLUS", "MINUS", "MUL", "DIV", "MOD", "GREATER", "GREATER_EQ", "LESS", 
			"LESS_EQ", "NOT_EQ", "EQUAL", "AND_LOGIC", "OR_LOGIC", "NOT_LOGIC", "RIGHT_SHIFT", 
			"LEFT_SHIFT", "AND_ARI", "OR_ARI", "XOR_ARI", "NOT_ARI", "ASSIGNMENT", 
			"SELF_PLUS", "SELF_MINUS", "DOT", "L_PARENTNESS", "R_PARENTNESS", "L_BRACKET", 
			"R_BRACKET", "L_BRACE", "R_BRACE", "SPACE", "COMMA", "SEMICOLON", "LINE_BREAK", 
			"TAB", "INT_KW", "BOOL_KW", "STRING_KW", "NULL_KW", "VOID_KW", "TRUE_KW", 
			"FALSE_KW", "IF_KW", "ELSE_KW", "FOR_KW", "WHILE_KW", "BREAK_KW", "CONTINUE_KW", 
			"RETURN_KW", "NEW_KW", "CLASS_KW", "THIS_KW", "ESCAPE_DB_QUOTATION", 
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
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << VOID_KW) | (1L << CLASS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(71);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(68);
					classDef();
					}
					break;
				case 2:
					{
					setState(69);
					declarationStatement();
					}
					break;
				case 3:
					{
					setState(70);
					functionDef();
					}
					break;
				}
				}
				setState(75);
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
			setState(76);
			returnType();
			setState(77);
			match(Identifier);
			setState(78);
			match(L_PARENTNESS);
			setState(79);
			functionParamDef();
			setState(80);
			match(R_PARENTNESS);
			setState(81);
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
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << Identifier))) != 0)) {
				{
				setState(83);
				varType(0);
				setState(84);
				match(Identifier);
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(85);
					match(COMMA);
					setState(86);
					varType(0);
					setState(87);
					match(Identifier);
					}
					}
					setState(93);
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
		enterRule(_localctx, 6, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(CLASS_KW);
			setState(97);
			match(Identifier);
			setState(98);
			match(L_BRACE);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << VOID_KW) | (1L << Identifier))) != 0)) {
				{
				setState(102);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(99);
					declarationStatement();
					}
					break;
				case 2:
					{
					setState(100);
					functionDef();
					}
					break;
				case 3:
					{
					setState(101);
					constructorDefinition();
					}
					break;
				}
				}
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(107);
			match(R_BRACE);
			setState(108);
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
		public FunctionParamDefContext functionParamDef() {
			return getRuleContext(FunctionParamDefContext.class,0);
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
		enterRule(_localctx, 8, RULE_constructorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(Identifier);
			setState(111);
			match(L_PARENTNESS);
			setState(112);
			functionParamDef();
			setState(113);
			match(R_PARENTNESS);
			setState(114);
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
		enterRule(_localctx, 10, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			expression(0);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(117);
				match(COMMA);
				setState(118);
				expression(0);
				}
				}
				setState(123);
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
		enterRule(_localctx, 12, RULE_suite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			match(L_BRACE);
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << L_BRACE) | (1L << SEMICOLON) | (1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << IF_KW) | (1L << FOR_KW) | (1L << WHILE_KW) | (1L << BREAK_KW) | (1L << CONTINUE_KW) | (1L << RETURN_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				{
				setState(125);
				statement();
				}
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(131);
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
		enterRule(_localctx, 14, RULE_statement);
		try {
			setState(142);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				suite();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				declarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(135);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(136);
				whileStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(137);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(138);
				continueStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(139);
				breakStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(140);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(141);
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
		enterRule(_localctx, 16, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(IF_KW);
			setState(145);
			match(L_PARENTNESS);
			setState(146);
			expression(0);
			setState(147);
			match(R_PARENTNESS);
			setState(148);
			((IfStatementContext)_localctx).trueStat = statement();
			setState(151);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(149);
				match(ELSE_KW);
				setState(150);
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
		enterRule(_localctx, 18, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(WHILE_KW);
			setState(154);
			match(L_PARENTNESS);
			setState(155);
			expression(0);
			setState(156);
			match(R_PARENTNESS);
			setState(157);
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
		enterRule(_localctx, 20, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			match(FOR_KW);
			setState(160);
			match(L_PARENTNESS);
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(161);
				((ForStatementContext)_localctx).initExp = exprOrDecl();
				}
			}

			setState(164);
			match(SEMICOLON);
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(165);
				((ForStatementContext)_localctx).condExp = expression(0);
				}
			}

			setState(168);
			match(SEMICOLON);
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(169);
				((ForStatementContext)_localctx).updExp = expression(0);
				}
			}

			setState(172);
			match(R_PARENTNESS);
			setState(173);
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
		enterRule(_localctx, 22, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(CONTINUE_KW);
			setState(176);
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
		enterRule(_localctx, 24, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(BREAK_KW);
			setState(179);
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
		enterRule(_localctx, 26, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(RETURN_KW);
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(182);
				expression(0);
				}
			}

			setState(185);
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
		enterRule(_localctx, 28, RULE_simpleStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(187);
				expression(0);
				}
			}

			setState(190);
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
		enterRule(_localctx, 30, RULE_declarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			declExpr();
			setState(193);
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
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
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
		enterRule(_localctx, 32, RULE_atomExp);
		try {
			setState(201);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(195);
				match(Identifier);
				}
				break;
			case DecInteger:
			case String:
			case NULL_KW:
			case TRUE_KW:
			case FALSE_KW:
			case THIS_KW:
				enterOuterAlt(_localctx, 2);
				{
				setState(196);
				constant();
				}
				break;
			case L_PARENTNESS:
				enterOuterAlt(_localctx, 3);
				{
				setState(197);
				match(L_PARENTNESS);
				setState(198);
				expression(0);
				setState(199);
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
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NewExprContext extends ExpressionContext {
		public TerminalNode NEW_KW() { return getToken(MxStarParser.NEW_KW, 0); }
		public ArraySemanticErrorContext arraySemanticError() {
			return getRuleContext(ArraySemanticErrorContext.class,0);
		}
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
		}
		public ConstructorCallContext constructorCall() {
			return getRuleContext(ConstructorCallContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public NewExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitNewExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IndexExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode L_BRACKET() { return getToken(MxStarParser.L_BRACKET, 0); }
		public TerminalNode R_BRACKET() { return getToken(MxStarParser.R_BRACKET, 0); }
		public IndexExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitIndexExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrefixExprContext extends ExpressionContext {
		public UnaryOpContext prefix;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryOpContext unaryOp() {
			return getRuleContext(UnaryOpContext.class,0);
		}
		public PrefixExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitPrefixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FuncExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFuncExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MemberExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(MxStarParser.DOT, 0); }
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public MemberExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitMemberExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SuffixExprContext extends ExpressionContext {
		public Token suffix;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SELF_PLUS() { return getToken(MxStarParser.SELF_PLUS, 0); }
		public TerminalNode SELF_MINUS() { return getToken(MxStarParser.SELF_MINUS, 0); }
		public SuffixExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitSuffixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomExprContext extends ExpressionContext {
		public AtomExpContext atomExp() {
			return getRuleContext(AtomExpContext.class,0);
		}
		public AtomExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAtomExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MultiplicativeOpContext multiplicativeOp() {
			return getRuleContext(MultiplicativeOpContext.class,0);
		}
		public AdditiveOpContext additiveOp() {
			return getRuleContext(AdditiveOpContext.class,0);
		}
		public ShiftOpContext shiftOp() {
			return getRuleContext(ShiftOpContext.class,0);
		}
		public RelationalCmpOpContext relationalCmpOp() {
			return getRuleContext(RelationalCmpOpContext.class,0);
		}
		public EqualityCmpOpContext equalityCmpOp() {
			return getRuleContext(EqualityCmpOpContext.class,0);
		}
		public TerminalNode AND_ARI() { return getToken(MxStarParser.AND_ARI, 0); }
		public TerminalNode XOR_ARI() { return getToken(MxStarParser.XOR_ARI, 0); }
		public TerminalNode OR_ARI() { return getToken(MxStarParser.OR_ARI, 0); }
		public TerminalNode AND_LOGIC() { return getToken(MxStarParser.AND_LOGIC, 0); }
		public TerminalNode OR_LOGIC() { return getToken(MxStarParser.OR_LOGIC, 0); }
		public BinaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ASSIGNMENT() { return getToken(MxStarParser.ASSIGNMENT, 0); }
		public AssignExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAssignExpr(this);
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
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DecInteger:
			case String:
			case L_PARENTNESS:
			case NULL_KW:
			case TRUE_KW:
			case FALSE_KW:
			case THIS_KW:
			case Identifier:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(204);
				atomExp();
				}
				break;
			case NEW_KW:
				{
				_localctx = new NewExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(205);
				match(NEW_KW);
				setState(210);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(206);
					arraySemanticError();
					}
					break;
				case 2:
					{
					setState(207);
					arrayLiteral();
					}
					break;
				case 3:
					{
					setState(208);
					constructorCall();
					}
					break;
				case 4:
					{
					setState(209);
					match(Identifier);
					}
					break;
				}
				}
				break;
			case PLUS:
			case MINUS:
			case NOT_LOGIC:
			case NOT_ARI:
			case SELF_PLUS:
			case SELF_MINUS:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(212);
				((PrefixExprContext)_localctx).prefix = unaryOp();
				setState(213);
				expression(12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(273);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(271);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(217);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(218);
						multiplicativeOp();
						setState(219);
						expression(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(221);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(222);
						additiveOp();
						setState(223);
						expression(11);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(225);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(226);
						shiftOp();
						setState(227);
						expression(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(229);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(230);
						relationalCmpOp();
						setState(231);
						expression(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(233);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(234);
						equalityCmpOp();
						setState(235);
						expression(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(237);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(238);
						match(AND_ARI);
						setState(239);
						expression(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(240);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(241);
						match(XOR_ARI);
						setState(242);
						expression(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(243);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(244);
						match(OR_ARI);
						setState(245);
						expression(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(246);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(247);
						match(AND_LOGIC);
						setState(248);
						expression(4);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(249);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(250);
						match(OR_LOGIC);
						setState(251);
						expression(3);
						}
						break;
					case 11:
						{
						_localctx = new AssignExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(252);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(253);
						match(ASSIGNMENT);
						setState(254);
						expression(1);
						}
						break;
					case 12:
						{
						_localctx = new MemberExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(255);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(256);
						match(DOT);
						setState(257);
						match(Identifier);
						}
						break;
					case 13:
						{
						_localctx = new IndexExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(258);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(259);
						match(L_BRACKET);
						setState(260);
						expression(0);
						setState(261);
						match(R_BRACKET);
						}
						break;
					case 14:
						{
						_localctx = new FuncExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(263);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(264);
						match(L_PARENTNESS);
						setState(266);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
							{
							setState(265);
							expressionList();
							}
						}

						setState(268);
						match(R_PARENTNESS);
						}
						break;
					case 15:
						{
						_localctx = new SuffixExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(269);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(270);
						((SuffixExprContext)_localctx).suffix = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==SELF_PLUS || _la==SELF_MINUS) ) {
							((SuffixExprContext)_localctx).suffix = (Token)_errHandler.recoverInline(this);
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
				setState(275);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
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

	public static class ConstructorCallContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode L_PARENTNESS() { return getToken(MxStarParser.L_PARENTNESS, 0); }
		public TerminalNode R_PARENTNESS() { return getToken(MxStarParser.R_PARENTNESS, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ConstructorCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitConstructorCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorCallContext constructorCall() throws RecognitionException {
		ConstructorCallContext _localctx = new ConstructorCallContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_constructorCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			match(Identifier);
			setState(277);
			match(L_PARENTNESS);
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(278);
				expressionList();
				}
			}

			setState(281);
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
		enterRule(_localctx, 38, RULE_exprOrDecl);
		try {
			setState(285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(283);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(284);
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
		enterRule(_localctx, 40, RULE_declExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			varType(0);
			setState(288);
			varDeclaration();
			setState(293);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(289);
				match(COMMA);
				setState(290);
				varDeclaration();
				}
				}
				setState(295);
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

	public static class ArraySemanticErrorContext extends ParserRuleContext {
		public BuiltinTypeContext builtinType() {
			return getRuleContext(BuiltinTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public List<TerminalNode> L_BRACKET() { return getTokens(MxStarParser.L_BRACKET); }
		public TerminalNode L_BRACKET(int i) {
			return getToken(MxStarParser.L_BRACKET, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> R_BRACKET() { return getTokens(MxStarParser.R_BRACKET); }
		public TerminalNode R_BRACKET(int i) {
			return getToken(MxStarParser.R_BRACKET, i);
		}
		public ArraySemanticErrorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arraySemanticError; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitArraySemanticError(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArraySemanticErrorContext arraySemanticError() throws RecognitionException {
		ArraySemanticErrorContext _localctx = new ArraySemanticErrorContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_arraySemanticError);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
				{
				setState(296);
				builtinType();
				}
				break;
			case Identifier:
				{
				setState(297);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(304); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(300);
					match(L_BRACKET);
					setState(301);
					expression(0);
					setState(302);
					match(R_BRACKET);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(306); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(310); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(308);
					match(L_BRACKET);
					setState(309);
					match(R_BRACKET);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(312); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(318); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(314);
					match(L_BRACKET);
					setState(315);
					expression(0);
					setState(316);
					match(R_BRACKET);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(320); 
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

	public static class ArrayLiteralContext extends ParserRuleContext {
		public BuiltinTypeContext builtinType() {
			return getRuleContext(BuiltinTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public List<TerminalNode> L_BRACKET() { return getTokens(MxStarParser.L_BRACKET); }
		public TerminalNode L_BRACKET(int i) {
			return getToken(MxStarParser.L_BRACKET, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> R_BRACKET() { return getTokens(MxStarParser.R_BRACKET); }
		public TerminalNode R_BRACKET(int i) {
			return getToken(MxStarParser.R_BRACKET, i);
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
		enterRule(_localctx, 44, RULE_arrayLiteral);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
				{
				setState(322);
				builtinType();
				}
				break;
			case Identifier:
				{
				setState(323);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(330); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(326);
					match(L_BRACKET);
					setState(327);
					expression(0);
					setState(328);
					match(R_BRACKET);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(332); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(338);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(334);
					match(L_BRACKET);
					setState(335);
					match(R_BRACKET);
					}
					} 
				}
				setState(340);
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
		enterRule(_localctx, 46, RULE_varDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			match(Identifier);
			setState(344);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGNMENT) {
				{
				setState(342);
				match(ASSIGNMENT);
				setState(343);
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
		public BuiltinTypeContext builtinType() {
			return getRuleContext(BuiltinTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
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
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_varType, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
				{
				setState(347);
				builtinType();
				}
				break;
			case Identifier:
				{
				setState(348);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(360);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VarTypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_varType);
					setState(351);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(354); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(352);
							match(L_BRACKET);
							setState(353);
							match(R_BRACKET);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(356); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					}
					} 
				}
				setState(362);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
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
		enterRule(_localctx, 50, RULE_returnType);
		try {
			setState(365);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID_KW:
				enterOuterAlt(_localctx, 1);
				{
				setState(363);
				match(VOID_KW);
				}
				break;
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(364);
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

	public static class BuiltinTypeContext extends ParserRuleContext {
		public TerminalNode INT_KW() { return getToken(MxStarParser.INT_KW, 0); }
		public TerminalNode STRING_KW() { return getToken(MxStarParser.STRING_KW, 0); }
		public TerminalNode BOOL_KW() { return getToken(MxStarParser.BOOL_KW, 0); }
		public BuiltinTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtinType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBuiltinType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BuiltinTypeContext builtinType() throws RecognitionException {
		BuiltinTypeContext _localctx = new BuiltinTypeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_builtinType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW))) != 0)) ) {
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

	public static class ConstantContext extends ParserRuleContext {
		public TerminalNode DecInteger() { return getToken(MxStarParser.DecInteger, 0); }
		public TerminalNode String() { return getToken(MxStarParser.String, 0); }
		public TerminalNode NULL_KW() { return getToken(MxStarParser.NULL_KW, 0); }
		public TerminalNode TRUE_KW() { return getToken(MxStarParser.TRUE_KW, 0); }
		public TerminalNode FALSE_KW() { return getToken(MxStarParser.FALSE_KW, 0); }
		public TerminalNode THIS_KW() { return getToken(MxStarParser.THIS_KW, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << THIS_KW))) != 0)) ) {
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
		enterRule(_localctx, 56, RULE_unaryOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
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
		enterRule(_localctx, 58, RULE_multiplicativeOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
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
		enterRule(_localctx, 60, RULE_additiveOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
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
		enterRule(_localctx, 62, RULE_shiftOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
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
		enterRule(_localctx, 64, RULE_relationalCmpOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
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
		enterRule(_localctx, 66, RULE_equalityCmpOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
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
		case 17:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 24:
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
			return precpred(_ctx, 14);
		case 14:
			return precpred(_ctx, 13);
		}
		return true;
	}
	private boolean varType_sempred(VarTypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 15:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3>\u0182\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\2\7\2J\n\2\f\2\16\2M\13\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4\\\n\4\f\4\16\4_\13\4\5\4a\n\4"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\7\5i\n\5\f\5\16\5l\13\5\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7z\n\7\f\7\16\7}\13\7\3\b\3\b\7\b\u0081\n"+
		"\b\f\b\16\b\u0084\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t"+
		"\u0091\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u009a\n\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\5\f\u00a5\n\f\3\f\3\f\5\f\u00a9\n\f\3\f\3\f"+
		"\5\f\u00ad\n\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\5\17\u00ba"+
		"\n\17\3\17\3\17\3\20\5\20\u00bf\n\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\5\22\u00cc\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\5\23\u00d5\n\23\3\23\3\23\3\23\5\23\u00da\n\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\5\23\u010d\n\23\3\23\3\23\3\23\7\23\u0112\n\23\f\23\16\23"+
		"\u0115\13\23\3\24\3\24\3\24\5\24\u011a\n\24\3\24\3\24\3\25\3\25\5\25\u0120"+
		"\n\25\3\26\3\26\3\26\3\26\7\26\u0126\n\26\f\26\16\26\u0129\13\26\3\27"+
		"\3\27\5\27\u012d\n\27\3\27\3\27\3\27\3\27\6\27\u0133\n\27\r\27\16\27\u0134"+
		"\3\27\3\27\6\27\u0139\n\27\r\27\16\27\u013a\3\27\3\27\3\27\3\27\6\27\u0141"+
		"\n\27\r\27\16\27\u0142\3\30\3\30\5\30\u0147\n\30\3\30\3\30\3\30\3\30\6"+
		"\30\u014d\n\30\r\30\16\30\u014e\3\30\3\30\7\30\u0153\n\30\f\30\16\30\u0156"+
		"\13\30\3\31\3\31\3\31\5\31\u015b\n\31\3\32\3\32\3\32\5\32\u0160\n\32\3"+
		"\32\3\32\3\32\6\32\u0165\n\32\r\32\16\32\u0166\7\32\u0169\n\32\f\32\16"+
		"\32\u016c\13\32\3\33\3\33\5\33\u0170\n\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#\2\4$\62$\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BD\2\13\3\2\35\36\3\2+-\6"+
		"\2\3\4..\60\61;;\6\2\b\t\25\25\33\33\35\36\3\2\n\f\3\2\b\t\3\2\26\27\3"+
		"\2\r\20\3\2\21\22\2\u019d\2K\3\2\2\2\4N\3\2\2\2\6`\3\2\2\2\bb\3\2\2\2"+
		"\np\3\2\2\2\fv\3\2\2\2\16~\3\2\2\2\20\u0090\3\2\2\2\22\u0092\3\2\2\2\24"+
		"\u009b\3\2\2\2\26\u00a1\3\2\2\2\30\u00b1\3\2\2\2\32\u00b4\3\2\2\2\34\u00b7"+
		"\3\2\2\2\36\u00be\3\2\2\2 \u00c2\3\2\2\2\"\u00cb\3\2\2\2$\u00d9\3\2\2"+
		"\2&\u0116\3\2\2\2(\u011f\3\2\2\2*\u0121\3\2\2\2,\u012c\3\2\2\2.\u0146"+
		"\3\2\2\2\60\u0157\3\2\2\2\62\u015f\3\2\2\2\64\u016f\3\2\2\2\66\u0171\3"+
		"\2\2\28\u0173\3\2\2\2:\u0175\3\2\2\2<\u0177\3\2\2\2>\u0179\3\2\2\2@\u017b"+
		"\3\2\2\2B\u017d\3\2\2\2D\u017f\3\2\2\2FJ\5\b\5\2GJ\5 \21\2HJ\5\4\3\2I"+
		"F\3\2\2\2IG\3\2\2\2IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2L\3\3\2\2\2"+
		"MK\3\2\2\2NO\5\64\33\2OP\7>\2\2PQ\7 \2\2QR\5\6\4\2RS\7!\2\2ST\5\16\b\2"+
		"T\5\3\2\2\2UV\5\62\32\2V]\7>\2\2WX\7\'\2\2XY\5\62\32\2YZ\7>\2\2Z\\\3\2"+
		"\2\2[W\3\2\2\2\\_\3\2\2\2][\3\2\2\2]^\3\2\2\2^a\3\2\2\2_]\3\2\2\2`U\3"+
		"\2\2\2`a\3\2\2\2a\7\3\2\2\2bc\7:\2\2cd\7>\2\2dj\7$\2\2ei\5 \21\2fi\5\4"+
		"\3\2gi\5\n\6\2he\3\2\2\2hf\3\2\2\2hg\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2"+
		"\2\2km\3\2\2\2lj\3\2\2\2mn\7%\2\2no\7(\2\2o\t\3\2\2\2pq\7>\2\2qr\7 \2"+
		"\2rs\5\6\4\2st\7!\2\2tu\5\16\b\2u\13\3\2\2\2v{\5$\23\2wx\7\'\2\2xz\5$"+
		"\23\2yw\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\r\3\2\2\2}{\3\2\2\2~\u0082"+
		"\7$\2\2\177\u0081\5\20\t\2\u0080\177\3\2\2\2\u0081\u0084\3\2\2\2\u0082"+
		"\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0085\3\2\2\2\u0084\u0082\3\2"+
		"\2\2\u0085\u0086\7%\2\2\u0086\17\3\2\2\2\u0087\u0091\5\16\b\2\u0088\u0091"+
		"\5 \21\2\u0089\u0091\5\22\n\2\u008a\u0091\5\24\13\2\u008b\u0091\5\26\f"+
		"\2\u008c\u0091\5\30\r\2\u008d\u0091\5\32\16\2\u008e\u0091\5\34\17\2\u008f"+
		"\u0091\5\36\20\2\u0090\u0087\3\2\2\2\u0090\u0088\3\2\2\2\u0090\u0089\3"+
		"\2\2\2\u0090\u008a\3\2\2\2\u0090\u008b\3\2\2\2\u0090\u008c\3\2\2\2\u0090"+
		"\u008d\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\21\3\2\2"+
		"\2\u0092\u0093\7\62\2\2\u0093\u0094\7 \2\2\u0094\u0095\5$\23\2\u0095\u0096"+
		"\7!\2\2\u0096\u0099\5\20\t\2\u0097\u0098\7\63\2\2\u0098\u009a\5\20\t\2"+
		"\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\23\3\2\2\2\u009b\u009c"+
		"\7\65\2\2\u009c\u009d\7 \2\2\u009d\u009e\5$\23\2\u009e\u009f\7!\2\2\u009f"+
		"\u00a0\5\20\t\2\u00a0\25\3\2\2\2\u00a1\u00a2\7\64\2\2\u00a2\u00a4\7 \2"+
		"\2\u00a3\u00a5\5(\25\2\u00a4\u00a3\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6"+
		"\3\2\2\2\u00a6\u00a8\7(\2\2\u00a7\u00a9\5$\23\2\u00a8\u00a7\3\2\2\2\u00a8"+
		"\u00a9\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\7(\2\2\u00ab\u00ad\5$\23"+
		"\2\u00ac\u00ab\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af"+
		"\7!\2\2\u00af\u00b0\5\20\t\2\u00b0\27\3\2\2\2\u00b1\u00b2\7\67\2\2\u00b2"+
		"\u00b3\7(\2\2\u00b3\31\3\2\2\2\u00b4\u00b5\7\66\2\2\u00b5\u00b6\7(\2\2"+
		"\u00b6\33\3\2\2\2\u00b7\u00b9\78\2\2\u00b8\u00ba\5$\23\2\u00b9\u00b8\3"+
		"\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\7(\2\2\u00bc"+
		"\35\3\2\2\2\u00bd\u00bf\5$\23\2\u00be\u00bd\3\2\2\2\u00be\u00bf\3\2\2"+
		"\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1\7(\2\2\u00c1\37\3\2\2\2\u00c2\u00c3"+
		"\5*\26\2\u00c3\u00c4\7(\2\2\u00c4!\3\2\2\2\u00c5\u00cc\7>\2\2\u00c6\u00cc"+
		"\58\35\2\u00c7\u00c8\7 \2\2\u00c8\u00c9\5$\23\2\u00c9\u00ca\7!\2\2\u00ca"+
		"\u00cc\3\2\2\2\u00cb\u00c5\3\2\2\2\u00cb\u00c6\3\2\2\2\u00cb\u00c7\3\2"+
		"\2\2\u00cc#\3\2\2\2\u00cd\u00ce\b\23\1\2\u00ce\u00da\5\"\22\2\u00cf\u00d4"+
		"\79\2\2\u00d0\u00d5\5,\27\2\u00d1\u00d5\5.\30\2\u00d2\u00d5\5&\24\2\u00d3"+
		"\u00d5\7>\2\2\u00d4\u00d0\3\2\2\2\u00d4\u00d1\3\2\2\2\u00d4\u00d2\3\2"+
		"\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00da\3\2\2\2\u00d6\u00d7\5:\36\2\u00d7"+
		"\u00d8\5$\23\16\u00d8\u00da\3\2\2\2\u00d9\u00cd\3\2\2\2\u00d9\u00cf\3"+
		"\2\2\2\u00d9\u00d6\3\2\2\2\u00da\u0113\3\2\2\2\u00db\u00dc\f\r\2\2\u00dc"+
		"\u00dd\5<\37\2\u00dd\u00de\5$\23\16\u00de\u0112\3\2\2\2\u00df\u00e0\f"+
		"\f\2\2\u00e0\u00e1\5> \2\u00e1\u00e2\5$\23\r\u00e2\u0112\3\2\2\2\u00e3"+
		"\u00e4\f\13\2\2\u00e4\u00e5\5@!\2\u00e5\u00e6\5$\23\f\u00e6\u0112\3\2"+
		"\2\2\u00e7\u00e8\f\n\2\2\u00e8\u00e9\5B\"\2\u00e9\u00ea\5$\23\13\u00ea"+
		"\u0112\3\2\2\2\u00eb\u00ec\f\t\2\2\u00ec\u00ed\5D#\2\u00ed\u00ee\5$\23"+
		"\n\u00ee\u0112\3\2\2\2\u00ef\u00f0\f\b\2\2\u00f0\u00f1\7\30\2\2\u00f1"+
		"\u0112\5$\23\t\u00f2\u00f3\f\7\2\2\u00f3\u00f4\7\32\2\2\u00f4\u0112\5"+
		"$\23\b\u00f5\u00f6\f\6\2\2\u00f6\u00f7\7\31\2\2\u00f7\u0112\5$\23\7\u00f8"+
		"\u00f9\f\5\2\2\u00f9\u00fa\7\23\2\2\u00fa\u0112\5$\23\6\u00fb\u00fc\f"+
		"\4\2\2\u00fc\u00fd\7\24\2\2\u00fd\u0112\5$\23\5\u00fe\u00ff\f\3\2\2\u00ff"+
		"\u0100\7\34\2\2\u0100\u0112\5$\23\3\u0101\u0102\f\22\2\2\u0102\u0103\7"+
		"\37\2\2\u0103\u0112\7>\2\2\u0104\u0105\f\21\2\2\u0105\u0106\7\"\2\2\u0106"+
		"\u0107\5$\23\2\u0107\u0108\7#\2\2\u0108\u0112\3\2\2\2\u0109\u010a\f\20"+
		"\2\2\u010a\u010c\7 \2\2\u010b\u010d\5\f\7\2\u010c\u010b\3\2\2\2\u010c"+
		"\u010d\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u0112\7!\2\2\u010f\u0110\f\17"+
		"\2\2\u0110\u0112\t\2\2\2\u0111\u00db\3\2\2\2\u0111\u00df\3\2\2\2\u0111"+
		"\u00e3\3\2\2\2\u0111\u00e7\3\2\2\2\u0111\u00eb\3\2\2\2\u0111\u00ef\3\2"+
		"\2\2\u0111\u00f2\3\2\2\2\u0111\u00f5\3\2\2\2\u0111\u00f8\3\2\2\2\u0111"+
		"\u00fb\3\2\2\2\u0111\u00fe\3\2\2\2\u0111\u0101\3\2\2\2\u0111\u0104\3\2"+
		"\2\2\u0111\u0109\3\2\2\2\u0111\u010f\3\2\2\2\u0112\u0115\3\2\2\2\u0113"+
		"\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114%\3\2\2\2\u0115\u0113\3\2\2\2"+
		"\u0116\u0117\7>\2\2\u0117\u0119\7 \2\2\u0118\u011a\5\f\7\2\u0119\u0118"+
		"\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\7!\2\2\u011c"+
		"\'\3\2\2\2\u011d\u0120\5$\23\2\u011e\u0120\5*\26\2\u011f\u011d\3\2\2\2"+
		"\u011f\u011e\3\2\2\2\u0120)\3\2\2\2\u0121\u0122\5\62\32\2\u0122\u0127"+
		"\5\60\31\2\u0123\u0124\7\'\2\2\u0124\u0126\5\60\31\2\u0125\u0123\3\2\2"+
		"\2\u0126\u0129\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128+"+
		"\3\2\2\2\u0129\u0127\3\2\2\2\u012a\u012d\5\66\34\2\u012b\u012d\7>\2\2"+
		"\u012c\u012a\3\2\2\2\u012c\u012b\3\2\2\2\u012d\u0132\3\2\2\2\u012e\u012f"+
		"\7\"\2\2\u012f\u0130\5$\23\2\u0130\u0131\7#\2\2\u0131\u0133\3\2\2\2\u0132"+
		"\u012e\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0132\3\2\2\2\u0134\u0135\3\2"+
		"\2\2\u0135\u0138\3\2\2\2\u0136\u0137\7\"\2\2\u0137\u0139\7#\2\2\u0138"+
		"\u0136\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2"+
		"\2\2\u013b\u0140\3\2\2\2\u013c\u013d\7\"\2\2\u013d\u013e\5$\23\2\u013e"+
		"\u013f\7#\2\2\u013f\u0141\3\2\2\2\u0140\u013c\3\2\2\2\u0141\u0142\3\2"+
		"\2\2\u0142\u0140\3\2\2\2\u0142\u0143\3\2\2\2\u0143-\3\2\2\2\u0144\u0147"+
		"\5\66\34\2\u0145\u0147\7>\2\2\u0146\u0144\3\2\2\2\u0146\u0145\3\2\2\2"+
		"\u0147\u014c\3\2\2\2\u0148\u0149\7\"\2\2\u0149\u014a\5$\23\2\u014a\u014b"+
		"\7#\2\2\u014b\u014d\3\2\2\2\u014c\u0148\3\2\2\2\u014d\u014e\3\2\2\2\u014e"+
		"\u014c\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0154\3\2\2\2\u0150\u0151\7\""+
		"\2\2\u0151\u0153\7#\2\2\u0152\u0150\3\2\2\2\u0153\u0156\3\2\2\2\u0154"+
		"\u0152\3\2\2\2\u0154\u0155\3\2\2\2\u0155/\3\2\2\2\u0156\u0154\3\2\2\2"+
		"\u0157\u015a\7>\2\2\u0158\u0159\7\34\2\2\u0159\u015b\5$\23\2\u015a\u0158"+
		"\3\2\2\2\u015a\u015b\3\2\2\2\u015b\61\3\2\2\2\u015c\u015d\b\32\1\2\u015d"+
		"\u0160\5\66\34\2\u015e\u0160\7>\2\2\u015f\u015c\3\2\2\2\u015f\u015e\3"+
		"\2\2\2\u0160\u016a\3\2\2\2\u0161\u0164\f\5\2\2\u0162\u0163\7\"\2\2\u0163"+
		"\u0165\7#\2\2\u0164\u0162\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0164\3\2"+
		"\2\2\u0166\u0167\3\2\2\2\u0167\u0169\3\2\2\2\u0168\u0161\3\2\2\2\u0169"+
		"\u016c\3\2\2\2\u016a\u0168\3\2\2\2\u016a\u016b\3\2\2\2\u016b\63\3\2\2"+
		"\2\u016c\u016a\3\2\2\2\u016d\u0170\7/\2\2\u016e\u0170\5\62\32\2\u016f"+
		"\u016d\3\2\2\2\u016f\u016e\3\2\2\2\u0170\65\3\2\2\2\u0171\u0172\t\3\2"+
		"\2\u0172\67\3\2\2\2\u0173\u0174\t\4\2\2\u01749\3\2\2\2\u0175\u0176\t\5"+
		"\2\2\u0176;\3\2\2\2\u0177\u0178\t\6\2\2\u0178=\3\2\2\2\u0179\u017a\t\7"+
		"\2\2\u017a?\3\2\2\2\u017b\u017c\t\b\2\2\u017cA\3\2\2\2\u017d\u017e\t\t"+
		"\2\2\u017eC\3\2\2\2\u017f\u0180\t\n\2\2\u0180E\3\2\2\2&IK]`hj{\u0082\u0090"+
		"\u0099\u00a4\u00a8\u00ac\u00b9\u00be\u00cb\u00d4\u00d9\u010c\u0111\u0113"+
		"\u0119\u011f\u0127\u012c\u0134\u013a\u0142\u0146\u014e\u0154\u015a\u015f"+
		"\u0166\u016a\u016f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
