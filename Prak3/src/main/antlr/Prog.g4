grammar Prog;


// Parser
start   : (line)* EOF;

line:   instr|
        whileStmt|
        ifStmt
;

ifStmt  :IF comp  DO  block (ELSE  DO  block)? END ;

block   :(line)+ ;

whileStmt   :WHILE comp  DO  block  END ;

instr   : ID ASSIGN comp;

comp    : arith (('=='|'!='|'<'|'>') arith)*;

arith   : term (('+'|'-') term)*;

term    : atom (('*'|'/') atom)*;

atom    : ID | NUM | STRING;


// Lexer

IF      : 'if';
ELSE    : 'else';
DO      : 'do';
WHILE   : 'while';
END     : 'end';
ASSIGN  : ':=';

ID      : [a-z][a-zA-Z]* ;
NUM     : [0-9]+ ;
STRING  : '"' (~["\r\n])* '"';


WS      : [ \t\r\n]+ -> skip ;
