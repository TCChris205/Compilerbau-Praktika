grammar Blatt8;

// --------------------------
// PARSER REGELN
// --------------------------

start
    : line+
    ;

line
    : statement
    | functionDeclaration
    | classDeclaration
    ;

classDeclaration
    : CLASS_KEY ID (COLON PUBLIC_KEY ID)? LBLOCKPAREN
      PUBLIC_KEY COLON classMember*
      RBLOCKPAREN SEMICOL
    ;

classMember
    : attributeDeclaration
    | constructorDefinition
    | methodDefinition
    ;

attributeDeclaration
    : type ID SEMICOL
    ;

methodDefinition
    : VIRTUAL_KEY? type ID LPAREN paramList? RPAREN block
    ;

constructorDefinition
    : Id LPAREN paramList? RPAREN block
    ;
    
paramList
    : typeReference ID (COMMA typeReference ID)*
    ;

statement
    :   variableDeclaration SEMICOL
    |   functionCall SEMICOL   
    |   expression SEMICOL
    |   ifStatement
    |   whileLoop
    |   returnStatement
    ;

expression
    :   idDotChain
    |   comparison
    |   functionCall
    |   boolean
    |   NUM
    |   STRING
    |   CHAR
    |   NOT expression
    |   LPAREN expression RPAREN
    ;

assignment
    :   idDotChain DEEPCOPY? ASSIGN expression
    ;

comparison
    :   expression comparator expression 
    ;

comparator
    :   NEQ
    |   EQ
    |   LT
    |   LE
    |   GT
    |   GE
    ;

ifStatement
    :   IF_KEY LPAREN expression RPAREN block ELSE_KEY block
    |   IF_KEY LPAREN expression RPAREN block
    ;

whileLoop
    :   WHILE_KEY LPAREN expression RPAREN block
    ;

functionCall
    :   idDotChain LPAREN (expression COMMA)* RPAREN
    ;

variableDeclaration
    :   type ID ASSIGN expression
    |   type ID
    ;

functionDeclaration
    :   type ID LPAREN (expression, COMMA)* RPAREN block
    ;

idDotChain
    : (ID | functionCall) (DOT (ID | functionCall))*
    ;

typeReference
    : type DEEPCOPY?
    ;

type
    : primitiveTypeKey
    | ID
    ;

primitiveTypeKey
    :   INT_KEY
    |   BOOL_KEY
    |   STRING_KEY
    |   CHAR_KEY
    ;

returnStatement
    :   RETURN_KEY expression? SEMICOL
    ;

block
    :   LBLOCKPAREN (statement)* RBLOCKPAREN
    ;

boolean
    : TRUE 
    | FALSE
    ;

// --------------------------
// LEXER REGELN
// --------------------------

// KEY WORDS

INT_KEY     : 'int' ;
BOOL_KEY    : 'bool' ;
CHAR_KEY    : 'char' ;
STRING_KEY  : 'string' ;
TRUE_KEY    : 'true' ;
FALSE_KEY   : 'false' ;
IF_KEY      : 'if' ;
ELSE_KEY    : 'else' ;
WHILE_KEY   : 'while' ;
RETURN_KEY  : 'return' ;
CLASS_KEY   : 'class' ;
VOID_KEY    : 'void' ;
PUBLIC_KEY  : 'public' ;
VIRTUAL_KEY : 'virtual' ;

// COMMENTS

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/'  -> skip
    ;

INCLUDE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

// Operators

ASSIGN      : '=' ;
EQ          : '==' ;
NEQ         : '!=';
LT          : '<';
LE          : '<=';
GT          : '>';
GE          : '>=';

NOT         : '!' ;
AND         : '&&' ;
OR          : '||' ;

PLUS        : '+' ;
MINUS       : '-' ;
MUL         : '*' ;
DIV         : '/' ;
MOD         : '%' ;

// LITERALS

NUM         : [0-9]+ ;
CHAR        : "'" ([a-zA-Z0-9]_") | ('\'['n'|'t'|'r'|'0'|"'"]) "'" ;
STRING      : '"' (~["\\] | '\\' .)* '"' ;

// Symbols
DOT         : '.' ;
SEMICOL     : ';' ;
COLON       : ';' ;
LPAREN      : '(' ;
RPAREN      : ')' ;
COMMA       : ',';
LBLOCKPAREN : '{';
RBLOCKPAREN : '}';
DEEPCOPY   : '&'

TRUE        : 'true' ;
FALSE       : 'false' ;

PENIS       : 'penis';
ID
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
