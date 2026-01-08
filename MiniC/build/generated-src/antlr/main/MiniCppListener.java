// Generated from MiniCpp.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniCppParser}.
 */
public interface MiniCppListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(MiniCppParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(MiniCppParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(MiniCppParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(MiniCppParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MiniCppParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MiniCppParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#classMember}.
	 * @param ctx the parse tree
	 */
	void enterClassMember(MiniCppParser.ClassMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#classMember}.
	 * @param ctx the parse tree
	 */
	void exitClassMember(MiniCppParser.ClassMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#attributeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAttributeDeclaration(MiniCppParser.AttributeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#attributeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAttributeDeclaration(MiniCppParser.AttributeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#methodDefinition}.
	 * @param ctx the parse tree
	 */
	void enterMethodDefinition(MiniCppParser.MethodDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#methodDefinition}.
	 * @param ctx the parse tree
	 */
	void exitMethodDefinition(MiniCppParser.MethodDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#constructorDefinition}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDefinition(MiniCppParser.ConstructorDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#constructorDefinition}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDefinition(MiniCppParser.ConstructorDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(MiniCppParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(MiniCppParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MiniCppParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MiniCppParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MiniCppParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MiniCppParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MiniCppParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MiniCppParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(MiniCppParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(MiniCppParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MiniCppParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MiniCppParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(MiniCppParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(MiniCppParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(MiniCppParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(MiniCppParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MiniCppParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MiniCppParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(MiniCppParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(MiniCppParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#logicalOr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOr(MiniCppParser.LogicalOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#logicalOr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOr(MiniCppParser.LogicalOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#logicalAnd}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAnd(MiniCppParser.LogicalAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#logicalAnd}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAnd(MiniCppParser.LogicalAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#equal}.
	 * @param ctx the parse tree
	 */
	void enterEqual(MiniCppParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#equal}.
	 * @param ctx the parse tree
	 */
	void exitEqual(MiniCppParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelation(MiniCppParser.RelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelation(MiniCppParser.RelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#arith}.
	 * @param ctx the parse tree
	 */
	void enterArith(MiniCppParser.ArithContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#arith}.
	 * @param ctx the parse tree
	 */
	void exitArith(MiniCppParser.ArithContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(MiniCppParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(MiniCppParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(MiniCppParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(MiniCppParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(MiniCppParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(MiniCppParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#literals}.
	 * @param ctx the parse tree
	 */
	void enterLiterals(MiniCppParser.LiteralsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#literals}.
	 * @param ctx the parse tree
	 */
	void exitLiterals(MiniCppParser.LiteralsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#typeReference}.
	 * @param ctx the parse tree
	 */
	void enterTypeReference(MiniCppParser.TypeReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#typeReference}.
	 * @param ctx the parse tree
	 */
	void exitTypeReference(MiniCppParser.TypeReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MiniCppParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MiniCppParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#primitiveTypeKey}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveTypeKey(MiniCppParser.PrimitiveTypeKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#primitiveTypeKey}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveTypeKey(MiniCppParser.PrimitiveTypeKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniCppParser#boolean}.
	 * @param ctx the parse tree
	 */
	void enterBoolean(MiniCppParser.BooleanContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniCppParser#boolean}.
	 * @param ctx the parse tree
	 */
	void exitBoolean(MiniCppParser.BooleanContext ctx);
}