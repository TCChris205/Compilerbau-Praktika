// Generated from MiniCpp.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniCppParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniCppVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(MiniCppParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine(MiniCppParser.LineContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(MiniCppParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#classMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassMember(MiniCppParser.ClassMemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#attributeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeDeclaration(MiniCppParser.AttributeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#methodDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDefinition(MiniCppParser.MethodDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#constructorDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDefinition(MiniCppParser.ConstructorDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(MiniCppParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MiniCppParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MiniCppParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(MiniCppParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MiniCppParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MiniCppParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MiniCppParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(MiniCppParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MiniCppParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MiniCppParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#logicalOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOr(MiniCppParser.LogicalOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#logicalAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAnd(MiniCppParser.LogicalAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#equal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(MiniCppParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelation(MiniCppParser.RelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#arith}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith(MiniCppParser.ArithContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(MiniCppParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(MiniCppParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(MiniCppParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#literals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiterals(MiniCppParser.LiteralsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#typeReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeReference(MiniCppParser.TypeReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MiniCppParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#primitiveTypeKey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveTypeKey(MiniCppParser.PrimitiveTypeKeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCppParser#boolean}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(MiniCppParser.BooleanContext ctx);
}