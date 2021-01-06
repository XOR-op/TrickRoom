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
		RULE_expression = 17, RULE_funcCall = 18, RULE_exprOrDecl = 19, RULE_declExpr = 20, 
		RULE_arrayLiteral = 21, RULE_varDeclaration = 22, RULE_varType = 23, RULE_returnType = 24, 
		RULE_builtinType = 25, RULE_constant = 26, RULE_unaryOp = 27, RULE_multiplicativeOp = 28, 
		RULE_additiveOp = 29, RULE_shiftOp = 30, RULE_relationalCmpOp = 31, RULE_equalityCmpOp = 32;
	private static String[] makeRuleNames() {
		return new String[] {
			"code", "functionDef", "functionParamDef", "classDef", "constructorDefinition", 
			"expressionList", "suite", "statement", "ifStatement", "whileStatement", 
			"forStatement", "continueStatement", "breakStatement", "returnStatement", 
			"simpleStatement", "declarationStatement", "atomExp", "expression", "funcCall", 
			"exprOrDecl", "declExpr", "arrayLiteral", "varDeclaration", "varType", 
			"returnType", "builtinType", "constant", "unaryOp", "multiplicativeOp", 
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
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << VOID_KW) | (1L << CLASS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(69);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(66);
					classDef();
					}
					break;
				case 2:
					{
					setState(67);
					declarationStatement();
					}
					break;
				case 3:
					{
					setState(68);
					functionDef();
					}
					break;
				}
				}
				setState(73);
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
			setState(74);
			returnType();
			setState(75);
			match(Identifier);
			setState(76);
			match(L_PARENTNESS);
			setState(77);
			functionParamDef();
			setState(78);
			match(R_PARENTNESS);
			setState(79);
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
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << Identifier))) != 0)) {
				{
				setState(81);
				varType(0);
				setState(82);
				match(Identifier);
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(83);
					match(COMMA);
					setState(84);
					varType(0);
					setState(85);
					match(Identifier);
					}
					}
					setState(91);
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
			setState(94);
			match(CLASS_KW);
			setState(95);
			match(Identifier);
			setState(96);
			match(L_BRACE);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << VOID_KW) | (1L << Identifier))) != 0)) {
				{
				setState(100);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(97);
					declarationStatement();
					}
					break;
				case 2:
					{
					setState(98);
					functionDef();
					}
					break;
				case 3:
					{
					setState(99);
					constructorDefinition();
					}
					break;
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			match(R_BRACE);
			setState(106);
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
			setState(108);
			match(Identifier);
			setState(109);
			match(L_PARENTNESS);
			setState(110);
			functionParamDef();
			setState(111);
			match(R_PARENTNESS);
			setState(112);
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
			setState(114);
			expression(0);
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(115);
				match(COMMA);
				setState(116);
				expression(0);
				}
				}
				setState(121);
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
			setState(122);
			match(L_BRACE);
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << L_BRACE) | (1L << SEMICOLON) | (1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << IF_KW) | (1L << FOR_KW) | (1L << WHILE_KW) | (1L << BREAK_KW) | (1L << CONTINUE_KW) | (1L << RETURN_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				{
				setState(123);
				statement();
				}
				}
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(129);
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
			setState(140);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(131);
				suite();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				declarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(133);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(134);
				whileStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(135);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(136);
				continueStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(137);
				breakStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(138);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(139);
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
			setState(142);
			match(IF_KW);
			setState(143);
			match(L_PARENTNESS);
			setState(144);
			expression(0);
			setState(145);
			match(R_PARENTNESS);
			setState(146);
			((IfStatementContext)_localctx).trueStat = statement();
			setState(149);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(147);
				match(ELSE_KW);
				setState(148);
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
			setState(151);
			match(WHILE_KW);
			setState(152);
			match(L_PARENTNESS);
			setState(153);
			expression(0);
			setState(154);
			match(R_PARENTNESS);
			setState(155);
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
			setState(157);
			match(FOR_KW);
			setState(158);
			match(L_PARENTNESS);
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << INT_KW) | (1L << BOOL_KW) | (1L << STRING_KW) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(159);
				((ForStatementContext)_localctx).initExp = exprOrDecl();
				}
			}

			setState(162);
			match(SEMICOLON);
			setState(164);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(163);
				((ForStatementContext)_localctx).condExp = expression(0);
				}
			}

			setState(166);
			match(SEMICOLON);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(167);
				((ForStatementContext)_localctx).updExp = expression(0);
				}
			}

			setState(170);
			match(R_PARENTNESS);
			setState(171);
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
			setState(173);
			match(CONTINUE_KW);
			setState(174);
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
			setState(176);
			match(BREAK_KW);
			setState(177);
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
			setState(179);
			match(RETURN_KW);
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(180);
				expression(0);
				}
			}

			setState(183);
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
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(185);
				expression(0);
				}
			}

			setState(188);
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
			setState(190);
			declExpr();
			setState(191);
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
			setState(199);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
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
				setState(194);
				constant();
				}
				break;
			case L_PARENTNESS:
				enterOuterAlt(_localctx, 3);
				{
				setState(195);
				match(L_PARENTNESS);
				setState(196);
				expression(0);
				setState(197);
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
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
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
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
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
			setState(213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(202);
				atomExp();
				}
				break;
			case 2:
				{
				_localctx = new FuncExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(203);
				funcCall();
				}
				break;
			case 3:
				{
				_localctx = new NewExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(204);
				match(NEW_KW);
				setState(208);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(205);
					arrayLiteral();
					}
					break;
				case 2:
					{
					setState(206);
					funcCall();
					}
					break;
				case 3:
					{
					setState(207);
					match(Identifier);
					}
					break;
				}
				}
				break;
			case 4:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(210);
				((PrefixExprContext)_localctx).prefix = unaryOp();
				setState(211);
				expression(12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(265);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(263);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(215);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(216);
						multiplicativeOp();
						setState(217);
						expression(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(219);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(220);
						additiveOp();
						setState(221);
						expression(11);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(223);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(224);
						relationalCmpOp();
						setState(225);
						expression(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(227);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(228);
						equalityCmpOp();
						setState(229);
						expression(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(231);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(232);
						shiftOp();
						setState(233);
						expression(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(235);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(236);
						match(AND_ARI);
						setState(237);
						expression(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(238);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(239);
						match(XOR_ARI);
						setState(240);
						expression(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(241);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(242);
						match(OR_ARI);
						setState(243);
						expression(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(244);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(245);
						match(AND_LOGIC);
						setState(246);
						expression(4);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(247);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(248);
						match(OR_LOGIC);
						setState(249);
						expression(3);
						}
						break;
					case 11:
						{
						_localctx = new AssignExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(250);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(251);
						match(ASSIGNMENT);
						setState(252);
						expression(1);
						}
						break;
					case 12:
						{
						_localctx = new MemberExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(253);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(254);
						match(DOT);
						setState(255);
						match(Identifier);
						}
						break;
					case 13:
						{
						_localctx = new IndexExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(256);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(257);
						match(L_BRACKET);
						setState(258);
						expression(0);
						setState(259);
						match(R_BRACKET);
						}
						break;
					case 14:
						{
						_localctx = new SuffixExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(261);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(262);
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
				setState(267);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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
		enterRule(_localctx, 36, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			match(Identifier);
			setState(269);
			match(L_PARENTNESS);
			setState(271);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DecInteger) | (1L << String) | (1L << PLUS) | (1L << MINUS) | (1L << NOT_LOGIC) | (1L << NOT_ARI) | (1L << SELF_PLUS) | (1L << SELF_MINUS) | (1L << L_PARENTNESS) | (1L << NULL_KW) | (1L << TRUE_KW) | (1L << FALSE_KW) | (1L << NEW_KW) | (1L << THIS_KW) | (1L << Identifier))) != 0)) {
				{
				setState(270);
				expressionList();
				}
			}

			setState(273);
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
			setState(277);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(275);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(276);
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
			setState(279);
			varType(0);
			setState(280);
			varDeclaration();
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(281);
				match(COMMA);
				setState(282);
				varDeclaration();
				}
				}
				setState(287);
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
		enterRule(_localctx, 42, RULE_arrayLiteral);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
				{
				setState(288);
				builtinType();
				}
				break;
			case Identifier:
				{
				setState(289);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(296); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(292);
					match(L_BRACKET);
					setState(293);
					expression(0);
					setState(294);
					match(R_BRACKET);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(298); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(304);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(300);
					match(L_BRACKET);
					setState(301);
					match(R_BRACKET);
					}
					} 
				}
				setState(306);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
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
		enterRule(_localctx, 44, RULE_varDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(Identifier);
			setState(310);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGNMENT) {
				{
				setState(308);
				match(ASSIGNMENT);
				setState(309);
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
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_varType, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
				{
				setState(313);
				builtinType();
				}
				break;
			case Identifier:
				{
				setState(314);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(326);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VarTypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_varType);
					setState(317);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(320); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(318);
							match(L_BRACKET);
							setState(319);
							match(R_BRACKET);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(322); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					}
					} 
				}
				setState(328);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
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
		enterRule(_localctx, 48, RULE_returnType);
		try {
			setState(331);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID_KW:
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				match(VOID_KW);
				}
				break;
			case INT_KW:
			case BOOL_KW:
			case STRING_KW:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(330);
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
		enterRule(_localctx, 50, RULE_builtinType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
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
		enterRule(_localctx, 52, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
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
		enterRule(_localctx, 54, RULE_unaryOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
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
		enterRule(_localctx, 56, RULE_multiplicativeOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
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
		enterRule(_localctx, 58, RULE_additiveOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
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
		enterRule(_localctx, 60, RULE_shiftOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
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
		enterRule(_localctx, 62, RULE_relationalCmpOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
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
		enterRule(_localctx, 64, RULE_equalityCmpOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
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
		case 23:
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
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3>\u0160\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\3\2\3\2\3\2\7\2H\n\2\f\2\16\2K\13\2\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4Z\n\4\f\4\16\4]\13\4\5\4_\n\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\7\5g\n\5\f\5\16\5j\13\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\7\3\7\3\7\7\7x\n\7\f\7\16\7{\13\7\3\b\3\b\7\b\177\n\b\f\b\16"+
		"\b\u0082\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u008f\n"+
		"\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0098\n\n\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\5\f\u00a3\n\f\3\f\3\f\5\f\u00a7\n\f\3\f\3\f\5\f\u00ab"+
		"\n\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\5\17\u00b8\n\17"+
		"\3\17\3\17\3\20\5\20\u00bd\n\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\5\22\u00ca\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23"+
		"\u00d3\n\23\3\23\3\23\3\23\5\23\u00d8\n\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\7\23\u010a\n\23\f\23\16\23\u010d\13\23\3\24\3\24\3\24\5\24\u0112\n"+
		"\24\3\24\3\24\3\25\3\25\5\25\u0118\n\25\3\26\3\26\3\26\3\26\7\26\u011e"+
		"\n\26\f\26\16\26\u0121\13\26\3\27\3\27\5\27\u0125\n\27\3\27\3\27\3\27"+
		"\3\27\6\27\u012b\n\27\r\27\16\27\u012c\3\27\3\27\7\27\u0131\n\27\f\27"+
		"\16\27\u0134\13\27\3\30\3\30\3\30\5\30\u0139\n\30\3\31\3\31\3\31\5\31"+
		"\u013e\n\31\3\31\3\31\3\31\6\31\u0143\n\31\r\31\16\31\u0144\7\31\u0147"+
		"\n\31\f\31\16\31\u014a\13\31\3\32\3\32\5\32\u014e\n\32\3\33\3\33\3\34"+
		"\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\2\4$\60#\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@B\2\13"+
		"\3\2\35\36\3\2+-\6\2\3\4..\60\61;;\6\2\b\t\25\25\33\33\35\36\3\2\n\f\3"+
		"\2\b\t\3\2\26\27\3\2\r\20\3\2\21\22\2\u0176\2I\3\2\2\2\4L\3\2\2\2\6^\3"+
		"\2\2\2\b`\3\2\2\2\nn\3\2\2\2\ft\3\2\2\2\16|\3\2\2\2\20\u008e\3\2\2\2\22"+
		"\u0090\3\2\2\2\24\u0099\3\2\2\2\26\u009f\3\2\2\2\30\u00af\3\2\2\2\32\u00b2"+
		"\3\2\2\2\34\u00b5\3\2\2\2\36\u00bc\3\2\2\2 \u00c0\3\2\2\2\"\u00c9\3\2"+
		"\2\2$\u00d7\3\2\2\2&\u010e\3\2\2\2(\u0117\3\2\2\2*\u0119\3\2\2\2,\u0124"+
		"\3\2\2\2.\u0135\3\2\2\2\60\u013d\3\2\2\2\62\u014d\3\2\2\2\64\u014f\3\2"+
		"\2\2\66\u0151\3\2\2\28\u0153\3\2\2\2:\u0155\3\2\2\2<\u0157\3\2\2\2>\u0159"+
		"\3\2\2\2@\u015b\3\2\2\2B\u015d\3\2\2\2DH\5\b\5\2EH\5 \21\2FH\5\4\3\2G"+
		"D\3\2\2\2GE\3\2\2\2GF\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2J\3\3\2\2\2"+
		"KI\3\2\2\2LM\5\62\32\2MN\7>\2\2NO\7 \2\2OP\5\6\4\2PQ\7!\2\2QR\5\16\b\2"+
		"R\5\3\2\2\2ST\5\60\31\2T[\7>\2\2UV\7\'\2\2VW\5\60\31\2WX\7>\2\2XZ\3\2"+
		"\2\2YU\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\_\3\2\2\2][\3\2\2\2^S\3"+
		"\2\2\2^_\3\2\2\2_\7\3\2\2\2`a\7:\2\2ab\7>\2\2bh\7$\2\2cg\5 \21\2dg\5\4"+
		"\3\2eg\5\n\6\2fc\3\2\2\2fd\3\2\2\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2"+
		"\2\2ik\3\2\2\2jh\3\2\2\2kl\7%\2\2lm\7(\2\2m\t\3\2\2\2no\7>\2\2op\7 \2"+
		"\2pq\5\6\4\2qr\7!\2\2rs\5\16\b\2s\13\3\2\2\2ty\5$\23\2uv\7\'\2\2vx\5$"+
		"\23\2wu\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\r\3\2\2\2{y\3\2\2\2|\u0080"+
		"\7$\2\2}\177\5\20\t\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2\u0080"+
		"\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0084\7%"+
		"\2\2\u0084\17\3\2\2\2\u0085\u008f\5\16\b\2\u0086\u008f\5 \21\2\u0087\u008f"+
		"\5\22\n\2\u0088\u008f\5\24\13\2\u0089\u008f\5\26\f\2\u008a\u008f\5\30"+
		"\r\2\u008b\u008f\5\32\16\2\u008c\u008f\5\34\17\2\u008d\u008f\5\36\20\2"+
		"\u008e\u0085\3\2\2\2\u008e\u0086\3\2\2\2\u008e\u0087\3\2\2\2\u008e\u0088"+
		"\3\2\2\2\u008e\u0089\3\2\2\2\u008e\u008a\3\2\2\2\u008e\u008b\3\2\2\2\u008e"+
		"\u008c\3\2\2\2\u008e\u008d\3\2\2\2\u008f\21\3\2\2\2\u0090\u0091\7\62\2"+
		"\2\u0091\u0092\7 \2\2\u0092\u0093\5$\23\2\u0093\u0094\7!\2\2\u0094\u0097"+
		"\5\20\t\2\u0095\u0096\7\63\2\2\u0096\u0098\5\20\t\2\u0097\u0095\3\2\2"+
		"\2\u0097\u0098\3\2\2\2\u0098\23\3\2\2\2\u0099\u009a\7\65\2\2\u009a\u009b"+
		"\7 \2\2\u009b\u009c\5$\23\2\u009c\u009d\7!\2\2\u009d\u009e\5\20\t\2\u009e"+
		"\25\3\2\2\2\u009f\u00a0\7\64\2\2\u00a0\u00a2\7 \2\2\u00a1\u00a3\5(\25"+
		"\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6"+
		"\7(\2\2\u00a5\u00a7\5$\23\2\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00a8\3\2\2\2\u00a8\u00aa\7(\2\2\u00a9\u00ab\5$\23\2\u00aa\u00a9\3\2"+
		"\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad\7!\2\2\u00ad"+
		"\u00ae\5\20\t\2\u00ae\27\3\2\2\2\u00af\u00b0\7\67\2\2\u00b0\u00b1\7(\2"+
		"\2\u00b1\31\3\2\2\2\u00b2\u00b3\7\66\2\2\u00b3\u00b4\7(\2\2\u00b4\33\3"+
		"\2\2\2\u00b5\u00b7\78\2\2\u00b6\u00b8\5$\23\2\u00b7\u00b6\3\2\2\2\u00b7"+
		"\u00b8\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba\7(\2\2\u00ba\35\3\2\2\2"+
		"\u00bb\u00bd\5$\23\2\u00bc\u00bb\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00be"+
		"\3\2\2\2\u00be\u00bf\7(\2\2\u00bf\37\3\2\2\2\u00c0\u00c1\5*\26\2\u00c1"+
		"\u00c2\7(\2\2\u00c2!\3\2\2\2\u00c3\u00ca\7>\2\2\u00c4\u00ca\5\66\34\2"+
		"\u00c5\u00c6\7 \2\2\u00c6\u00c7\5$\23\2\u00c7\u00c8\7!\2\2\u00c8\u00ca"+
		"\3\2\2\2\u00c9\u00c3\3\2\2\2\u00c9\u00c4\3\2\2\2\u00c9\u00c5\3\2\2\2\u00ca"+
		"#\3\2\2\2\u00cb\u00cc\b\23\1\2\u00cc\u00d8\5\"\22\2\u00cd\u00d8\5&\24"+
		"\2\u00ce\u00d2\79\2\2\u00cf\u00d3\5,\27\2\u00d0\u00d3\5&\24\2\u00d1\u00d3"+
		"\7>\2\2\u00d2\u00cf\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d1\3\2\2\2\u00d3"+
		"\u00d8\3\2\2\2\u00d4\u00d5\58\35\2\u00d5\u00d6\5$\23\16\u00d6\u00d8\3"+
		"\2\2\2\u00d7\u00cb\3\2\2\2\u00d7\u00cd\3\2\2\2\u00d7\u00ce\3\2\2\2\u00d7"+
		"\u00d4\3\2\2\2\u00d8\u010b\3\2\2\2\u00d9\u00da\f\r\2\2\u00da\u00db\5:"+
		"\36\2\u00db\u00dc\5$\23\16\u00dc\u010a\3\2\2\2\u00dd\u00de\f\f\2\2\u00de"+
		"\u00df\5<\37\2\u00df\u00e0\5$\23\r\u00e0\u010a\3\2\2\2\u00e1\u00e2\f\13"+
		"\2\2\u00e2\u00e3\5@!\2\u00e3\u00e4\5$\23\f\u00e4\u010a\3\2\2\2\u00e5\u00e6"+
		"\f\n\2\2\u00e6\u00e7\5B\"\2\u00e7\u00e8\5$\23\13\u00e8\u010a\3\2\2\2\u00e9"+
		"\u00ea\f\t\2\2\u00ea\u00eb\5> \2\u00eb\u00ec\5$\23\n\u00ec\u010a\3\2\2"+
		"\2\u00ed\u00ee\f\b\2\2\u00ee\u00ef\7\30\2\2\u00ef\u010a\5$\23\t\u00f0"+
		"\u00f1\f\7\2\2\u00f1\u00f2\7\32\2\2\u00f2\u010a\5$\23\b\u00f3\u00f4\f"+
		"\6\2\2\u00f4\u00f5\7\31\2\2\u00f5\u010a\5$\23\7\u00f6\u00f7\f\5\2\2\u00f7"+
		"\u00f8\7\23\2\2\u00f8\u010a\5$\23\6\u00f9\u00fa\f\4\2\2\u00fa\u00fb\7"+
		"\24\2\2\u00fb\u010a\5$\23\5\u00fc\u00fd\f\3\2\2\u00fd\u00fe\7\34\2\2\u00fe"+
		"\u010a\5$\23\3\u00ff\u0100\f\22\2\2\u0100\u0101\7\37\2\2\u0101\u010a\7"+
		">\2\2\u0102\u0103\f\21\2\2\u0103\u0104\7\"\2\2\u0104\u0105\5$\23\2\u0105"+
		"\u0106\7#\2\2\u0106\u010a\3\2\2\2\u0107\u0108\f\17\2\2\u0108\u010a\t\2"+
		"\2\2\u0109\u00d9\3\2\2\2\u0109\u00dd\3\2\2\2\u0109\u00e1\3\2\2\2\u0109"+
		"\u00e5\3\2\2\2\u0109\u00e9\3\2\2\2\u0109\u00ed\3\2\2\2\u0109\u00f0\3\2"+
		"\2\2\u0109\u00f3\3\2\2\2\u0109\u00f6\3\2\2\2\u0109\u00f9\3\2\2\2\u0109"+
		"\u00fc\3\2\2\2\u0109\u00ff\3\2\2\2\u0109\u0102\3\2\2\2\u0109\u0107\3\2"+
		"\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"%\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u010f\7>\2\2\u010f\u0111\7 \2\2\u0110"+
		"\u0112\5\f\7\2\u0111\u0110\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0113\3\2"+
		"\2\2\u0113\u0114\7!\2\2\u0114\'\3\2\2\2\u0115\u0118\5$\23\2\u0116\u0118"+
		"\5*\26\2\u0117\u0115\3\2\2\2\u0117\u0116\3\2\2\2\u0118)\3\2\2\2\u0119"+
		"\u011a\5\60\31\2\u011a\u011f\5.\30\2\u011b\u011c\7\'\2\2\u011c\u011e\5"+
		".\30\2\u011d\u011b\3\2\2\2\u011e\u0121\3\2\2\2\u011f\u011d\3\2\2\2\u011f"+
		"\u0120\3\2\2\2\u0120+\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0125\5\64\33"+
		"\2\u0123\u0125\7>\2\2\u0124\u0122\3\2\2\2\u0124\u0123\3\2\2\2\u0125\u012a"+
		"\3\2\2\2\u0126\u0127\7\"\2\2\u0127\u0128\5$\23\2\u0128\u0129\7#\2\2\u0129"+
		"\u012b\3\2\2\2\u012a\u0126\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012a\3\2"+
		"\2\2\u012c\u012d\3\2\2\2\u012d\u0132\3\2\2\2\u012e\u012f\7\"\2\2\u012f"+
		"\u0131\7#\2\2\u0130\u012e\3\2\2\2\u0131\u0134\3\2\2\2\u0132\u0130\3\2"+
		"\2\2\u0132\u0133\3\2\2\2\u0133-\3\2\2\2\u0134\u0132\3\2\2\2\u0135\u0138"+
		"\7>\2\2\u0136\u0137\7\34\2\2\u0137\u0139\5$\23\2\u0138\u0136\3\2\2\2\u0138"+
		"\u0139\3\2\2\2\u0139/\3\2\2\2\u013a\u013b\b\31\1\2\u013b\u013e\5\64\33"+
		"\2\u013c\u013e\7>\2\2\u013d\u013a\3\2\2\2\u013d\u013c\3\2\2\2\u013e\u0148"+
		"\3\2\2\2\u013f\u0142\f\5\2\2\u0140\u0141\7\"\2\2\u0141\u0143\7#\2\2\u0142"+
		"\u0140\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0142\3\2\2\2\u0144\u0145\3\2"+
		"\2\2\u0145\u0147\3\2\2\2\u0146\u013f\3\2\2\2\u0147\u014a\3\2\2\2\u0148"+
		"\u0146\3\2\2\2\u0148\u0149\3\2\2\2\u0149\61\3\2\2\2\u014a\u0148\3\2\2"+
		"\2\u014b\u014e\7/\2\2\u014c\u014e\5\60\31\2\u014d\u014b\3\2\2\2\u014d"+
		"\u014c\3\2\2\2\u014e\63\3\2\2\2\u014f\u0150\t\3\2\2\u0150\65\3\2\2\2\u0151"+
		"\u0152\t\4\2\2\u0152\67\3\2\2\2\u0153\u0154\t\5\2\2\u01549\3\2\2\2\u0155"+
		"\u0156\t\6\2\2\u0156;\3\2\2\2\u0157\u0158\t\7\2\2\u0158=\3\2\2\2\u0159"+
		"\u015a\t\b\2\2\u015a?\3\2\2\2\u015b\u015c\t\t\2\2\u015cA\3\2\2\2\u015d"+
		"\u015e\t\n\2\2\u015eC\3\2\2\2!GI[^fhy\u0080\u008e\u0097\u00a2\u00a6\u00aa"+
		"\u00b7\u00bc\u00c9\u00d2\u00d7\u0109\u010b\u0111\u0117\u011f\u0124\u012c"+
		"\u0132\u0138\u013d\u0144\u0148\u014d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
