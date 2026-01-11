public class SemanticAnalyzer {
    private Scope globalScope;
    private Scope currentScope;


    public SemanticAnalyzer() {
        globalScope = new Scope(null);
        currentScope = globalScope;
    }

    private Scope.VariableInfo buildVariableInfo(AST.AttributeDeclaration attrDecl) {
        return new Scope.VariableInfo(
            attrDecl.name,
            attrDecl.type,
            null,
            false // attrDecl.type.endsWith("&") needed???
        );
    }

    private Scope.VariableInfo buildVariableInfo(AST.VariableDeclaration attrDecl) {
        return new Scope.VariableInfo(
            attrDecl.varName,
            attrDecl.type,
            null,
            attrDecl.deepcopy
        );
    }

    private Scope.MethodInfo buildMethodInfo(AST.MethodDefinition methodDef, String definingClass) {
        return new Scope.MethodInfo(
            methodDef.methodName,
            methodDef.type,
            methodDef.paramList.type,
            methodDef.paramList.name,
            methodDef.virtual,
            definingClass
        );
    }

    private Scope.MethodInfo buildMethodInfo(AST.FunctionDeclaration methodDef) {
        return new Scope.MethodInfo(
            methodDef.functionName,
            methodDef.type,
            methodDef.paramList.type,
            methodDef.paramList.name,
            false,
            null
        );
    }

    private Scope.MethodInfo buildConstructorInfo(AST.Constructor ctor) {
        return new Scope.MethodInfo(
            ctor.className,
            ctor.className,
            ctor.paramList.type,
            ctor.paramList.name,
            false,
            ctor.className
        );
    }

    public void analyze(AST.Start start){

        collectTree(start);
        
        analyzeTree(start);
    }

    private void collectTree(AST.Start start) {
        for (AST.ASTToken line : start.lines) {
            if (line instanceof AST.ClassDeclaration classDecl) {
                collectClass(classDecl);
            } else if (line instanceof AST.FunctionDeclaration funcDecl) {
                collectFunction(funcDecl);
            }
            else{
                //collectVariables();
            }
        }
    }

    private void collectClass(AST.ClassDeclaration classDecl){
        String className = classDecl.className;
        String parentClass = classDecl.parentClass;

        Scope.ClassInfo classInfo = new Scope.ClassInfo(className, parentClass);
        
        currentScope.declareClass(className, classInfo);

        for (AST.ASTToken member : classDecl.members) {
            if (member instanceof AST.Constructor constructor) {
                Scope.MethodInfo constructorInfo = buildConstructorInfo(constructor);
                if (currentScope.methods.containsKey(constructorInfo.getSignature())) {
                    throw new SemanticException("Constructor '" + constructorInfo.getSignature() + "' already defined in class '" + classInfo.name + "'");
                }
                currentScope.methods.put(constructorInfo.getSignature(), constructorInfo);
            }
        }
    }

    private void collectFunction(AST.FunctionDeclaration funcDecl){
        Scope.MethodInfo methodInfo = buildMethodInfo(funcDecl);
        currentScope.declareMethod(methodInfo.getSignature(), methodInfo);
    }

    public void analyzeTree(AST.Start start){
        for (AST.ASTToken line : start.lines) {
            if (line instanceof AST.ClassDeclaration classDecl) {
                analyzeClass(classDecl);
            } else if (line instanceof AST.FunctionDeclaration funcDecl) {
                analyzeFunctionDeclaration(funcDecl);
            } else {
                analyzeStatement(line);
            }
        }
    }

    private void analyzeClass(AST.ClassDeclaration classDecl) {
        Scope.ClassInfo classInfo = globalScope.getClass(classDecl.className);

        Scope classScope = new Scope(globalScope);

        if (classInfo.parent != null){
            if(globalScope.getClass(classInfo.parent) == null){
                throw new SemanticException("Parent class '" + classInfo.parent + "' not found");
            }
        }

        for (AST.ASTToken member : classDecl.members) {
            if (member instanceof AST.AttributeDeclaration attrDecl) {
                if (currentScope.variables.containsKey(attrDecl.name)) {
                    throw new SemanticException("Attribute '" + attrDecl.name + "' already defined in class '" + classInfo.name + "'");
                }
                currentScope.variables.put(attrDecl.name, buildVariableInfo(attrDecl));
            } else if (member instanceof AST.MethodDefinition methodDef) {
                Scope.MethodInfo methodInfo = buildMethodInfo(methodDef, classInfo.name);
                if (currentScope.methods.containsKey(methodInfo.getSignature())) {
                    throw new SemanticException("Method '" + methodInfo.getSignature() + "' already defined in class '" + classInfo.name + "'");
                }
                currentScope.methods.put(methodInfo.getSignature(), methodInfo);
            }
        }

        for (AST.ASTToken member : classDecl.members) {
            if (member instanceof AST.Constructor constructor) {
                analyzeConstructor(constructor);
            }
            else if (member instanceof AST.MethodDefinition method){
                analyzeMethod(method);
            }
        }

    }

    private void analyzeConstructor(AST.Constructor ctor) {
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);

        analyzeBlock(ctor.block);
        
        currentScope = prevScope;
    }

    private void analyzeBlock(AST.Block block) {
        for (AST.ASTToken line : block.lines) {
            if (line instanceof AST.Block b){
                analyzeBlock(b);
            }
            else if (line instanceof AST.FunctionDeclaration f){
                analyzeFunctionDeclaration(f);
            }
            else if (line instanceof AST.VariableDeclaration v){
                analyzeVariableDeclaration(v);
            }
            else if (line instanceof AST.ReturnStatement r){
                analyzeReturn(r);
            }
            else if (line instanceof AST.IfStatement i){
                analyzeIf(i);
            }
            else if (line instanceof AST.WhileLoop w){
                analyzeWhile(w);
            }
            else if (line instanceof AST.Assignment a){
                analyzeAssignment(a);
            }
            else if (line instanceof AST.Operation o){
                analyzeOperation(o);
            }
            else if (line instanceof AST.NOT n){
                analyzeNot(n);
            }
            else if (line instanceof AST.Literal l){
                analyzeLiteral(l);
            }
            else if (line instanceof AST.FunctionCall f){
                analyzeFunctionCall(f);
            }
            else if (line instanceof AST.VariableCall v){
                analyzeVariableCall(v);
            }

        }
    }

    private void analyzeFunctionDeclaration(AST.FunctionDeclaration functionDeclaration) {

    }

    private void analyzeVariableDeclaration(AST.VariableDeclaration variableDeclaration) {

    }

    private void analyzeReturn(AST.ReturnStatement returnStatement) {

    }

    private void analyzeIf(AST.IfStatement ifStatement) {

    }

    private void analyzeWhile(AST.WhileLoop whileLoop) {

    }

    private void analyzeAssignment(AST.Assignment assignment) {

    }

    private void analyzeMethod(AST.MethodDefinition methodDefinition) {

    }

    private String analyzeOperation(AST.Operation operation) {
        return "";
    }

    private String analyzeNot(AST.NOT not) {
        return "";

    }

    private String analyzeLiteral(AST.Literal literal) {
        return literal.type;
    }

    private String analyzeFunctionCall(AST.FunctionCall functionCall) {
        buildMethodInfo(functionCall);
        if (functionCall.next == null) {
            
        }
        return "";

    }
    
    private String analyzeVariableCall(AST.VariableCall variableCall) {
        if (variableCall.next == null){
            Scope.VariableInfo vInfo = currentScope.variables.get(variableCall.name);
            return vInfo.type;
        }
        
        if (variableCall.next instanceof AST.VariableCall n) {
            return analyzeVariableCall(n);
        }

        else if (variableCall.next instanceof AST.FunctionCall n){
            return analyzeFunctionCall(n);
        }

        throw new SemanticException("unexpected AST Type found");
    }

    private void analyzeStatement(AST.ASTToken classDecl) {

    }

}

class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super("Semantic Error: " + message);
    }
}