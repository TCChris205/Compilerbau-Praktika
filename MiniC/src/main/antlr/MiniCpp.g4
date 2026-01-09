grammar MiniCpp;

// --------------------------
// PARSER REGELN
// --------------------------

start
    : line+
    ;

line
    : statement
    | classDeclaration
    ;
// ----------------------- class -----------------------
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
    : ID LPAREN paramList? RPAREN block
    ;

paramList
    : typeReference ID (COMMA typeReference ID)*
    ;
// ----------------------- statment -----------------------
statement
    :   block
    |   functionDeclaration
    |   variableDeclaration
    |   returnStatement
    |   ifStatement
    |   whileLoop
    |   expression SEMICOL
    ;

block
    :   LBLOCKPAREN statement* RBLOCKPAREN
    ;

functionDeclaration
    :   type ID LPAREN paramList? RPAREN (ASSIGN expression)? block
    ;

variableDeclaration
    :   type ID (ASSIGN expression)? SEMICOL
    |   type DEEPCOPY ID ASSIGN (idChain DOT)? ID SEMICOL
    ;

idChain
    : ID (DOT idChain)?
    | ID (LPAREN args? RPAREN) (DOT idChain)?
    ;

returnStatement
    :   RETURN_KEY expression? SEMICOL
    ;

ifStatement
    :   IF_KEY LPAREN expression RPAREN block (ELSE_KEY block)?
    ;

whileLoop
    :   WHILE_KEY LPAREN expression RPAREN block
    ;

// ----------------------- expression -----------------------

expression
    :   assignment
    |   logicalOr
    ;

assignment
    : (idChain DOT)? ID ASSIGN logicalOr
    ;

logicalOr
    : logicalAnd (OR logicalAnd)*
    ;

logicalAnd
    : equal (AND equal)*
    ;

equal
    : relation ((EQ|NEQ) relation)*
    ;

relation
    : arith ((LT|LE|GT|GE) arith)*
    ;

arith
    : term ((PLUS|MINUS) term)*
    ;

term
    : unary ((MUL|DIV|MOD) unary)*
    ;

unary
    : NOT? idChain
    | NOT? (PLUS|MINUS)? literals
    | NOT? LPAREN expression RPAREN
    ;

args
    : expression (COMMA expression)*
    ;

literals
    : NUM
    | CHAR
    | STRING
    | TRUE
    | FALSE
    | ID
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
    |   VOID_KEY
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
CHAR        : '\'' (~['] | '\\' [ntr0'\\]) '\'' ;

STRING      : '"' (~["\\] | '\\' .)* '"' ;

// Symbols
DOT         : '.' ;
SEMICOL     : ';' ;
COLON       : ':' ;
LPAREN      : '(' ;
RPAREN      : ')' ;
COMMA       : ',' ;
LBLOCKPAREN : '{' ;
RBLOCKPAREN : '}' ;
DEEPCOPY    : '&' ;

TRUE        : 'true' ;
FALSE       : 'false' ;

ID
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
