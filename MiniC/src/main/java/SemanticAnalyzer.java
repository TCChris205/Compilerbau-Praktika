import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SemanticAnalyzer {
    private Scope globalScope;
    private Scope currentScope;
    private String currentClass;

    public SemanticAnalyzer() {
        globalScope = new Scope(null);
        currentScope = globalScope;
        currentClass = null;

        initializeBuiltinFunctions();
    }

    private void initializeBuiltinFunctions() {
        ArrayList<String> intParamTypes = new ArrayList<>();
        intParamTypes.add("int");
        globalScope.methods.put(
                "print_int/int,",
                new Scope.MethodInfo(
                        "print_int", "void", intParamTypes, Arrays.asList("value"), false, null));

        ArrayList<String> boolParamTypes = new ArrayList<>();
        boolParamTypes.add("bool");
        globalScope.methods.put(
                "print_bool/bool,",
                new Scope.MethodInfo(
                        "print_bool", "void", boolParamTypes, Arrays.asList("value"), false, null));

        ArrayList<String> charParamTypes = new ArrayList<>();
        charParamTypes.add("char");
        globalScope.methods.put(
                "print_char/char,",
                new Scope.MethodInfo(
                        "print_char", "void", charParamTypes, Arrays.asList("value"), false, null));

        ArrayList<String> stringParamTypes = new ArrayList<>();
        stringParamTypes.add("string");
        globalScope.methods.put(
                "print_string/string,",
                new Scope.MethodInfo(
                        "print_string",
                        "void",
                        stringParamTypes,
                        Arrays.asList("value"),
                        false,
                        null));
    }

    private Scope.VariableInfo buildVariableInfo(AST.AttributeDeclaration attrDecl) {
        return new Scope.VariableInfo(
                attrDecl.name, attrDecl.type, null, false // attrDecl.type.endsWith("&") needed???
                );
    }

    private Scope.VariableInfo buildVariableInfo(AST.VariableDeclaration attrDecl) {
        return new Scope.VariableInfo(attrDecl.varName, attrDecl.type, null, attrDecl.deepcopy);
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
                definingClass);
    }

    private Scope.MethodInfo buildMethodInfo(String name, ArrayList<String> paramTypes) {

        if (paramTypes == null) {
            return new Scope.MethodInfo(name, null, new ArrayList<String>(), null, false, null);
        }
        return new Scope.MethodInfo(name, null, paramTypes, null, false, null);
    }

    private Scope.MethodInfo buildMethodInfo(AST.FunctionDeclaration methodDef) {

        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (methodDef.paramList != null) {
            types = methodDef.paramList.type;
            names = methodDef.paramList.name;
        }

        return new Scope.MethodInfo(
                methodDef.functionName, methodDef.type, types, names, false, null);
    }

    private Scope.MethodInfo buildConstructorInfo(AST.Constructor ctor) {

        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (ctor.paramList != null) {
            types = ctor.paramList.type;
            names = ctor.paramList.name;
        }

        return new Scope.MethodInfo(
                ctor.className, ctor.className, types, names, false, ctor.className);
    }

    public void analyze(AST.Start start) {

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
        }
    }

    private void collectClass(AST.ClassDeclaration classDecl) {
        String className = classDecl.className;
        String parentClass = classDecl.parentClass;

        Scope.ClassInfo classInfo = new Scope.ClassInfo(className, parentClass);

        currentScope.declareClass(className, classInfo);

        int counter = 0;
        for (AST.ASTToken member : classDecl.members) {
            if (member instanceof AST.Constructor constructor) {
                counter++;
                Scope.MethodInfo constructorInfo = buildConstructorInfo(constructor);
                if (currentScope.methods.containsKey(constructorInfo.getSignature())) {
                    printCurrentScope();
                    throw new SemanticException(
                            "Constructor '"
                                    + constructorInfo.getSignature()
                                    + "' already defined in class '"
                                    + classInfo.name
                                    + "'");
                }
                currentScope.methods.put(constructorInfo.getSignature(), constructorInfo);
            }
        }

        if (counter == 0) {
            // add default Ctor
            Scope.MethodInfo constructorInfo =
                    new Scope.MethodInfo(
                            className,
                            className,
                            new ArrayList<String>(),
                            new ArrayList<String>(),
                            false,
                            className);
            currentScope.methods.put(constructorInfo.getSignature(), constructorInfo);
        }

        // add coppy Ctor if not exists
        ArrayList<String> ParamTypes = new ArrayList<>();
        ParamTypes.add(className);
        Scope.MethodInfo constructorInfo =
                new Scope.MethodInfo(
                        className, className, ParamTypes, Arrays.asList("value"), false, className);
        if (currentScope.getMethod(constructorInfo.getSignature()) == null) {
            globalScope.methods.put(constructorInfo.getSignature(), constructorInfo);
        }
    }

    private void collectFunction(AST.FunctionDeclaration funcDecl) {
        Scope.MethodInfo methodInfo = buildMethodInfo(funcDecl);
        if (methodInfo.name.equals("main")) {
            if (!(methodInfo.returnType.equals("void") || methodInfo.returnType.equals("int"))) {
                throw new SemanticException("main function must return int or void");
            }
        }
        currentScope.declareMethod(methodInfo.getSignature(), methodInfo);
    }

    public void analyzeTree(AST.Start start) {
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

        if (classInfo.parent != null) {
            if (globalScope.getClass(classInfo.parent) == null) {
                printCurrentScope();
                throw new SemanticException("Parent class '" + classInfo.parent + "' not found");
            }
        }
        for (AST.ASTToken member : classDecl.members) {
            if (member instanceof AST.AttributeDeclaration attrDecl) {
                if (classScope.variables.containsKey(attrDecl.name)) {
                    printCurrentScope();
                    throw new SemanticException(
                            "Attribute '"
                                    + attrDecl.name
                                    + "' already defined in class '"
                                    + classInfo.name
                                    + "'");
                }
                classScope.variables.put(attrDecl.name, buildVariableInfo(attrDecl));
            } else if (member instanceof AST.MethodDefinition methodDef) {
                Scope.MethodInfo methodInfo = buildMethodInfo(methodDef, classInfo.name);

                if (classScope.methods.containsKey(methodInfo.getSignature())) {
                    printCurrentScope();
                    throw new SemanticException(
                            "Method '"
                                    + methodInfo.getSignature()
                                    + "' already defined in class '"
                                    + classInfo.name
                                    + "'");
                }

                Scope.MethodInfo parentMethod =
                        findMethodInAncestors(classInfo.parent, methodInfo.getSignature());
                if (parentMethod != null) {

                    if (!parentMethod.isVirtual) {
                        printCurrentScope();
                        throw new SemanticException(
                                "Method '"
                                        + methodInfo.getSignature()
                                        + "' can only override a virtual method");
                    }
                }

                classScope.methods.put(methodInfo.getSignature(), methodInfo);
            }
        }

        classInfo.classScope = classScope;

        Scope prevScope = currentScope;
        String prevClass = currentClass;
        currentScope = classScope;
        currentClass = classDecl.className;

        for (AST.ASTToken member : classDecl.members) {
            if (member instanceof AST.Constructor constructor) {
                analyzeConstructor(constructor);
            } else if (member instanceof AST.MethodDefinition method) {
                analyzeMethod(method);
            }
        }

        currentScope = prevScope;
        currentClass = prevClass;
    }

    private void analyzeConstructor(AST.Constructor ctor) {
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);
        if (ctor.paramList != null) {
            for (int i = 0; i < ctor.paramList.name.size(); i++) {
                currentScope.declareVariable(
                        new Scope.VariableInfo(
                                ctor.paramList.name.get(i), ctor.paramList.type.get(i), "", false));
            }
        }
        analyzeBlock(ctor.block, null);
        currentScope = prevScope;
    }

    private void analyzeBlock(AST.Block block, String returnType) {
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);
        for (AST.ASTToken line : block.lines) {
            if (line instanceof AST.Block b) {
                analyzeBlock(b, returnType);
            } else if (line instanceof AST.FunctionDeclaration f) {
                analyzeFunctionDeclaration(f);
            } else if (line instanceof AST.VariableDeclaration v) {
                analyzeVariableDeclaration(v);
            } else if (line instanceof AST.ReturnStatement r) {
                analyzeReturn(r, returnType);
            } else if (line instanceof AST.IfStatement i) {
                analyzeIf(i, returnType);
            } else if (line instanceof AST.WhileLoop w) {
                analyzeWhile(w, returnType);
            } else if (line instanceof AST.Assignment a) {
                analyzeAssignment(a);
            } else {
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
            for (int i = 0; i < functionDeclaration.paramList.name.size(); i++) {
                currentScope.declareVariable(
                        new Scope.VariableInfo(
                                functionDeclaration.paramList.name.get(i),
                                functionDeclaration.paramList.type.get(i),
                                "",
                                false));
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

                // Allow exact type match or subclass assignment (slicing)
                if (variableInfo.type.equals(exprtype)
                        || isSubclassOf(exprtype, variableInfo.type)) {
                    currentScope.variables.put(variableInfo.name, variableInfo);
                    return;
                }
                printCurrentScope();
                throw new SemanticException(
                        "types dont match " + variableInfo.type + " is not equal to " + exprtype);
            }
            currentScope.variables.put(variableInfo.name, variableInfo);
        } else {
            if (variableDeclaration.expression != null) {
                variableInfo.type = analyzeExpression(variableDeclaration.expression);
                currentScope.variables.put(variableInfo.name, variableInfo);
                return;
            }
            currentScope.variables.put(variableInfo.name, variableInfo);
        }
    }

    private void analyzeReturn(AST.ReturnStatement returnStatement, String returnType) {
        if (returnType == null) {
            throw new SemanticException("Unexpected Return Call:" + returnStatement);
        }
        String type = analyzeExpression(returnStatement.expression);
        if (!type.equals(returnType)) {
            throw new SemanticException(
                    "Return Type '" + type + "' not equal to '" + returnType + "'");
        }
    }

    private void analyzeIf(AST.IfStatement ifStatement, String returnType) {
        String type = analyzeExpression(ifStatement.expression);
        if (!(type.equals("bool")
                || type.equals("int")
                || type.equals("char")
                || type.equals("string"))) {
            throw new SemanticException(
                    "Expression type '" + type + "' cant be converted to boolean");
        }

        analyzeBlock(ifStatement.block, returnType);
        if (ifStatement.elseBlock != null) {
            analyzeBlock(ifStatement.elseBlock, returnType);
        }
    }

    private void analyzeWhile(AST.WhileLoop whileLoop, String returnType) {
        String type = analyzeExpression(whileLoop.expression);
        if (!(type.equals("bool")
                || type.equals("int")
                || type.equals("char")
                || type.equals("string"))) {
            throw new SemanticException(
                    "Expression type '" + type + "' cant be converted to boolean");
        }

        analyzeBlock(whileLoop.block, returnType);
    }

    private void analyzeAssignment(AST.Assignment assignment) {
        String type = analyzeExpression(assignment.operation);
        String setterType;
        if (assignment.chain instanceof AST.VariableCall n) {
            setterType = analyzeVariableCall(n);
        } else if (assignment.chain instanceof AST.FunctionCall n) {
            setterType = analyzeFunctionCall(n);
        } else {
            throw new SemanticException("unexpected Token");
        }

        // Allow exact type match or subclass assignment (slicing)
        if (!type.equals(setterType) && !isSubclassOf(type, setterType)) {
            throw new SemanticException(
                    "Expression Type '"
                            + setterType
                            + "' not equal to variable type'"
                            + type
                            + "'");
        }
    }

    private void analyzeMethod(AST.MethodDefinition methodDefinition) {

        Scope.MethodInfo methodInfo = buildMethodInfo(methodDefinition, null);
        currentScope.declareMethod(methodInfo.getSignature(), methodInfo);
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);
        if (methodDefinition.paramList != null) {
            for (int i = 0; i < methodDefinition.paramList.name.size(); i++) {
                currentScope.declareVariable(
                        new Scope.VariableInfo(
                                methodDefinition.paramList.name.get(i),
                                methodDefinition.paramList.type.get(i),
                                "",
                                false));
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
        if (op1 instanceof AST.Operation o) {
            op1Type = analyzeOperation(o);
        } else {
            op1Type = analyzeExpression(op1);
        }

        for (int i = 0; i < operation.operations.size(); i++) {
            op2 = operation.elements.get(i + 1);
            if (op2 instanceof AST.Operation o) {
                op2Type = analyzeOperation(o);
            } else {
                op2Type = analyzeExpression(op2);
            }
            if (operation.operations.get(i).equals("==")
                    || operation.operations.get(i).equals("!=")) {
                if (op1Type.equals(op2Type)) {
                    op1Type = "bool";
                } else {
                    throw new SemanticException(
                            "Cannot compare two different Types: "
                                    + op1Type
                                    + " and "
                                    + op2Type
                                    + ".");
                }
            } else if (operation.operations.get(i).equals("<=")
                    || operation.operations.get(i).equals(">=")
                    || operation.operations.get(i).equals("<")
                    || operation.operations.get(i).equals(">")) {
                if (op1Type.equals("int") && op2Type.equals("int")) {
                    op1Type = "bool";
                } else if (op1Type.equals("char") && op2Type.equals("char")) {
                    op1Type = "bool";
                } else {
                    throw new SemanticException(
                            "Non integer or char values cannot be greater or less than. "
                                    + op1Type
                                    + " and "
                                    + op2Type
                                    + ".");
                }
            } else if (operation.operations.get(i).equals("+")
                    || operation.operations.get(i).equals("-")
                    || operation.operations.get(i).equals("*")
                    || operation.operations.get(i).equals("/")
                    || operation.operations.get(i).equals("%")) {
                if (op1Type.equals("int") && op2Type.equals("int")) {
                    op1Type = "int";
                } else {
                    throw new SemanticException(
                            "Cannot do "
                                    + operation.operations.get(i)
                                    + " operation on non int values.");
                }
            } else if (operation.operations.get(i).equals("||")
                    || operation.operations.get(i).equals("&&")) {
                if (op1Type.equals("bool") && op2Type.equals("bool")) {
                    op1Type = "bool";
                } else {
                    throw new SemanticException(
                            "Cannot do "
                                    + operation.operations.get(i)
                                    + " operation on non int values.");
                }
            } else {
                throw new SemanticException(
                        "Unexpected Operation: " + operation.operations.get(i) + ".");
            }
        }
        return op1Type;
    }

    private String analyzeNot(AST.NOT not) {
        String returnType = analyzeExpression(not.child);
        if (returnType.equals("bool")
                || returnType.equals("int")
                || returnType.equals("string")
                || returnType.equals("char")) {
            return "bool";
        }
        throw new SemanticException("Not can not be of type:'" + returnType + "'");
    }

    private String analyzeLiteral(AST.Literal literal) {

        if (literal.type.equals("var")) {
            Scope.VariableInfo variableInfo = currentScope.getVariable(literal.value);

            if (variableInfo.type == null) {
                printCurrentScope();
                throw new SemanticException("Variable: " + variableInfo.name + " not initialized");
            }
            return variableInfo.type;
        }
        return literal.type;
    }

    private ArrayList<String> analyzeArgs(AST.Args args) {
        ArrayList<String> a = new ArrayList<String>();

        if (args == null) {
            return a;
        }
        if (args.expressions == null) {
            return a;
        }
        for (AST.ASTToken expression : args.expressions) {
            a.add(analyzeExpression(expression));
        }
        return a;
    }

    private String analyzeFunctionCall(AST.FunctionCall functionCall) {
        ArrayList<String> args = analyzeArgs(functionCall.args);

        String sig = buildMethodInfo(functionCall.name, args).getSignature();

        Scope.MethodInfo methodInfo = currentScope.getMethod(sig);
        Scope.MethodInfo AncestorMethod = findMethodInAncestors(currentClass, sig);
        if (methodInfo == null && AncestorMethod == null) {
            printCurrentScope();
            throw new SemanticException("Can not find function " + sig);
        }

        String returnType;
        if (methodInfo == null) {
            returnType = AncestorMethod.returnType;
        } else {
            returnType = methodInfo.returnType;
        }

        if (functionCall.next == null) {
            return returnType;
        }

        if (functionCall.next instanceof AST.VariableCall n) {

            if (isPrimitive(returnType)) {
                printCurrentScope();
                throw new SemanticException(
                        returnType + " is primitive and has no variable called " + n);
            }

            Scope.ClassInfo classInfo = globalScope.getClass(returnType);

            if (classInfo == null) {
                printCurrentScope();
                throw new SemanticException(returnType + " class not found");
            }
            Scope prevScope = currentScope;
            String prevClass = currentClass;
            currentScope = classInfo.classScope;
            currentClass = returnType;
            String rType = analyzeVariableCall(n);
            currentScope = prevScope;
            currentClass = prevClass;
            return rType;

        } else if (functionCall.next instanceof AST.FunctionCall n) {

            if (isPrimitive(returnType)) {
                throw new SemanticException(
                        returnType + " is primitive and has no variable called " + n);
            }

            Scope.ClassInfo classInfo = globalScope.getClass(returnType);

            if (classInfo == null) {
                throw new SemanticException(returnType + " class not found");
            }
            Scope prevScope = currentScope;
            String prevClass = currentClass;
            currentScope = classInfo.classScope;
            currentClass = returnType;
            String rType = analyzeFunctionCall(n);
            currentScope = prevScope;
            currentClass = prevClass;
            return rType;
        }
        return "";
    }

    private String analyzeVariableCall(AST.VariableCall variableCall) {
        if (variableCall.next == null) {
            Scope.VariableInfo vInfo = currentScope.getVariable(variableCall.name);

            if (vInfo == null && currentClass != null) {
                vInfo = findVariableInAncestors(currentClass, variableCall.name);
            }

            if (vInfo == null) {
                printCurrentScope();
                throw new SemanticException("Variable not found: '" + variableCall.name + "'");
            }
            return vInfo.type;
        }

        if (variableCall.next instanceof AST.VariableCall n) {
            Scope.VariableInfo vInfo = currentScope.getVariable(variableCall.name);
            if (vInfo == null && currentClass != null) {
                vInfo = findVariableInAncestors(currentClass, variableCall.name);
            }

            if (vInfo == null) {
                printCurrentScope();
                throw new SemanticException("Variable not found: '" + variableCall.name + "'");
            }

            if (!isPrimitive(vInfo.type)) {
                Scope.ClassInfo classInfo = globalScope.getClass(vInfo.type);
                if (classInfo == null) {
                    throw new SemanticException("Class not found: '" + vInfo.type + "'");
                }
                Scope prevScope = currentScope;
                String prevClass = currentClass;
                currentScope = classInfo.classScope;
                currentClass = vInfo.type;
                String rType = analyzeVariableCall(n);
                currentScope = prevScope;
                currentClass = prevClass;
                return rType;
            } else {
                throw new SemanticException(vInfo.type + " is primitive and has no members");
            }
        } else if (variableCall.next instanceof AST.FunctionCall n) {

            Scope.VariableInfo vInfo = currentScope.getVariable(variableCall.name);
            if (vInfo == null && currentClass != null) {
                vInfo = findVariableInAncestors(currentClass, variableCall.name);
            }

            if (vInfo == null) {
                printCurrentScope();
                throw new SemanticException("Variable not found: '" + variableCall.name + "'");
            }

            if (!isPrimitive(vInfo.type)) {
                Scope.ClassInfo classInfo = globalScope.getClass(vInfo.type);
                if (classInfo == null) {
                    throw new SemanticException("Class not found: '" + vInfo.type + "'");
                }
                Scope prevScope = currentScope;
                String prevClass = currentClass;
                currentScope = classInfo.classScope;
                currentClass = vInfo.type;
                String rType = analyzeFunctionCall(n);
                currentScope = prevScope;
                currentClass = prevClass;
                return rType;
            } else {
                throw new SemanticException(vInfo.type + " is primitive and has no methods");
            }
        }

        throw new SemanticException("unexpected AST Type found");
    }

    private void analyzeStatement(AST.ASTToken statement) {

        if (statement instanceof AST.Block b) {
            analyzeBlock(b, null);
        } else if (statement instanceof AST.FunctionDeclaration f) {
            analyzeFunctionDeclaration(f);
        } else if (statement instanceof AST.VariableDeclaration v) {
            analyzeVariableDeclaration(v);
        } else if (statement instanceof AST.ReturnStatement r) {
            analyzeReturn(r, null);
        } else if (statement instanceof AST.IfStatement i) {
            analyzeIf(i, null);
        } else if (statement instanceof AST.WhileLoop w) {
            analyzeWhile(w, null);
        } else if (statement instanceof AST.Assignment a) {
            analyzeAssignment(a);
        } else {
            analyzeExpression(statement);
        }
    }

    private String analyzeExpression(AST.ASTToken expression) {
        if (expression instanceof AST.Operation o) {
            return analyzeOperation(o);
        } else if (expression instanceof AST.NOT n) {
            return analyzeNot(n);
        } else if (expression instanceof AST.Literal l) {
            return analyzeLiteral(l);
        } else if (expression instanceof AST.FunctionCall f) {
            return analyzeFunctionCall(f);
        } else if (expression instanceof AST.VariableCall v) {
            return analyzeVariableCall(v);
        }
        throw new SemanticException("Unexpected Token in Expression: " + expression.toString());
    }

    private boolean isPrimitive(String returnType) {
        List<String> primitiveType = Arrays.asList("int", "bool", "string", "char", "void");
        return primitiveType.contains(returnType);
    }

    /** Check if toCheck is a subclass of targetClass (or equal to it) */
    private boolean isSubclassOf(String toCheck, String targetClass) {
        if (toCheck == null || targetClass == null) {
            return false;
        }

        if (toCheck.equals(targetClass)) {
            return true;
        }

        Scope.ClassInfo classInfo = globalScope.getClass(toCheck);
        if (classInfo == null || classInfo.parent == null) {
            return false;
        }

        // Recursively check if parent is subclass of target
        return isSubclassOf(classInfo.parent, targetClass);
    }

    private Scope.MethodInfo findMethodInAncestors(String className, String methodSignature) {
        if (className == null) {
            return null;
        }

        Scope.ClassInfo classInfo = globalScope.getClass(className);
        if (classInfo == null || classInfo.classScope == null) {
            return null;
        }

        // hier extra nicht classInfo.classScope.getMethod() weil uns hier nur der local scope
        // intresiert
        Scope.MethodInfo method = classInfo.classScope.methods.get(methodSignature);
        if (method != null) {
            return method;
        }

        return findMethodInAncestors(classInfo.parent, methodSignature);
    }

    private Scope.VariableInfo findVariableInAncestors(String className, String variableName) {
        if (className == null) {
            return null;
        }

        Scope.ClassInfo classInfo = globalScope.getClass(className);
        if (classInfo == null || classInfo.classScope == null) {
            return null;
        }

        // hier extra nicht classInfo.classScope.getVariable() weil uns hier nur der local scope
        // intresiert
        Scope.VariableInfo variable = classInfo.classScope.variables.get(variableName);
        if (variable != null) {
            return variable;
        }

        return findVariableInAncestors(classInfo.parent, variableName);
    }

    public void printCurrentScope() {
        System.out.println("\n=== SCOPE HIERARCHY ===");
        printScopeHierarchy(currentScope, 0);
    }

    private void printScopeHierarchy(Scope scope, int depth) {
        String indent = "  ".repeat(depth);

        if (scope == globalScope) {
            System.out.println(indent + "GLOBAL SCOPE");
        } else {
            System.out.println(indent + "LOCAL SCOPE (parent exists)");
        }

        if (!scope.classes.isEmpty()) {
            System.out.println(indent + "CLASSES:");
            for (String className : scope.classes.keySet()) {
                Scope.ClassInfo classInfo = scope.classes.get(className);
                String parent = classInfo.parent != null ? " extends " + classInfo.parent : "";
                System.out.println(indent + "    • " + className + parent);
            }
        }

        if (!scope.methods.isEmpty()) {
            System.out.println(indent + "METHODS:");
            for (String signature : scope.methods.keySet()) {
                Scope.MethodInfo method = scope.methods.get(signature);
                String virtualStr = method.isVirtual ? "[VIRTUAL]" : "[STATIC]";
                System.out.println(
                        indent
                                + "    • "
                                + method.returnType
                                + " "
                                + method.name
                                + "("
                                + String.join(", ", method.paramTypes)
                                + ") "
                                + virtualStr
                                + " ["
                                + method.definingClass
                                + "]");
            }
        }

        if (!scope.variables.isEmpty()) {
            System.out.println(indent + "VARIABLES:");
            for (String varName : scope.variables.keySet()) {
                Scope.VariableInfo var = scope.variables.get(varName);
                String refStr = var.isReference ? " (ref)" : "";
                System.out.println(indent + "    " + var.type + " " + varName + refStr);
            }
        }

        if (scope.parent != null) {
            System.out.println(indent + " |- Parent scope:");
            printScopeHierarchy(scope.parent, depth + 2);
        }
    }
}

class SemanticException extends RuntimeException {
    public SemanticException(String message) {

        super("Semantic Error: " + message);
    }
}
