import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import my.pkg.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {
  static void main(String... args) throws IOException, URISyntaxException {

    try (InputStream in = Main.class.getResourceAsStream("/test.txt")) {
      String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
      ProgLexer lexer = new ProgLexer(CharStreams.fromString(text));
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      ProgParser parser = new ProgParser(tokens);

      PrettyVisitor p = new PrettyVisitor();
      IO.println(parser.start().accept(p));
    }
  }
}
