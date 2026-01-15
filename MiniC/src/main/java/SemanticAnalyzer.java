import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SemanticAnalyzer {
    public Scope globalScope;
    private Scope currentScope;
    private String currentClass;

    public SemanticAnalyzer() {
        globalScope = new Scope(null);
        currentScope = globalScope;
        currentClass = null;

        initializeBuiltinFunctions();
    }

    private void initializeBuiltinFunctions() {
        ArrayList<Scope.VariableInfo> intParams = new ArrayList<>();
        intParams.add(new Scope.VariableInfo("value", "int", null, false));
        globalScope.methods.put(
                "print_int/int,",
                new Scope.MethodInfo("print_int", "void", intParams, false, null));

        ArrayList<Scope.VariableInfo> boolParams = new ArrayList<>();
        boolParams.add(new Scope.VariableInfo("value", "bool", null, false));
        globalScope.methods.put(
                "print_bool/bool,",
                new Scope.MethodInfo("print_bool", "void", boolParams, false, null));

        ArrayList<Scope.VariableInfo> charParams = new ArrayList<>();
        charParams.add(new Scope.VariableInfo("value", "char", null, false));
        globalScope.methods.put(
                "print_char/char,",
                new Scope.MethodInfo("print_char", "void", charParams, false, null));

        ArrayList<Scope.VariableInfo> stringParams = new ArrayList<>();
        stringParams.add(new Scope.VariableInfo("value", "string", null, false));
        globalScope.methods.put(
                "print_string/string,",
                new Scope.MethodInfo("print_string", "void", stringParams, false, null));
    }

    private Scope.VariableInfo buildVariableInfo(AST.AttributeDeclaration attrDecl) {
        return new Scope.VariableInfo(
                attrDecl.name, attrDecl.type, null, false //
                );
    }

    private Scope.VariableInfo buildVariableInfo(AST.VariableDeclaration attrDecl) {
        return new Scope.VariableInfo(attrDecl.varName, attrDecl.type, null, attrDecl.deepcopy);
    }

    private Scope.MethodInfo buildMethodInfo(AST.MethodDefinition methodDef, String definingClass) {
        ArrayList<Scope.VariableInfo> parameters = new ArrayList<>();
        if (methodDef.paramList != null) {
            for (int i = 0; i < methodDef.paramList.name.size(); i++) {
                parameters.add(
                        new Scope.VariableInfo(
                                methodDef.paramList.name.get(i),
                                methodDef.paramList.type.get(i),
                                null,
                                methodDef.paramList.isRef.get(i)));
            }
        }

        return new Scope.MethodInfo(
                methodDef.methodName, methodDef.type, parameters, methodDef.virtual, definingClass);
    }

    private Scope.MethodInfo buildMethodInfo(String name, ArrayList<String> paramTypes) {
        ArrayList<Scope.VariableInfo> parameters = new ArrayList<>();
        if (paramTypes != null) {
            for (String type : paramTypes) {
                parameters.add(new Scope.VariableInfo(null, type, null, false));
            }
        }
        return new Scope.MethodInfo(name, null, parameters, false, null);
    }

    private Scope.MethodInfo buildMethodInfo(AST.FunctionDeclaration methodDef) {
        ArrayList<Scope.VariableInfo> parameters = new ArrayList<>();
        if (methodDef.paramList != null) {
            for (int i = 0; i < methodDef.paramList.name.size(); i++) {
                parameters.add(
                        new Scope.VariableInfo(
                                methodDef.paramList.name.get(i),
                                methodDef.paramList.type.get(i),
                                null,
                                methodDef.paramList.isRef.get(i)));
            }
        }

        return new Scope.MethodInfo(
                methodDef.functionName, methodDef.type, parameters, false, null);
    }

    private Scope.MethodInfo buildConstructorInfo(AST.Constructor ctor) {
        ArrayList<Scope.VariableInfo> parameters = new ArrayList<>();
        if (ctor.paramList != null) {
            for (int i = 0; i < ctor.paramList.name.size(); i++) {
                parameters.add(
                        new Scope.VariableInfo(
                                ctor.paramList.name.get(i),
                                ctor.paramList.type.get(i),
                                null,
                                ctor.paramList.isRef.get(i)));
            }
        }

        return new Scope.MethodInfo(
                ctor.className, ctor.className, parameters, false, ctor.className);
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
                currentScope.declareMethod(constructorInfo.getSignature(), constructorInfo);
            }
        }

        if (counter == 0) {
            ArrayList<Scope.VariableInfo> emptyParams = new ArrayList<>();
            Scope.MethodInfo constructorInfo =
                    new Scope.MethodInfo(className, className, emptyParams, false, className);
            currentScope.declareMethod(constructorInfo.getSignature(), constructorInfo);
        }

        ArrayList<Scope.VariableInfo> copyParams = new ArrayList<>();
        copyParams.add(new Scope.VariableInfo("value", className, null, false));
        Scope.MethodInfo constructorInfo =
                new Scope.MethodInfo(className, className, copyParams, false, className);
        if (globalScope.getMethod(constructorInfo.getSignature()) == null) {
            globalScope.declareMethod(constructorInfo.getSignature(), constructorInfo);
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
                Scope.VariableInfo varInfo = buildVariableInfo(attrDecl);
                classScope.declareVariable(varInfo);
                classInfo.attributes.put(attrDecl.name, varInfo);
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

                // Check for virtual method override compatibility
                if (classInfo.parent != null) {
                    validateVirtualMethodOverride(methodInfo, classInfo);
                }

                classScope.declareMethod(methodInfo.getSignature(), methodInfo);
                classInfo.methods.put(methodInfo.getSignature(), methodInfo);
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

    private void validateVirtualMethodOverride(
            Scope.MethodInfo childMethod, Scope.ClassInfo childClass) {
        Scope.MethodInfo parentMethod =
                findMethodInAncestors(childClass.parent, childMethod.getSignature());

        if (parentMethod != null) {
            if (!childMethod.returnType.equals(parentMethod.returnType)) {
                throw new SemanticException(
                        "Method override '"
                                + childMethod.getSignature()
                                + "' in class '"
                                + childClass.name
                                + "' has different return type: '"
                                + childMethod.returnType
                                + "' vs '"
                                + parentMethod.returnType
                                + "'");
            }
        }
    }

    private void analyzeConstructor(AST.Constructor ctor) {
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);
        if (ctor.paramList != null) {
            for (int i = 0; i < ctor.paramList.name.size(); i++) {
                currentScope.declareVariable(
                        new Scope.VariableInfo(
                                ctor.paramList.name.get(i),
                                ctor.paramList.type.get(i),
                                "",
                                ctor.paramList.isRef.get(i)));
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
                                functionDeclaration.paramList.isRef.get(i)));
            }
        }
        analyzeBlock(functionDeclaration.block, functionDeclaration.type);

        currentScope = prevScope;
    }

    private void analyzeVariableDeclaration(AST.VariableDeclaration variableDeclaration) {
        Scope.VariableInfo variableInfo = buildVariableInfo(variableDeclaration);

        if (variableInfo.type != null) {
            currentScope.declareVariable(variableInfo);
            if (variableDeclaration.expression != null) {
                String exprtype = analyzeExpression(variableDeclaration.expression);

                if (variableInfo.type.equals(exprtype)
                        || isSubclassOf(exprtype, variableInfo.type)) {
                    return;
                }
                printCurrentScope();
                throw new SemanticException(
                        "types dont match " + variableInfo.type + " is not equal to " + exprtype);
            }
        } else {
            if (variableDeclaration.expression != null) {
                variableInfo.type = analyzeExpression(variableDeclaration.expression);
                currentScope.declareVariable(variableInfo);
                return;
            }
            currentScope.declareVariable(variableInfo);
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
                                methodDefinition.paramList.isRef.get(i)));
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

        // Check if arguments are lvalues or rvalues for reference binding
        ArrayList<Boolean> isLvalue = new ArrayList<>();
        if (functionCall.args != null && functionCall.args.expressions != null) {
            for (AST.ASTToken expr : functionCall.args.expressions) {
                // Only variables are lvalues. Function calls return rvalues (temporaries)
                isLvalue.add(expr instanceof AST.VariableCall);
            }
        }

        // Try to find a matching function (considering reference parameters)
        Scope.MethodInfo methodInfo = findMatchingFunction(functionCall.name, args, isLvalue);

        if (methodInfo == null) {
            printCurrentScope();
            throw new SemanticException(
                    "Can not find function "
                            + functionCall.name
                            + "/"
                            + String.join(",", args)
                            + ",");
        }

        // Check for ambiguous overloads (e.g., f(int) and f(int&) both match)
        checkAmbiguousOverload(functionCall.name, args, isLvalue);

        String returnType = methodInfo.returnType;

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

            return analyzeVariableCallOnClass(n, classInfo);

        } else if (functionCall.next instanceof AST.FunctionCall n) {

            if (isPrimitive(returnType)) {
                throw new SemanticException(
                        returnType + " is primitive and has no variable called " + n);
            }

            Scope.ClassInfo classInfo = globalScope.getClass(returnType);

            if (classInfo == null) {
                throw new SemanticException(returnType + " class not found");
            }

            return analyzeFunctionCallOnClass(n, classInfo);
        }
        return "";
    }

    private String analyzeVariableCallOnClass(
            AST.VariableCall variableCall, Scope.ClassInfo classInfo) {
        if (variableCall.next == null) {
            Scope.VariableInfo vInfo = classInfo.attributes.get(variableCall.name);

            if (vInfo == null && classInfo.parent != null) {
                vInfo = findVariableInAncestors(classInfo.parent, variableCall.name);
            }

            if (vInfo == null) {
                throw new SemanticException(
                        "Variable '"
                                + variableCall.name
                                + "' not found in class '"
                                + classInfo.name
                                + "'");
            }
            return vInfo.type;
        }

        if (variableCall.next instanceof AST.VariableCall n) {
            Scope.VariableInfo vInfo = classInfo.attributes.get(variableCall.name);
            if (vInfo == null && classInfo.parent != null) {
                vInfo = findVariableInAncestors(classInfo.parent, variableCall.name);
            }

            if (vInfo == null) {
                throw new SemanticException(
                        "Variable '"
                                + variableCall.name
                                + "' not found in class '"
                                + classInfo.name
                                + "'");
            }

            if (!isPrimitive(vInfo.type)) {
                Scope.ClassInfo nextClassInfo = globalScope.getClass(vInfo.type);
                if (nextClassInfo == null) {
                    throw new SemanticException("Class not found: '" + vInfo.type + "'");
                }
                return analyzeVariableCallOnClass(n, nextClassInfo);
            } else {
                throw new SemanticException(vInfo.type + " is primitive and has no members");
            }
        } else if (variableCall.next instanceof AST.FunctionCall n) {
            Scope.VariableInfo vInfo = classInfo.attributes.get(variableCall.name);
            if (vInfo == null && classInfo.parent != null) {
                vInfo = findVariableInAncestors(classInfo.parent, variableCall.name);
            }

            if (vInfo == null) {
                throw new SemanticException(
                        "Variable '"
                                + variableCall.name
                                + "' not found in class '"
                                + classInfo.name
                                + "'");
            }

            if (!isPrimitive(vInfo.type)) {
                Scope.ClassInfo nextClassInfo = globalScope.getClass(vInfo.type);
                if (nextClassInfo == null) {
                    throw new SemanticException("Class not found: '" + vInfo.type + "'");
                }
                return analyzeFunctionCallOnClass(n, nextClassInfo);
            } else {
                throw new SemanticException(vInfo.type + " is primitive and has no methods");
            }
        }

        throw new SemanticException("unexpected AST Type found");
    }

    private String analyzeFunctionCallOnClass(
            AST.FunctionCall functionCall, Scope.ClassInfo classInfo) {
        ArrayList<String> args = analyzeArgs(functionCall.args);

        // Track which arguments are lvalues (only variables, not function results)
        ArrayList<Boolean> isLvalue = new ArrayList<>();
        if (functionCall.args != null && functionCall.args.expressions != null) {
            for (AST.ASTToken expr : functionCall.args.expressions) {
                // Only variables are lvalues. Function calls return rvalues (temporaries)
                isLvalue.add(expr instanceof AST.VariableCall);
            }
        }

        // Try to find matching method using smart matching (exact first, then references)
        Scope.MethodInfo methodInfo =
                findMethodInClass(functionCall.name, args, isLvalue, classInfo);

        if (methodInfo == null) {
            String sig = buildMethodInfo(functionCall.name, args).getSignature();
            throw new SemanticException(
                    "Can not find function " + sig + " in class " + classInfo.name);
        }

        String returnType = methodInfo.returnType;

        if (functionCall.next == null) {
            return returnType;
        }

        if (functionCall.next instanceof AST.VariableCall n) {

            if (isPrimitive(returnType)) {
                throw new SemanticException(
                        returnType + " is primitive and has no variable called " + n);
            }

            Scope.ClassInfo nextClassInfo = globalScope.getClass(returnType);

            if (nextClassInfo == null) {
                throw new SemanticException(returnType + " class not found");
            }

            return analyzeVariableCallOnClass(n, nextClassInfo);

        } else if (functionCall.next instanceof AST.FunctionCall n) {

            if (isPrimitive(returnType)) {
                throw new SemanticException(
                        returnType + " is primitive and has no variable called " + n);
            }

            Scope.ClassInfo nextClassInfo = globalScope.getClass(returnType);

            if (nextClassInfo == null) {
                throw new SemanticException(returnType + " class not found");
            }

            return analyzeFunctionCallOnClass(n, nextClassInfo);
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
                return analyzeVariableCallOnClass(n, classInfo);
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
                return analyzeFunctionCallOnClass(n, classInfo);
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

        return isSubclassOf(classInfo.parent, targetClass);
    }

    private Scope.MethodInfo findMethodInAncestors(String className, String methodSignature) {
        if (className == null) {
            return null;
        }

        Scope.ClassInfo classInfo = globalScope.getClass(className);
        if (classInfo == null) {
            return null;
        }

        Scope.MethodInfo method = classInfo.methods.get(methodSignature);
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
        if (classInfo == null) {
            return null;
        }

        Scope.VariableInfo variable = classInfo.attributes.get(variableName);
        if (variable != null) {
            return variable;
        }

        return findVariableInAncestors(classInfo.parent, variableName);
    }

    private Scope.MethodInfo findMatchingFunction(
            String functionName, ArrayList<String> argTypes, ArrayList<Boolean> isLvalue) {
        // Try exact match first, then reference variants
        Scope.MethodInfo exactMatch = null;
        Scope.MethodInfo refMatch = null;

        // Check current scope
        for (Scope.MethodInfo method : currentScope.methods.values()) {
            if (!method.name.equals(functionName)) {
                continue;
            }

            if (isExactMatch(method, argTypes, isLvalue)) {
                exactMatch = method;
                break;
            }
        }

        // Check global scope
        if (exactMatch == null && refMatch == null) {
            for (Scope.MethodInfo method : globalScope.methods.values()) {
                if (!method.name.equals(functionName)) {
                    continue;
                }

                if (isExactMatch(method, argTypes, isLvalue)) {
                    exactMatch = method;
                    break;
                }
            }
        }

        return exactMatch != null ? exactMatch : refMatch;
    }

    private boolean isExactMatch(
            Scope.MethodInfo method, ArrayList<String> argTypes, ArrayList<Boolean> isLvalue) {
        if (method.parameters.size() != argTypes.size()) {
            return false;
        }

        for (int i = 0; i < argTypes.size(); i++) {
            Scope.VariableInfo param = method.parameters.get(i);
            String argType = argTypes.get(i);
            boolean argIsLvalue = i < isLvalue.size() && isLvalue.get(i);

            // Type must match exactly
            if (!param.type.equals(argType)) {
                return false;
            }

            // Reference compatibility check:
            // - Non-ref param always accepts the argument (whether lvalue or rvalue)
            // - Ref param requires argument to be lvalue
            if (param.isReference && !argIsLvalue) {
                return false; // rvalue cannot bind to non-const reference
            }
        }
        return true;
    }

    private Scope.MethodInfo findMethodInClass(
            String methodName,
            ArrayList<String> argTypes,
            ArrayList<Boolean> isLvalue,
            Scope.ClassInfo classInfo) {
        // First try exact match in this class
        for (Scope.MethodInfo method : classInfo.methods.values()) {
            if (!method.name.equals(methodName)) {
                continue;
            }
            if (isExactMatch(method, argTypes, isLvalue)) {
                return method;
            }
        }

        // Then try exact match in ancestors
        if (classInfo.parent != null) {
            Scope.ClassInfo parentClass = globalScope.getClass(classInfo.parent);
            if (parentClass != null) {
                return findMethodInClass(methodName, argTypes, isLvalue, parentClass);
            }
        }

        return null;
    }

    private void checkAmbiguousOverload(
            String functionName, ArrayList<String> argTypes, ArrayList<Boolean> isLvalue) {
        // Check for ambiguity - multiple methods can match the same arguments
        boolean hasExactMatch = false;
        List<Scope.MethodInfo> exactMatches = new ArrayList<>();
        List<Scope.MethodInfo> refMatches = new ArrayList<>();

        // Check global scope
        for (Scope.MethodInfo method : globalScope.methods.values()) {
            if (!method.name.equals(functionName)) {
                continue;
            }

            if (isExactMatch(method, argTypes, isLvalue)) {
                hasExactMatch = true;
                exactMatches.add(method);
            }
        }

        // Check current scope
        for (Scope.MethodInfo method : currentScope.methods.values()) {
            if (!method.name.equals(functionName)) {
                continue;
            }

            if (isExactMatch(method, argTypes, isLvalue)) {
                hasExactMatch = true;
                exactMatches.add(method);
            }
        }

        // Report ambiguity if multiple exact matches
        if (exactMatches.size() > 1) {
            StringBuilder sigList = new StringBuilder();
            for (Scope.MethodInfo m : exactMatches) {
                sigList.append(m.getSignature()).append(", ");
            }
            throw new SemanticException(
                    "Ambiguous overload: function '"
                            + functionName
                            + "' matches multiple exact overloads ("
                            + sigList.toString()
                            + ") for arguments ("
                            + String.join(", ", argTypes)
                            + ")");
        }
    }

    private boolean canMatchFunction(
            Scope.MethodInfo method, ArrayList<String> argTypes, ArrayList<Boolean> isLvalue) {
        return isExactMatch(method, argTypes, isLvalue);
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
                ArrayList<String> paramTypes = new ArrayList<>();
                for (Scope.VariableInfo param : method.parameters) {
                    String paramStr = param.type;
                    if (param.isReference) {
                        paramStr += "&";
                    }
                    paramTypes.add(paramStr);
                }
                System.out.println(
                        indent
                                + "    • "
                                + method.returnType
                                + " "
                                + method.name
                                + "("
                                + String.join(", ", paramTypes)
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
