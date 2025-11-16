# Praktikum 4

## 1

$First_1(S) = \{1,3\}$  
$First_1(A) = \{2, \epsilon\}$  
$First_1(1AS) = 1$  
$First_1(3) = 3$  
$First_1(2AS) = 2$  
$First_1(\epsilon) = \epsilon$  

$Follow(S) = \{\$, 1, 3\}$  
$Follow(A) = \{1,3\}$  

$First_1(1AS)$ und $First_1(3)$ sind disjunkt.  
$First_1(2AS)$ und $First_1(\epsilon)$ sind disjunkt.  

Weil Epsilon in Regeln enthalten -> auch Follow Mengen betrachten

$First_1(A) = \{2, \epsilon\}$  
$Follow(A) = \{1,3\}$  

First und Follow Menge überschneiden nicht, also LL(1) Grammatik

## 2

Blatt4.g4

## 3

Lexer.py

## 4

Parser.py

## 5

## 6

- GCC
  - Gewechselt 2004/5
- Javac
- Pascal, Modula, Oberon
  - Einfache und Simple Implementationsmöglichkeit
- Go

### ANTLR

#### Pro

- übernimmt Großteil der Aufgaben
- automatisiert Prozess sobald Grammatik gegeben ist
- Kann live Bäume generieren
- Simples Testen
- Bietet Möglichkeiten zur Einbindung von Visitorn und Listenern

#### Contra

- nervig aufzusetzen
- dreht groß- und kleinschreibungskonvention um
- Java
