import java.util.ArrayList;

import javax.sound.sampled.Line;

import org.antlr.v4.runtime.tree.ParseTree;
import org.stringtemplate.v4.compiler.CodeGenerator.region_return;

public class AST {

// ---------------------------- METHODS ----------------------------

    public ASTToken toAST(ParseTree e) {
        return null;
    }

    public Start toAST(MiniCppParser.StartContext e) {
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
                if (statment.expression() != null) {
                    if (statment.expression().assignment() != null) {
                        lines.add(toAST(statment.expression().assignment()));
                    }
                    else if (statment.expression().logicalOr() != null) {
                        lines.add(toAST(statment.expression().logicalOr()));
                    }
                }
            }
            if (l.classDeclaration() != null) {
                lines.add(toAST(l.classDeclaration()));
            }
        }
        return new Start(lines);
    }

    public ClassDeclaration toAST(MiniCppParser.ClassDeclarationContext e) {
        String className = e.ID(0).getText();
        String parentClass = null;
        if(e.ID().size() == 2){
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
        String attributeName = e.ID().getText();
        String type = e.type().getText();
        return new AttributeDeclaration(attributeName,type);
    }

    public MethodDefinition toAST(MiniCppParser.MethodDefinitionContext e) {
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
        String name = e.ID().getText();
        ParamList paramList = toAST(e.paramList());
        Block block = toAST(e.block());
        return new Constructor(name, block, paramList);
    }

    public ParamList toAST(MiniCppParser.ParamListContext e) {
        
        ArrayList<String> type = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        for (int i = 0; i < e.ID().size(); i++) {
            type.add(e.typeReference(i).getText());
            name.add(e.ID(i).getText());
        }
        return new ParamList(type,name);
    }

    public Block toAST(MiniCppParser.BlockContext e) {

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
                if (statment.expression() != null) {
                    lines.add(toAST(statment.expression()));
                }
        }
        return new Block(lines);
    }

    public FunctionDeclaration toAST(MiniCppParser.FunctionDeclarationContext e) {
        String type = e.type().getText();
        String functionName = e.ID().getText();
        ParamList paramList = toAST(e.paramList());
        ASTToken expression = null;

        if (e.expression().assignment() != null) {
            expression = toAST(e.expression().assignment());
        }
        else if (e.expression().logicalOr() != null) {
            expression = toAST(e.expression().logicalOr());
        }

        Block block = toAST(e.block());

        return new FunctionDeclaration(type, functionName, paramList, expression, block);
    }

    public VariableDeclaration toAST(MiniCppParser.VariableDeclarationContext e) {
        String type = e.type().getText();
        boolean deepcopy = e.DEEPCOPY() != null;
        String varName = e.ID(0).getText();
        if(deepcopy)
        {
            VariableCall call = new VariableCall(e.ID(1).getText(), null);
            if(e.idChain() != null)
            {
                IdChainElement start = (IdChainElement) toAST(e.idChain());
                IdChainElement i = start;
                while(i.GetNext() != null)
                {
                    i = i.GetNext();
                }
                i.SetNext(call);
                return new VariableDeclaration(type, varName, start);
            }
            return new VariableDeclaration(type, varName, call);
        }
        ASTToken expression = null;
        
        if (e.expression().assignment() != null) {
            expression = toAST(e.expression().assignment());
        }
        if (e.expression().logicalOr() != null) {
            expression = toAST(e.expression().logicalOr());
        }
        return new VariableDeclaration(type, varName, expression);
    }

    public ReturnStatement toAST(MiniCppParser.ReturnStatementContext e) {
        ASTToken expression = null;
        
        if (e.expression().assignment() != null) {
            expression = toAST(e.expression().assignment());
        }
        if (e.expression().logicalOr() != null) {
            expression = toAST(e.expression().logicalOr());
        }
        return new ReturnStatement(expression);
    }

    public IfStatement toAST(MiniCppParser.IfStatementContext e) {
        ASTToken expression = null;

        if (e.expression().assignment() != null) {
            expression = toAST(e.expression().assignment());
        }
        else if (e.expression().logicalOr() != null) {
            expression = toAST(e.expression().logicalOr());
        }

        Block block = toAST(e.block(1));
        Block elseBlock = null;
        if (e.block().size() > 1) {
            elseBlock = toAST(e.block(2));
        }

        return new IfStatement(expression, block, elseBlock);
    }

    public WhileLoop toAST(MiniCppParser.WhileLoopContext e) {
        Block block = toAST(e.block());
        ASTToken expression = null;

        if (e.expression().assignment() != null) {
            expression = toAST(e.expression().assignment());
        }
        else if (e.expression().logicalOr() != null) {
            expression = toAST(e.expression().logicalOr());
        }

        return new WhileLoop(block, expression);
    }

    public Assignment toAST(MiniCppParser.AssignmentContext e) {
        ASTToken idchain = toAST(e.idChain());
        String id = e.ID().getText();
        ASTToken logicalOr = toAST(e.logicalOr());
        
        VariableCall call = new VariableCall(e.ID().getText(), null);
        if(e.idChain() != null)
        {
            IdChainElement start = (IdChainElement) toAST(e.idChain());
            IdChainElement i = start;
            while(i.GetNext() != null)
            {
                 i = i.GetNext();
            }
            i.SetNext(call);
            return new Assignment(start, logicalOr);
        }
        return new Assignment(call, logicalOr);
    }

    public ASTToken toAST(MiniCppParser.LogicalOrContext e) {
        if (e.logicalAnd().size() == 1) {
            return toAST(e.logicalAnd(0));
        }
        
        String type = null;
        if (e.OR()!= null){
            type = "||";
        }


        return new Operation(toAST(e.logicalAnd(0)),toAST(e.logicalAnd(1)),type);
    }

    public ASTToken toAST(MiniCppParser.LogicalAndContext e) {
        if (e.equal().size() == 1) {
            return toAST(e.equal(0));
        }
        
        String type = null;
        if (e.AND()!= null){
            type = "&&";
        }


        return new Operation(toAST(e.equal(0)),toAST(e.equal(1)),type);
    }

    public ASTToken toAST(MiniCppParser.EqualContext e) {
        if (e.relation().size() == 1) {
            return toAST(e.relation(0));
        }
        
        String type = null;
        if (e.EQ()!= null){
            type = "==";
        }
        if (e.NEQ()!= null){
            type = "!=";
        }

        return new Operation(toAST(e.relation(0)),toAST(e.relation(1)),type);
    }

    public ASTToken toAST(MiniCppParser.RelationContext e) {
        if (e.arith().size() == 1) {
            return toAST(e.arith(0));
        }
        
        String type = null;
        if (e.LE()!= null){
            type = "<=";
        }
        if (e.LT()!= null){
            type = "<";
        }
        if (e.GT()!= null){
            type = ">";
        }
        if (e.GE()!= null){
            type = ">=";
        }
        return new Operation(toAST(e.arith(0)),toAST(e.arith(1)),type);
    }

    public ASTToken toAST(MiniCppParser.ArithContext e) {
        if (e.term().size() == 1) {
            return toAST(e.term(0));
        }
        
        String type = null;
        if (e.PLUS()!= null){
            type = "+";
        }
        if (e.MINUS()!= null){
            type = "-";
        }
        return new Operation(toAST(e.term(0)),toAST(e.term(1)),type);
    }

    public ASTToken toAST(MiniCppParser.TermContext e) {
        if (e.unary().size() == 1) {
            return toAST(e.unary(0));
        }
        String type = null;
        if (e.MOD()!= null){
            type = "%";
        }
        if (e.DIV()!= null){
            type = "/";
        }
        if (e.MUL()!= null){
            type = "*";
        }
        return new Operation(toAST(e.unary(0)),toAST(e.unary(1)),type);
    }

    public ASTToken toAST(MiniCppParser.UnaryContext e) {
        boolean invert;
        if (e.NOT() != null) {
            invert = true;
        }
        else{
            invert = false;
        }

        
        if (e.expression() != null) {
            ASTToken expression = null;

            if (e.expression().assignment() != null) {
                expression = toAST(e.expression().assignment());
            }
            else if (e.expression().logicalOr() != null) {
                expression = toAST(e.expression().logicalOr());
            }
            
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
            if (e.literals().NUM() != null){
                type = "num";
                value = e.literals().NUM().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().CHAR() != null){
                type = "char";
                value = e.literals().CHAR().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().STRING() != null){
                type = "string";
                value = e.literals().STRING().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().TRUE() != null){
                type = "bool";
                value = e.literals().TRUE().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().FALSE() != null){
                type = "bool";
                value = e.literals().FALSE().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (e.literals().ID() != null){
                type = "var";
                value = e.literals().ID().getText();
                literal = new Literal(type, value, vorzeichen);
            }
            if (invert) {
                return new NOT(literal);
            }
            else{
                return literal;
            }
            
        }
        if (e.idChain() != null){ //!foo.bar()
            if (invert) {
                return new NOT(toAST(e.idChain()));
            }
            else{
                return toAST(e.idChain());
            }
        }
        return null;
    }

    public ASTToken toAST(MiniCppParser.IdChainContext e) {
        if (e == null) return null;

        if (e.LPAREN() != null){
            //FunctionCall
            String name = e.ID().getText();
            Args args = null;
            if (e.args() != null) {
                args = toAST(e.args());
            }
            ASTToken next = toAST(e.idChain());
            return new FunctionCall(name, args, next);
        }
        else{
            //VariableCall
            String name = e.ID().getText();
            ASTToken next = toAST(e.idChain());
            return new VariableCall(name, next);
        }
    }

    public Args toAST(MiniCppParser.ArgsContext e) {
        if (e == null) return null;
        ArrayList<ASTToken> expressions = new ArrayList<>();

        for (MiniCppParser.ExpressionContext expr : e.expression())
            if (expr.assignment() != null) {
                expressions.add(toAST(expr.assignment()));
            }
            else if (expr.logicalOr() != null) {
                expressions.add(toAST(expr.logicalOr()));
            }

        return new Args(expressions);
    }

    public ASTToken toAST(MiniCppParser.TypeReferenceContext e) {
        if (e.DEEPCOPY() != null) {
            return new TypeReference(toAST(e.type()));
        }
        else{
            return toAST(e.type());
        }
        
    }

    public Type toAST(MiniCppParser.TypeContext e) {
        if (e.ID() != null) {
            return new Type(e.ID().getText());
        }
        else{
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
    }

    public class Start extends ASTToken {
        private ArrayList<ASTToken> lines;

        public Start(ArrayList<ASTToken> lines)
        {
            this.lines = lines;
        }

        public void evaluate(){

        }
    }


    public class ClassDeclaration extends ASTToken {

        private String className;
        private String parentClass;
        private ArrayList<ASTToken> members;

        public ClassDeclaration(String className, String parentClass, ArrayList<ASTToken> members) {
            this.className = className;
            this.parentClass = parentClass;
            this.members = members;
            
        }
        
        public void evaluate(){
            
        }
    }

    public class AttributeDeclaration extends ASTToken {
        
        private String type;
        private String name;
            
        public AttributeDeclaration(String type, String name)
        {
            this.type = type;
            this.name = name;   
        }

        public void evaluate(){
            
        }
    }

    public class MethodDefinition extends ASTToken {

        private boolean virtual;
        private String type;
        private String methodName;
        private ParamList parameter;
        private Block block;

        public MethodDefinition(boolean virtual, String type, String methodName, ParamList paramList, Block block) {
            this.virtual = virtual;
            this.type = type;
            this.methodName = methodName;
            this.parameter = parameter;
            this.block = block;
        }

        public void evaluate(){
            
        }
    }

    public class Constructor extends ASTToken {
        
        private String className;
        private Block block;
        private ParamList paramList;
        
        public Constructor(String className, Block block, ParamList paramList)
        {
            this.className = className;
            this.block = block;
            this.paramList = paramList;
        }
        
        public void evaluate(){
            
        }
    }

    public class ParamList extends ASTToken {

        private ArrayList<String> type = new ArrayList<>();
        private ArrayList<String> name = new ArrayList<>();
        public ParamList (ArrayList<String> type, ArrayList<String> name) {
            this.type = type;
            this.name = name;
        }
        
        public void evaluate(){
            
        }
    }

    public class Block extends ASTToken {

        ArrayList<ASTToken> lines = new ArrayList<>();
        public Block(ArrayList<ASTToken> lines) {
            this.lines = lines;
        }
        public void evaluate(){
            
        }
    }

    public class FunctionDeclaration extends ASTToken {

        private String type;
        private String functionName;
        private ParamList paramList;
        private ASTToken expression;
        private Block block;

        public FunctionDeclaration(String type, String functionName, ParamList paramList, ASTToken expression, Block block) {
            this.type = type;
            this.functionName = functionName;
            this.paramList = paramList;
            this.block = block;
            this.expression = expression;
        }

        public void evaluate(){
            
        }
    }

    public class VariableDeclaration extends ASTToken {
        private boolean deepcopy = false;
        private String varName;
        private String type;
        private ASTToken expression = null;
        private IdChainElement varCall;

        public VariableDeclaration(String type, String varName, ASTToken expression)
        {
            this.type = type;
            this.varName = varName;
            this.expression = expression;
        }

        public VariableDeclaration(String type, String varName, IdChainElement varCall)
        {
            this.type = type;
            this.varName = varName;
            this.varCall = varCall;
            this.deepcopy = true;
            
        }

        public void evaluate(){
            
        }
    }

    public class ReturnStatement extends ASTToken {

        private ASTToken expression;

        public ReturnStatement(ASTToken expression) {
            this.expression = expression;
        }

        public void evaluate(){
            
        }
    }

    public class IfStatement extends ASTToken {

        private ASTToken expression;
        private Block block;
        private Block elseBlock;

        public IfStatement(ASTToken expression, Block block, Block elseBlock) {
            this.expression = expression;
            this.block = block;
            this.elseBlock = elseBlock;
        }

        public void evaluate(){
            
        }
    }

    public class WhileLoop extends ASTToken {

        private Block block;
        private ASTToken expression;
        
        public WhileLoop(Block block, ASTToken expression)
        {
            this.expression = expression;
            this.block = block;
        }
        
        public void evaluate(){
            
        }
    }

    public class Assignment extends ASTToken {

        private IdChainElement chain;
        private ASTToken operation;

        public Assignment(IdChainElement chain, ASTToken operation)
        {
            this.chain = chain;
            this.operation = operation;
        }

        public void evaluate(){
            
        }
    }

    public class Operation extends ASTToken {

        ASTToken left;
        ASTToken right;

        String OperationType;

        public Operation(ASTToken left,ASTToken right, String OperationType) {
            this.left = left;
            this.right = right;
            this.OperationType = OperationType;
        }

        public void evaluate(){
            
        }
    }

    public class Unary extends ASTToken {

        public void evaluate(){
            
        }
    }

    public interface IdChainElement
    {
        public IdChainElement GetNext();
        public void SetNext(IdChainElement next);
    } 

    public class VariableCall extends ASTToken implements IdChainElement{

        String name;
        ASTToken next;
        public VariableCall(String name, ASTToken next) {
            this.name = name;
            this.next = next;
        }
        public void SetNext(IdChainElement next)
        {
            this.next = (ASTToken) next;
        }

        public IdChainElement GetNext()
        {
            if(next instanceof IdChainElement)
            {
                return (IdChainElement) next;
            }
            return null;
        }

        public void evaluate(){
            
        }
    }

    public class Literal extends ASTToken{
        String type;
        String value;
        String vorzeichen;
        public Literal(String type, String value, String vorzeichen) {
            this.type = type;
            this.value = value;
            this.vorzeichen = vorzeichen;
        }
        public void evaluate(){
            
        }
    }

    public class FunctionCall extends ASTToken implements IdChainElement{

        String name;
        Args args;
        ASTToken next;

        public FunctionCall(String name, Args args, ASTToken next) {
            this.name = name;
            this.args = args;
            this.next = next;
        }
        public void SetNext(IdChainElement next)
        {
            this.next = (ASTToken) next;
        }
        
        public IdChainElement GetNext()
        {
            if(next instanceof IdChainElement)
            {
                return (IdChainElement) next;
            }
            return null;
        }
        
        public void evaluate(){
            
        }
    }

    public class Args extends ASTToken {

        ArrayList<ASTToken> expressions;

        public Args(ArrayList<ASTToken> expressions) {
            this.expressions = expressions;
        }

        public void evaluate(){
            
        }
    }

    public class Literals extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class TypeReference extends ASTToken {

        Type type;
        public TypeReference (Type type) {
            this.type = type;
        }
        public void evaluate(){
            
        }
    }

    public class Type extends ASTToken {
        String type;
        public Type(String type) {
            this.type = type;
        }
        public void evaluate(){
            
        }
    }

    public class PrimitiveTypeKey extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class NOT extends ASTToken {
        private ASTToken child;
        
        public NOT(ASTToken child) {
            this.child = child;
        }

        public void evaluate(){
            
        }
    }
    
    //temp
    public class Expression extends ASTToken{
        public void evaluate(){
            
        }
    }

    

}
