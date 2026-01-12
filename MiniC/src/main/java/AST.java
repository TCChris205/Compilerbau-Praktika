import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AST {

    // Helper method for formatting tree output
    private static String formatTree(String name, Object value, String indent, boolean isLast) {
        String prefix = isLast ? "└── " : "├── ";
        String continuation = isLast ? "    " : "│   ";

        if (value == null) {
            return indent + prefix + name + ": null\n";
        }

        String valueStr = value.toString();
        // If value is multiline (contains tree structure), indent it properly
        if (valueStr.contains("\n")) {
            String[] lines = valueStr.split("\n");
            StringBuilder result = new StringBuilder();
            result.append(indent).append(prefix).append(name).append("\n");
            for (int i = 0; i < lines.length; i++) {
                if (i < lines.length - 1 || !lines[i].isEmpty()) {
                    result.append(indent).append(continuation).append(lines[i]).append("\n");
                }
            }
            return result.toString();
        } else {
            return indent + prefix + name + ": " + valueStr + "\n";
        }
    }

    private static String formatValue(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof ArrayList) {
            ArrayList<?> list = (ArrayList<?>) obj;
            if (list.isEmpty()) return "[]";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                Object item = list.get(i);
                String itemStr = formatValue(item);
                if (item instanceof ASTToken && itemStr.contains("\n")) {
                    if (i == 0) sb.append("\n");
                    sb.append(itemStr);
                } else {
                    if (i > 0) sb.append(", ");
                    sb.append(itemStr);
                }
            }
            return sb.toString();
        }
        return obj.toString();
    }

    // ---------------------------- METHODS ----------------------------

    public ASTToken toAST(ParseTree e) {
        return null;
    }

    public Start toAST(MiniCppParser.StartContext e) {
        if (e == null) return null;
        ArrayList<ASTToken> lines = new ArrayList<>();
        for (MiniCppParser.LineContext l : e.line()) {
            if (l.statement() != null) {
                MiniCppParser.StatementContext statment = l.statement();
                if (statment.block() != null) {
                    lines.add(toAST(statment.block()));
                }
                if (statment.functionDeclaration() != null) {
                    lines.add(toAST(statment.functionDeclaration()));
                }
                if (statment.variableDeclaration() != null) {
                    lines.add(toAST(statment.variableDeclaration()));
                }
                if (statment.returnStatement() != null) {
                    lines.add(toAST(statment.returnStatement()));
                }
                if (statment.ifStatement() != null) {
                    lines.add(toAST(statment.ifStatement()));
                }
                if (statment.whileLoop() != null) {
                    lines.add(toAST(statment.whileLoop()));
                }
                if (statment.assignment() != null) {
                    lines.add(toAST(statment.assignment()));
                }
                if (statment.logicalOr() != null) {
                    lines.add(toAST(statment.logicalOr()));
                }
            }
            if (l.classDeclaration() != null) {
                lines.add(toAST(l.classDeclaration()));
            }
        }
        return new Start(lines);
    }

    public ClassDeclaration toAST(MiniCppParser.ClassDeclarationContext e) {
        if (e == null) return null;
        String className = e.ID(0).getText();
        String parentClass = null;
        if (e.ID().size() == 2) {
            parentClass = e.ID(1).getText();
        }
        ArrayList<ASTToken> members = new ArrayList<>();
        for (MiniCppParser.ClassMemberContext c : e.classMember()) {
            if (c.attributeDeclaration() != null) {
                members.add(toAST(c.attributeDeclaration()));
            }
            if (c.constructorDefinition() != null) {
                members.add(toAST(c.constructorDefinition()));
            }
            if (c.methodDefinition() != null) {
                members.add(toAST(c.methodDefinition()));
            }
        }
        return new ClassDeclaration(className, parentClass, members);
    }

    public AttributeDeclaration toAST(MiniCppParser.AttributeDeclarationContext e) {
        if (e == null) return null;
        String attributeName = e.ID().getText();
        String type = e.type().getText();
        return new AttributeDeclaration(type, attributeName);
    }

    public MethodDefinition toAST(MiniCppParser.MethodDefinitionContext e) {
        if (e == null) return null;
        boolean virtual = false;
        if (e.VIRTUAL_KEY() != null) {
            virtual = true;
        }
        String type = e.type().getText();
        String methodName = e.ID().getText();
        ParamList paramList = toAST(e.paramList());
        Block block = toAST(e.block());
        return new MethodDefinition(virtual, type, methodName, paramList, block);
    }

    public Constructor toAST(MiniCppParser.ConstructorDefinitionContext e) {
        if (e == null) return null;
        String name = e.ID().getText();
        ParamList paramList = toAST(e.paramList());
        Block block = toAST(e.block());
        return new Constructor(name, block, paramList);
    }

    public ParamList toAST(MiniCppParser.ParamListContext e) {
        if (e == null) return null;
        ArrayList<String> type = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        for (int i = 0; i < e.ID().size(); i++) {
            type.add(e.typeReference(i).getText());
            name.add(e.ID(i).getText());
        }
        return new ParamList(type, name);
    }

    public Block toAST(MiniCppParser.BlockContext e) {
        if (e == null) return null;
        ArrayList<ASTToken> lines = new ArrayList<>();

        for (MiniCppParser.StatementContext statment : e.statement()) {
            if (statment.block() != null) {
                lines.add(toAST(statment.block()));
            }
            if (statment.functionDeclaration() != null) {
                lines.add(toAST(statment.functionDeclaration()));
            }
            if (statment.variableDeclaration() != null) {
                lines.add(toAST(statment.variableDeclaration()));
            }
            if (statment.returnStatement() != null) {
                lines.add(toAST(statment.returnStatement()));
            }
            if (statment.ifStatement() != null) {
                lines.add(toAST(statment.ifStatement()));
            }
            if (statment.whileLoop() != null) {
                lines.add(toAST(statment.whileLoop()));
            }
            if (statment.assignment() != null) {
                    lines.add(toAST(statment.assignment()));
            }
            if (statment.logicalOr() != null) {
                lines.add(toAST(statment.logicalOr()));
            }
        }
        return new Block(lines);
    }

    public FunctionDeclaration toAST(MiniCppParser.FunctionDeclarationContext e) {
        if (e == null) return null;
        String type = e.type().getText();
        String functionName = e.ID().getText();
        ParamList paramList = toAST(e.paramList());

        Block block = toAST(e.block());

        return new FunctionDeclaration(type, functionName, paramList, block);
    }

    public VariableDeclaration toAST(MiniCppParser.VariableDeclarationContext e) {
        if (e == null) return null;
        String type = e.type().getText();
        boolean deepcopy = e.DEEPCOPY() != null;
        String varName = e.ID(0).getText();
        if (deepcopy) {
            VariableCall call = new VariableCall(e.ID(1).getText(), null);
            if (e.idChain() != null) {
                IdChainElement start = (IdChainElement) toAST(e.idChain());
                IdChainElement i = start;
                while (i.GetNext() != null) {
                    i = i.GetNext();
                }
                i.SetNext(call);
                return new VariableDeclaration(type, varName, start);
            }
            return new VariableDeclaration(type, varName, (IdChainElement) call);
        }
        ASTToken expression = null;
            if (e.logicalOr() != null) {
                expression = toAST(e.logicalOr());
            }
        
        return new VariableDeclaration(type, varName, expression);
    }

    public ReturnStatement toAST(MiniCppParser.ReturnStatementContext e) {
        if (e == null) return null;
        ASTToken expression = null;

        
        if (e.logicalOr() != null) {
            expression = toAST(e.logicalOr());
        }
        
        return new ReturnStatement(expression);
    }

    public IfStatement toAST(MiniCppParser.IfStatementContext e) {
        if (e == null) return null;
        ASTToken expression = null;

        if (e.logicalOr() != null) {
            expression = toAST(e.logicalOr());
        }

        Block block = toAST(e.block(0));
        Block elseBlock = null;
        if (e.block().size() > 1) {
            elseBlock = toAST(e.block(1));
        }

        return new IfStatement(expression, block, elseBlock);
    }

    public WhileLoop toAST(MiniCppParser.WhileLoopContext e) {
        if (e == null) return null;
        Block block = toAST(e.block());
        ASTToken expression = null;

        if (e.logicalOr() != null) {
            expression = toAST(e.logicalOr());
        }

        return new WhileLoop(block, expression);
    }

    public Assignment toAST(MiniCppParser.AssignmentContext e) {
        if (e == null) return null;
        ASTToken logicalOr = toAST(e.logicalOr());

        VariableCall call = new VariableCall(e.ID().getText(), null);
        if (e.idChain() != null) {
            IdChainElement start = (IdChainElement) toAST(e.idChain());
            IdChainElement i = start;
            while (i.GetNext() != null) {
                i = i.GetNext();
            }
            i.SetNext(call);
            return new Assignment(start, logicalOr);
        }
        return new Assignment(call, logicalOr);
    }

    public ASTToken toAST(MiniCppParser.LogicalOrContext e) {
        if (e == null) return null;
        if (e.logicalAnd().size() == 1) {
            return toAST(e.logicalAnd(0));
        }

        ArrayList<String> operation = new ArrayList<>();
        ArrayList<ASTToken> element = new ArrayList<>();

        for (int i = 0; i < e.OR().size(); i++) {
            operation.add(e.OR(i).getText());
            element.add(toAST(e.logicalAnd(i + 1)));
        }
        return new Operation(element, operation);
    }

    public ASTToken toAST(MiniCppParser.LogicalAndContext e) {
        if (e == null) return null;
        if (e.equal().size() == 1) {
            return toAST(e.equal(0));
        }

        ArrayList<String> operation = new ArrayList<>();
        ArrayList<ASTToken> element = new ArrayList<>();

        for (int i = 0; i < e.AND().size(); i++) {
            operation.add(e.AND(i).getText());
            element.add(toAST(e.equal(i + 1)));
        }
        return new Operation(element, operation);
    }

    public ASTToken toAST(MiniCppParser.EqualContext e) {
        if (e == null) return null;
        if (e.relation().size() == 1) {
            return toAST(e.relation(0));
        }

        ArrayList<String> operation = new ArrayList<>();
        ArrayList<ASTToken> element = new ArrayList<>();

        for (ParseTree tree : e.children) {
            if (tree instanceof TerminalNode t) {
                if (t.getText().equals("==")) operation.add("==");
                if (t.getText().equals("!=")) operation.add("!=");
            }
        }

        for (int i = 0; i < e.relation().size(); i++) {
            element.add(toAST(e.relation(i)));
        }
        return new Operation(element, operation);
    }

    public ASTToken toAST(MiniCppParser.RelationContext e) {
        if (e == null) return null;
        if (e.arith().size() == 1) {
            return toAST(e.arith(0));
        }

        ArrayList<String> operation = new ArrayList<>();
        ArrayList<ASTToken> element = new ArrayList<>();

        for (ParseTree tree : e.children) {
            if (tree instanceof TerminalNode t) {
                if (t.getText().equals("<=")) operation.add("<=");
                if (t.getText().equals("<")) operation.add("<");
                if (t.getText().equals(">")) operation.add(">");
                if (t.getText().equals(">=")) operation.add(">=");
            }
        }

        for (int i = 0; i < e.arith().size(); i++) {
            element.add(toAST(e.arith(i)));
        }
        return new Operation(element, operation);
    }

    public ASTToken toAST(MiniCppParser.ArithContext e) {
        if (e == null) return null;
        if (e.term().size() == 1) {
            return toAST(e.term(0));
        }

        ArrayList<String> operation = new ArrayList<>();
        ArrayList<ASTToken> element = new ArrayList<>();

        for (ParseTree tree : e.children) {
            if (tree instanceof TerminalNode t) {
                if (t.getText().equals("+")) operation.add("+");
                if (t.getText().equals("-")) operation.add("-");
            }
        }

        for (int i = 0; i < e.term().size(); i++) {
            element.add(toAST(e.term(i)));
        }
        return new Operation(element, operation);
    }

    public ASTToken toAST(MiniCppParser.TermContext e) {
        if (e == null) return null;
        if (e.unary().size() == 1) {
            return toAST(e.unary(0));
        }
        ArrayList<String> operation = new ArrayList<>();
        ArrayList<ASTToken> element = new ArrayList<>();

        for (ParseTree tree : e.children) {
            if (tree instanceof TerminalNode t) {
                if (t.getText().equals("*")) operation.add("*");
                if (t.getText().equals("/")) operation.add("/");
                if (t.getText().equals("%")) operation.add("%");
            }
        }

        for (int i = 0; i < e.unary().size(); i++) {
            element.add(toAST(e.unary(i)));
        }
        return new Operation(element, operation);
    }

    public ASTToken toAST(MiniCppParser.UnaryContext e) {
        if (e == null) return null;
        boolean invert;
        invert = e.NOT() != null;

        if (e.logicalOr() != null) {
            ASTToken expression = null;
            expression = toAST(e.logicalOr());

            if (invert) {
                return new NOT(expression);
            } else {
                return expression;
            }
        }

        if (e.literals() != null) {
            String vorzeichen = null;
            if (e.PLUS() != null) {
                vorzeichen = "+";
            }
            if (e.MINUS() != null) {
                vorzeichen = "-";
            }
            String type;
            String value;
            Literal literal = null;
            if (e.literals().NUM() != null) {
                type = "int";
                value = e.literals().NUM().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().CHAR() != null) {
                type = "char";
                value = e.literals().CHAR().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().STRING() != null) {
                type = "string";
                value = e.literals().STRING().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().TRUE() != null) {
                type = "bool";
                value = e.literals().TRUE().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().FALSE() != null) {
                type = "bool";
                value = e.literals().FALSE().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().ID() != null) {
                type = "var";
                value = e.literals().ID().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (invert) {
                return new NOT(literal);
            } else {
                return literal;
            }
        }

        if (e.idChain() != null) { // !foo.bar()
            if (invert) {
                return new NOT(toAST(e.idChain()));
            } else {
                return toAST(e.idChain());
            }
        }

        return null;
    }

    public ASTToken toAST(MiniCppParser.IdChainContext e) {
        if (e == null) return null;

        if (e.LPAREN() != null) {
            // FunctionCall
            String name = e.ID().getText();
            Args args = null;
            if (e.args() != null) {
                args = toAST(e.args());
            }
            ASTToken next = toAST(e.idChain());
            return new FunctionCall(name, args, next);
        } else {
            // VariableCall
            String name = e.ID().getText();
            ASTToken next = toAST(e.idChain());
            return new VariableCall(name, next);
        }
    }

    public Args toAST(MiniCppParser.ArgsContext e) {
        if (e == null) return null;
        ArrayList<ASTToken> expressions = new ArrayList<>();

        for (MiniCppParser.LogicalOrContext expr : e.logicalOr())
            expressions.add(toAST(expr));

        
        return new Args(expressions);
    }

    public ASTToken toAST(MiniCppParser.TypeReferenceContext e) {
        if (e == null) return null;
        if (e.DEEPCOPY() != null) {
            return new TypeReference(toAST(e.type()));
        } else {
            return toAST(e.type());
        }
    }

    public Type toAST(MiniCppParser.TypeContext e) {
        if (e == null) return null;
        if (e.ID() != null) {
            return new Type(e.ID().getText());
        } else {
            if (e.primitiveTypeKey().INT_KEY() != null) {
                return new Type("int");
            }
            if (e.primitiveTypeKey().BOOL_KEY() != null) {
                return new Type("bool");
            }
            if (e.primitiveTypeKey().STRING_KEY() != null) {
                return new Type("string");
            }
            if (e.primitiveTypeKey().CHAR_KEY() != null) {
                return new Type("char");
            }
            if (e.primitiveTypeKey().VOID_KEY() != null) {
                return new Type("void");
            }
            return null;
        }
    }

    // ---------------------------- CLASSES ----------------------------

    public abstract class ASTToken {
        public abstract void evaluate();

        @Override
        public abstract String toString();
    }

    public class Start extends ASTToken {
        public ArrayList<ASTToken> lines;

        public Start(ArrayList<ASTToken> lines) {
            this.lines = lines;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Start\n");
            if (lines != null && !lines.isEmpty()) {
                for (int i = 0; i < lines.size(); i++) {
                    boolean isLast = (i == lines.size() - 1);
                    String prefix = isLast ? "└── " : "├── ";
                    String continuation = isLast ? "    " : "│   ";
                    String itemStr = lines.get(i).toString();
                    String[] itemLines = itemStr.split("\n");
                    sb.append(prefix).append(itemLines[0]).append("\n");
                    for (int j = 1; j < itemLines.length; j++) {
                        if (!itemLines[j].isEmpty()) {
                            sb.append(continuation).append(itemLines[j]).append("\n");
                        }
                    }
                }
            }
            return sb.toString();
        }
    }

    public class ClassDeclaration extends ASTToken {

        public String className;
        public String parentClass;
        public ArrayList<ASTToken> members;

        public ClassDeclaration(String className, String parentClass, ArrayList<ASTToken> members) {
            this.className = className;
            this.parentClass = parentClass;
            this.members = members;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ClassDeclaration\n");
            sb.append(formatTree("className", className, "", false));
            sb.append(formatTree("parentClass", parentClass, "", false));
            sb.append(formatTree("members", formatValue(members), "", true));
            return sb.toString();
        }
    }

    public class AttributeDeclaration extends ASTToken {

        public String type;
        public String name;

        public AttributeDeclaration(String type, String name) {
            if (type.endsWith("&")) {
                this.type = type.substring(0, type.length() - 1);
            }
            else{
                this.type = type;
            }
            this.name = name;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("AttributeDeclaration\n");
            sb.append(formatTree("type", type, "", false));
            sb.append(formatTree("name", name, "", true));
            return sb.toString();
        }
    }

    public class MethodDefinition extends ASTToken {

        public boolean virtual;
        public String type;
        public String methodName;
        public ParamList paramList;
        public Block block;

        public MethodDefinition(
                boolean virtual, String type, String methodName, ParamList paramList, Block block) {
            this.virtual = virtual;
            if (type.endsWith("&")) {
                this.type = type.substring(0, type.length() - 1);
                }
                else{
                    this.type = type;
                }
            this.methodName = methodName;
            this.paramList = paramList;
            this.block = block;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("MethodDefinition\n");
            sb.append(formatTree("virtual", virtual, "", false));
            sb.append(formatTree("type", type, "", false));
            sb.append(formatTree("methodName", methodName, "", false));
            sb.append(formatTree("parameter", paramList, "", false));
            sb.append(formatTree("block", block, "", true));
            return sb.toString();
        }
    }

    public class Constructor extends ASTToken {

        public String className;
        public Block block;
        public ParamList paramList;

        public Constructor(String className, Block block, ParamList paramList) {
            this.className = className;
            this.block = block;
            this.paramList = paramList;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Constructor\n");
            sb.append(formatTree("className", className, "", false));
            sb.append(formatTree("block", block, "", false));
            sb.append(formatTree("paramList", paramList, "", true));
            return sb.toString();
        }
    }

    public class ParamList extends ASTToken {

        public ArrayList<String> type = new ArrayList<>();
        public ArrayList<String> name = new ArrayList<>();

        public ParamList(ArrayList<String> type, ArrayList<String> name) {
            for (String t : type) {
                if (t.endsWith("&")) {
                this.type.add(t.substring(0, t.length() - 1));
                }
                else{
                    this.type.add(t);
                }
            }
            this.name = name;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ParamList\n");
            sb.append(formatTree("type", type, "", false));
            sb.append(formatTree("name", name, "", true));
            return sb.toString();
        }
    }

    public class Block extends ASTToken {

        ArrayList<ASTToken> lines = new ArrayList<>();

        public Block(ArrayList<ASTToken> lines) {
            this.lines = lines;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Block\n");
            if (lines != null && !lines.isEmpty()) {
                for (int i = 0; i < lines.size(); i++) {
                    boolean isLast = (i == lines.size() - 1);
                    String prefix = isLast ? "└── " : "├── ";
                    String continuation = isLast ? "    " : "│   ";
                    String itemStr = lines.get(i).toString();
                    String[] itemLines = itemStr.split("\n");
                    sb.append(prefix).append(itemLines[0]).append("\n");
                    for (int j = 1; j < itemLines.length; j++) {
                        if (!itemLines[j].isEmpty()) {
                            sb.append(continuation).append(itemLines[j]).append("\n");
                        }
                    }
                }
            }
            return sb.toString();
        }
    }

    public class FunctionDeclaration extends ASTToken {

        public String type;
        public String functionName;
        public ParamList paramList;
        public Block block;

        public FunctionDeclaration(
                String type, String functionName, ParamList paramList, Block block) {
            if (type.endsWith("&")) {
                this.type = type.substring(0, type.length() - 1);
            }
            else{
                this.type = type;
                
            }
            this.functionName = functionName;
            this.paramList = paramList;
            this.block = block;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("FunctionDeclaration\n");
            sb.append(formatTree("type", type, "", false));
            sb.append(formatTree("functionName", functionName, "", false));
            sb.append(formatTree("paramList", paramList, "", false));
            sb.append(formatTree("block", block, "", true));
            return sb.toString();
        }
    }

    public class VariableDeclaration extends ASTToken {
        public boolean deepcopy = false;
        public String varName;
        public String type;
        public ASTToken expression = null;
        public IdChainElement varCall;

        public VariableDeclaration(String type, String varName, ASTToken expression) {
            if (type.endsWith("&")) {
                this.type = type.substring(0, type.length() - 1);
            }
            else{
                this.type = type;
                
            }
            this.varName = varName;
            this.expression = expression;
        }

        public VariableDeclaration(String type, String varName, IdChainElement varCall) {
            this.varName = varName;
            this.varCall = varCall;
            if (type.endsWith("&")) {
                this.type = type.substring(0, type.length() - 1);
            }
            else{
                this.type = type;
                
            }
            this.varName = varName;
            this.deepcopy = true;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("VariableDeclaration\n");
            sb.append(formatTree("deepcopy", deepcopy, "", false));
            sb.append(formatTree("varName", varName, "", false));
            sb.append(formatTree("type", type, "", false));
            sb.append(formatTree("expression", expression, "", false));
            sb.append(formatTree("varCall", varCall, "", true));
            return sb.toString();
        }
    }

    public class ReturnStatement extends ASTToken {

        public ASTToken expression;

        public ReturnStatement(ASTToken expression) {
            this.expression = expression;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ReturnStatement\n");
            sb.append(formatTree("expression", expression, "", true));
            return sb.toString();
        }
    }

    public class IfStatement extends ASTToken {

        public ASTToken expression;
        public Block block;
        public Block elseBlock;

        public IfStatement(ASTToken expression, Block block, Block elseBlock) {
            this.expression = expression;
            this.block = block;
            this.elseBlock = elseBlock;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("IfStatement\n");
            sb.append(formatTree("expression", expression, "", false));
            sb.append(formatTree("block", block, "", false));
            sb.append(formatTree("elseBlock", elseBlock, "", true));
            return sb.toString();
        }
    }

    public class WhileLoop extends ASTToken {

        public Block block;
        public ASTToken expression;

        public WhileLoop(Block block, ASTToken expression) {
            this.expression = expression;
            this.block = block;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("WhileLoop\n");
            sb.append(formatTree("block", block, "", false));
            sb.append(formatTree("expression", expression, "", true));
            return sb.toString();
        }
    }

    public class Assignment extends ASTToken {

        public IdChainElement chain;
        public ASTToken operation;

        public Assignment(IdChainElement chain, ASTToken operation) {
            this.chain = chain;
            this.operation = operation;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Assignment\n");
            sb.append(formatTree("chain", chain, "", false));
            sb.append(formatTree("operation", operation, "", true));
            return sb.toString();
        }
    }

    public class Operation extends ASTToken {

        public ArrayList<String> operations;
        public ArrayList<ASTToken> elements;

        public Operation(ArrayList<ASTToken> elements, ArrayList<String> operations) {
            this.operations = operations;
            this.elements = elements;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Operation\n");
            sb.append(formatTree("elements", formatValue(elements), "", false));
            sb.append(formatTree("operations", operations, "", true));
            return sb.toString();
        }
    }

    public interface IdChainElement {
        public IdChainElement GetNext();

        public void SetNext(IdChainElement next);
    }

    public class VariableCall extends ASTToken implements IdChainElement {

        String name;
        ASTToken next;

        public VariableCall(String name, ASTToken next) {
            this.name = name;
            this.next = next;
        }

        @Override
        public void SetNext(IdChainElement next) {
            this.next = (ASTToken) next;
        }

        @Override
        public IdChainElement GetNext() {
            if (next instanceof IdChainElement idChainElement) {
                return idChainElement;
            }
            return null;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("VariableCall\n");
            sb.append(formatTree("name", name, "", false));
            sb.append(formatTree("next", next, "", true));
            return sb.toString();
        }
    }

    public class Literal extends ASTToken {
        String type;
        String value;
        String vorzeichen;

        public Literal(String type, String value, String vorzeichen) {
            if (type.endsWith("&")) {
                this.type = type.substring(0, type.length() - 1);
            }
            else{
                this.type = type;
                
            }
            this.value = value;
            this.vorzeichen = vorzeichen;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Literal\n");
            sb.append(formatTree("type", type, "", false));
            sb.append(formatTree("value", value, "", false));
            sb.append(formatTree("vorzeichen", vorzeichen, "", true));
            return sb.toString();
        }
    }

    public class FunctionCall extends ASTToken implements IdChainElement {

        String name;
        Args args;
        ASTToken next;

        public FunctionCall(String name, Args args, ASTToken next) {
            this.name = name;
            this.args = args;
            this.next = next;
        }

        @Override
        public void SetNext(IdChainElement next) {
            this.next = (ASTToken) next;
        }

        @Override
        public IdChainElement GetNext() {
            if (next instanceof IdChainElement idChainElement) {
                return idChainElement;
            }
            return null;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("FunctionCall\n");
            sb.append(formatTree("name", name, "", false));
            sb.append(formatTree("args", args, "", false));
            sb.append(formatTree("next", next, "", true));
            return sb.toString();
        }
    }

    public class Args extends ASTToken {

        public ArrayList<ASTToken> expressions;

        public Args(ArrayList<ASTToken> expressions) {
            this.expressions = expressions;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Args\n");
            if (expressions != null && !expressions.isEmpty()) {
                for (int i = 0; i < expressions.size(); i++) {
                    boolean isLast = (i == expressions.size() - 1);
                    String prefix = isLast ? "└── " : "├── ";
                    String continuation = isLast ? "    " : "│   ";
                    String itemStr = expressions.get(i).toString();
                    String[] itemLines = itemStr.split("\n");
                    sb.append(prefix).append(itemLines[0]).append("\n");
                    for (int j = 1; j < itemLines.length; j++) {
                        if (!itemLines[j].isEmpty()) {
                            sb.append(continuation).append(itemLines[j]).append("\n");
                        }
                    }
                }
            }
            return sb.toString();
        }
    }

    public class TypeReference extends ASTToken {

        Type type;

        public TypeReference(Type type) {
            this.type = type;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TypeReference\n");
            sb.append(formatTree("type", type, "", true));
            return sb.toString();
        }
    }

    public class Type extends ASTToken {
        String type;

        public Type(String type) {
            
            if (type.endsWith("&")) {
                this.type = type.substring(0, type.length() - 1);
            }
            else{
                this.type = type;
            }
            
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            return "Type(type=" + type + ")";
        }
    }

    public class PrimitiveTypeKey extends ASTToken {

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            return "PrimitiveTypeKey";
        }
    }

    public class NOT extends ASTToken {
        public ASTToken child;

        public NOT(ASTToken child) {
            this.child = child;
        }

        @Override
        public void evaluate() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("NOT\n");
            sb.append(formatTree("child", child, "", true));
            return sb.toString();
        }
    }
}
