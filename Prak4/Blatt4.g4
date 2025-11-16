grammar Blatt4;

// --------------------------
// PARSER REGELN
// --------------------------

start
    : expression+ EOF
    ;

expression
    : literal
    | ifExpr
    | defExpr
    | defnExpr
    | letExpr
    | doExpr
    | printExpr
    | strExpr
    | callExpr
    | listExpr
    | nthExpr
    | headExpr
    | tailExpr
    | ID
    ;

literal
    : NUM
    | STRING
    | TRUE
    | FALSE
    ;

printExpr
    : '(' PRINT literal ')'
    ;

strExpr
    : '(' STRKW (literal|ID)+ ')'
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
    : '(' DEFN ID params expression ')'
    ;

params
    : '(' ID* ')'
    ;

letExpr
    : '(' LET '(' (ID expression)+ ')' expression ')'
    ;

doExpr
    : '(' DO expression+ ')'
    ;

nthExpr
    : '(' NTH listExpr (NUM|ID)')'
    ;

headExpr
    : '(' HEAD listExpr ')'
    ;

tailExpr
    : '(' TAIL listExpr ')'
    ;

// --------------------------
// LEXER REGELN
// --------------------------

NUM         : [0-9]+ ;
STRING      : '"' (~["\\] | '\\' .)* '"' ;

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

ID          : [a-zA-Z][a-zA-Z0-9_]* ;

AOP         : '+' | '-' | '*' | '/' ;
COP         : '=' | '<' | '>' ;

COMMENT
    : ';;' ~[\n]* -> skip
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
