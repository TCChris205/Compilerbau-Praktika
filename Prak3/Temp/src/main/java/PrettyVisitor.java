import org.antlr.v4.runtime.tree.ParseTree;

public class PrettyVisitor extends ProgBaseVisitor<String> {

  @Override
  public String visitStart(ProgParser.StartContext ctx) {
    StringBuilder s = new StringBuilder();

    for (ProgParser.LineContext p : ctx.line()) {
      s.append(p.accept(this));
    }
    return s.toString();
  }

  @Override
  public String visitLine(ProgParser.LineContext ctx) {
    StringBuilder s = new StringBuilder();

    for (ParseTree p : ctx.children) {
      s.append("\n");
      s.append(p.accept(this));
    }
    return s.toString();
  }

  @Override
  public String visitIfStmt(ProgParser.IfStmtContext ctx) {
    StringBuilder s = new StringBuilder();
    s.append("if ").append(ctx.comp().accept(this)).append(" do");
    s.append(ctx.block(0).accept(this));

    if (ctx.block().size() > 1) {
      s.append("\nelse do").append(ctx.block(1).accept(this));
    }
    s.append("\nend");

    return s.toString();
  }

  @Override
  public String visitBlock(ProgParser.BlockContext ctx) {
    StringBuilder s = new StringBuilder();

    for (ParseTree p : ctx.children) {
      s.append(p.accept(this));
    }
    return s.toString().replace("\n", "\n\t");
  }

  @Override
  public String visitWhileStmt(ProgParser.WhileStmtContext ctx) {
    StringBuilder s = new StringBuilder();
    s.append("while ").append(ctx.comp().accept(this)).append(" do");
    s.append(ctx.block().accept(this));
    s.append("\nend");

    return s.toString();
  }

  @Override
  public String visitInstr(ProgParser.InstrContext ctx) {
    StringBuilder s = new StringBuilder();
    s.append(ctx.ID().getText())
        .append(" ")
        .append(ctx.ASSIGN().getText())
        .append(" ")
        .append(ctx.comp().accept(this));

    return s.toString();
  }

  @Override
  public String visitComp(ProgParser.CompContext ctx) {
    StringBuilder s = new StringBuilder();

    s.append(ctx.arith(0).accept(this));

    for (int i = 1; i < ctx.children.size(); i++) {
      ParseTree child = ctx.getChild(i);
      if (child.getText().equals("==")
          || child.getText().equals("!=")
          || child.getText().equals(">")
          || child.getText().equals("<")) {
        s.append(" ").append(child.getText()).append(" ");
      } else {
        s.append(child.accept(this));
      }
    }

    return s.toString();
  }

  @Override
  public String visitArith(ProgParser.ArithContext ctx) {
    StringBuilder s = new StringBuilder();

    s.append(ctx.term(0).accept(this));

    for (int i = 1; i < ctx.children.size(); i++) {
      ParseTree child = ctx.getChild(i);
      if (child.getText().equals("+") || child.getText().equals("-")) {
        s.append(" ").append(child.getText()).append(" ");
      } else {
        s.append(child.accept(this));
      }
    }

    return s.toString();
  }

  @Override
  public String visitTerm(ProgParser.TermContext ctx) {
    StringBuilder s = new StringBuilder();

    s.append(ctx.atom(0).accept(this));

    for (int i = 1; i < ctx.children.size(); i++) {
      ParseTree child = ctx.getChild(i);
      if (child.getText().equals("*") || child.getText().equals("/")) {
        s.append(" ").append(child.getText()).append(" ");
      } else {
        s.append(child.accept(this));
      }
    }

    return s.toString();
  }

  @Override
  public String visitAtom(ProgParser.AtomContext ctx) {

    return ctx.getChild(0).getText();
  }
}
