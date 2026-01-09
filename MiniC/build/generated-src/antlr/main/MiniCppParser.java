// Generated from MiniCpp.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MiniCppParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT_KEY=1, BOOL_KEY=2, CHAR_KEY=3, STRING_KEY=4, IF_KEY=5, ELSE_KEY=6, 
		WHILE_KEY=7, RETURN_KEY=8, CLASS_KEY=9, VOID_KEY=10, PUBLIC_KEY=11, VIRTUAL_KEY=12, 
		LINE_COMMENT=13, BLOCK_COMMENT=14, INCLUDE_COMMENT=15, ASSIGN=16, EQ=17, 
		NEQ=18, LT=19, LE=20, GT=21, GE=22, NOT=23, AND=24, OR=25, PLUS=26, MINUS=27, 
		MUL=28, DIV=29, MOD=30, NUM=31, CHAR=32, STRING=33, DOT=34, SEMICOL=35, 
		COLON=36, LPAREN=37, RPAREN=38, COMMA=39, LBLOCKPAREN=40, RBLOCKPAREN=41, 
		DEEPCOPY=42, TRUE=43, FALSE=44, ID=45, WS=46;
	public static final int
		RULE_start = 0, RULE_line = 1, RULE_classDeclaration = 2, RULE_classMember = 3, 
		RULE_attributeDeclaration = 4, RULE_methodDefinition = 5, RULE_constructorDefinition = 6, 
		RULE_paramList = 7, RULE_statement = 8, RULE_block = 9, RULE_functionDeclaration = 10, 
		RULE_variableDeclaration = 11, RULE_idChain = 12, RULE_returnStatement = 13, 
		RULE_ifStatement = 14, RULE_whileLoop = 15, RULE_expression = 16, RULE_assignment = 17, 
		RULE_logicalOr = 18, RULE_logicalAnd = 19, RULE_equal = 20, RULE_relation = 21, 
		RULE_arith = 22, RULE_term = 23, RULE_unary = 24, RULE_args = 25, RULE_literals = 26, 
		RULE_typeReference = 27, RULE_type = 28, RULE_primitiveTypeKey = 29, RULE_boolean = 30;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "line", "classDeclaration", "classMember", "attributeDeclaration", 
			"methodDefinition", "constructorDefinition", "paramList", "statement", 
			"block", "functionDeclaration", "variableDeclaration", "idChain", "returnStatement", 
			"ifStatement", "whileLoop", "expression", "assignment", "logicalOr", 
			"logicalAnd", "equal", "relation", "arith", "term", "unary", "args", 
			"literals", "typeReference", "type", "primitiveTypeKey", "boolean"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'bool'", "'char'", "'string'", "'if'", "'else'", "'while'", 
			"'return'", "'class'", "'void'", "'public'", "'virtual'", null, null, 
			null, "'='", "'=='", "'!='", "'<'", "'<='", "'>'", "'>='", "'!'", "'&&'", 
			"'||'", "'+'", "'-'", "'*'", "'/'", "'%'", null, null, null, "'.'", "';'", 
			"':'", "'('", "')'", "','", "'{'", "'}'", "'&'", "'true'", "'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT_KEY", "BOOL_KEY", "CHAR_KEY", "STRING_KEY", "IF_KEY", "ELSE_KEY", 
			"WHILE_KEY", "RETURN_KEY", "CLASS_KEY", "VOID_KEY", "PUBLIC_KEY", "VIRTUAL_KEY", 
			"LINE_COMMENT", "BLOCK_COMMENT", "INCLUDE_COMMENT", "ASSIGN", "EQ", "NEQ", 
			"LT", "LE", "GT", "GE", "NOT", "AND", "OR", "PLUS", "MINUS", "MUL", "DIV", 
			"MOD", "NUM", "CHAR", "STRING", "DOT", "SEMICOL", "COLON", "LPAREN", 
			"RPAREN", "COMMA", "LBLOCKPAREN", "RBLOCKPAREN", "DEEPCOPY", "TRUE", 
			"FALSE", "ID", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MiniCpp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniCppParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(62);
				line();
				}
				}
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 62824843839422L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LineContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		try {
			setState(69);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KEY:
			case BOOL_KEY:
			case CHAR_KEY:
			case STRING_KEY:
			case IF_KEY:
			case WHILE_KEY:
			case RETURN_KEY:
			case VOID_KEY:
			case NOT:
			case PLUS:
			case MINUS:
			case NUM:
			case CHAR:
			case STRING:
			case LPAREN:
			case LBLOCKPAREN:
			case TRUE:
			case FALSE:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				statement();
				}
				break;
			case CLASS_KEY:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				classDeclaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode CLASS_KEY() { return getToken(MiniCppParser.CLASS_KEY, 0); }
		public List<TerminalNode> ID() { return getTokens(MiniCppParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniCppParser.ID, i);
		}
		public TerminalNode LBLOCKPAREN() { return getToken(MiniCppParser.LBLOCKPAREN, 0); }
		public List<TerminalNode> PUBLIC_KEY() { return getTokens(MiniCppParser.PUBLIC_KEY); }
		public TerminalNode PUBLIC_KEY(int i) {
			return getToken(MiniCppParser.PUBLIC_KEY, i);
		}
		public List<TerminalNode> COLON() { return getTokens(MiniCppParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MiniCppParser.COLON, i);
		}
		public TerminalNode RBLOCKPAREN() { return getToken(MiniCppParser.RBLOCKPAREN, 0); }
		public TerminalNode SEMICOL() { return getToken(MiniCppParser.SEMICOL, 0); }
		public List<ClassMemberContext> classMember() {
			return getRuleContexts(ClassMemberContext.class);
		}
		public ClassMemberContext classMember(int i) {
			return getRuleContext(ClassMemberContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(CLASS_KEY);
			setState(72);
			match(ID);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(73);
				match(COLON);
				setState(74);
				match(PUBLIC_KEY);
				setState(75);
				match(ID);
				}
			}

			setState(78);
			match(LBLOCKPAREN);
			setState(79);
			match(PUBLIC_KEY);
			setState(80);
			match(COLON);
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 35184372093982L) != 0)) {
				{
				{
				setState(81);
				classMember();
				}
				}
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(87);
			match(RBLOCKPAREN);
			setState(88);
			match(SEMICOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassMemberContext extends ParserRuleContext {
		public AttributeDeclarationContext attributeDeclaration() {
			return getRuleContext(AttributeDeclarationContext.class,0);
		}
		public ConstructorDefinitionContext constructorDefinition() {
			return getRuleContext(ConstructorDefinitionContext.class,0);
		}
		public MethodDefinitionContext methodDefinition() {
			return getRuleContext(MethodDefinitionContext.class,0);
		}
		public ClassMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterClassMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitClassMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitClassMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassMemberContext classMember() throws RecognitionException {
		ClassMemberContext _localctx = new ClassMemberContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classMember);
		try {
			setState(93);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(90);
				attributeDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				constructorDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				methodDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public TerminalNode SEMICOL() { return getToken(MiniCppParser.SEMICOL, 0); }
		public AttributeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterAttributeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitAttributeDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitAttributeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeDeclarationContext attributeDeclaration() throws RecognitionException {
		AttributeDeclarationContext _localctx = new AttributeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_attributeDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			type();
			setState(96);
			match(ID);
			setState(97);
			match(SEMICOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodDefinitionContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(MiniCppParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MiniCppParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode VIRTUAL_KEY() { return getToken(MiniCppParser.VIRTUAL_KEY, 0); }
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public MethodDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterMethodDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitMethodDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitMethodDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDefinitionContext methodDefinition() throws RecognitionException {
		MethodDefinitionContext _localctx = new MethodDefinitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_methodDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VIRTUAL_KEY) {
				{
				setState(99);
				match(VIRTUAL_KEY);
				}
			}

			setState(102);
			type();
			setState(103);
			match(ID);
			setState(104);
			match(LPAREN);
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 35184372089886L) != 0)) {
				{
				setState(105);
				paramList();
				}
			}

			setState(108);
			match(RPAREN);
			setState(109);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstructorDefinitionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(MiniCppParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MiniCppParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public ConstructorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterConstructorDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitConstructorDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitConstructorDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDefinitionContext constructorDefinition() throws RecognitionException {
		ConstructorDefinitionContext _localctx = new ConstructorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constructorDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(ID);
			setState(112);
			match(LPAREN);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 35184372089886L) != 0)) {
				{
				setState(113);
				paramList();
				}
			}

			setState(116);
			match(RPAREN);
			setState(117);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamListContext extends ParserRuleContext {
		public List<TypeReferenceContext> typeReference() {
			return getRuleContexts(TypeReferenceContext.class);
		}
		public TypeReferenceContext typeReference(int i) {
			return getRuleContext(TypeReferenceContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(MiniCppParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniCppParser.ID, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MiniCppParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MiniCppParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			typeReference();
			setState(120);
			match(ID);
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(121);
				match(COMMA);
				setState(122);
				typeReference();
				setState(123);
				match(ID);
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileLoopContext whileLoop() {
			return getRuleContext(WhileLoopContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOL() { return getToken(MiniCppParser.SEMICOL, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_statement);
		try {
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				functionDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(132);
				variableDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(133);
				returnStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(134);
				ifStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(135);
				whileLoop();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(136);
				expression();
				setState(137);
				match(SEMICOL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBLOCKPAREN() { return getToken(MiniCppParser.LBLOCKPAREN, 0); }
		public TerminalNode RBLOCKPAREN() { return getToken(MiniCppParser.RBLOCKPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(LBLOCKPAREN);
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 62824843838910L) != 0)) {
				{
				{
				setState(142);
				statement();
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148);
			match(RBLOCKPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(MiniCppParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MiniCppParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(MiniCppParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			type();
			setState(151);
			match(ID);
			setState(152);
			match(LPAREN);
			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 35184372089886L) != 0)) {
				{
				setState(153);
				paramList();
				}
			}

			setState(156);
			match(RPAREN);
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(157);
				match(ASSIGN);
				setState(158);
				expression();
				}
			}

			setState(161);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(MiniCppParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniCppParser.ID, i);
		}
		public TerminalNode SEMICOL() { return getToken(MiniCppParser.SEMICOL, 0); }
		public TerminalNode ASSIGN() { return getToken(MiniCppParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DEEPCOPY() { return getToken(MiniCppParser.DEEPCOPY, 0); }
		public IdChainContext idChain() {
			return getRuleContext(IdChainContext.class,0);
		}
		public TerminalNode DOT() { return getToken(MiniCppParser.DOT, 0); }
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_variableDeclaration);
		int _la;
		try {
			setState(183);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(163);
				type();
				setState(164);
				match(ID);
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASSIGN) {
					{
					setState(165);
					match(ASSIGN);
					setState(166);
					expression();
					}
				}

				setState(169);
				match(SEMICOL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(171);
				type();
				setState(172);
				match(DEEPCOPY);
				setState(173);
				match(ID);
				setState(174);
				match(ASSIGN);
				setState(178);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(175);
					idChain();
					setState(176);
					match(DOT);
					}
					break;
				}
				setState(180);
				match(ID);
				setState(181);
				match(SEMICOL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdChainContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public TerminalNode DOT() { return getToken(MiniCppParser.DOT, 0); }
		public IdChainContext idChain() {
			return getRuleContext(IdChainContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MiniCppParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MiniCppParser.RPAREN, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public IdChainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idChain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterIdChain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitIdChain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitIdChain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdChainContext idChain() throws RecognitionException {
		IdChainContext _localctx = new IdChainContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_idChain);
		int _la;
		try {
			setState(201);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				match(ID);
				setState(188);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(186);
					match(DOT);
					setState(187);
					idChain();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				match(ID);
				{
				setState(191);
				match(LPAREN);
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 61725332209664L) != 0)) {
					{
					setState(192);
					args();
					}
				}

				setState(195);
				match(RPAREN);
				}
				setState(199);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
				case 1:
					{
					setState(197);
					match(DOT);
					setState(198);
					idChain();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN_KEY() { return getToken(MiniCppParser.RETURN_KEY, 0); }
		public TerminalNode SEMICOL() { return getToken(MiniCppParser.SEMICOL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(RETURN_KEY);
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 61725332209664L) != 0)) {
				{
				setState(204);
				expression();
				}
			}

			setState(207);
			match(SEMICOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF_KEY() { return getToken(MiniCppParser.IF_KEY, 0); }
		public TerminalNode LPAREN() { return getToken(MiniCppParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MiniCppParser.RPAREN, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE_KEY() { return getToken(MiniCppParser.ELSE_KEY, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(IF_KEY);
			setState(210);
			match(LPAREN);
			setState(211);
			expression();
			setState(212);
			match(RPAREN);
			setState(213);
			block();
			setState(216);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE_KEY) {
				{
				setState(214);
				match(ELSE_KEY);
				setState(215);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileLoopContext extends ParserRuleContext {
		public TerminalNode WHILE_KEY() { return getToken(MiniCppParser.WHILE_KEY, 0); }
		public TerminalNode LPAREN() { return getToken(MiniCppParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MiniCppParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileLoop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterWhileLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitWhileLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileLoopContext whileLoop() throws RecognitionException {
		WhileLoopContext _localctx = new WhileLoopContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_whileLoop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(WHILE_KEY);
			setState(219);
			match(LPAREN);
			setState(220);
			expression();
			setState(221);
			match(RPAREN);
			setState(222);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public LogicalOrContext logicalOr() {
			return getRuleContext(LogicalOrContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_expression);
		try {
			setState(226);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(224);
				assignment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(225);
				logicalOr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(MiniCppParser.ASSIGN, 0); }
		public LogicalOrContext logicalOr() {
			return getRuleContext(LogicalOrContext.class,0);
		}
		public IdChainContext idChain() {
			return getRuleContext(IdChainContext.class,0);
		}
		public TerminalNode DOT() { return getToken(MiniCppParser.DOT, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(228);
				idChain();
				setState(229);
				match(DOT);
				}
				break;
			}
			setState(233);
			match(ID);
			setState(234);
			match(ASSIGN);
			setState(235);
			logicalOr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalOrContext extends ParserRuleContext {
		public List<LogicalAndContext> logicalAnd() {
			return getRuleContexts(LogicalAndContext.class);
		}
		public LogicalAndContext logicalAnd(int i) {
			return getRuleContext(LogicalAndContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(MiniCppParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(MiniCppParser.OR, i);
		}
		public LogicalOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterLogicalOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitLogicalOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitLogicalOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrContext logicalOr() throws RecognitionException {
		LogicalOrContext _localctx = new LogicalOrContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_logicalOr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			logicalAnd();
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(238);
				match(OR);
				setState(239);
				logicalAnd();
				}
				}
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalAndContext extends ParserRuleContext {
		public List<EqualContext> equal() {
			return getRuleContexts(EqualContext.class);
		}
		public EqualContext equal(int i) {
			return getRuleContext(EqualContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(MiniCppParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(MiniCppParser.AND, i);
		}
		public LogicalAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterLogicalAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitLogicalAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitLogicalAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndContext logicalAnd() throws RecognitionException {
		LogicalAndContext _localctx = new LogicalAndContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_logicalAnd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			equal();
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(246);
				match(AND);
				setState(247);
				equal();
				}
				}
				setState(252);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EqualContext extends ParserRuleContext {
		public List<RelationContext> relation() {
			return getRuleContexts(RelationContext.class);
		}
		public RelationContext relation(int i) {
			return getRuleContext(RelationContext.class,i);
		}
		public List<TerminalNode> EQ() { return getTokens(MiniCppParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(MiniCppParser.EQ, i);
		}
		public List<TerminalNode> NEQ() { return getTokens(MiniCppParser.NEQ); }
		public TerminalNode NEQ(int i) {
			return getToken(MiniCppParser.NEQ, i);
		}
		public EqualContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitEqual(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitEqual(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualContext equal() throws RecognitionException {
		EqualContext _localctx = new EqualContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_equal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			relation();
			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQ || _la==NEQ) {
				{
				{
				setState(254);
				_la = _input.LA(1);
				if ( !(_la==EQ || _la==NEQ) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(255);
				relation();
				}
				}
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationContext extends ParserRuleContext {
		public List<ArithContext> arith() {
			return getRuleContexts(ArithContext.class);
		}
		public ArithContext arith(int i) {
			return getRuleContext(ArithContext.class,i);
		}
		public List<TerminalNode> LT() { return getTokens(MiniCppParser.LT); }
		public TerminalNode LT(int i) {
			return getToken(MiniCppParser.LT, i);
		}
		public List<TerminalNode> LE() { return getTokens(MiniCppParser.LE); }
		public TerminalNode LE(int i) {
			return getToken(MiniCppParser.LE, i);
		}
		public List<TerminalNode> GT() { return getTokens(MiniCppParser.GT); }
		public TerminalNode GT(int i) {
			return getToken(MiniCppParser.GT, i);
		}
		public List<TerminalNode> GE() { return getTokens(MiniCppParser.GE); }
		public TerminalNode GE(int i) {
			return getToken(MiniCppParser.GE, i);
		}
		public RelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationContext relation() throws RecognitionException {
		RelationContext _localctx = new RelationContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_relation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			arith();
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7864320L) != 0)) {
				{
				{
				setState(262);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7864320L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(263);
				arith();
				}
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArithContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(MiniCppParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(MiniCppParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(MiniCppParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(MiniCppParser.MINUS, i);
		}
		public ArithContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arith; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterArith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitArith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitArith(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithContext arith() throws RecognitionException {
		ArithContext _localctx = new ArithContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_arith);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			term();
			setState(274);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(270);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(271);
				term();
				}
				}
				setState(276);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public List<UnaryContext> unary() {
			return getRuleContexts(UnaryContext.class);
		}
		public UnaryContext unary(int i) {
			return getRuleContext(UnaryContext.class,i);
		}
		public List<TerminalNode> MUL() { return getTokens(MiniCppParser.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(MiniCppParser.MUL, i);
		}
		public List<TerminalNode> DIV() { return getTokens(MiniCppParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(MiniCppParser.DIV, i);
		}
		public List<TerminalNode> MOD() { return getTokens(MiniCppParser.MOD); }
		public TerminalNode MOD(int i) {
			return getToken(MiniCppParser.MOD, i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			unary();
			setState(282);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1879048192L) != 0)) {
				{
				{
				setState(278);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1879048192L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(279);
				unary();
				}
				}
				setState(284);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnaryContext extends ParserRuleContext {
		public IdChainContext idChain() {
			return getRuleContext(IdChainContext.class,0);
		}
		public TerminalNode NOT() { return getToken(MiniCppParser.NOT, 0); }
		public LiteralsContext literals() {
			return getRuleContext(LiteralsContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(MiniCppParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MiniCppParser.MINUS, 0); }
		public TerminalNode LPAREN() { return getToken(MiniCppParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MiniCppParser.RPAREN, 0); }
		public UnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryContext unary() throws RecognitionException {
		UnaryContext _localctx = new UnaryContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_unary);
		int _la;
		try {
			setState(303);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(286);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(285);
					match(NOT);
					}
				}

				setState(288);
				idChain();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(289);
					match(NOT);
					}
				}

				setState(293);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PLUS || _la==MINUS) {
					{
					setState(292);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(295);
				literals();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(297);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(296);
					match(NOT);
					}
				}

				setState(299);
				match(LPAREN);
				setState(300);
				expression();
				setState(301);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgsContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MiniCppParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MiniCppParser.COMMA, i);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_args);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			expression();
			setState(310);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(306);
				match(COMMA);
				setState(307);
				expression();
				}
				}
				setState(312);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralsContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(MiniCppParser.NUM, 0); }
		public TerminalNode CHAR() { return getToken(MiniCppParser.CHAR, 0); }
		public TerminalNode STRING() { return getToken(MiniCppParser.STRING, 0); }
		public TerminalNode TRUE() { return getToken(MiniCppParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(MiniCppParser.FALSE, 0); }
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public LiteralsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literals; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterLiterals(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitLiterals(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitLiterals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralsContext literals() throws RecognitionException {
		LiteralsContext _localctx = new LiteralsContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_literals);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 61587683540992L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeReferenceContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode DEEPCOPY() { return getToken(MiniCppParser.DEEPCOPY, 0); }
		public TypeReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterTypeReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitTypeReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitTypeReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeReferenceContext typeReference() throws RecognitionException {
		TypeReferenceContext _localctx = new TypeReferenceContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_typeReference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			type();
			setState(317);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEEPCOPY) {
				{
				setState(316);
				match(DEEPCOPY);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public PrimitiveTypeKeyContext primitiveTypeKey() {
			return getRuleContext(PrimitiveTypeKeyContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniCppParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_type);
		try {
			setState(321);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_KEY:
			case BOOL_KEY:
			case CHAR_KEY:
			case STRING_KEY:
			case VOID_KEY:
				enterOuterAlt(_localctx, 1);
				{
				setState(319);
				primitiveTypeKey();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(320);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimitiveTypeKeyContext extends ParserRuleContext {
		public TerminalNode INT_KEY() { return getToken(MiniCppParser.INT_KEY, 0); }
		public TerminalNode BOOL_KEY() { return getToken(MiniCppParser.BOOL_KEY, 0); }
		public TerminalNode STRING_KEY() { return getToken(MiniCppParser.STRING_KEY, 0); }
		public TerminalNode CHAR_KEY() { return getToken(MiniCppParser.CHAR_KEY, 0); }
		public TerminalNode VOID_KEY() { return getToken(MiniCppParser.VOID_KEY, 0); }
		public PrimitiveTypeKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveTypeKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterPrimitiveTypeKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitPrimitiveTypeKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitPrimitiveTypeKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeKeyContext primitiveTypeKey() throws RecognitionException {
		PrimitiveTypeKeyContext _localctx = new PrimitiveTypeKeyContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_primitiveTypeKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1054L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(MiniCppParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(MiniCppParser.FALSE, 0); }
		public BooleanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).enterBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniCppListener ) ((MiniCppListener)listener).exitBoolean(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniCppVisitor ) return ((MiniCppVisitor<? extends T>)visitor).visitBoolean(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanContext boolean_() throws RecognitionException {
		BooleanContext _localctx = new BooleanContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_boolean);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001.\u0148\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0001\u0000\u0004\u0000@\b\u0000\u000b\u0000\f\u0000A\u0001\u0001\u0001"+
		"\u0001\u0003\u0001F\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0003\u0002M\b\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0005\u0002S\b\u0002\n\u0002\f\u0002V\t\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003^\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0003\u0005e\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005k\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0003\u0006s\b\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0005\u0007~\b\u0007\n\u0007\f\u0007\u0081\t\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0003\b\u008c\b\b\u0001\t\u0001\t\u0005\t\u0090\b\t\n\t\f\t\u0093\t"+
		"\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u009b\b\n\u0001"+
		"\n\u0001\n\u0001\n\u0003\n\u00a0\b\n\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00a8\b\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0003\u000b\u00b3\b\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0003\u000b\u00b8\b\u000b\u0001\f\u0001\f\u0001\f\u0003\f\u00bd"+
		"\b\f\u0001\f\u0001\f\u0001\f\u0003\f\u00c2\b\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0003\f\u00c8\b\f\u0003\f\u00ca\b\f\u0001\r\u0001\r\u0003\r\u00ce"+
		"\b\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00d9\b\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0003\u0010\u00e3\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0003"+
		"\u0011\u00e8\b\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u00f1\b\u0012\n\u0012\f\u0012"+
		"\u00f4\t\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00f9\b"+
		"\u0013\n\u0013\f\u0013\u00fc\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0005\u0014\u0101\b\u0014\n\u0014\f\u0014\u0104\t\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0005\u0015\u0109\b\u0015\n\u0015\f\u0015\u010c\t\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u0111\b\u0016\n\u0016"+
		"\f\u0016\u0114\t\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017"+
		"\u0119\b\u0017\n\u0017\f\u0017\u011c\t\u0017\u0001\u0018\u0003\u0018\u011f"+
		"\b\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u0123\b\u0018\u0001\u0018"+
		"\u0003\u0018\u0126\b\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u012a\b"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u0130"+
		"\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0005\u0019\u0135\b\u0019"+
		"\n\u0019\f\u0019\u0138\t\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001"+
		"\u001b\u0003\u001b\u013e\b\u001b\u0001\u001c\u0001\u001c\u0003\u001c\u0142"+
		"\b\u001c\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0000"+
		"\u0000\u001f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*,.02468:<\u0000\u0007\u0001\u0000\u0011"+
		"\u0012\u0001\u0000\u0013\u0016\u0001\u0000\u001a\u001b\u0001\u0000\u001c"+
		"\u001e\u0002\u0000\u001f!+-\u0002\u0000\u0001\u0004\n\n\u0001\u0000+,"+
		"\u0155\u0000?\u0001\u0000\u0000\u0000\u0002E\u0001\u0000\u0000\u0000\u0004"+
		"G\u0001\u0000\u0000\u0000\u0006]\u0001\u0000\u0000\u0000\b_\u0001\u0000"+
		"\u0000\u0000\nd\u0001\u0000\u0000\u0000\fo\u0001\u0000\u0000\u0000\u000e"+
		"w\u0001\u0000\u0000\u0000\u0010\u008b\u0001\u0000\u0000\u0000\u0012\u008d"+
		"\u0001\u0000\u0000\u0000\u0014\u0096\u0001\u0000\u0000\u0000\u0016\u00b7"+
		"\u0001\u0000\u0000\u0000\u0018\u00c9\u0001\u0000\u0000\u0000\u001a\u00cb"+
		"\u0001\u0000\u0000\u0000\u001c\u00d1\u0001\u0000\u0000\u0000\u001e\u00da"+
		"\u0001\u0000\u0000\u0000 \u00e2\u0001\u0000\u0000\u0000\"\u00e7\u0001"+
		"\u0000\u0000\u0000$\u00ed\u0001\u0000\u0000\u0000&\u00f5\u0001\u0000\u0000"+
		"\u0000(\u00fd\u0001\u0000\u0000\u0000*\u0105\u0001\u0000\u0000\u0000,"+
		"\u010d\u0001\u0000\u0000\u0000.\u0115\u0001\u0000\u0000\u00000\u012f\u0001"+
		"\u0000\u0000\u00002\u0131\u0001\u0000\u0000\u00004\u0139\u0001\u0000\u0000"+
		"\u00006\u013b\u0001\u0000\u0000\u00008\u0141\u0001\u0000\u0000\u0000:"+
		"\u0143\u0001\u0000\u0000\u0000<\u0145\u0001\u0000\u0000\u0000>@\u0003"+
		"\u0002\u0001\u0000?>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000"+
		"A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000B\u0001\u0001\u0000"+
		"\u0000\u0000CF\u0003\u0010\b\u0000DF\u0003\u0004\u0002\u0000EC\u0001\u0000"+
		"\u0000\u0000ED\u0001\u0000\u0000\u0000F\u0003\u0001\u0000\u0000\u0000"+
		"GH\u0005\t\u0000\u0000HL\u0005-\u0000\u0000IJ\u0005$\u0000\u0000JK\u0005"+
		"\u000b\u0000\u0000KM\u0005-\u0000\u0000LI\u0001\u0000\u0000\u0000LM\u0001"+
		"\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000NO\u0005(\u0000\u0000OP\u0005"+
		"\u000b\u0000\u0000PT\u0005$\u0000\u0000QS\u0003\u0006\u0003\u0000RQ\u0001"+
		"\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000"+
		"TU\u0001\u0000\u0000\u0000UW\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000"+
		"\u0000WX\u0005)\u0000\u0000XY\u0005#\u0000\u0000Y\u0005\u0001\u0000\u0000"+
		"\u0000Z^\u0003\b\u0004\u0000[^\u0003\f\u0006\u0000\\^\u0003\n\u0005\u0000"+
		"]Z\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000]\\\u0001\u0000\u0000"+
		"\u0000^\u0007\u0001\u0000\u0000\u0000_`\u00038\u001c\u0000`a\u0005-\u0000"+
		"\u0000ab\u0005#\u0000\u0000b\t\u0001\u0000\u0000\u0000ce\u0005\f\u0000"+
		"\u0000dc\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000ef\u0001\u0000"+
		"\u0000\u0000fg\u00038\u001c\u0000gh\u0005-\u0000\u0000hj\u0005%\u0000"+
		"\u0000ik\u0003\u000e\u0007\u0000ji\u0001\u0000\u0000\u0000jk\u0001\u0000"+
		"\u0000\u0000kl\u0001\u0000\u0000\u0000lm\u0005&\u0000\u0000mn\u0003\u0012"+
		"\t\u0000n\u000b\u0001\u0000\u0000\u0000op\u0005-\u0000\u0000pr\u0005%"+
		"\u0000\u0000qs\u0003\u000e\u0007\u0000rq\u0001\u0000\u0000\u0000rs\u0001"+
		"\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tu\u0005&\u0000\u0000uv\u0003"+
		"\u0012\t\u0000v\r\u0001\u0000\u0000\u0000wx\u00036\u001b\u0000x\u007f"+
		"\u0005-\u0000\u0000yz\u0005\'\u0000\u0000z{\u00036\u001b\u0000{|\u0005"+
		"-\u0000\u0000|~\u0001\u0000\u0000\u0000}y\u0001\u0000\u0000\u0000~\u0081"+
		"\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001"+
		"\u0000\u0000\u0000\u0080\u000f\u0001\u0000\u0000\u0000\u0081\u007f\u0001"+
		"\u0000\u0000\u0000\u0082\u008c\u0003\u0012\t\u0000\u0083\u008c\u0003\u0014"+
		"\n\u0000\u0084\u008c\u0003\u0016\u000b\u0000\u0085\u008c\u0003\u001a\r"+
		"\u0000\u0086\u008c\u0003\u001c\u000e\u0000\u0087\u008c\u0003\u001e\u000f"+
		"\u0000\u0088\u0089\u0003 \u0010\u0000\u0089\u008a\u0005#\u0000\u0000\u008a"+
		"\u008c\u0001\u0000\u0000\u0000\u008b\u0082\u0001\u0000\u0000\u0000\u008b"+
		"\u0083\u0001\u0000\u0000\u0000\u008b\u0084\u0001\u0000\u0000\u0000\u008b"+
		"\u0085\u0001\u0000\u0000\u0000\u008b\u0086\u0001\u0000\u0000\u0000\u008b"+
		"\u0087\u0001\u0000\u0000\u0000\u008b\u0088\u0001\u0000\u0000\u0000\u008c"+
		"\u0011\u0001\u0000\u0000\u0000\u008d\u0091\u0005(\u0000\u0000\u008e\u0090"+
		"\u0003\u0010\b\u0000\u008f\u008e\u0001\u0000\u0000\u0000\u0090\u0093\u0001"+
		"\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0091\u0092\u0001"+
		"\u0000\u0000\u0000\u0092\u0094\u0001\u0000\u0000\u0000\u0093\u0091\u0001"+
		"\u0000\u0000\u0000\u0094\u0095\u0005)\u0000\u0000\u0095\u0013\u0001\u0000"+
		"\u0000\u0000\u0096\u0097\u00038\u001c\u0000\u0097\u0098\u0005-\u0000\u0000"+
		"\u0098\u009a\u0005%\u0000\u0000\u0099\u009b\u0003\u000e\u0007\u0000\u009a"+
		"\u0099\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b"+
		"\u009c\u0001\u0000\u0000\u0000\u009c\u009f\u0005&\u0000\u0000\u009d\u009e"+
		"\u0005\u0010\u0000\u0000\u009e\u00a0\u0003 \u0010\u0000\u009f\u009d\u0001"+
		"\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001"+
		"\u0000\u0000\u0000\u00a1\u00a2\u0003\u0012\t\u0000\u00a2\u0015\u0001\u0000"+
		"\u0000\u0000\u00a3\u00a4\u00038\u001c\u0000\u00a4\u00a7\u0005-\u0000\u0000"+
		"\u00a5\u00a6\u0005\u0010\u0000\u0000\u00a6\u00a8\u0003 \u0010\u0000\u00a7"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a9\u0001\u0000\u0000\u0000\u00a9\u00aa\u0005#\u0000\u0000\u00aa\u00b8"+
		"\u0001\u0000\u0000\u0000\u00ab\u00ac\u00038\u001c\u0000\u00ac\u00ad\u0005"+
		"*\u0000\u0000\u00ad\u00ae\u0005-\u0000\u0000\u00ae\u00b2\u0005\u0010\u0000"+
		"\u0000\u00af\u00b0\u0003\u0018\f\u0000\u00b0\u00b1\u0005\"\u0000\u0000"+
		"\u00b1\u00b3\u0001\u0000\u0000\u0000\u00b2\u00af\u0001\u0000\u0000\u0000"+
		"\u00b2\u00b3\u0001\u0000\u0000\u0000\u00b3\u00b4\u0001\u0000\u0000\u0000"+
		"\u00b4\u00b5\u0005-\u0000\u0000\u00b5\u00b6\u0005#\u0000\u0000\u00b6\u00b8"+
		"\u0001\u0000\u0000\u0000\u00b7\u00a3\u0001\u0000\u0000\u0000\u00b7\u00ab"+
		"\u0001\u0000\u0000\u0000\u00b8\u0017\u0001\u0000\u0000\u0000\u00b9\u00bc"+
		"\u0005-\u0000\u0000\u00ba\u00bb\u0005\"\u0000\u0000\u00bb\u00bd\u0003"+
		"\u0018\f\u0000\u00bc\u00ba\u0001\u0000\u0000\u0000\u00bc\u00bd\u0001\u0000"+
		"\u0000\u0000\u00bd\u00ca\u0001\u0000\u0000\u0000\u00be\u00bf\u0005-\u0000"+
		"\u0000\u00bf\u00c1\u0005%\u0000\u0000\u00c0\u00c2\u00032\u0019\u0000\u00c1"+
		"\u00c0\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2"+
		"\u00c3\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005&\u0000\u0000\u00c4\u00c7"+
		"\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\"\u0000\u0000\u00c6\u00c8\u0003"+
		"\u0018\f\u0000\u00c7\u00c5\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000"+
		"\u0000\u0000\u00c8\u00ca\u0001\u0000\u0000\u0000\u00c9\u00b9\u0001\u0000"+
		"\u0000\u0000\u00c9\u00be\u0001\u0000\u0000\u0000\u00ca\u0019\u0001\u0000"+
		"\u0000\u0000\u00cb\u00cd\u0005\b\u0000\u0000\u00cc\u00ce\u0003 \u0010"+
		"\u0000\u00cd\u00cc\u0001\u0000\u0000\u0000\u00cd\u00ce\u0001\u0000\u0000"+
		"\u0000\u00ce\u00cf\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005#\u0000\u0000"+
		"\u00d0\u001b\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\u0005\u0000\u0000"+
		"\u00d2\u00d3\u0005%\u0000\u0000\u00d3\u00d4\u0003 \u0010\u0000\u00d4\u00d5"+
		"\u0005&\u0000\u0000\u00d5\u00d8\u0003\u0012\t\u0000\u00d6\u00d7\u0005"+
		"\u0006\u0000\u0000\u00d7\u00d9\u0003\u0012\t\u0000\u00d8\u00d6\u0001\u0000"+
		"\u0000\u0000\u00d8\u00d9\u0001\u0000\u0000\u0000\u00d9\u001d\u0001\u0000"+
		"\u0000\u0000\u00da\u00db\u0005\u0007\u0000\u0000\u00db\u00dc\u0005%\u0000"+
		"\u0000\u00dc\u00dd\u0003 \u0010\u0000\u00dd\u00de\u0005&\u0000\u0000\u00de"+
		"\u00df\u0003\u0012\t\u0000\u00df\u001f\u0001\u0000\u0000\u0000\u00e0\u00e3"+
		"\u0003\"\u0011\u0000\u00e1\u00e3\u0003$\u0012\u0000\u00e2\u00e0\u0001"+
		"\u0000\u0000\u0000\u00e2\u00e1\u0001\u0000\u0000\u0000\u00e3!\u0001\u0000"+
		"\u0000\u0000\u00e4\u00e5\u0003\u0018\f\u0000\u00e5\u00e6\u0005\"\u0000"+
		"\u0000\u00e6\u00e8\u0001\u0000\u0000\u0000\u00e7\u00e4\u0001\u0000\u0000"+
		"\u0000\u00e7\u00e8\u0001\u0000\u0000\u0000\u00e8\u00e9\u0001\u0000\u0000"+
		"\u0000\u00e9\u00ea\u0005-\u0000\u0000\u00ea\u00eb\u0005\u0010\u0000\u0000"+
		"\u00eb\u00ec\u0003$\u0012\u0000\u00ec#\u0001\u0000\u0000\u0000\u00ed\u00f2"+
		"\u0003&\u0013\u0000\u00ee\u00ef\u0005\u0019\u0000\u0000\u00ef\u00f1\u0003"+
		"&\u0013\u0000\u00f0\u00ee\u0001\u0000\u0000\u0000\u00f1\u00f4\u0001\u0000"+
		"\u0000\u0000\u00f2\u00f0\u0001\u0000\u0000\u0000\u00f2\u00f3\u0001\u0000"+
		"\u0000\u0000\u00f3%\u0001\u0000\u0000\u0000\u00f4\u00f2\u0001\u0000\u0000"+
		"\u0000\u00f5\u00fa\u0003(\u0014\u0000\u00f6\u00f7\u0005\u0018\u0000\u0000"+
		"\u00f7\u00f9\u0003(\u0014\u0000\u00f8\u00f6\u0001\u0000\u0000\u0000\u00f9"+
		"\u00fc\u0001\u0000\u0000\u0000\u00fa\u00f8\u0001\u0000\u0000\u0000\u00fa"+
		"\u00fb\u0001\u0000\u0000\u0000\u00fb\'\u0001\u0000\u0000\u0000\u00fc\u00fa"+
		"\u0001\u0000\u0000\u0000\u00fd\u0102\u0003*\u0015\u0000\u00fe\u00ff\u0007"+
		"\u0000\u0000\u0000\u00ff\u0101\u0003*\u0015\u0000\u0100\u00fe\u0001\u0000"+
		"\u0000\u0000\u0101\u0104\u0001\u0000\u0000\u0000\u0102\u0100\u0001\u0000"+
		"\u0000\u0000\u0102\u0103\u0001\u0000\u0000\u0000\u0103)\u0001\u0000\u0000"+
		"\u0000\u0104\u0102\u0001\u0000\u0000\u0000\u0105\u010a\u0003,\u0016\u0000"+
		"\u0106\u0107\u0007\u0001\u0000\u0000\u0107\u0109\u0003,\u0016\u0000\u0108"+
		"\u0106\u0001\u0000\u0000\u0000\u0109\u010c\u0001\u0000\u0000\u0000\u010a"+
		"\u0108\u0001\u0000\u0000\u0000\u010a\u010b\u0001\u0000\u0000\u0000\u010b"+
		"+\u0001\u0000\u0000\u0000\u010c\u010a\u0001\u0000\u0000\u0000\u010d\u0112"+
		"\u0003.\u0017\u0000\u010e\u010f\u0007\u0002\u0000\u0000\u010f\u0111\u0003"+
		".\u0017\u0000\u0110\u010e\u0001\u0000\u0000\u0000\u0111\u0114\u0001\u0000"+
		"\u0000\u0000\u0112\u0110\u0001\u0000\u0000\u0000\u0112\u0113\u0001\u0000"+
		"\u0000\u0000\u0113-\u0001\u0000\u0000\u0000\u0114\u0112\u0001\u0000\u0000"+
		"\u0000\u0115\u011a\u00030\u0018\u0000\u0116\u0117\u0007\u0003\u0000\u0000"+
		"\u0117\u0119\u00030\u0018\u0000\u0118\u0116\u0001\u0000\u0000\u0000\u0119"+
		"\u011c\u0001\u0000\u0000\u0000\u011a\u0118\u0001\u0000\u0000\u0000\u011a"+
		"\u011b\u0001\u0000\u0000\u0000\u011b/\u0001\u0000\u0000\u0000\u011c\u011a"+
		"\u0001\u0000\u0000\u0000\u011d\u011f\u0005\u0017\u0000\u0000\u011e\u011d"+
		"\u0001\u0000\u0000\u0000\u011e\u011f\u0001\u0000\u0000\u0000\u011f\u0120"+
		"\u0001\u0000\u0000\u0000\u0120\u0130\u0003\u0018\f\u0000\u0121\u0123\u0005"+
		"\u0017\u0000\u0000\u0122\u0121\u0001\u0000\u0000\u0000\u0122\u0123\u0001"+
		"\u0000\u0000\u0000\u0123\u0125\u0001\u0000\u0000\u0000\u0124\u0126\u0007"+
		"\u0002\u0000\u0000\u0125\u0124\u0001\u0000\u0000\u0000\u0125\u0126\u0001"+
		"\u0000\u0000\u0000\u0126\u0127\u0001\u0000\u0000\u0000\u0127\u0130\u0003"+
		"4\u001a\u0000\u0128\u012a\u0005\u0017\u0000\u0000\u0129\u0128\u0001\u0000"+
		"\u0000\u0000\u0129\u012a\u0001\u0000\u0000\u0000\u012a\u012b\u0001\u0000"+
		"\u0000\u0000\u012b\u012c\u0005%\u0000\u0000\u012c\u012d\u0003 \u0010\u0000"+
		"\u012d\u012e\u0005&\u0000\u0000\u012e\u0130\u0001\u0000\u0000\u0000\u012f"+
		"\u011e\u0001\u0000\u0000\u0000\u012f\u0122\u0001\u0000\u0000\u0000\u012f"+
		"\u0129\u0001\u0000\u0000\u0000\u01301\u0001\u0000\u0000\u0000\u0131\u0136"+
		"\u0003 \u0010\u0000\u0132\u0133\u0005\'\u0000\u0000\u0133\u0135\u0003"+
		" \u0010\u0000\u0134\u0132\u0001\u0000\u0000\u0000\u0135\u0138\u0001\u0000"+
		"\u0000\u0000\u0136\u0134\u0001\u0000\u0000\u0000\u0136\u0137\u0001\u0000"+
		"\u0000\u0000\u01373\u0001\u0000\u0000\u0000\u0138\u0136\u0001\u0000\u0000"+
		"\u0000\u0139\u013a\u0007\u0004\u0000\u0000\u013a5\u0001\u0000\u0000\u0000"+
		"\u013b\u013d\u00038\u001c\u0000\u013c\u013e\u0005*\u0000\u0000\u013d\u013c"+
		"\u0001\u0000\u0000\u0000\u013d\u013e\u0001\u0000\u0000\u0000\u013e7\u0001"+
		"\u0000\u0000\u0000\u013f\u0142\u0003:\u001d\u0000\u0140\u0142\u0005-\u0000"+
		"\u0000\u0141\u013f\u0001\u0000\u0000\u0000\u0141\u0140\u0001\u0000\u0000"+
		"\u0000\u01429\u0001\u0000\u0000\u0000\u0143\u0144\u0007\u0005\u0000\u0000"+
		"\u0144;\u0001\u0000\u0000\u0000\u0145\u0146\u0007\u0006\u0000\u0000\u0146"+
		"=\u0001\u0000\u0000\u0000&AELT]djr\u007f\u008b\u0091\u009a\u009f\u00a7"+
		"\u00b2\u00b7\u00bc\u00c1\u00c7\u00c9\u00cd\u00d8\u00e2\u00e7\u00f2\u00fa"+
		"\u0102\u010a\u0112\u011a\u011e\u0122\u0125\u0129\u012f\u0136\u013d\u0141";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}