# Praktikum 2

## 1

![image](doppeltAwieC.png "Bild des PDA")

Anmerkung: Die in den Übergängen angegebene Reihenfolge der auf den Stack zu legenden Keller-Buchstaben ist invertiert. D.h., dass der Buchstabe ganz links am Ende der Operation derjenige ist, der ganz oben auf dem Stack liegt.

### bcaba

b -> q2, Stack = [#
c -> q1, Stack = [#, 1b, 1
ϵ -> q0, Stack = [#, 1b, 1
a -> q0, Stack = [#, 1b
b -> q0, Stack = [#, 1b
a -> q2, Stack = [#

### bccac

b -> q2, Stack = [#
c -> q1, Stack = [#, 1b, 1
ϵ -> q0, Stack = [#, 1b, 1
c -> q1, Stack = [#, 1b, 1, 1, 1
ϵ -> q0, Stack = [#, 1b, 1, 1, 1
a -> q0, Stack = [#, 1b, 1, 1
c -> q1, Stack = [#, 1b, 1, 1, 1, 1
ϵ -> q0, Stack = [#, 1b, 1, 1, 1, 1

Endet in q0 -> Illegaler Zustand

## 2

## 3

Die Sprache besteht aus endlosen Ketten an "if" und "else". Nach jedem if kommt eine undefinierte Condition. Nach jeder Condition kann ein "if" oder "else" kommen. Ein "else" kann erst nach einer unendlichen menge an "if" folgen. D.h. wenn wir nur die ersten n Zeichen eines Wortes der Sprache betrachten, dann wäre es in jedem Fall "if Condition if Condition if Condition ..."


Die Sprache ist nicht eindeutig:

Statement -> "if" Condition Statement -> "if" Condition "if" Condition Statement "else" Statement

ergibt das selbe wie

Statement -> "if" Contidion Statement "else" Statement -> "if" Condition "if" Condition Statement "else" Statement

## 4
