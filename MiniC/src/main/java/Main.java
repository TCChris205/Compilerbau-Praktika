import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {
    static void main(String... args) throws IOException, URISyntaxException {

        String path;
        // path = "pos/myTest.cpp";

        // path = "pos/GOLD01_basics.cpp";
        // path = "pos/GOLD02_ref_params.cpp";
        // path = "pos/GOLD03_classes_dispatch.cpp";
        // path = "pos/GOLD04_slicing.cpp";
        path = "pos/GOLD05_virtual_override.cpp";
        // path = "pos/GOLD06_constructors_basic.cpp";
        // path = "pos/GOLD07_constructors_inheritance.cpp";

        // path = "pos/P01_vars.cpp";
        // path = "pos/P02_expr.cpp";
        // path = "pos/P03_ifthenelse.cpp";
        // path = "pos/P04_while.cpp";
        // path = "pos/P05_operators.cpp";
        // path = "pos/P06_refs.cpp";
        // path = "pos/P07_scopes_and_shadowing.cpp";
        // path = "pos/P08_func.cpp";
        // path = "pos/P09_short_circuit.cpp";

        // path = "pos/P10_class_defaults.cpp";
        // path = "pos/P11_class_custom.cpp";
        // path = "pos/P12_class_mixed.cpp";
        // path = "pos/P13_ambiguous_overload.cpp";
        // path = "pos/P14_methods_refs_chaining.cpp";
        // path = "pos/P15_return_object_by_value.cpp";
        // path = "pos/P17_inheritance.cpp";
        // path = "pos/P18_polymorphie_static.cpp";
        // path = "pos/P19_polymorphie_static_ref.cpp";
        // path = "pos/P20_polymorphie_dynamic.cpp";

        // path = "neg/N01_redeclaration.cpp";
        // path = "neg/N02_redeclaration.cpp";
        // path = "neg/N03_ref_noinit.cpp";
        // path = "neg/N04_ref_init_rvalue.cpp";
        // path = "neg/N05_assign_to_rvalue.cpp";
        // path = "neg/N06_wrong_arity.cpp";
        // path = "neg/N07_void_return_with_value.cpp";
        // path = "neg/N08_unknown_member.cpp";
        // path = "neg/N09_method_not_in_static_type.cpp";
        // path = "neg/N10_polymorphie.cpp";
        // path = "neg/N11_ambiguous_overload.cpp";
        try (InputStream in = Main.class.getResourceAsStream(path)) {
            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            MiniCppLexer lexer = new MiniCppLexer(CharStreams.fromString(text));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniCppParser parser = new MiniCppParser(tokens);

            MiniCppParser.StartContext tree = parser.start();

            AST ast = new AST();
            AST.Start astToken = ast.toAST(tree);
            System.out.println(astToken.toString());

            SemanticAnalyzer sA = new SemanticAnalyzer();
            sA.analyze(astToken);

        } catch (SemanticException e) {
            System.err.println("Semantic Error: " + e.getMessage());
        }
    }
}
