import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        
        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (methodDef.paramList != null) {
            types = methodDef.paramList.type;
            names = methodDef.paramList.name;
        }

        return new Scope.MethodInfo(
            methodDef.methodName,
            methodDef.type,
            types,
            names,
            methodDef.virtual,
            definingClass
        );
    }

    private Scope.MethodInfo buildMethodInfo(String name, ArrayList<String> paramTypes) {

        if (paramTypes == null ) {
            return new Scope.MethodInfo(
            name,
            null,
            new ArrayList<String>(),
            null,
            false,
            null
        );
        }
        return new Scope.MethodInfo(
            name,
            null,
            paramTypes,
            null,
            false,
            null
        );
    }

    private Scope.MethodInfo buildMethodInfo(AST.FunctionDeclaration methodDef) {

        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (methodDef.paramList != null) {
            types = methodDef.paramList.type;
            names = methodDef.paramList.name;
        }
        
        return new Scope.MethodInfo(
            methodDef.functionName,
            methodDef.type,
            types,
            names,
            false,
            null
        );
    }

    private Scope.MethodInfo buildConstructorInfo(AST.Constructor ctor) {

        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (ctor.paramList != null) {
            types = ctor.paramList.type;
            names = ctor.paramList.name;
        }

        return new Scope.MethodInfo(
            ctor.className,
            ctor.className,
            types,
            names,
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
        if (methodInfo.name.equals("main")) {
            if (!(methodInfo.returnType.equals("void") || methodInfo.returnType.equals("int"))) {
                throw new SemanticException("main function must return int or void");
            }
        }
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

        classInfo.classScope = classScope;
        
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
        if (ctor.paramList != null) {
            for(int i = 0; i < ctor.paramList.name.size(); i ++)
            {
                currentScope.declareVariable(new Scope.VariableInfo(ctor.paramList.name.get(i), ctor.paramList.type.get(i), "", false));
            }
        }
        analyzeBlock(ctor.block, null);
        currentScope = prevScope;
    }

    private void analyzeBlock(AST.Block block, String returnType) {
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);
        for (AST.ASTToken line : block.lines) {
            if (line instanceof AST.Block b){
                analyzeBlock(b, returnType);
            }
            else if (line instanceof AST.FunctionDeclaration f){
                analyzeFunctionDeclaration(f);
            }
            else if (line instanceof AST.VariableDeclaration v){
                analyzeVariableDeclaration(v);
            }
            else if (line instanceof AST.ReturnStatement r){
                analyzeReturn(r,returnType);
            }
            else if (line instanceof AST.IfStatement i){
                analyzeIf(i,returnType);
            }
            else if (line instanceof AST.WhileLoop w){
                analyzeWhile(w,returnType);
            }
            else if (line instanceof AST.Assignment a){
                analyzeAssignment(a);
            }
            else {
                analyzeExpression(line);
            }
        }
        currentScope = prevScope;
    }

    private void analyzeFunctionDeclaration(AST.FunctionDeclaration functionDeclaration) {
        Scope.MethodInfo methodInfo = buildMethodInfo(functionDeclaration);
        currentScope.declareMethod(methodInfo.getSignature(), methodInfo);
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);
        if (functionDeclaration.paramList != null) {
            for(int i = 0; i < functionDeclaration.paramList.name.size(); i ++)
            {
                currentScope.declareVariable(new Scope.VariableInfo(functionDeclaration.paramList.name.get(i), functionDeclaration.paramList.type.get(i), "", false));
            }
        }
        analyzeBlock(functionDeclaration.block, functionDeclaration.type);
        
        currentScope = prevScope;
    }

    private void analyzeVariableDeclaration(AST.VariableDeclaration variableDeclaration) {
        Scope.VariableInfo variableInfo = buildVariableInfo(variableDeclaration);
        
        if (variableInfo.type != null) {
            currentScope.variables.put(variableInfo.name, variableInfo);
            if (variableDeclaration.expression != null) {
                String exprtype = analyzeExpression(variableDeclaration.expression);

                if (variableInfo.type.equals(exprtype)) {
                    currentScope.variables.put(variableInfo.name,variableInfo);
                    return;
                }
                throw new SemanticException("types dont match " + variableInfo.type + " is not equal to " + exprtype);
            }
            currentScope.variables.put(variableInfo.name,variableInfo);
        } else {
            if (variableDeclaration.expression != null) {
                variableInfo.type = analyzeExpression(variableDeclaration.expression);
                currentScope.variables.put(variableInfo.name,variableInfo);
                return;
            }
            currentScope.variables.put(variableInfo.name,variableInfo);
        }
    }

    private void analyzeReturn(AST.ReturnStatement returnStatement, String returnType) {
        if (returnType == null) {
            throw new SemanticException("Unexpected Return Call:" + returnStatement);
        }
        String type = analyzeExpression(returnStatement.expression);
        if (!type.equals(returnType)) {
            throw new SemanticException("Return Type '" + type + "' not equal to '" + returnType + "'");
        }
    }

    private void analyzeIf(AST.IfStatement ifStatement, String returnType) {
        String type = analyzeExpression(ifStatement.expression);
        if (!type.equals("bool")) {
            throw new SemanticException("Expression type '" + type + "' is not a boolean");
        }

        analyzeBlock(ifStatement.block, returnType);
        if (ifStatement.elseBlock != null) {
            analyzeBlock(ifStatement.elseBlock, returnType);
        }
    }

    private void analyzeWhile(AST.WhileLoop whileLoop, String returnType) {
        String type = analyzeExpression(whileLoop.expression);
        if (!type.equals("bool")) {
            throw new SemanticException("Expression type '" + type + "' is not a boolean");
        }
        
        analyzeBlock(whileLoop.block, returnType);
    }

    private void analyzeAssignment(AST.Assignment assignment) {
        String type = analyzeExpression(assignment.operation);
        String setterType;
        if (assignment.chain instanceof AST.VariableCall n) {
            setterType = analyzeVariableCall(n);
        }
        else if(assignment.chain instanceof AST.FunctionCall n){
            setterType = analyzeFunctionCall(n);
        }
        else{
            throw new SemanticException("unexpected Token");
        }

        if (!type.equals(setterType)) {
            throw new SemanticException("Expression Type '" + setterType + "' not equal to variable type'" + type + "'");
        }
    }

    private void analyzeMethod(AST.MethodDefinition methodDefinition) {

        Scope.MethodInfo methodInfo = buildMethodInfo(methodDefinition, null);
        currentScope.declareMethod(methodInfo.getSignature(), methodInfo);
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);
        if (methodDefinition.paramList != null) {
            for(int i = 0; i < methodDefinition.paramList.name.size(); i ++)
            {
                currentScope.declareVariable(new Scope.VariableInfo(methodDefinition.paramList.name.get(i), methodDefinition.paramList.type.get(i), "", false));
            }
        }
        analyzeBlock(methodDefinition.block, methodDefinition.type);
        
        currentScope = prevScope;
    }

    private String analyzeOperation(AST.Operation operation) {
        
            // == != < > <= >= + - * / %
            
            AST.ASTToken op1 = operation.elements.get(0);
            AST.ASTToken op2;
            String op1Type = "";
            String op2Type = "";
            if(op1 instanceof AST.Operation o)
            {
                op1Type = analyzeOperation(o);
            }
            else
            {
                op1Type = analyzeExpression(op1);
            }
            
        for(int i = 0; i < operation.operations.size(); i++)
        {
            op2 = operation.elements.get(i+1);
            if(op2 instanceof AST.Operation o)
            {
                op2Type = analyzeOperation(o);
            }
            else
            {
                op2Type = analyzeExpression(op2);
            }
            if(operation.operations.get(i).equals("==") || operation.operations.get(i).equals("!="))
            {
                if(op1Type.equals(op2Type))
                {
                    op1Type = "bool";
                }
                else{
                    throw new SemanticException("Cannot compare two different Types: " + op1Type + " and " + op2Type + ".");
                }
            }
            else if(operation.operations.get(i).equals("<=")
                || operation.operations.get(i).equals(">=") 
                || operation.operations.get(i).equals("<") 
                || operation.operations.get(i).equals(">"))
            {
                if(op1Type.equals("int") && op2Type.equals("int"))
                {
                    op1Type = "bool";
                }
                else{
                    throw new SemanticException("Non integer values cannot be greater or less than. " + op1Type + " and " + op2Type + ".");
                }
            }
            else if(operation.operations.get(i).equals("+") || operation.operations.get(i).equals("-") || operation.operations.get(i).equals("*") || operation.operations.get(i).equals("/") || operation.operations.get(i).equals("%"))
            {
                if(op1Type.equals("int") && op2Type.equals("int"))
                {
                    op1Type = "int";
                }
                else{
                    throw new SemanticException("Cannot do " + operation.operations.get(i) + " operation on non int values.");
                }
            }
            else{
                throw new SemanticException("Unexpected Operation: " + operation.operations.get(i) + ".");
            }
        }
        return op1Type;
        
    }

    private String analyzeNot(AST.NOT not) {
        String returnType = analyzeExpression(not);
        if (returnType.equals("bool") || returnType.equals("int") || returnType.equals("string") || returnType.equals("char")) {
            return "bool";
        }
        throw new SemanticException("Not can not be of type:'" + returnType + "'");
    }

    private String analyzeLiteral(AST.Literal literal) {

        if(literal.type.equals("var")){
            Scope.VariableInfo variableInfo = currentScope.getVariable(literal.value);

            if (variableInfo.type == null) {
                throw new SemanticException("Variable: " + variableInfo.name + " not initialized");
            }
            return variableInfo.type;
        }
        return literal.type;
    }

    private ArrayList<String> analyzeArgs(AST.Args args) {
        ArrayList<String> a = new ArrayList<String>();

        if (args.expressions == null) {
            return a;
        }
        for (AST.ASTToken expression : args.expressions) {
            a.add(analyzeExpression(expression));
        }
        return a;
    }

    private String analyzeFunctionCall(AST.FunctionCall functionCall) {
        ArrayList<String> args =  analyzeArgs(functionCall.args);


        String sig = buildMethodInfo(functionCall.name, args).getSignature();
        //Check if method exists
        Scope.MethodInfo methodInfo = currentScope.getMethod(sig);
        if (methodInfo == null) {
            throw new SemanticException("Can not find function " + sig);
        }
        //check if has a next
        if (functionCall.next == null) {
            return methodInfo.returnType;
        }

        if (functionCall.next instanceof AST.VariableCall n) {
            //Check if Return value of func has variable
            String returnType = methodInfo.returnType;

            if (isPrimitive(returnType)){
                throw new SemanticException(returnType + " is primitive and has no variable called " + n);
            }

            Scope.ClassInfo classInfo = globalScope.getClass(returnType);

            if (classInfo == null) {
                throw new SemanticException(returnType + " class not found");
            }
            Scope prevScope = currentScope;
            currentScope = classInfo.classScope;
            String rType = analyzeVariableCall(n);
            currentScope = prevScope;
            return rType;
            
        }

        else if (functionCall.next instanceof AST.FunctionCall n){
            //Check if Return value of func has method next
            String returnType = methodInfo.returnType;

            if (isPrimitive(returnType)){
                throw new SemanticException(returnType + " is primitive and has no variable called " + n);
            }

            Scope.ClassInfo classInfo = globalScope.getClass(returnType);

            if (classInfo == null) {
                throw new SemanticException(returnType + " class not found");
            }
            Scope prevScope = currentScope;
            currentScope = classInfo.classScope;
            String rType = analyzeFunctionCall(n);
            currentScope = prevScope;
            return rType;
        }
        return "";

    }
    
    private String analyzeVariableCall(AST.VariableCall variableCall) {
        if (variableCall.next == null){            Scope.VariableInfo vInfo = currentScope.getVariable(variableCall.name);
            if (vInfo == null) {
                throw new SemanticException("Variable not found: '" + variableCall.name + "'");
            }
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


    private void analyzeStatement(AST.ASTToken statement) {
    
        if (statement instanceof AST.Block b){
            analyzeBlock(b,null);
        }
        else if (statement instanceof AST.FunctionDeclaration f){
            analyzeFunctionDeclaration(f);
        }
        else if (statement instanceof AST.VariableDeclaration v){
            analyzeVariableDeclaration(v);
        }
        else if (statement instanceof AST.ReturnStatement r){
            analyzeReturn(r, null);
        }
        else if (statement instanceof AST.IfStatement i){
            analyzeIf(i,null);
        }
        else if (statement instanceof AST.WhileLoop w){
            analyzeWhile(w,null);
        }
        else if (statement instanceof AST.Assignment a){
            analyzeAssignment(a);
        }
        else {
            analyzeExpression(statement);
        }
    
    }

    private String analyzeExpression(AST.ASTToken expression) {
        if (expression instanceof AST.Operation o){
            return analyzeOperation(o);
        }
        else if (expression instanceof AST.NOT n){
            return analyzeNot(n);
        }
        else if (expression instanceof AST.Literal l){
            return analyzeLiteral(l);
        }
        else if (expression instanceof AST.FunctionCall f){
            return analyzeFunctionCall(f);
        }
        else if (expression instanceof AST.VariableCall v){
            return analyzeVariableCall(v);
        }
        throw new SemanticException("Unexpected Token in Expression: " + expression.toString());
    }

    private boolean isPrimitive(String returnType) {
        List<String> primitiveType = Arrays.asList("int", "bool", "string", "char", "void");
        return primitiveType.contains(returnType);
    }
}

class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super("Semantic Error: " + message);
    }
}