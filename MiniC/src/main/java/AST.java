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
                    if (e.expression().assignment() != null) {
                        lines.add(toAST(e.expression().assignment()));
                    }
                    if (e.expression().logicalOr() != null) {
                        return new ReturnStatement(toAST(e.expression().logicalOr()));
                    }
                    
                    
                    lines.add(toAST(statment.expression()));
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
        Expression expression = toAST(e.expression());
        Block block = toAST(e.block());

        return new FunctionDeclaration(type, functionName, paramList, expression, block);
    }

    public VariableDeclaration toAST(MiniCppParser.VariableDeclarationContext e) {
        String type = e.type().getText();
        boolean deepcopy = e.DEEPCOPY() != null;
        String varName = e.ID(0).getText();
        if(deepcopy)
        {
            
        }
        Expression expr = toAST(e.expression());
        
        
        
    }

    public ReturnStatement toAST(MiniCppParser.ReturnStatementContext e) {
        if (e.expression().assignment() != null) {
            return new ReturnStatement(toAST(e.expression().assignment()));
        }
        if (e.expression().logicalOr() != null) {
            return new ReturnStatement(toAST(e.expression().logicalOr()));
        }
    }

    public IfStatement toAST(MiniCppParser.IfStatementContext e) {
        Expression expression = toAST(e.expression());
        Block block = toAST(e.block(1));
        Block elseBlock = null;
        if (e.block().size() > 1) {
            elseBlock = toAST(e.block(2));
        }

        return new IfStatement(expression, block, elseBlock);
    }

    public WhileLoop toAST(MiniCppParser.WhileLoopContext e) {
        Expression expression = toAST(e.expression());
        Block block = toAST(e.block());
        return new WhileLoop(block, expression);
    }

    public Assignment toAST(MiniCppParser.AssignmentContext e) {
        
    }

    public ASTToken toAST(MiniCppParser.LogicalOrContext e) {
        if (e.logicalAnd().size() == 1) {
            return toAST(e.logicalAnd(0));
        }
        
        ArrayList<ASTToken> operations = new ArrayList<>();
        for (int i = 0; i < e.logicalAnd().size(); i++) {
            operations.add(toAST(e.logicalAnd(i)));
        }
    }

    public ASTToken toAST(MiniCppParser.LogicalAndContext e) {
        if (e.equal().size() == 1) {
            return toAST(e.equal(0));
        }
        
        ArrayList<ASTToken> operations = new ArrayList<>();
        for (int i = 0; i < e.equal().size(); i++) {
            operations.add(toAST(e.equal(i)));
        }
    }

    public ASTToken toAST(MiniCppParser.EqualContext e) {
        if (e.relation().size() == 1) {
            return toAST(e.relation(0));
        }
        
        ArrayList<ASTToken> operations = new ArrayList<>();
        for (int i = 0; i < e.relation().size(); i++) {
            operations.add(toAST(e.relation(i)));
        }
    }

    public ASTToken toAST(MiniCppParser.RelationContext e) {
        if (e.arith().size() == 1) {
            return toAST(e.arith(0));
        }
        
        ArrayList<ASTToken> operations = new ArrayList<>();
        for (int i = 0; i < e.arith().size(); i++) {
            operations.add(toAST(e.arith(i)));
        }
    }

    public ASTToken toAST(MiniCppParser.ArithContext e) {
        if (e.term().size() == 1) {
            return toAST(e.term(0));
        }
        
        ArrayList<ASTToken> operations = new ArrayList<>();
        for (int i = 0; i < e.term().size(); i++) {
            operations.add(toAST(e.term(i)));
        }
    }

    public ASTToken toAST(MiniCppParser.TermContext e) {
        if (e.unary().size() == 1) {
            return toAST(e.unary(0));
        }
        
        ArrayList<ASTToken> operations = new ArrayList<>();
        for (int i = 0; i < e.unary().size(); i++) {
            operations.add(toAST(e.unary(i)));
        }
    }

    public ASTToken toAST(MiniCppParser.UnaryContext e) {
        if (e.expression() != null){
            //expression
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
            if (e.literals().NUM() != null){
                type = "num";
                value = e.literals().NUM().getText();
                return new Literal(type, value, vorzeichen);
            }
            if (e.literals().CHAR() != null){
                type = "char";
                value = e.literals().CHAR().getText();
                return new Literal(type, value, vorzeichen);
            }
            if (e.literals().STRING() != null){
                type = "string";
                value = e.literals().STRING().getText();
                return new Literal(type, value, vorzeichen);
            }
            if (e.literals().TRUE() != null){
                type = "bool";
                value = e.literals().TRUE().getText();
                return new Literal(type, value, vorzeichen);
            }
            if (e.literals().FALSE() != null){
                type = "bool";
                value = e.literals().FALSE().getText();
                return new Literal(type, value, vorzeichen);
            }
            if (e.literals().ID() != null){
                type = "var";
                value = e.literals().ID().getText();
                return new Literal(type, value, vorzeichen);
            }
            
        }
        if (e.idChain() != null){
            return toAST(e.idChain());
        }
    }

    public ASTToken toAST(MiniCppParser.IdChainContext e) {
        if (e.LPAREN() != null){
            //FunctionCall
            String name = e.ID().getText();
            Args args;
            if (e.args() != null) {
                args = toAST(e.args());
            }
            ASTToken next = toAST(e.idChain());
            return new FunctionCall(name,args,next);
        }
        else{
            //VariableCall
            String name = e.ID().getText();
            ASTToken next = toAST(e.idChain());
            return new VariableCall(name, next);
        }
    }

    public Args toAST(MiniCppParser.ArgsContext e) {
        //expressions
    }

    public Literals toAST(MiniCppParser.LiteralsContext e) {
        
    }

    public TypeReference toAST(MiniCppParser.TypeReferenceContext e) {
        
    }

    public Type toAST(MiniCppParser.TypeContext e) {
        
    }

    public PrimitiveTypeKey toAST(MiniCppParser.PrimitiveTypeKeyContext e) {
        
    }

    public Boolean toAST(MiniCppParser.BooleanContext e) {
        
    }

