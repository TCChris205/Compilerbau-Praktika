import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;



public class Main {
    static void main(String... args) throws IOException, URISyntaxException {

        try (InputStream in = Main.class.getResourceAsStream("pos/GOLD01_basics.cpp")) {
            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            MiniCppLexer lexer = new MiniCppLexer(CharStreams.fromString(text));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniCppParser parser = new MiniCppParser(tokens);

            MiniCppParser.StartContext tree = parser.start();


            AST ast = new AST();
            AST.ASTToken astToken = ast.toAST(tree);
            System.out.println(astToken.toString());
        }
    }
}
