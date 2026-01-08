import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Brrr {
    public static void main(String[] args) {
        URL url = Brrr.class.getResource("pos\\GOLD01_basics.cpp");
        String txt = Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8);

        MiniCppLexer = new MiniCppLexer(txt);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniCppParser = new MiniCppParser(tokens);

        ParseTree tree = parser.start(); // Start-Regel

        IO.println(tree.toStringTree(parser));
    }
}
