grammar Blatt4;

// --------------------------
// PARSER REGELN
// --------------------------

start
    : expression+ EOF
    ;

expression
    : literal
    | ID
    | listExpr
    | callExpr
    | ifExpr
    | defExpr
    | defnExpr
    | letExpr
    | doExpr
    ;

literal
    : NUM
    | STRING
    | TRUE
    | FALSE
    ;

listExpr
    : '(' LIST expression+ ')'
    ;

callExpr
    : '(' operator expression+ ')'
    | '(' ID expression* ')'
    ;

operator
    : AOP
    | COP
    ;

ifExpr
    : '(' IF expression expression (expression)? ')'
    ;

defExpr
    : '(' DEF ID expression ')'
    ;

defnExpr
    : '(' DEFN ID '(' ID* ')' expression ')'
    ;

letExpr
    : '(' LET '(' ID expression ')' expression ')'
    ;

doExpr
    : '(' DO expression+ ')'
    ;

// --------------------------
// LEXER REGELN
// --------------------------

NUM         : [0-9]+ ;
STRING      : '"' (~["\\] | '\\' .)* '"' ;
ID          : [a-zA-Z][a-zA-Z0-9_]* ;

TRUE        : 'true' ;
FALSE       : 'false' ;

PRINT       : 'print' ;
STRKW       : 'str' ;
DEF         : 'def' ;
DEFN        : 'defn' ;
LET         : 'let' ;
LIST        : 'list' ;
HEAD        : 'head' ;
TAIL        : 'tail' ;
NTH         : 'nth' ;
IF          : 'if' ;
DO          : 'do' ;

AOP         : '+' | '-' | '*' | '/' ;
COP         : '=' | '<' | '>' ;

COMMENT
    : ';;' ~[\n]* -> skip
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
