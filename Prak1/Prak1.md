# Praktikum 1

## Task 1.1

Alle Worte sind entweder ein einzelnes a oder ein aa mit beliebig vielen a's oder b's dazwischen (z.B. aaa, aba, 
ababbbabbbabbba...)

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

## Task 1.6
a((b+c)*dc)*((b+c)*d(a+b))