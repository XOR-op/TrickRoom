grammar MxStar;

// Grammar

code: (classDef | declarationStatement | functionDef)*;

// Function

functionDef:
	returnType Identifier L_PARENTNESS functionParamDef R_PARENTNESS suite;

functionParamDef: (
		varType Identifier (COMMA varType Identifier)*
	)?;

// Class

classDef:
	CLASS_KW Identifier L_BRACE (
		declarationStatement
		| functionDef
		| constructorDefinition
	)* R_BRACE SEMICOLON;

constructorDefinition:
	Identifier L_PARENTNESS functionParamDef R_PARENTNESS suite;
// parameter as extension

expressionList: expression (COMMA expression)*;

// Statement
suite: L_BRACE statement* R_BRACE;

statement:
	suite
	| declarationStatement
	| ifStatement
	| whileStatement
	| forStatement
	| continueStatement
	| breakStatement
	| returnStatement
	| simpleStatement;

ifStatement:
	IF_KW L_PARENTNESS expression R_PARENTNESS trueStat = statement (
		ELSE_KW falseStat = statement
	)?;

whileStatement:
	WHILE_KW L_PARENTNESS expression R_PARENTNESS statement;

forStatement:
	FOR_KW L_PARENTNESS initExp = exprOrDecl? SEMICOLON condExp = expression? SEMICOLON updExp =
		expression? R_PARENTNESS statement;

continueStatement: CONTINUE_KW SEMICOLON;

breakStatement: BREAK_KW SEMICOLON;

returnStatement: RETURN_KW expression? SEMICOLON;

simpleStatement: expression? SEMICOLON;

declarationStatement:
	declExpr SEMICOLON; // multi initializer as extension

// Expression
atomExp:
	Identifier
	| constant
	| L_PARENTNESS expression R_PARENTNESS;

expression:
	atomExp												                                # atomExpr
	| NEW_KW (arraySemanticError|arrayLiteral | constructorCall | Identifier)		    # newExpr
	| expression DOT Identifier							                                # memberExpr
	| expression L_BRACKET expression R_BRACKET			                                # indexExpr
	| expression L_PARENTNESS expressionList? R_PARENTNESS                            	# funcExpr
	| expression suffix = (SELF_PLUS | SELF_MINUS)		                                # suffixExpr
	| <assoc = right> prefix = unaryOp expression		                                # prefixExpr
	| expression multiplicativeOp expression			                                # binaryExpr
	| expression additiveOp expression					                                # binaryExpr
	| expression shiftOp expression						                                # binaryExpr
	| expression relationalCmpOp expression				                                # binaryExpr
	| expression equalityCmpOp expression				                                # binaryExpr
	| expression AND_ARI expression						                                # binaryExpr
	| expression XOR_ARI expression						                                # binaryExpr
	| expression OR_ARI expression						                                # binaryExpr
	| expression AND_LOGIC expression					                                # binaryExpr
	| expression OR_LOGIC expression					                                # binaryExpr
	| <assoc = right> expression ASSIGNMENT expression	                                # assignExpr;

// Basic Compnent
constructorCall: Identifier L_PARENTNESS expressionList? R_PARENTNESS;
exprOrDecl: expression | declExpr;
declExpr: varType varDeclaration (COMMA varDeclaration)*;
arraySemanticError:(builtinType | Identifier) (L_BRACKET expression R_BRACKET)+ (
                   		L_BRACKET R_BRACKET
                   	)+(L_BRACKET expression R_BRACKET)+ ;
arrayLiteral:
	(builtinType | Identifier) (L_BRACKET expression R_BRACKET)+ (
		L_BRACKET R_BRACKET
	)*;

varDeclaration: Identifier (ASSIGNMENT expression)?;
varType: varType (L_BRACKET R_BRACKET)+ | builtinType | Identifier;
returnType: VOID_KW | varType;
builtinType: INT_KW | STRING_KW | BOOL_KW;

// Token
DecInteger: [1-9][0-9]* | '0';
String: '"' (ESCAPE_DB_QUOTATION | ESCAPE_BACKSLASH | .)*? '"';
LineComment: '//' (~'\n')* -> skip;
BlockComment: '/*' .*? '*/' -> skip;
WhiteSpace: (SPACE | LINE_BREAK | TAB)+ -> skip;

// Associativity
constant:
	DecInteger
	| String
	| NULL_KW
	| TRUE_KW
	| FALSE_KW
	| THIS_KW;
unaryOp:
	SELF_PLUS
	| SELF_MINUS
	| PLUS
	| MINUS
	| NOT_LOGIC
	| NOT_ARI;
multiplicativeOp: MUL | DIV | MOD;
additiveOp: PLUS | MINUS;
shiftOp: LEFT_SHIFT | RIGHT_SHIFT;
relationalCmpOp: LESS | LESS_EQ | GREATER | GREATER_EQ;
equalityCmpOp: EQUAL | NOT_EQ;

// Sign
PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';
MOD: '%';
GREATER: '>';
GREATER_EQ: '>=';
LESS: '<';
LESS_EQ: '<=';
NOT_EQ: '!=';
EQUAL: '==';
AND_LOGIC: '&&';
OR_LOGIC: '||';
NOT_LOGIC: '!';
RIGHT_SHIFT: '>>';
LEFT_SHIFT: '<<';
AND_ARI: '&';
OR_ARI: '|';
XOR_ARI: '^';
NOT_ARI: '~';
ASSIGNMENT: '=';
SELF_PLUS: '++';
SELF_MINUS: '--';
DOT: '.';
L_PARENTNESS: '(';
R_PARENTNESS: ')';
L_BRACKET: '[';
R_BRACKET: ']';
L_BRACE: '{';
R_BRACE: '}';
SPACE: ' ';
COMMA: ',';
SEMICOLON: ';';
LINE_BREAK: '\n';
TAB: '\t';
INT_KW: 'int';
BOOL_KW: 'bool';
STRING_KW: 'string';
NULL_KW: 'null';
VOID_KW: 'void';
TRUE_KW: 'true';
FALSE_KW: 'false';
IF_KW: 'if';
ELSE_KW: 'else';
FOR_KW: 'for';
WHILE_KW: 'while';
BREAK_KW: 'break';
CONTINUE_KW: 'continue';
RETURN_KW: 'return';
NEW_KW: 'new';
CLASS_KW: 'class';
THIS_KW: 'this';
ESCAPE_DB_QUOTATION: '\\"';
ESCAPE_BACKSLASH: '\\\\';

Identifier: [a-zA-Z][a-zA-Z0-9_]*;
