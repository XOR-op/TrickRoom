// Generated from Grammar/MxStar.g4 by ANTLR 4.9
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Constant", "DecInteger", "String", "LineComment", "BlockComment", "WhiteSpace", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2?\u0177\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\3\2\3\2\3\2\3\2\3\2\3\2\5\2\u0084\n\2\3\3\3\3\7\3\u0088\n\3\f\3"+
		"\16\3\u008b\13\3\3\3\5\3\u008e\n\3\3\4\3\4\3\4\3\4\7\4\u0094\n\4\f\4\16"+
		"\4\u0097\13\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5\u009f\n\5\f\5\16\5\u00a2\13"+
		"\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\7\6\u00ac\n\6\f\6\16\6\u00af\13\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\6\7\u00b9\n\7\r\7\16\7\u00ba\3\7\3\7\3"+
		"\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17\3"+
		"\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3"+
		"\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\31\3\31\3"+
		"\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3"+
		" \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3"+
		"+\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3/\3/\3"+
		"/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3"+
		"\62\3\62\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3"+
		"\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3"+
		"\67\3\67\3\67\3\67\38\38\38\38\38\38\38\39\39\39\39\3:\3:\3:\3:\3:\3:"+
		"\3;\3;\3;\3;\3;\3<\3<\3<\3=\3=\3=\3>\3>\7>\u0173\n>\f>\16>\u0176\13>\5"+
		"\u0095\u00a0\u00ad\2?\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65i\66k\67m8o9q:s;u<w=y>{?\3\2\6\3\2\63;\3\2\62;\4\2C\\c|\6\2\62"+
		";C\\aac|\2\u0186\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3"+
		"\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2"+
		"\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E"+
		"\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2"+
		"\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2"+
		"\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k"+
		"\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2"+
		"\2\2\2y\3\2\2\2\2{\3\2\2\2\3\u0083\3\2\2\2\5\u008d\3\2\2\2\7\u008f\3\2"+
		"\2\2\t\u009a\3\2\2\2\13\u00a7\3\2\2\2\r\u00b8\3\2\2\2\17\u00be\3\2\2\2"+
		"\21\u00c0\3\2\2\2\23\u00c2\3\2\2\2\25\u00c4\3\2\2\2\27\u00c6\3\2\2\2\31"+
		"\u00c8\3\2\2\2\33\u00ca\3\2\2\2\35\u00cd\3\2\2\2\37\u00cf\3\2\2\2!\u00d2"+
		"\3\2\2\2#\u00d5\3\2\2\2%\u00d8\3\2\2\2\'\u00db\3\2\2\2)\u00de\3\2\2\2"+
		"+\u00e0\3\2\2\2-\u00e3\3\2\2\2/\u00e6\3\2\2\2\61\u00e8\3\2\2\2\63\u00ea"+
		"\3\2\2\2\65\u00ec\3\2\2\2\67\u00ee\3\2\2\29\u00f0\3\2\2\2;\u00f3\3\2\2"+
		"\2=\u00f6\3\2\2\2?\u00f8\3\2\2\2A\u00fa\3\2\2\2C\u00fc\3\2\2\2E\u00fe"+
		"\3\2\2\2G\u0100\3\2\2\2I\u0102\3\2\2\2K\u0104\3\2\2\2M\u0106\3\2\2\2O"+
		"\u0108\3\2\2\2Q\u010a\3\2\2\2S\u010c\3\2\2\2U\u010e\3\2\2\2W\u0112\3\2"+
		"\2\2Y\u0117\3\2\2\2[\u011e\3\2\2\2]\u0123\3\2\2\2_\u0128\3\2\2\2a\u012d"+
		"\3\2\2\2c\u0133\3\2\2\2e\u0136\3\2\2\2g\u013b\3\2\2\2i\u013f\3\2\2\2k"+
		"\u0145\3\2\2\2m\u014b\3\2\2\2o\u0154\3\2\2\2q\u015b\3\2\2\2s\u015f\3\2"+
		"\2\2u\u0165\3\2\2\2w\u016a\3\2\2\2y\u016d\3\2\2\2{\u0170\3\2\2\2}\u0084"+
		"\5\5\3\2~\u0084\5\7\4\2\177\u0084\5[.\2\u0080\u0084\5_\60\2\u0081\u0084"+
		"\5a\61\2\u0082\u0084\5u;\2\u0083}\3\2\2\2\u0083~\3\2\2\2\u0083\177\3\2"+
		"\2\2\u0083\u0080\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084"+
		"\4\3\2\2\2\u0085\u0089\t\2\2\2\u0086\u0088\t\3\2\2\u0087\u0086\3\2\2\2"+
		"\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008e"+
		"\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008e\7\62\2\2\u008d\u0085\3\2\2\2"+
		"\u008d\u008c\3\2\2\2\u008e\6\3\2\2\2\u008f\u0095\7$\2\2\u0090\u0094\5"+
		"w<\2\u0091\u0094\5y=\2\u0092\u0094\13\2\2\2\u0093\u0090\3\2\2\2\u0093"+
		"\u0091\3\2\2\2\u0093\u0092\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0096\3\2"+
		"\2\2\u0095\u0093\3\2\2\2\u0096\u0098\3\2\2\2\u0097\u0095\3\2\2\2\u0098"+
		"\u0099\7$\2\2\u0099\b\3\2\2\2\u009a\u009b\7\61\2\2\u009b\u009c\7\61\2"+
		"\2\u009c\u00a0\3\2\2\2\u009d\u009f\13\2\2\2\u009e\u009d\3\2\2\2\u009f"+
		"\u00a2\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a3\3\2"+
		"\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a4\7\f\2\2\u00a4\u00a5\3\2\2\2\u00a5"+
		"\u00a6\b\5\2\2\u00a6\n\3\2\2\2\u00a7\u00a8\7\61\2\2\u00a8\u00a9\7,\2\2"+
		"\u00a9\u00ad\3\2\2\2\u00aa\u00ac\13\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00af"+
		"\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af"+
		"\u00ad\3\2\2\2\u00b0\u00b1\7,\2\2\u00b1\u00b2\7\61\2\2\u00b2\u00b3\3\2"+
		"\2\2\u00b3\u00b4\b\6\2\2\u00b4\f\3\2\2\2\u00b5\u00b9\5K&\2\u00b6\u00b9"+
		"\5Q)\2\u00b7\u00b9\5S*\2\u00b8\u00b5\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8"+
		"\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2"+
		"\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\b\7\2\2\u00bd\16\3\2\2\2\u00be\u00bf"+
		"\7-\2\2\u00bf\20\3\2\2\2\u00c0\u00c1\7/\2\2\u00c1\22\3\2\2\2\u00c2\u00c3"+
		"\7,\2\2\u00c3\24\3\2\2\2\u00c4\u00c5\7\61\2\2\u00c5\26\3\2\2\2\u00c6\u00c7"+
		"\7\'\2\2\u00c7\30\3\2\2\2\u00c8\u00c9\7@\2\2\u00c9\32\3\2\2\2\u00ca\u00cb"+
		"\7@\2\2\u00cb\u00cc\7?\2\2\u00cc\34\3\2\2\2\u00cd\u00ce\7>\2\2\u00ce\36"+
		"\3\2\2\2\u00cf\u00d0\7>\2\2\u00d0\u00d1\7?\2\2\u00d1 \3\2\2\2\u00d2\u00d3"+
		"\7#\2\2\u00d3\u00d4\7?\2\2\u00d4\"\3\2\2\2\u00d5\u00d6\7?\2\2\u00d6\u00d7"+
		"\7?\2\2\u00d7$\3\2\2\2\u00d8\u00d9\7(\2\2\u00d9\u00da\7(\2\2\u00da&\3"+
		"\2\2\2\u00db\u00dc\7~\2\2\u00dc\u00dd\7~\2\2\u00dd(\3\2\2\2\u00de\u00df"+
		"\7#\2\2\u00df*\3\2\2\2\u00e0\u00e1\7@\2\2\u00e1\u00e2\7@\2\2\u00e2,\3"+
		"\2\2\2\u00e3\u00e4\7>\2\2\u00e4\u00e5\7>\2\2\u00e5.\3\2\2\2\u00e6\u00e7"+
		"\7(\2\2\u00e7\60\3\2\2\2\u00e8\u00e9\7~\2\2\u00e9\62\3\2\2\2\u00ea\u00eb"+
		"\7`\2\2\u00eb\64\3\2\2\2\u00ec\u00ed\7\u0080\2\2\u00ed\66\3\2\2\2\u00ee"+
		"\u00ef\7?\2\2\u00ef8\3\2\2\2\u00f0\u00f1\7-\2\2\u00f1\u00f2\7-\2\2\u00f2"+
		":\3\2\2\2\u00f3\u00f4\7/\2\2\u00f4\u00f5\7/\2\2\u00f5<\3\2\2\2\u00f6\u00f7"+
		"\7\60\2\2\u00f7>\3\2\2\2\u00f8\u00f9\7*\2\2\u00f9@\3\2\2\2\u00fa\u00fb"+
		"\7+\2\2\u00fbB\3\2\2\2\u00fc\u00fd\7]\2\2\u00fdD\3\2\2\2\u00fe\u00ff\7"+
		"_\2\2\u00ffF\3\2\2\2\u0100\u0101\7}\2\2\u0101H\3\2\2\2\u0102\u0103\7\177"+
		"\2\2\u0103J\3\2\2\2\u0104\u0105\7\"\2\2\u0105L\3\2\2\2\u0106\u0107\7."+
		"\2\2\u0107N\3\2\2\2\u0108\u0109\7=\2\2\u0109P\3\2\2\2\u010a\u010b\7\f"+
		"\2\2\u010bR\3\2\2\2\u010c\u010d\7\13\2\2\u010dT\3\2\2\2\u010e\u010f\7"+
		"k\2\2\u010f\u0110\7p\2\2\u0110\u0111\7v\2\2\u0111V\3\2\2\2\u0112\u0113"+
		"\7d\2\2\u0113\u0114\7q\2\2\u0114\u0115\7q\2\2\u0115\u0116\7n\2\2\u0116"+
		"X\3\2\2\2\u0117\u0118\7u\2\2\u0118\u0119\7v\2\2\u0119\u011a\7t\2\2\u011a"+
		"\u011b\7k\2\2\u011b\u011c\7p\2\2\u011c\u011d\7i\2\2\u011dZ\3\2\2\2\u011e"+
		"\u011f\7p\2\2\u011f\u0120\7w\2\2\u0120\u0121\7n\2\2\u0121\u0122\7n\2\2"+
		"\u0122\\\3\2\2\2\u0123\u0124\7x\2\2\u0124\u0125\7q\2\2\u0125\u0126\7k"+
		"\2\2\u0126\u0127\7f\2\2\u0127^\3\2\2\2\u0128\u0129\7v\2\2\u0129\u012a"+
		"\7t\2\2\u012a\u012b\7w\2\2\u012b\u012c\7g\2\2\u012c`\3\2\2\2\u012d\u012e"+
		"\7h\2\2\u012e\u012f\7c\2\2\u012f\u0130\7n\2\2\u0130\u0131\7u\2\2\u0131"+
		"\u0132\7g\2\2\u0132b\3\2\2\2\u0133\u0134\7k\2\2\u0134\u0135\7h\2\2\u0135"+
		"d\3\2\2\2\u0136\u0137\7g\2\2\u0137\u0138\7n\2\2\u0138\u0139\7u\2\2\u0139"+
		"\u013a\7g\2\2\u013af\3\2\2\2\u013b\u013c\7h\2\2\u013c\u013d\7q\2\2\u013d"+
		"\u013e\7t\2\2\u013eh\3\2\2\2\u013f\u0140\7y\2\2\u0140\u0141\7j\2\2\u0141"+
		"\u0142\7k\2\2\u0142\u0143\7n\2\2\u0143\u0144\7g\2\2\u0144j\3\2\2\2\u0145"+
		"\u0146\7d\2\2\u0146\u0147\7t\2\2\u0147\u0148\7g\2\2\u0148\u0149\7c\2\2"+
		"\u0149\u014a\7m\2\2\u014al\3\2\2\2\u014b\u014c\7e\2\2\u014c\u014d\7q\2"+
		"\2\u014d\u014e\7p\2\2\u014e\u014f\7v\2\2\u014f\u0150\7k\2\2\u0150\u0151"+
		"\7p\2\2\u0151\u0152\7w\2\2\u0152\u0153\7g\2\2\u0153n\3\2\2\2\u0154\u0155"+
		"\7t\2\2\u0155\u0156\7g\2\2\u0156\u0157\7v\2\2\u0157\u0158\7w\2\2\u0158"+
		"\u0159\7t\2\2\u0159\u015a\7p\2\2\u015ap\3\2\2\2\u015b\u015c\7p\2\2\u015c"+
		"\u015d\7g\2\2\u015d\u015e\7y\2\2\u015er\3\2\2\2\u015f\u0160\7e\2\2\u0160"+
		"\u0161\7n\2\2\u0161\u0162\7c\2\2\u0162\u0163\7u\2\2\u0163\u0164\7u\2\2"+
		"\u0164t\3\2\2\2\u0165\u0166\7v\2\2\u0166\u0167\7j\2\2\u0167\u0168\7k\2"+
		"\2\u0168\u0169\7u\2\2\u0169v\3\2\2\2\u016a\u016b\7^\2\2\u016b\u016c\7"+
		"$\2\2\u016cx\3\2\2\2\u016d\u016e\7^\2\2\u016e\u016f\7^\2\2\u016fz\3\2"+
		"\2\2\u0170\u0174\t\4\2\2\u0171\u0173\t\5\2\2\u0172\u0171\3\2\2\2\u0173"+
		"\u0176\3\2\2\2\u0174\u0172\3\2\2\2\u0174\u0175\3\2\2\2\u0175|\3\2\2\2"+
		"\u0176\u0174\3\2\2\2\r\2\u0083\u0089\u008d\u0093\u0095\u00a0\u00ad\u00b8"+
		"\u00ba\u0174\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}