import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class REPL {
    private Interpreter interpreter;
    private Scope globalScope;
    private Scope sessionScope;
    private Map<String, AST.FunctionDeclaration> functions = new HashMap<>();
    private Map<String, AST.ClassDeclaration> classes = new HashMap<>();
    private AST astConverter = new AST();

    public REPL(Scope globalScope, Interpreter interpreter) {
        this.globalScope = globalScope;
        this.interpreter = interpreter;
        this.sessionScope = new Scope(globalScope);
    }

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();

        String prompt = ">>> ";
        String continuationPrompt = "... ";

        while (true) {
            System.out.print(prompt);
            StringBuilder input = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (input.length() == 0 && (line.equals("exit") || line.equals("quit"))) {
                    System.out.println("Goodbye!");
                    return;
                }

                if (input.length() == 0 && line.equals("?")) {
                    printHelp();
                    System.out.print(prompt);
                    continue;
                }

                input.append(line).append("\n");

                // Check if input is complete
                if (isCompleteInput(input.toString())) {
                    break;
                }

                System.out.print(continuationPrompt);
            }

            if (line == null) {
                // EOF
                break;
            }

            String code = input.toString().trim();
            if (code.isEmpty()) {
                continue;
            }

            try {
                processInput(code);
            } catch (SemanticException e) {
                System.err.println("Semantic Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void processInput(String code) {
        // Try to parse as a complete program
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

        try {
            // Try parsing as statement(s)
            MiniCppParser.StatementContext stmtCtx = parser.statement();
            if (stmtCtx != null) {
                AST.ASTToken stmt = astConverter.toASTStatement(stmtCtx);
                if (stmt != null) {
                    // Set the REPL scope for variable lookup
                    interpreter.setREPLScope(sessionScope);
                    Interpreter.Value result = interpreter.evaluateForREPL(stmt);
                    if (result != null && !result.type.equals("void")) {
                        System.out.println(formatOutput(result));
                    }
                    return;
                }
            }

            // Try parsing as complete program
            parser.reset();
            tokens = new CommonTokenStream(lexer);
            parser = new MiniCppParser(tokens);
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

            MiniCppParser.StartContext startCtx = parser.start();
            AST.Start start = astConverter.toAST(startCtx);

            // Process classes and functions
            for (AST.ASTToken line : start.lines) {
                if (line instanceof AST.ClassDeclaration classDecl) {
                    classes.put(classDecl.className, classDecl);
                    // Analyze and register class
                    SemanticAnalyzer analyzer = new SemanticAnalyzer();
                    // This would need to be modified to work with REPL scope
                } else if (line instanceof AST.FunctionDeclaration funcDecl) {
                    functions.put(funcDecl.functionName, funcDecl);
                    // Set the REPL scope and evaluate to register the function
                    interpreter.setREPLScope(sessionScope);
                    interpreter.evaluateForREPL(line);
                } else {
                    // Set the REPL scope for variable lookup
                    interpreter.setREPLScope(sessionScope);
                    Interpreter.Value result = interpreter.evaluateForREPL(line);
                    if (result != null && !result.type.equals("void")) {
                        System.out.println(formatOutput(result));
                    }
                }
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private AST.ASTToken toASTStatement(MiniCppParser.StatementContext ctx) {
        return astConverter.toASTStatement(ctx);
    }

    private boolean isCompleteInput(String input) {
        int braceCount = 0;
        int parenCount = 0;
        int bracketCount = 0;
        boolean inString = false;
        boolean inChar = false;
        char prevChar = ' ';

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '"' && prevChar != '\\') inString = !inString;
            if (c == '\'' && prevChar != '\\') inChar = !inChar;

            if (!inString && !inChar) {
                if (c == '{') braceCount++;
                if (c == '}') braceCount--;
                if (c == '(') parenCount++;
                if (c == ')') parenCount--;
                if (c == '[') bracketCount++;
                if (c == ']') bracketCount--;
            }

            prevChar = c;
        }

        // Input is complete if all brackets are balanced and it ends with ; or }
        if (braceCount == 0 && parenCount == 0 && bracketCount == 0) {
            String trimmed = input.trim();
            return trimmed.endsWith(";") || trimmed.endsWith("}");
        }

        return false;
    }

    private String formatOutput(Interpreter.Value value) {
        if (value == null) return "null";
        if (value.type.equals("bool")) {
            return ((Boolean) value.value) ? "true" : "false";
        }
        if (value.type.equals("char")) {
            return "'" + value.value + "'";
        }
        if (value.type.equals("string")) {
            return "\"" + value.value + "\"";
        }
        if (value.type.equals("int")) {
            return value.value.toString();
        }
        return value.toString();
    }

    private void printHelp() {
        System.out.println("MiniC++ REPL Help:");
        System.out.println("  exit or quit  - Exit the REPL");
        System.out.println("  ?            - Show this help");
        System.out.println();
        System.out.println("You can enter:");
        System.out.println("  - Variable declarations: int x = 5;");
        System.out.println("  - Expressions: x + 3");
        System.out.println("  - Function calls: print_int(x)");
        System.out.println("  - Control flow: if (x > 0) { ... }");
        System.out.println("  - Function definitions: int add(int a, int b) { return a + b; }");
        System.out.println();
    }
}
