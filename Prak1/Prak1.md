# Praktikum 1

## Task 1.1

Alle Worte sind entweder ein einzelnes a oder ein aa mit beliebig vielen a's oder b's dazwischen (z.B. aaa, aba, ababbbabbbabbba...)

## Task 1.2

### Regulärer Ausdruck

(a-z + A-Z)(a-z + A-Z + 0-9 + _ )*(a-z + A-Z + 0-9)

1. VPeta
V = (a-z + A-Z)
Pet = (a-z + A-Z + 0-9 + _ )*
a = (a-z + A-Z + 0-9)

2. a_RAmboss
a = (a-z + A-Z)
_RAmbos = (a-z + A-Z + 0-9 + _ )*
s = (a-z + A-Z + 0-9)

### DFA

![image](DFA.png "e")

1. Deutschland
D = q1 -> q2
eutschlan = q2 -> q2
d = q2 -> q2

2. Die_Kiste123
D = q1 -> q2
ie = q2 -> q2
_ = q2 -> q3
K = q3 -> q2
site12 = q2 -> q2
3 = q2 -> q2

### Grammatik

({N1,N2}, {a-z, A-Z, 1-9, _}, P, N1)

P:
N1 -> a-zN2 | A-ZN2
N2 -> a-zN2 | A-ZN2 | 0-9N2 | _N2 | a-z | A-Z | 0-9

![image](Ableitungsbaum.png "Ableitungsbaum")

## Task 1.3

### Python

In Python muss eine Gleitkommazahl entweder einen Punkt oder ein e/E enthalten. Nach einem e/E kann ein +/- folgen.
Die genauen Regeln sind in in der [Python Dokumentation](https://docs.python.org/3/reference/lexical_analysis.html) nach zu lesen.

#### Python-Regex

((0-9)(\_(0-9))\* . (0-9) (\_(0-9))\* (((e + E)(+ + - + ε)) + ε) (0-9) (\_(0-9))\*) +
(. (0-9) (\_(0-9))\* (((e + E)(+ + - + ε)) + ε) (0-9) (\_(0-9))\*) +
((0-9) (\_(0-9))\* (e + E) (+ + - + ε) (0-9) (\_(0-9))\*)

#### Python-DFA

![image](DFAPython.jpg "DFAPython")

#### Python-reguläre Grammatik

### Java

Java speichert Gleitkommazahlen in zwei Varianten ab. Floates werden in 4Bytes abgespeichert und können bis zu ca. 7 Nachkommastellen darstellen. Doubles nutzen 8 Bytes und können somit doppelt so viele Nachkommastellen abspeichern, ca. 14 Stück.

#### Java-Regex

#### Java-DFA
![image](DFAJava.jpg "DFAJava")
#### Java-reguläre Grammatik

## Task 1.4

Warum ist der Regex ungeeignet?

- Schreibweise a-z ungeeignet (siehe Aufgabe)
- akzeptiert keine Großbuchstaben
- nach dem @ kann nur ein einzelner Buchstabe gefolgt vom . und noch einem Buchstaben kommen (aaa@gmail.com ist nicht möglich)
- letztes a-z kann besser alle gültigen endungen enthalten (.com + .de + .ch + .fr + ...)
- Anfangsteil und Domain aktzepieren keine Zahlen

Warum ist  a + b + c + c + … + z nicht richtig?

- verwechslungsgefahr mit '…' ist das Zeichen selbst oder alle buchstaben von d bis y gemeint?
- Ist … ein valides Zeichen?

(a-z + A-Z + 1-9)\* @ (a-z + A-Z + 1-9)\* (.com + .de + .ch + .fr + …)

Alle Gültigen Domainenden müssten aufgelistet werden

## Task 1.5

![image](Aufgabe1.5.jpg "DFA")

## Task 1.6

a((b+c)\*dc)\*((b+c)\*d(a+b))
