# TODO

## Lexer & Parser

- [x] Typen
  - [x] bool -> true, false
  - [x] int -> Ziffernfolgen ohne Dezimalpunkt
  - [x] char -> Character in einfachen Anführungsstrichen, etwa 'a'
  - [x] string -> Zeichenkette in doppelten Anführungsstrichen, etwa "foo"
- [x] Variablen und Zuweisung
  - [x] Deklaration -> T x;
  - [x] Initialisierung -> T x = expr;
  - [x] Zuweisung -> =
  - Nur lokale, keine globalen Variablen
- [x] Ausdrücke
  - [x] Arithmetik (Nur für int)
    - [x] Vorzeichen -> unäre Ausdrücke, +4, -4
    - [x] +, -, *, /, %
  - [x] Vergleiche
    - [x] ==
    - [x] !=
    - [x] <
    - [x] <=
    - [x] >
    - [x] >=
    - Alle außer '==' und '!=' nur für int und char, sonst auch bool und string
  - [x] Logik
    - [x] &&
    - [x] ||
    - [x] ! -> Nur für Bool
  - [x] Klammern Zur Gruppierung -> (...)
  - [x] Funktionsaufrufe -> f(args)
  - [x] Attribut-/Methodenzugriff -> obj.a / obj.m(args)
- [x] Kontrollfluss
  - [x] if-else -> Else Optional
  - [x] while -> while
  - [x] Block -> {...}
  - [x] return
- [x] Funktionen/Methoden
  - [x] Definition -> type name(Attr){...}
  - [x] Call -> name(attr)
- [x] "light" C++ Referenzen
  - [x] Deklaration var -> T& x = expr
  - [x] Deklaration Parameter -> T& p
  - [x] Referenzvariable -> int x = 10, int& rx = x -> Logik dafür in Semantischer
- [x] Klassen und Vererbung, Polymorphie
  - [x] Klassen -> class A {public: Attribute + Methoden}; -> Alles public, kein private
  - [x] Attribute -> Basistypen oder Klassen -> int x, MyClass x
  - [x] Konstruktor -> T x; -> called T() oder T x = T(args);
  - [x] Methoden -> Auch als virtual
  - [x] Vererbung -> class D: public B {...}, B ist die Elternklasse
- [x] Kommentare

## AST

- [x] Expression entfernen
  - [x] statement
  - [x] functionDeclaration
  - [x] variableDeclaration
  - [x] returnStatement
  - [x] ifStatement
  - [x] whileLoop
  - [x] unary
  - [x] args
  - [x] Klasse entfernen
- [x] NOT Remove 

- [ ] DotChains

## Sem. Analyse

- [ ] Referenzvariable -> int x = 10, int& rx = x, dass rx immer gleich x ist
- [ ] Overload (Auch Konstruktor)  -> Overloads dürfen nur bei Exakt passender Signatur (Name, Attributanzahl und Typ) gecalled werden
- [ ] Vererbung -> class D: public B {...}, B ist die Elternklasse
- [ ] Bei Vergleichen: Alle außer '==' und '!=' nur für int und char, sonst auch bool und string
- [ ] Full Planning still necessary

## Interpreter

- [ ] Standartfunktionen implementieren
  - [ ] print_bool
  - [ ] print_int
  - [ ] print_char
  - [ ] print_string
- [ ] Ein SourceFile mit einer `int main()` oder `void main()`
- [ ] Planning
