import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class Main {
    static void main(String... args) throws IOException, URISyntaxException {

        String path = null;
        boolean runREPL = true;

        // Check for command line arguments
        if (args.length > 0) {
            if ("--repl".equals(args[0])) {
                runREPL = true;
            } else {
                path = args[0];
            }
        }

        // If no path specified, use default test file
        if (path == null) {
            // path = "pos/myTest.cpp";
            // path = "pos/GOLD01_basics.cpp";
            // path = "pos/GOLD02_ref_params.cpp";
            // path = "pos/GOLD03_classes_dispatch.cpp";
            // path = "pos/GOLD04_slicing.cpp";
            // path = "pos/GOLD05_virtual_override.cpp";
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
            path = "pos/P20_polymorphie_dynamic.cpp";

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
        }

        try {
            Map<String, AST.FunctionDeclaration> functions = new HashMap<>();
            Map<String, AST.ClassDeclaration> classes = new HashMap<>();
            SemanticAnalyzer analyzer = null;
            Interpreter interpreter = null;

            if (path != null) {
                // Load and execute test file
                ExecutionContext ctx = processTestFile(path);
                functions = ctx.functions;
                classes = ctx.classes;
                analyzer = ctx.analyzer;
                interpreter = ctx.interpreter;
            }

            if (runREPL) {
                // Start REPL with the interpreter from test file, or create new one
                startREPL(analyzer, functions, classes, interpreter);
            }
        } catch (SemanticException e) {
            System.err.println("Semantic Error: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static class ExecutionContext {
        SemanticAnalyzer analyzer;
        Map<String, AST.FunctionDeclaration> functions;
        Map<String, AST.ClassDeclaration> classes;
        Interpreter interpreter;

        ExecutionContext(
                SemanticAnalyzer analyzer,
                Map<String, AST.FunctionDeclaration> functions,
                Map<String, AST.ClassDeclaration> classes) {
            this(analyzer, functions, classes, null);
        }

        ExecutionContext(
                SemanticAnalyzer analyzer,
                Map<String, AST.FunctionDeclaration> functions,
                Map<String, AST.ClassDeclaration> classes,
                Interpreter interpreter) {
            this.analyzer = analyzer;
            this.functions = functions;
            this.classes = classes;
            this.interpreter = interpreter;
        }
    }

    private static ExecutionContext processTestFile(String path)
            throws IOException, URISyntaxException {
        try (InputStream in = Main.class.getResourceAsStream(path)) {
            if (in == null) {
                System.err.println("File not found: " + path);
                return new ExecutionContext(null, new HashMap<>(), new HashMap<>());
            }

            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            return executeCode(text);
        }
    }

    private static ExecutionContext executeCode(String code) {
        try {
            MiniCppLexer lexer = new MiniCppLexer(CharStreams.fromString(code));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniCppParser parser = new MiniCppParser(tokens);

            parser.removeErrorListeners();
            parser.addErrorListener(
                    new BaseErrorListener() {
                        @Override
                        public void syntaxError(
                                Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line,
                                int charPositionInLine,
                                String msg,
                                RecognitionException e) {
                            throw new RuntimeException(
                                    "Parse Error at line "
                                            + line
                                            + ":"
                                            + charPositionInLine
                                            + " - "
                                            + msg);
                        }
                    });

            MiniCppParser.StartContext tree = parser.start();
            AST ast = new AST();
            AST.Start astToken = ast.toAST(tree);
            // System.out.println(astToken.toString());

            SemanticAnalyzer sA = new SemanticAnalyzer();
            sA.analyze(astToken);

            // Now execute
            Map<String, AST.FunctionDeclaration> functions = new HashMap<>();
            Map<String, AST.ClassDeclaration> classes = new HashMap<>();

            for (AST.ASTToken line : astToken.lines) {
                if (line instanceof AST.FunctionDeclaration funcDecl) {
                    functions.put(funcDecl.functionName, funcDecl);
                } else if (line instanceof AST.ClassDeclaration classDecl) {
                    classes.put(classDecl.className, classDecl);
                }
            }

            Interpreter interpreter = new Interpreter(sA.globalScope, functions, classes, astToken);
            interpreter.execute(astToken);

            // Call main() if it exists
            if (functions.containsKey("main")) {
                interpreter.callMain();
            }

            return new ExecutionContext(sA, functions, classes, interpreter);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private static void startREPL(
            SemanticAnalyzer globalAnalyzer,
            Map<String, AST.FunctionDeclaration> functions,
            Map<String, AST.ClassDeclaration> classes,
            Interpreter existingInterpreter)
            throws IOException {
        Interpreter interpreter = existingInterpreter;
        
        if (interpreter == null) {
            // No test file executed, create fresh interpreter
            if (globalAnalyzer == null) {
                globalAnalyzer = new SemanticAnalyzer();
                functions = new HashMap<>();
                classes = new HashMap<>();
            }
            interpreter = new Interpreter(globalAnalyzer.globalScope, functions, classes);
        }
        
        REPL repl = new REPL(globalAnalyzer.globalScope, interpreter);
        repl.start();
    }
}
