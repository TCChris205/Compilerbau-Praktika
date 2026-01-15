import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestRunner {
    private static final List<String> positiveTests =
            List.of(
                    "pos/GOLD01_basics.cpp",
                    "pos/GOLD02_ref_params.cpp",
                    "pos/GOLD03_classes_dispatch.cpp",
                    "pos/GOLD04_slicing.cpp",
                    "pos/GOLD05_virtual_override.cpp",
                    "pos/GOLD06_constructors_basic.cpp",
                    "pos/GOLD07_constructors_inheritance.cpp",
                    "pos/P01_vars.cpp",
                    "pos/P02_expr.cpp",
                    "pos/P03_ifthenelse.cpp",
                    "pos/P04_while.cpp",
                    "pos/P05_operators.cpp",
                    "pos/P06_refs.cpp",
                    "pos/P07_scopes_and_shadowing.cpp",
                    "pos/P08_func.cpp",
                    "pos/P09_short_circuit.cpp",
                    "pos/P10_class_defaults.cpp",
                    "pos/P11_class_custom.cpp",
                    "pos/P12_class_mixed.cpp",
                    "pos/P13_ambiguous_overload.cpp",
                    "pos/P14_methods_refs_chaining.cpp",
                    "pos/P15_return_object_by_value.cpp",
                    "pos/P17_inheritance.cpp",
                    "pos/P18_polymorphie_static.cpp",
                    "pos/P19_polymorphie_static_ref.cpp",
                    "pos/P20_polymorphie_dynamic.cpp");

    private static final List<String> negativeTests =
            List.of(
                    "neg/N01_redeclaration.cpp",
                    "neg/N02_redeclaration.cpp",
                    "neg/N03_ref_noinit.cpp",
                    "neg/N04_ref_init_rvalue.cpp",
                    "neg/N05_assign_to_rvalue.cpp",
                    "neg/N06_wrong_arity.cpp",
                    "neg/N07_void_return_with_value.cpp",
                    "neg/N08_unknown_member.cpp",
                    "neg/N09_method_not_in_static_type.cpp",
                    "neg/N10_polymorphie.cpp",
                    "neg/N11_ambiguous_overload.cpp");

    static void main(String[] args) throws IOException {
        int passedPositive = 0;
        int failedPositive = 0;
        int passedNegative = 0;
        int failedNegative = 0;

        List<String> failedPositiveTests = new java.util.ArrayList<>();
        List<String> failedNegativeTests = new java.util.ArrayList<>();

        System.out.println("=== POSITIVE TESTS ===\n");
        for (String testFile : positiveTests) {
            try {
                analyzeFile(testFile);
                System.out.println("+ " + testFile);
                passedPositive++;
            } catch (Exception e) {
                System.out.println("- " + testFile);
                System.out.println("  Error: " + e.getMessage());
                failedPositive++;
                failedPositiveTests.add(testFile);
            }
        }

        System.out.println("\n=== NEGATIVE TESTS ===\n");
        for (String testFile : negativeTests) {
            try {
                analyzeFile(testFile);
                System.out.println("- " + testFile + " (should have failed)");
                failedNegative++;
                failedNegativeTests.add(testFile);
            } catch (SemanticException e) {
                System.out.println("+ " + testFile + " (correctly failed)");
                System.out.println("  Reason: " + e.getMessage());
                passedNegative++;
            } catch (RuntimeException e) {
                System.out.println("+ " + testFile + " (correctly failed)");
                System.out.println("  Reason: " + e.getMessage());
                passedNegative++;
            } catch (Exception e) {
                System.out.println("+ " + testFile + " (correctly failed)");
                System.out.println(
                        "  Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                passedNegative++;
            }
        }

        System.out.println("\n=== SUMMARY ===");
        System.out.println(
                "Positive tests: " + passedPositive + " passed, " + failedPositive + " failed");
        System.out.println(
                "Negative tests: " + passedNegative + " passed, " + failedNegative + " failed");
        System.out.println(
                "Total: "
                        + (passedPositive + passedNegative)
                        + " passed, "
                        + (failedPositive + failedNegative)
                        + " failed");

        if (!failedPositiveTests.isEmpty()) {
            System.out.println("\n=== FAILED POSITIVE TESTS ===");
            for (String test : failedPositiveTests) {
                System.out.println("  - " + test);
            }
        }

        if (!failedNegativeTests.isEmpty()) {
            System.out.println("\n=== FAILED NEGATIVE TESTS ===");
            for (String test : failedNegativeTests) {
                System.out.println("  - " + test);
            }
        }
    }

    private static void analyzeFile(String path) throws IOException {
        try (InputStream in = TestRunner.class.getResourceAsStream(path)) {
            if (in == null) {
                throw new IOException("Test file not found: " + path);
            }

            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            MiniCppLexer lexer = new MiniCppLexer(CharStreams.fromString(text));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniCppParser parser = new MiniCppParser(tokens);

            // Add error listener to detect parse errors
            ParseErrorListener errorListener = new ParseErrorListener();
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            MiniCppParser.StartContext tree = parser.start();

            // Check if parse had errors
            if (errorListener.hasErrors()) {
                throw new RuntimeException("Parse error: " + errorListener.getErrorMessage());
            }

            AST ast = new AST();
            AST.Start astToken = ast.toAST(tree);

            // Perform semantic analysis
            SemanticAnalyzer analyzer = new SemanticAnalyzer();
            analyzer.analyze(astToken);
        }
    }
}

class ParseErrorListener extends org.antlr.v4.runtime.BaseErrorListener {
    private boolean hasErrors = false;
    private String errorMessage = "";

    @Override
    public void syntaxError(
            org.antlr.v4.runtime.Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            org.antlr.v4.runtime.RecognitionException e) {
        hasErrors = true;
        errorMessage = "line " + line + ":" + charPositionInLine + " " + msg;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