// ---------------------------- CLASSES ----------------------------

    public abstract class ASTToken {
        public abstract void evaluate();
    }

    public class Start extends ASTToken {
        private ArrayList<ASTToken> lines;

        public Start(ArrayList<ASTToken> lines)
        {

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

    public class Statement extends ASTToken {

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
        private Expression expression;
        private Block block;

        public FunctionDeclaration(String type, String functionName, ParamList paramList, Expression expression, Block block) {
            this.type = type;
            this.functionName = functionName;
            this.paramList = paramList;
            this.expression = expression;
            this.block = block;
        }

        public void evaluate(){
            
        }
    }

    public class VariableDeclaration extends ASTToken {
        boolean deepcopy = false;
        String varName;
        String type;
        Expression expression = null;

        public VariableDeclaration(String type, String varName, Expression expression)
        {
            this.type = type;
            this.varName = varName;
            this.expression = expression;
        }

        public VariableDeclaration(String type, String varName, ) // Ketten Problem für später
        {

            
        }

        public void evaluate(){
            
        }
    }

    public class ReturnStatement extends ASTToken {

        private Assignment assignment;
        private ASTToken logicalOr; 

        public ReturnStatement(Assignment assignment) {
            this.assignment = assignment;
            this.logicalOr = null;
        }

        public ReturnStatement(ASTToken logicalOr) {
            this.assignment = null;
            this.logicalOr = logicalOr;
        }

        public void evaluate(){
            
        }
    }

    public class IfStatement extends ASTToken {

        private Expression expression;
        private Block block;
        private Block elseBlock;

        public IfStatement(Expression expression, Block block, Block elseBlock) {
            this.expression = expression;
            this.block = block;
            this.elseBlock = elseBlock;
        }

        public void evaluate(){
            
        }
    }

    public class WhileLoop extends ASTToken {

        private Block block;
        private Expression expression;
        
        public WhileLoop(Block block, Expression expression)
        {
            this.block = block;
            this.expression = expression;
        }
        
        public void evaluate(){
            
        }
    }

    public class Assignment extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class Operation extends ASTToken {
        Operation opleft;
        Operation opRight;

        String OperationType;
        
        Unary unaryleft;
        Unary unaryright;

        public void evaluate(){
            
        }
    }

    public class Unary extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class VariableCall extends ASTToken {

        String name;
        ASTToken next;
        public VariableCall(String name, ASTToken next) {
            this.name = name;
            this.next = next;
        }
        public void evaluate(){
            
        }
    }

    public class Literal extends ASTToken {
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

    public class FunctionCall extends ASTToken {

        String name;
        Args args;
        ASTToken next;

        public FunctionCall(String name, Args args, ASTToken next) {
            this.name = name;
            this.args = args;
            this.next = next;
        }

        public void evaluate(){
            
        }
    }

    public class Args extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class Literals extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class TypeReference extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class Type extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class PrimitiveTypeKey extends ASTToken {

        public void evaluate(){
            
        }
    }

    public class Boolean extends ASTToken {

        public void evaluate(){
            
        }
    }
    //temp
    public class Expression extends ASTToken{
        public void evaluate(){
            
        }
    }

}
