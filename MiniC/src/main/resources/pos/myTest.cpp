class Base {
public:
  int x;
  void g() { x = x + 2; }
  void x() { x = x + x; }
};

int main(){
  Base b;
  b.g();
  b.x();
  print_int(b.x);
}