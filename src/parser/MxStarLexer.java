package parser;// Generated from MxStar.g4 by ANTLR 4.9
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxStarLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"DecInteger", "String", "LineComment", "BlockComment", "WhiteSpace", 
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


	public MxStarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MxStar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2>\u016b\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\3\2\3\2\7\2~\n\2\f\2\16\2\u0081\13\2\3\2\5\2\u0084\n\2\3\3\3\3\3\3\3"+
		"\3\7\3\u008a\n\3\f\3\16\3\u008d\13\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u0095"+
		"\n\4\f\4\16\4\u0098\13\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5\u00a0\n\5\f\5\16"+
		"\5\u00a3\13\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\6\6\u00ad\n\6\r\6\16\6\u00ae"+
		"\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r"+
		"\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30"+
		"\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3"+
		"(\3(\3)\3)\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3-\3-\3-\3"+
		"-\3-\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\61"+
		"\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38\38\3"+
		"9\39\39\39\39\39\3:\3:\3:\3:\3:\3;\3;\3;\3<\3<\3<\3=\3=\7=\u0167\n=\f"+
		"=\16=\u016a\13=\4\u008b\u00a1\2>\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60"+
		"_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>\3\2\7\3\2\63;\3\2\62;\3\2\f"+
		"\f\4\2C\\c|\6\2\62;C\\aac|\2\u0175\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2"+
		"O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3"+
		"\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2"+
		"\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2"+
		"u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\3\u0083\3\2\2\2\5\u0085\3\2\2\2\7\u0090"+
		"\3\2\2\2\t\u009b\3\2\2\2\13\u00ac\3\2\2\2\r\u00b2\3\2\2\2\17\u00b4\3\2"+
		"\2\2\21\u00b6\3\2\2\2\23\u00b8\3\2\2\2\25\u00ba\3\2\2\2\27\u00bc\3\2\2"+
		"\2\31\u00be\3\2\2\2\33\u00c1\3\2\2\2\35\u00c3\3\2\2\2\37\u00c6\3\2\2\2"+
		"!\u00c9\3\2\2\2#\u00cc\3\2\2\2%\u00cf\3\2\2\2\'\u00d2\3\2\2\2)\u00d4\3"+
		"\2\2\2+\u00d7\3\2\2\2-\u00da\3\2\2\2/\u00dc\3\2\2\2\61\u00de\3\2\2\2\63"+
		"\u00e0\3\2\2\2\65\u00e2\3\2\2\2\67\u00e4\3\2\2\29\u00e7\3\2\2\2;\u00ea"+
		"\3\2\2\2=\u00ec\3\2\2\2?\u00ee\3\2\2\2A\u00f0\3\2\2\2C\u00f2\3\2\2\2E"+
		"\u00f4\3\2\2\2G\u00f6\3\2\2\2I\u00f8\3\2\2\2K\u00fa\3\2\2\2M\u00fc\3\2"+
		"\2\2O\u00fe\3\2\2\2Q\u0100\3\2\2\2S\u0102\3\2\2\2U\u0106\3\2\2\2W\u010b"+
		"\3\2\2\2Y\u0112\3\2\2\2[\u0117\3\2\2\2]\u011c\3\2\2\2_\u0121\3\2\2\2a"+
		"\u0127\3\2\2\2c\u012a\3\2\2\2e\u012f\3\2\2\2g\u0133\3\2\2\2i\u0139\3\2"+
		"\2\2k\u013f\3\2\2\2m\u0148\3\2\2\2o\u014f\3\2\2\2q\u0153\3\2\2\2s\u0159"+
		"\3\2\2\2u\u015e\3\2\2\2w\u0161\3\2\2\2y\u0164\3\2\2\2{\177\t\2\2\2|~\t"+
		"\3\2\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0084"+
		"\3\2\2\2\u0081\177\3\2\2\2\u0082\u0084\7\62\2\2\u0083{\3\2\2\2\u0083\u0082"+
		"\3\2\2\2\u0084\4\3\2\2\2\u0085\u008b\7$\2\2\u0086\u008a\5u;\2\u0087\u008a"+
		"\5w<\2\u0088\u008a\13\2\2\2\u0089\u0086\3\2\2\2\u0089\u0087\3\2\2\2\u0089"+
		"\u0088\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u008c\3\2\2\2\u008b\u0089\3\2"+
		"\2\2\u008c\u008e\3\2\2\2\u008d\u008b\3\2\2\2\u008e\u008f\7$\2\2\u008f"+
		"\6\3\2\2\2\u0090\u0091\7\61\2\2\u0091\u0092\7\61\2\2\u0092\u0096\3\2\2"+
		"\2\u0093\u0095\n\4\2\2\u0094\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094"+
		"\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099"+
		"\u009a\b\4\2\2\u009a\b\3\2\2\2\u009b\u009c\7\61\2\2\u009c\u009d\7,\2\2"+
		"\u009d\u00a1\3\2\2\2\u009e\u00a0\13\2\2\2\u009f\u009e\3\2\2\2\u00a0\u00a3"+
		"\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a4\3\2\2\2\u00a3"+
		"\u00a1\3\2\2\2\u00a4\u00a5\7,\2\2\u00a5\u00a6\7\61\2\2\u00a6\u00a7\3\2"+
		"\2\2\u00a7\u00a8\b\5\2\2\u00a8\n\3\2\2\2\u00a9\u00ad\5I%\2\u00aa\u00ad"+
		"\5O(\2\u00ab\u00ad\5Q)\2\u00ac\u00a9\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac"+
		"\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2"+
		"\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\b\6\2\2\u00b1\f\3\2\2\2\u00b2\u00b3"+
		"\7-\2\2\u00b3\16\3\2\2\2\u00b4\u00b5\7/\2\2\u00b5\20\3\2\2\2\u00b6\u00b7"+
		"\7,\2\2\u00b7\22\3\2\2\2\u00b8\u00b9\7\61\2\2\u00b9\24\3\2\2\2\u00ba\u00bb"+
		"\7\'\2\2\u00bb\26\3\2\2\2\u00bc\u00bd\7@\2\2\u00bd\30\3\2\2\2\u00be\u00bf"+
		"\7@\2\2\u00bf\u00c0\7?\2\2\u00c0\32\3\2\2\2\u00c1\u00c2\7>\2\2\u00c2\34"+
		"\3\2\2\2\u00c3\u00c4\7>\2\2\u00c4\u00c5\7?\2\2\u00c5\36\3\2\2\2\u00c6"+
		"\u00c7\7#\2\2\u00c7\u00c8\7?\2\2\u00c8 \3\2\2\2\u00c9\u00ca\7?\2\2\u00ca"+
		"\u00cb\7?\2\2\u00cb\"\3\2\2\2\u00cc\u00cd\7(\2\2\u00cd\u00ce\7(\2\2\u00ce"+
		"$\3\2\2\2\u00cf\u00d0\7~\2\2\u00d0\u00d1\7~\2\2\u00d1&\3\2\2\2\u00d2\u00d3"+
		"\7#\2\2\u00d3(\3\2\2\2\u00d4\u00d5\7@\2\2\u00d5\u00d6\7@\2\2\u00d6*\3"+
		"\2\2\2\u00d7\u00d8\7>\2\2\u00d8\u00d9\7>\2\2\u00d9,\3\2\2\2\u00da\u00db"+
		"\7(\2\2\u00db.\3\2\2\2\u00dc\u00dd\7~\2\2\u00dd\60\3\2\2\2\u00de\u00df"+
		"\7`\2\2\u00df\62\3\2\2\2\u00e0\u00e1\7\u0080\2\2\u00e1\64\3\2\2\2\u00e2"+
		"\u00e3\7?\2\2\u00e3\66\3\2\2\2\u00e4\u00e5\7-\2\2\u00e5\u00e6\7-\2\2\u00e6"+
		"8\3\2\2\2\u00e7\u00e8\7/\2\2\u00e8\u00e9\7/\2\2\u00e9:\3\2\2\2\u00ea\u00eb"+
		"\7\60\2\2\u00eb<\3\2\2\2\u00ec\u00ed\7*\2\2\u00ed>\3\2\2\2\u00ee\u00ef"+
		"\7+\2\2\u00ef@\3\2\2\2\u00f0\u00f1\7]\2\2\u00f1B\3\2\2\2\u00f2\u00f3\7"+
		"_\2\2\u00f3D\3\2\2\2\u00f4\u00f5\7}\2\2\u00f5F\3\2\2\2\u00f6\u00f7\7\177"+
		"\2\2\u00f7H\3\2\2\2\u00f8\u00f9\7\"\2\2\u00f9J\3\2\2\2\u00fa\u00fb\7."+
		"\2\2\u00fbL\3\2\2\2\u00fc\u00fd\7=\2\2\u00fdN\3\2\2\2\u00fe\u00ff\7\f"+
		"\2\2\u00ffP\3\2\2\2\u0100\u0101\7\13\2\2\u0101R\3\2\2\2\u0102\u0103\7"+
		"k\2\2\u0103\u0104\7p\2\2\u0104\u0105\7v\2\2\u0105T\3\2\2\2\u0106\u0107"+
		"\7d\2\2\u0107\u0108\7q\2\2\u0108\u0109\7q\2\2\u0109\u010a\7n\2\2\u010a"+
		"V\3\2\2\2\u010b\u010c\7u\2\2\u010c\u010d\7v\2\2\u010d\u010e\7t\2\2\u010e"+
		"\u010f\7k\2\2\u010f\u0110\7p\2\2\u0110\u0111\7i\2\2\u0111X\3\2\2\2\u0112"+
		"\u0113\7p\2\2\u0113\u0114\7w\2\2\u0114\u0115\7n\2\2\u0115\u0116\7n\2\2"+
		"\u0116Z\3\2\2\2\u0117\u0118\7x\2\2\u0118\u0119\7q\2\2\u0119\u011a\7k\2"+
		"\2\u011a\u011b\7f\2\2\u011b\\\3\2\2\2\u011c\u011d\7v\2\2\u011d\u011e\7"+
		"t\2\2\u011e\u011f\7w\2\2\u011f\u0120\7g\2\2\u0120^\3\2\2\2\u0121\u0122"+
		"\7h\2\2\u0122\u0123\7c\2\2\u0123\u0124\7n\2\2\u0124\u0125\7u\2\2\u0125"+
		"\u0126\7g\2\2\u0126`\3\2\2\2\u0127\u0128\7k\2\2\u0128\u0129\7h\2\2\u0129"+
		"b\3\2\2\2\u012a\u012b\7g\2\2\u012b\u012c\7n\2\2\u012c\u012d\7u\2\2\u012d"+
		"\u012e\7g\2\2\u012ed\3\2\2\2\u012f\u0130\7h\2\2\u0130\u0131\7q\2\2\u0131"+
		"\u0132\7t\2\2\u0132f\3\2\2\2\u0133\u0134\7y\2\2\u0134\u0135\7j\2\2\u0135"+
		"\u0136\7k\2\2\u0136\u0137\7n\2\2\u0137\u0138\7g\2\2\u0138h\3\2\2\2\u0139"+
		"\u013a\7d\2\2\u013a\u013b\7t\2\2\u013b\u013c\7g\2\2\u013c\u013d\7c\2\2"+
		"\u013d\u013e\7m\2\2\u013ej\3\2\2\2\u013f\u0140\7e\2\2\u0140\u0141\7q\2"+
		"\2\u0141\u0142\7p\2\2\u0142\u0143\7v\2\2\u0143\u0144\7k\2\2\u0144\u0145"+
		"\7p\2\2\u0145\u0146\7w\2\2\u0146\u0147\7g\2\2\u0147l\3\2\2\2\u0148\u0149"+
		"\7t\2\2\u0149\u014a\7g\2\2\u014a\u014b\7v\2\2\u014b\u014c\7w\2\2\u014c"+
		"\u014d\7t\2\2\u014d\u014e\7p\2\2\u014en\3\2\2\2\u014f\u0150\7p\2\2\u0150"+
		"\u0151\7g\2\2\u0151\u0152\7y\2\2\u0152p\3\2\2\2\u0153\u0154\7e\2\2\u0154"+
		"\u0155\7n\2\2\u0155\u0156\7c\2\2\u0156\u0157\7u\2\2\u0157\u0158\7u\2\2"+
		"\u0158r\3\2\2\2\u0159\u015a\7v\2\2\u015a\u015b\7j\2\2\u015b\u015c\7k\2"+
		"\2\u015c\u015d\7u\2\2\u015dt\3\2\2\2\u015e\u015f\7^\2\2\u015f\u0160\7"+
		"$\2\2\u0160v\3\2\2\2\u0161\u0162\7^\2\2\u0162\u0163\7^\2\2\u0163x\3\2"+
		"\2\2\u0164\u0168\t\5\2\2\u0165\u0167\t\6\2\2\u0166\u0165\3\2\2\2\u0167"+
		"\u016a\3\2\2\2\u0168\u0166\3\2\2\2\u0168\u0169\3\2\2\2\u0169z\3\2\2\2"+
		"\u016a\u0168\3\2\2\2\f\2\177\u0083\u0089\u008b\u0096\u00a1\u00ac\u00ae"+
		"\u0168\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
