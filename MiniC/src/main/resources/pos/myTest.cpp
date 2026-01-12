#include "hsbi_runtime.h"

int inc(int& r) { r = r + 1; return r; }
void swap2(int& a, int& b) { int t = a; a = b; b = t; }

int main() {
   int x = 10;

   int a = 3;
   int b = 9;
   swap2(a, b);

   // lokale Referenz
   int& rx = x;
   rx = rx + 5;

   return 0;
}
/* EXPECT (Zeile für Zeile):
11
11
9
3
16
*/
