import java.util.ArrayList;

import javax.sound.sampled.Line;

import org.antlr.v4.runtime.tree.ParseTree;

public class AST {

// ---------------------------- METHODS ----------------------------

    public ASTToken toAST(ParseTree e) {
        return null;
    }

    public Start toAST(MiniCppParser.StartContext e) {
        
        ArrayList<ASTToken> lines = new ArrayList<>();
        for (int i = 0; i < e.getChildCount(); i++)
        {
            lines.add(toAST(e.getChild(i)));
        }
        
    }

    public Line toAST(MiniCppParser.LineContext e) {
        
    }

    public ClassDeclaration toAST(MiniCppParser.ClassDeclarationContext e) {
        
    }

    public ClassMember toAST(MiniCppParser.ClassMemberContext e) {
        
    }

    public AttributeDeclaration toAST(MiniCppParser.AttributeDeclarationContext e) {
    
    }

    public MethodDefinition toAST(MiniCppParser.MethodDefinitionContext e) {
        
    }

    public Constructor toAST(MiniCppParser.ConstructorDefinitionContext e) {
        
    }

    public ParamList toAST(MiniCppParser.ParamListContext e) {
        
    }

    public Statement toAST(MiniCppParser.StatementContext e) {
        
    }

    public Block toAST(MiniCppParser.BlockContext e) {
        
    }

    public FunctionDeclaration toAST(MiniCppParser.FunctionDeclarationContext e) {
        
    }

    public VariableDeclaration toAST(MiniCppParser.VariableDeclarationContext e) {
        
    }

    public ReturnStatement toAST(MiniCppParser.ReturnStatementContext e) {
        
    }

    public IfStatement toAST(MiniCppParser.IfStatementContext e) {
        
    }

    public WhileLoop toAST(MiniCppParser.WhileLoopContext e) {
        
    }

    public Expression toAST(MiniCppParser.ExpressionContext e) {
        
    }

    public Assignment toAST(MiniCppParser.AssignmentContext e) {
        
    }

    public Operation toAST(MiniCppParser.LogicalOrContext e) {
        
    }

    public Operation toAST(MiniCppParser.LogicalAndContext e) {
        
    }

    public Operation toAST(MiniCppParser.EqualContext e) {
        
    }

    public Operation toAST(MiniCppParser.RelationContext e) {
        
    }

    public Operation toAST(MiniCppParser.ArithContext e) {
        
    }

    public Operation toAST(MiniCppParser.TermContext e) {
        
    }

    public Unary toAST(MiniCppParser.UnaryContext e) {
        
    }

    public Args toAST(MiniCppParser.ArgsContext e) {
        
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

    }

    public class Start extends ASTToken {
        private ArrayList<Line> lines;
    }

    public class Line extends ASTToken {

    }

    public class ClassDeclaration extends ASTToken {

    }

    public class ClassMember extends ASTToken {

    }

    public class AttributeDeclaration extends ASTToken {

    }

    public class MethodDefinition extends ASTToken {

    }

    public class Constructor extends ASTToken {

    }

    public class ParamList extends ASTToken {

    }

    public class Statement extends ASTToken {

    }

    public class Block extends ASTToken {

    }

    public class FunctionDeclaration extends ASTToken {

    }

    public class VariableDeclaration extends ASTToken {

    }

    public class ReturnStatement extends ASTToken {

    }

    public class IfStatement extends ASTToken {

    }

    public class WhileLoop extends ASTToken {

    }

    public class Expression extends ASTToken {

    }

    public class Assignment extends ASTToken {

    }

    public class Operation extends ASTToken {
        Operation opleft;
        Operation opRight;

        String OperationType;
        
        Unary unaryleft;
        Unary unaryright;
    }

    public class Unary extends ASTToken {

    }

    public class Args extends ASTToken {

    }

    public class Literals extends ASTToken {

    }

    public class TypeReference extends ASTToken {

    }

    public class Type extends ASTToken {

    }

    public class PrimitiveTypeKey extends ASTToken {

    }

    public class Boolean extends ASTToken {

    }

}
