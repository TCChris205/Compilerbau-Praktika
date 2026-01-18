import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Interpreter {
    private Scope globalScope;
    private Scope currentScope;
    private String currentClass;
    private Map<String, AST.Block> methodBodies = new HashMap<>();
    private Map<String, AST.Block> constructorBodies = new HashMap<>();
    private Map<String, Scope.ClassInfo> classDefinitions = new HashMap<>();
    private ReturnValue returnValue = null;

    // Value representation for runtime
    public static class Value {
        String type; // "int", "bool", "char", "string", or class name
        Object value; // Integer, Boolean, Character, String, or ClassInstance
        boolean isReference = false;
        Variable referencedVariable = null; // for references
        String declaredType = null; // for references with different static type than instance

        public Value(String type, Object value) {
            this.type = type;
            this.value = value;
        }

        public Value(String type, Object value, boolean isRef, Variable refVar) {
            this.type = type;
            this.value = value;
            this.isReference = isRef;
            this.referencedVariable = refVar;
        }

        public Value(String type, Object value, String declaredType) {
            this.type = type;
            this.value = value;
            this.declaredType = declaredType;
        }

        @Override
        public String toString() {
            if (value == null) return "null";
            if (value instanceof Integer) return value.toString();
            if (value instanceof Boolean) return ((Boolean) value) ? "1" : "0";
            if (value instanceof Character) return value.toString();
            if (value instanceof String) return (String) value;
            if (value instanceof ClassInstance) return ((ClassInstance) value).toString();
            return value.toString();
        }

        public Value copy() {
            if (value instanceof ClassInstance ci) {
                return new Value(type, ci.deepCopy());
            }
            return new Value(type, value);
        }

        public Value dereference() {
            if (isReference && referencedVariable != null) {
                Value v = referencedVariable.getValue();
                return v.copy();
            }
            return this;
        }
    }

    // Variable storage
    public static class Variable {
        String name;
        String type;
        Value value;
        boolean isReference;
        Variable referencedVar; // for references

        public Variable(String name, String type, Value value, boolean isRef, Variable refVar) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.isReference = isRef;
            this.referencedVar = refVar;
        }

        public Value getValue() {
            if (isReference && referencedVar != null) {
                Value refVal = referencedVar.getValue();
                // Preserve the reference's declared type
                if (refVal != null && refVal.declaredType == null && !type.equals(refVal.type)) {
                    // Create a new Value with the reference's declared type
                    return new Value(refVal.type, refVal.value, type);
                }
                return refVal;
            }
            return value;
        }

        public void setValue(Value v) {
            if (isReference && referencedVar != null) {
                referencedVar.setValue(v);
            } else {
                this.value = v;
            }
        }
    }

    // Class instance
    public static class ClassInstance {
        String className;
        String staticType; // for slicing
        Map<String, Value> fields = new HashMap<>();
        Interpreter interpreter;

        public ClassInstance(String className, String staticType, Interpreter interpreter) {
            this.className = className;
            this.staticType = staticType;
            this.interpreter = interpreter;
        }

        public ClassInstance deepCopy() {
            ClassInstance copy = new ClassInstance(className, staticType, interpreter);
            for (String fieldName : fields.keySet()) {
                copy.fields.put(fieldName, fields.get(fieldName).copy());
            }
            return copy;
        }

        @Override
        public String toString() {
            return className + "{...}";
        }
    }

    // Return value wrapper
    public static class ReturnValue extends RuntimeException {
        Value value;

        public ReturnValue(Value value) {
            this.value = value;
        }
    }

    public Interpreter(
            Scope globalScope,
            Map<String, AST.FunctionDeclaration> functions,
            Map<String, AST.ClassDeclaration> classes,
            AST.Start ast) {
        this.globalScope = globalScope;
        this.currentScope = globalScope;
        this.currentClass = null;

        // Store method bodies (method definitions are already in globalScope from SemanticAnalyzer)
        // Iterate through the full AST to get all function overloads, not just the map
        for (AST.ASTToken line : ast.lines) {
            if (line instanceof AST.FunctionDeclaration func) {
                Scope.MethodInfo info =
                        new Scope.MethodInfo(
                                func.functionName,
                                func.type,
                                buildParamList(func.paramList),
                                false,
                                null);
                String sig = info.getSignature();
                methodBodies.put(sig, func.block);
            }
        }

        // Store all class definitions
        for (AST.ClassDeclaration classDecl : classes.values()) {
            classDefinitions.put(classDecl.className, globalScope.getClass(classDecl.className));
            storeClassMethods(classDecl);
        }
    }

    // Keep old constructor for backwards compatibility
    public Interpreter(
            Scope globalScope,
            Map<String, AST.FunctionDeclaration> functions,
            Map<String, AST.ClassDeclaration> classes) {
        this.globalScope = globalScope;
        this.currentScope = globalScope;
        this.currentClass = null;

        // Store method bodies from the functions map
        // NOTE: This will lose overloaded functions since the map is keyed by name only
        for (AST.FunctionDeclaration func : functions.values()) {
            Scope.MethodInfo info =
                    new Scope.MethodInfo(
                            func.functionName,
                            func.type,
                            buildParamList(func.paramList),
                            false,
                            null);
            String sig = info.getSignature();
            methodBodies.put(sig, func.block);
        }

        // Store all class definitions
        for (AST.ClassDeclaration classDecl : classes.values()) {
            classDefinitions.put(classDecl.className, globalScope.getClass(classDecl.className));
            storeClassMethods(classDecl);
        }
    }

    private List<Scope.VariableInfo> buildParamList(AST.ParamList paramList) {
        List<Scope.VariableInfo> params = new ArrayList<>();
        if (paramList != null) {
            for (int i = 0; i < paramList.name.size(); i++) {
                String type = paramList.type.get(i);
                boolean isRef = paramList.isRef.get(i);

                // Strip "&" from type since we store it separately in isRef
                if (type.endsWith("&")) {
                    type = type.substring(0, type.length() - 1);
                }

                params.add(new Scope.VariableInfo(paramList.name.get(i), type, null, isRef));
            }
        }
        return params;
    }

    private void storeClassMethods(AST.ClassDeclaration classDecl) {
        for (AST.ASTToken member : classDecl.members) {
            if (member instanceof AST.MethodDefinition methodDef) {
                Scope.MethodInfo info =
                        new Scope.MethodInfo(
                                methodDef.methodName,
                                methodDef.type,
                                buildParamList(methodDef.paramList),
                                methodDef.virtual,
                                classDecl.className);
                String sig = info.getSignature();
                String key = classDecl.className + ":" + sig;
                methodBodies.put(key, methodDef.block);
            } else if (member instanceof AST.Constructor ctor) {
                String sig = classDecl.className + "/";
                if (ctor.paramList != null) {
                    for (int i = 0; i < ctor.paramList.type.size(); i++) {
                        sig += ctor.paramList.type.get(i);
                        if (ctor.paramList.isRef.get(i)) {
                            sig += "&";
                        }
                        sig += ",";
                    }
                }
                String key = classDecl.className + ":" + sig;
                constructorBodies.put(key, ctor.block);
            }
        }
    }

    public void execute(AST.Start start) {
        for (AST.ASTToken line : start.lines) {
            if (line instanceof AST.FunctionDeclaration || line instanceof AST.ClassDeclaration) {
                // Already stored
                continue;
            }
            executeStatement(line);
        }
    }

    public void executeStatement(AST.ASTToken stmt) {
        if (stmt == null) return;

        try {
            if (stmt instanceof AST.Block block) {
                Scope prevScope = currentScope;
                currentScope = new Scope(currentScope);
                for (AST.ASTToken s : block.lines) {
                    executeStatement(s);
                }
                currentScope = prevScope;
            } else if (stmt instanceof AST.VariableDeclaration varDecl) {
                executeVariableDeclaration(varDecl);
            } else if (stmt instanceof AST.ReturnStatement retStmt) {
                Value val = null;
                if (retStmt.expression != null) {
                    val = evaluateExpression(retStmt.expression);
                }
                throw new ReturnValue(val);
            } else if (stmt instanceof AST.IfStatement ifStmt) {
                Value cond = evaluateExpression(ifStmt.expression);
                if (isTruthy(cond)) {
                    executeStatement(ifStmt.block);
                } else if (ifStmt.elseBlock != null) {
                    executeStatement(ifStmt.elseBlock);
                }
            } else if (stmt instanceof AST.WhileLoop whileLoop) {
                while (isTruthy(evaluateExpression(whileLoop.expression))) {
                    executeStatement(whileLoop.block);
                }
            } else if (stmt instanceof AST.Assignment assignment) {
                evaluateExpression(stmt);
            } else if (stmt instanceof AST.Operation
                    || stmt instanceof AST.FunctionCall
                    || stmt instanceof AST.VariableCall) {
                evaluateExpression(stmt);
            } else if (stmt instanceof AST.FunctionDeclaration func) {
                // Register new function in REPL
                Scope.MethodInfo info =
                        new Scope.MethodInfo(
                                func.functionName,
                                func.type,
                                buildParamList(func.paramList),
                                false,
                                null);
                String sig = info.getSignature();
                methodBodies.put(sig, func.block);
                // Also add to globalScope if not already there
                globalScope.declareMethod(sig, info);
            } else if (stmt instanceof AST.ClassDeclaration classDecl) {
                // Register new class in REPL - delegate to semantic analyzer would be ideal
                // For now, just silently ignore (user can't redefine classes in REPL safely)
            }
        } catch (ReturnValue rv) {
            this.returnValue = rv;
            throw rv;
        }
    }

    private void executeVariableDeclaration(AST.VariableDeclaration varDecl) {
        Value initialValue = null;
        Variable referencedVar = null;

        // Check if it's a reference declaration (marked with deepcopy flag or type ending with &)
        boolean isRef = varDecl.deepcopy || varDecl.type.endsWith("&");
        String actualType = varDecl.type.replace("&", "");

        if (varDecl.deepcopy) {
            // Reference declaration with deepcopy syntax (int& rx = x;)
            if (varDecl.varCall instanceof AST.VariableCall varCall) {
                // Find the variable being referenced
                referencedVar = findVariable(varCall.name);
                if (referencedVar != null) {
                    initialValue = referencedVar.getValue();
                } else {
                    throw new RuntimeException("Variable not found for reference: " + varCall.name);
                }
            } else if (varDecl.varCall instanceof AST.FunctionCall) {
                initialValue = evaluateExpression((AST.ASTToken) varDecl.varCall);
            }
        } else if (varDecl.expression != null) {
            initialValue = evaluateExpression(varDecl.expression);

            // Handle class instance slicing for non-reference declarations
            if (initialValue != null
                    && initialValue.value instanceof ClassInstance rhsInstance
                    && !isRef) {
                // If initializing a class variable from a different class type, slice it
                if (!actualType.equals(rhsInstance.staticType)) {
                    Scope.ClassInfo targetClass = globalScope.getClass(actualType);
                    if (targetClass != null) {
                        ClassInstance sliced = new ClassInstance(actualType, actualType, this);

                        // Collect all fields including inherited ones
                        Set<String> allFields = new HashSet<>();
                        String classToWalk = actualType;
                        while (classToWalk != null) {
                            Scope.ClassInfo walkInfo = globalScope.getClass(classToWalk);
                            if (walkInfo != null) {
                                allFields.addAll(walkInfo.attributes.keySet());
                                classToWalk = walkInfo.parent;
                            } else {
                                break;
                            }
                        }

                        // Only copy fields that exist in the target class hierarchy
                        for (String fieldName : allFields) {
                            if (rhsInstance.fields.containsKey(fieldName)) {
                                sliced.fields.put(fieldName, rhsInstance.fields.get(fieldName));
                            } else {
                                // Initialize missing fields to default
                                // Find which class this field belongs to
                                classToWalk = actualType;
                                Scope.VariableInfo fieldInfo = null;
                                while (classToWalk != null && fieldInfo == null) {
                                    Scope.ClassInfo walkInfo = globalScope.getClass(classToWalk);
                                    if (walkInfo != null
                                            && walkInfo.attributes.containsKey(fieldName)) {
                                        fieldInfo = walkInfo.attributes.get(fieldName);
                                        break;
                                    }
                                    classToWalk = walkInfo != null ? walkInfo.parent : null;
                                }
                                if (fieldInfo != null) {
                                    sliced.fields.put(fieldName, getDefaultValue(fieldInfo.type));
                                }
                            }
                        }
                        initialValue = new Value(actualType, sliced);
                    }
                }
            }

            // If this is a reference type, try to bind to the referenced variable
            if (isRef && varDecl.expression instanceof AST.VariableCall varCall) {
                referencedVar = findVariable(varCall.name);
            }
        } else {
            // No initialization - use default
            initialValue = getDefaultValue(actualType);
        }

        Variable var;
        if (isRef && referencedVar != null) {
            // For references, bind to the original variable but with declared static type
            initialValue = new Value(actualType, initialValue.value, actualType);
            var = new Variable(varDecl.varName, actualType, initialValue, true, referencedVar);
        } else {
            var = new Variable(varDecl.varName, actualType, initialValue, false, null);
        }

        currentScope.declareVariable(
                new Scope.VariableInfo(varDecl.varName, actualType, null, isRef));
        // Store the actual value in runtime map
        currentScope.runtimeValues.put(varDecl.varName, var);
    }

    private Variable findVariableForRef(Value val) {
        // This is a simplification - in a full implementation, we'd track which variable
        // a value came from
        return null;
    }

    private Value getDefaultValue(String type) {
        if (type == null) return null;
        type = type.replace("&", "");

        switch (type) {
            case "int":
                return new Value("int", 0);
            case "bool":
                return new Value("bool", false);
            case "char":
                return new Value("char", '\0');
            case "string":
                return new Value("string", "");
            case "void":
                return null;
            default:
                // Class type
                Scope.ClassInfo classInfo = globalScope.getClass(type);
                if (classInfo != null) {
                    ClassInstance instance = new ClassInstance(type, type, this);
                    // Initialize field defaults
                    String classToWalk = type;
                    while (classToWalk != null) {
                        Scope.ClassInfo walkInfo = globalScope.getClass(classToWalk);
                        if (walkInfo != null) {
                            for (Map.Entry<String, Scope.VariableInfo> field :
                                    walkInfo.attributes.entrySet()) {
                                if (!instance.fields.containsKey(field.getKey())) {
                                    String fieldType = field.getValue().type.replace("&", "");
                                    Value defaultVal = null;
                                    switch (fieldType) {
                                        case "int":
                                            defaultVal = new Value("int", 0);
                                            break;
                                        case "bool":
                                            defaultVal = new Value("bool", false);
                                            break;
                                        case "char":
                                            defaultVal = new Value("char", '\0');
                                            break;
                                        case "string":
                                            defaultVal = new Value("string", "");
                                            break;
                                        default:
                                            // For class fields, recursively get their defaults
                                            // (which initializes their fields)
                                            defaultVal = getDefaultValue(fieldType);
                                            break;
                                    }
                                    instance.fields.put(field.getKey(), defaultVal);
                                }
                            }
                            classToWalk = walkInfo.parent;
                        } else {
                            break;
                        }
                    }
                    // Call parameterless constructor if it exists
                    initializeClassInstance(instance, classInfo);
                    return new Value(type, instance);
                }
                return null;
        }
    }

    private void initializeClassInstance(ClassInstance instance, Scope.ClassInfo classInfo) {
        // Call parameterless constructor if it exists
        String ctorSig = instance.className + "/";
        if (constructorBodies.containsKey(instance.className + ":" + ctorSig)) {
            Scope prevScope = currentScope;
            String prevClass = currentClass;
            Scope constructorScope = new Scope(currentScope);
            currentScope = constructorScope;
            currentClass = instance.className;
            try {
                // Make fields accessible
                for (Map.Entry<String, Value> field : instance.fields.entrySet()) {
                    Variable fieldVar =
                            new Variable(field.getKey(), null, field.getValue(), false, null);
                    currentScope.runtimeValues.put(field.getKey(), fieldVar);
                }

                // Set up 'this' in the scope
                Variable thisVar =
                        new Variable(
                                "this",
                                instance.className,
                                new Value(instance.className, instance),
                                false,
                                null);
                currentScope.runtimeValues.put("this", thisVar);

                // If this class has a parent, recursively call parent constructors up the chain
                if (classInfo != null && classInfo.parent != null) {
                    executeParentConstructorChain(instance, classInfo.parent, currentScope);
                }

                AST.Block body = constructorBodies.get(instance.className + ":" + ctorSig);
                executeStatement(body);

                // Sync modified fields back to the instance
                syncFieldsToInstance(instance, constructorScope);
            } catch (ReturnValue rv) {
                // Constructor returns void, ignore, but still sync fields
                syncFieldsToInstance(instance, constructorScope);
            } finally {
                currentScope = prevScope;
                currentClass = prevClass;
            }
        }
    }

    public Value evaluateExpression(AST.ASTToken expr) {
        if (expr == null) return null;

        if (expr instanceof AST.Literal lit) {
            return evaluateLiteral(lit);
        } else if (expr instanceof AST.Operation op) {
            return evaluateOperation(op);
        } else if (expr instanceof AST.NOT not) {
            Value child = evaluateExpression(not.child);
            boolean result = !isTruthy(child);
            return new Value("bool", result);
        } else if (expr instanceof AST.FunctionCall funcCall) {
            return evaluateFunctionCall(funcCall);
        } else if (expr instanceof AST.VariableCall varCall) {
            return evaluateVariableCall(varCall);
        } else if (expr instanceof AST.Assignment assignment) {
            return evaluateAssignment(assignment);
        } else {
            throw new RuntimeException("Unknown expression type: " + expr.getClass().getName());
        }
    }

    private Value evaluateLiteral(AST.Literal lit) {
        switch (lit.type) {
            case "int":
                int intVal = Integer.parseInt(lit.value);
                if ("-".equals(lit.vorzeichen)) intVal = -intVal;
                return new Value("int", intVal);
            case "bool":
                return new Value("bool", "true".equals(lit.value));
            case "char":
                String charVal = lit.value;
                if (charVal.startsWith("'") && charVal.endsWith("'")) {
                    charVal = charVal.substring(1, charVal.length() - 1);
                }
                charVal = unescapeCharLiteral(charVal);
                return new Value("char", charVal.charAt(0));
            case "string":
                String strVal = lit.value;
                if (strVal.startsWith("\"") && strVal.endsWith("\"")) {
                    strVal = strVal.substring(1, strVal.length() - 1);
                }
                strVal = unescapeString(strVal);
                return new Value("string", strVal);
            case "var":
                // Variable reference
                Variable var = findVariable(lit.value);
                if (var != null) {
                    return var.getValue();
                }
                throw new RuntimeException("Variable not found: " + lit.value);
            default:
                throw new RuntimeException("Unknown literal type: " + lit.type);
        }
    }

    private String unescapeCharLiteral(String s) {
        if (s.contains("\\")) {
            s =
                    s.replace("\\n", "\n")
                            .replace("\\t", "\t")
                            .replace("\\r", "\r")
                            .replace("\\\\", "\\")
                            .replace("\\'", "'")
                            .replace("\\\"", "\"");
        }
        return s;
    }

    private String unescapeString(String s) {
        if (s.contains("\\")) {
            s =
                    s.replace("\\n", "\n")
                            .replace("\\t", "\t")
                            .replace("\\r", "\r")
                            .replace("\\\\", "\\")
                            .replace("\\'", "'")
                            .replace("\\\"", "\"");
        }
        return s;
    }

    private Value evaluateOperation(AST.Operation op) {
        // Evaluate first operand
        Value result;
        AST.ASTToken firstElem = op.elements.get(0);
        if (firstElem instanceof AST.Operation) {
            result = evaluateOperation((AST.Operation) firstElem);
        } else {
            result = evaluateExpression(firstElem);
        }

        // Process operations with short-circuit evaluation for && and ||
        for (int i = 0; i < op.operations.size(); i++) {
            String operator = op.operations.get(i);

            // Short-circuit evaluation for &&
            if ("&&".equals(operator)) {
                if (!isTruthy(result)) {
                    // Left side is false, don't evaluate right side
                    return new Value("bool", false);
                }
                // Evaluate right side since left is true
                AST.ASTToken rightElem = op.elements.get(i + 1);
                Value right;
                if (rightElem instanceof AST.Operation) {
                    right = evaluateOperation((AST.Operation) rightElem);
                } else {
                    right = evaluateExpression(rightElem);
                }
                result = new Value("bool", isTruthy(right));
                continue;
            }

            // Short-circuit evaluation for ||
            if ("||".equals(operator)) {
                if (isTruthy(result)) {
                    // Left side is true, don't evaluate right side
                    return new Value("bool", true);
                }
                // Evaluate right side since left is false
                AST.ASTToken rightElem = op.elements.get(i + 1);
                Value right;
                if (rightElem instanceof AST.Operation) {
                    right = evaluateOperation((AST.Operation) rightElem);
                } else {
                    right = evaluateExpression(rightElem);
                }
                result = new Value("bool", isTruthy(right));
                continue;
            }

            // For non-short-circuit operators, evaluate right side normally
            AST.ASTToken rightElem = op.elements.get(i + 1);
            Value right;
            if (rightElem instanceof AST.Operation) {
                right = evaluateOperation((AST.Operation) rightElem);
            } else {
                right = evaluateExpression(rightElem);
            }

            result = applyOperator(operator, result, right);
        }

        return result;
    }

    private Value applyOperator(String op, Value left, Value right) {
        // Handle short-circuit operators
        if ("&&".equals(op)) {
            return new Value("bool", isTruthy(left) && isTruthy(right));
        }
        if ("||".equals(op)) {
            return new Value("bool", isTruthy(left) || isTruthy(right));
        }

        // Arithmetic operators (int only)
        if (left.type.equals("int") && right.type.equals("int")) {
            int l = (Integer) left.value;
            int r = (Integer) right.value;
            switch (op) {
                case "+":
                    return new Value("int", l + r);
                case "-":
                    return new Value("int", l - r);
                case "*":
                    return new Value("int", l * r);
                case "/":
                    if (r == 0) throw new RuntimeException("Division by zero");
                    return new Value("int", l / r);
                case "%":
                    if (r == 0) throw new RuntimeException("Division by zero");
                    return new Value("int", l % r);
                case "<":
                    return new Value("bool", (boolean) (l < r));
                case "<=":
                    return new Value("bool", (boolean) (l <= r));
                case ">":
                    return new Value("bool", (boolean) (l > r));
                case ">=":
                    return new Value("bool", (boolean) (l >= r));
                case "==":
                    return new Value("bool", (boolean) (l == r));
                case "!=":
                    return new Value("bool", (boolean) (l != r));
            }
        }

        // Char operators
        if (left.type.equals("char") && right.type.equals("char")) {
            char l = (Character) left.value;
            char r = (Character) right.value;
            switch (op) {
                case "<":
                    return new Value("bool", (boolean) (l < r));
                case "<=":
                    return new Value("bool", (boolean) (l <= r));
                case ">":
                    return new Value("bool", (boolean) (l > r));
                case ">=":
                    return new Value("bool", (boolean) (l >= r));
                case "==":
                    return new Value("bool", (boolean) (l == r));
                case "!=":
                    return new Value("bool", (boolean) (l != r));
            }
        }

        // String operators
        if (left.type.equals("string") && right.type.equals("string")) {
            String l = (String) left.value;
            String r = (String) right.value;
            switch (op) {
                case "==":
                    return new Value("bool", l.equals(r));
                case "!=":
                    return new Value("bool", !l.equals(r));
            }
        }

        // Bool operators
        if (left.type.equals("bool") && right.type.equals("bool")) {
            boolean l = (Boolean) left.value;
            boolean r = (Boolean) right.value;
            switch (op) {
                case "==":
                    return new Value("bool", l == r);
                case "!=":
                    return new Value("bool", l != r);
            }
        }

        throw new RuntimeException("Invalid operation: " + left.type + " " + op + " " + right.type);
    }

    private Value evaluateVariableCall(AST.VariableCall varCall) {
        Variable var = findVariable(varCall.name);
        if (var == null) {
            throw new RuntimeException("Variable not found: " + varCall.name);
        }

        Value result = var.getValue();

        // Handle chaining
        if (varCall.next != null && varCall.next instanceof AST.IdChainElement) {
            result = handleChain(result, (AST.IdChainElement) varCall.next);
        }

        return result;
    }

    private Value handleChain(Value obj, AST.IdChainElement chain) {
        if (chain == null) return obj;

        // Dereference if it's a reference, but preserve declared type for method lookup
        String declaredType = obj.declaredType;
        if (obj.isReference && obj.referencedVariable != null) {
            Value deref = obj.referencedVariable.getValue();
            // Preserve declared type through dereference
            if (declaredType != null) {
                deref = new Value(deref.type, deref.value, declaredType);
                deref.isReference = obj.isReference;
                deref.referencedVariable = obj.referencedVariable;
            }
            obj = deref;
        }

        if (chain instanceof AST.VariableCall varCall) {
            if (obj.value instanceof ClassInstance ci) {
                Value fieldVal = ci.fields.get(varCall.name);
                if (fieldVal != null) {
                    if (varCall.next != null && varCall.next instanceof AST.IdChainElement) {
                        return handleChain(fieldVal, (AST.IdChainElement) varCall.next);
                    }
                    return fieldVal;
                }
            }
            throw new RuntimeException("Field not found: " + varCall.name);
        } else if (chain instanceof AST.FunctionCall funcCall) {
            if (obj.value instanceof ClassInstance ci) {
                Value result = callMethod(obj, funcCall);
                if (funcCall.next != null && funcCall.next instanceof AST.IdChainElement) {
                    return handleChain(result, (AST.IdChainElement) funcCall.next);
                }
                return result;
            }
            throw new RuntimeException("Cannot call method on non-object");
        }

        return obj;
    }

    private Value callMethod(Value objValue, AST.FunctionCall funcCall) {
        ClassInstance instance = (ClassInstance) objValue.value;
        ArrayList<Value> args = new ArrayList<>();
        ArrayList<AST.ASTToken> argExprs = new ArrayList<>();
        if (funcCall.args != null) {
            for (AST.ASTToken argExpr : funcCall.args.expressions) {
                argExprs.add(argExpr);
                args.add(evaluateExpression(argExpr));
            }
        }

        // Search in the class hierarchy for the method
        Scope.MethodInfo methodInfo = null;
        String methodFoundInClass = null; // Track which class the method was found in
        boolean justResetForVirtual = false; // Flag to prevent parent walk on iteration after reset

        // Use declared type for method lookup if available (for static polymorphism), otherwise use
        // instance's static type
        String classToSearch =
                (objValue.declaredType != null) ? objValue.declaredType : instance.staticType;

        while (classToSearch != null && methodInfo == null) {
            Scope.ClassInfo classInfo = globalScope.getClass(classToSearch);
            if (classInfo != null) {
                // Search in this class's methods
                for (Scope.MethodInfo method : classInfo.methods.values()) {
                    if (method.name.equals(funcCall.name)
                            && method.parameters.size() == args.size()) {
                        // Check for match
                        boolean matches = true;
                        for (int i = 0; i < args.size(); i++) {
                            Scope.VariableInfo param = method.parameters.get(i);
                            Value arg = args.get(i);
                            AST.ASTToken argExpr = argExprs.get(i);

                            String paramBaseType = param.type.replace("&", "");
                            if (!paramBaseType.equals(arg.type)) {
                                matches = false;
                                break;
                            }

                            // For reference parameters, check if argument can bind
                            if (param.isReference && !(argExpr instanceof AST.VariableCall)) {
                                matches = false;
                                break;
                            }
                        }

                        if (matches) {
                            methodInfo = method;
                            methodFoundInClass =
                                    classToSearch; // Remember which class the method was found in

                            // If this method is virtual and we have a different declared type than
                            // actual type,
                            // AND we haven't already reset to the actual type, restart search from
                            // the actual instance type
                            if (method.isVirtual
                                    && objValue.declaredType != null
                                    && !objValue.declaredType.equals(instance.staticType)
                                    && !classToSearch.equals(instance.staticType)) {
                                // Reset to search from actual instance type for virtual method
                                // overrides
                                methodInfo = null;
                                methodFoundInClass = null;
                                classToSearch = instance.staticType;
                                justResetForVirtual = true;
                                break; // Break inner loop to restart search
                            }
                            break;
                        }
                    }
                }
            }

            // Move to parent class (but not if we just reset for virtual method)
            if (methodInfo == null && !justResetForVirtual) {
                classToSearch = classInfo != null ? classInfo.parent : null;
            } else {
                justResetForVirtual = false; // Reset flag for next iteration
            }
        }

        if (methodInfo != null) {
            // Build the signature to look up the method body
            StringBuilder sig = new StringBuilder(methodInfo.name).append("/");
            for (Scope.VariableInfo param : methodInfo.parameters) {
                sig.append(param.type);
                if (param.isReference) {
                    sig.append("&");
                }
                sig.append(",");
            }

            // For static polymorphism, look up the method body in the class where the method
            // signature was found
            // not in the instance's actual class
            String key = methodFoundInClass + ":" + sig.toString();
            AST.Block body = methodBodies.get(key);
            if (body != null) {
                return callMethodWithBody(
                        instance, methodInfo.name, args, argExprs, methodInfo, body);
            }
        }

        throw new RuntimeException("Method not found: " + funcCall.name);
    }

    private Value callMethodWithBody(
            ClassInstance instance,
            String methodName,
            ArrayList<Value> args,
            ArrayList<AST.ASTToken> argExprs,
            Scope.MethodInfo methodInfo,
            AST.Block body) {
        Scope methodScope = new Scope(currentScope); // Save the method scope
        Scope prevScope = currentScope;
        String prevClass = currentClass;
        currentScope = methodScope;
        currentClass = instance.className;

        try {
            // Make fields accessible
            for (Map.Entry<String, Value> field : instance.fields.entrySet()) {
                Variable fieldVar =
                        new Variable(field.getKey(), null, field.getValue(), false, null);
                currentScope.variables.put(
                        field.getKey(), new Scope.VariableInfo(field.getKey(), null, null, false));
                currentScope.runtimeValues.put(field.getKey(), fieldVar);
            }

            // Set up 'this' variable
            Variable thisVar =
                    new Variable(
                            "this",
                            instance.className,
                            new Value(instance.className, instance),
                            false,
                            null);
            currentScope.runtimeValues.put("this", thisVar);
            currentScope.variables.put(
                    "this", new Scope.VariableInfo("this", instance.className, null, false));

            // Bind method parameters
            for (int i = 0; i < methodInfo.parameters.size(); i++) {
                Scope.VariableInfo param = methodInfo.parameters.get(i);
                Value argVal = args.get(i);
                AST.ASTToken argExpr = (i < argExprs.size()) ? argExprs.get(i) : null;

                Variable var;
                if (param.isReference && argExpr instanceof AST.VariableCall varCall) {
                    // For reference parameters, bind to the original variable in caller's scope
                    Variable origVar = findVariableInScope(varCall.name, prevScope);
                    if (origVar != null) {
                        var = new Variable(param.name, param.type, argVal, true, origVar);
                    } else {
                        var = new Variable(param.name, param.type, argVal, param.isReference, null);
                    }
                } else {
                    // For non-reference parameters, create a new variable with the value
                    var = new Variable(param.name, param.type, argVal, false, null);
                }

                currentScope.variables.put(
                        param.name,
                        new Scope.VariableInfo(param.name, param.type, null, param.isReference));
                currentScope.runtimeValues.put(param.name, var);
            }

            executeStatement(body);

            // Sync modified fields back to the instance from methodScope
            syncFieldsToInstance(instance, methodScope);

            return new Value("void", null);
        } catch (ReturnValue rv) {
            // Sync modified fields back to the instance even on return, using methodScope
            syncFieldsToInstance(instance, methodScope);
            return rv.value;
        } finally {
            currentScope = prevScope;
            currentClass = prevClass;
        }
    }

    private void syncFieldsToInstance(ClassInstance instance, Scope methodScope) {
        for (Map.Entry<String, Value> field : instance.fields.entrySet()) {
            String fieldName = field.getKey();
            if (methodScope.runtimeValues.containsKey(fieldName)) {
                Variable modifiedField = (Variable) methodScope.runtimeValues.get(fieldName);
                Value newValue = modifiedField.getValue();
                instance.fields.put(fieldName, newValue);
            }
        }
    }


    private void executeParentConstructorChain(
            ClassInstance instance, String parentClassName, Scope currentScope) {
        // Recursively execute parent constructors up the inheritance chain
        Scope.ClassInfo parentInfo = globalScope.getClass(parentClassName);
        if (parentInfo != null) {
            // First, recursively execute grandparent's constructors
            if (parentInfo.parent != null) {
                executeParentConstructorChain(instance, parentInfo.parent, currentScope);
            }

            // Then execute this parent's default constructor if it exists
            String parentCtorSig = parentClassName + "/";
            if (constructorBodies.containsKey(parentClassName + ":" + parentCtorSig)) {
                AST.Block parentBody = constructorBodies.get(parentClassName + ":" + parentCtorSig);
                executeStatement(parentBody);

                // Update field variables from their modified values
                for (Map.Entry<String, Value> field : instance.fields.entrySet()) {
                    if (currentScope.runtimeValues.containsKey(field.getKey())) {
                        Variable fieldVar =
                                (Variable) currentScope.runtimeValues.get(field.getKey());
                        instance.fields.put(field.getKey(), fieldVar.getValue());
                    }
                }
                // Refresh field variables after parent constructor
                for (Map.Entry<String, Value> field : instance.fields.entrySet()) {
                    Variable fieldVar =
                            new Variable(field.getKey(), null, field.getValue(), false, null);
                    currentScope.runtimeValues.put(field.getKey(), fieldVar);
                }
            }
        }
    }

    private String buildMethodSignature(String name, ArrayList<Value> args) {
        StringBuilder sig = new StringBuilder(name).append("/");
        for (Value arg : args) {
            sig.append(arg.type).append(",");
        }
        return sig.toString();
    }

    private Value evaluateFunctionCall(AST.FunctionCall funcCall) {
        ArrayList<Value> args = new ArrayList<>();
        ArrayList<AST.ASTToken> argExprs = new ArrayList<>();
        if (funcCall.args != null) {
            for (AST.ASTToken argExpr : funcCall.args.expressions) {
                argExprs.add(argExpr);
                args.add(evaluateExpression(argExpr));
            }
        }

        // Check if this is a constructor call (function name matches a class name)
        Scope.ClassInfo classInfo = globalScope.getClass(funcCall.name);
        if (classInfo != null) {
            // This is a constructor call
            ClassInstance instance = new ClassInstance(funcCall.name, funcCall.name, this);

            // Initialize fields to default values - including inherited fields
            // Walk up the inheritance chain and collect all fields
            String classToWalk = funcCall.name;
            while (classToWalk != null) {
                Scope.ClassInfo walkInfo = globalScope.getClass(classToWalk);
                if (walkInfo != null) {
                    // Add fields from this class (in inheritance order, base first)
                    for (Map.Entry<String, Scope.VariableInfo> field :
                            walkInfo.attributes.entrySet()) {
                        if (!instance.fields.containsKey(field.getKey())) {
                            Value defaultVal = getDefaultValue(field.getValue().type);
                            instance.fields.put(field.getKey(), defaultVal);
                        }
                    }
                    classToWalk = walkInfo.parent;
                } else {
                    break;
                }
            }

            // Build constructor signature
            String ctorSig = funcCall.name + "/";
            for (Value arg : args) {
                ctorSig += arg.type + ",";
            }

            // Check if this is a copy constructor (single argument of same class type)
            boolean isCopyConstructor =
                    args.size() == 1
                            && args.get(0).value instanceof ClassInstance
                            && args.get(0).type.equals(funcCall.name);

            if (isCopyConstructor) {
                // Default copy constructor: copy all fields from the argument
                ClassInstance sourceInstance = (ClassInstance) args.get(0).value;
                for (Map.Entry<String, Value> field : sourceInstance.fields.entrySet()) {
                    instance.fields.put(field.getKey(), field.getValue().copy());
                }
            } else if (constructorBodies.containsKey(funcCall.name + ":" + ctorSig)) {
                Scope prevScope = currentScope;
                String prevClass = currentClass;
                Scope constructorScope = new Scope(currentScope);
                currentScope = constructorScope;
                currentClass = funcCall.name;
                try {
                    // Make fields accessible (includes inherited fields)
                    for (Map.Entry<String, Value> field : instance.fields.entrySet()) {
                        Variable fieldVar =
                                new Variable(field.getKey(), null, field.getValue(), false, null);
                        currentScope.runtimeValues.put(field.getKey(), fieldVar);
                    }

                    // Set up 'this' in the scope
                    Variable thisVar =
                            new Variable(
                                    "this",
                                    funcCall.name,
                                    new Value(funcCall.name, instance),
                                    false,
                                    null);
                    currentScope.runtimeValues.put("this", thisVar);

                    // If this class has a parent, recursively call parent constructors up the chain
                    if (classInfo != null && classInfo.parent != null) {
                        executeParentConstructorChain(instance, classInfo.parent, currentScope);
                    }

                    // Get constructor body
                    AST.Block body = constructorBodies.get(funcCall.name + ":" + ctorSig);

                    // Get parameter names from the method info
                    Scope.MethodInfo ctorInfo = globalScope.getMethod(ctorSig);
                    if (ctorInfo != null && ctorInfo.parameters != null) {
                        // Bind constructor parameters using names from MethodInfo
                        for (int i = 0; i < ctorInfo.parameters.size(); i++) {
                            Scope.VariableInfo param = ctorInfo.parameters.get(i);
                            Value argVal = args.get(i);
                            Variable var =
                                    new Variable(param.name, param.type, argVal, false, null);
                            currentScope.runtimeValues.put(param.name, var);
                        }
                    }

                    executeStatement(body);

                    // Sync modified fields back to the instance
                    syncFieldsToInstance(instance, constructorScope);
                } catch (ReturnValue rv) {
                    // Constructor returns void, ignore, but still sync fields
                    syncFieldsToInstance(instance, constructorScope);
                } finally {
                    currentScope = prevScope;
                    currentClass = prevClass;
                }
            }

            return new Value(funcCall.name, instance);
        }

        // Handle built-in print functions
        if (handleBuiltinPrint(funcCall.name, args)) {
            return new Value("void", null);
        }

        // Handle user-defined functions - find the matching method
        Scope.MethodInfo methodInfo = findMethodInfo(funcCall.name, args, argExprs);
        if (methodInfo != null) {
            // Build the signature the same way we stored it
            StringBuilder sig = new StringBuilder(methodInfo.name).append("/");
            for (Scope.VariableInfo param : methodInfo.parameters) {
                sig.append(param.type);
                if (param.isReference) {
                    sig.append("&");
                }
                sig.append(",");
            }
            String bodyKey = sig.toString();
            AST.Block body = methodBodies.get(bodyKey);
            if (body != null) {
                return callFunctionWithBody(funcCall.name, args, argExprs, methodInfo, body);
            }
        }

        throw new RuntimeException("Function not found: " + funcCall.name);
    }

    private boolean handleBuiltinPrint(String funcName, ArrayList<Value> args) {
        switch (funcName) {
            case "print_int":
                if (args.size() == 1 && args.get(0).type.equals("int")) {
                    System.out.println(args.get(0).value);
                    return true;
                }
                break;
            case "print_bool":
                if (args.size() == 1 && args.get(0).type.equals("bool")) {
                    System.out.println((Boolean) args.get(0).value ? 1 : 0);
                    return true;
                }
                break;
            case "print_char":
                if (args.size() == 1 && args.get(0).type.equals("char")) {
                    System.out.println(args.get(0).value);
                    return true;
                }
                break;
            case "print_string":
                if (args.size() == 1 && args.get(0).type.equals("string")) {
                    System.out.println(args.get(0).value);
                    return true;
                }
                break;
        }
        return false;
    }

    private Value callFunctionWithBody(
            String funcName,
            ArrayList<Value> args,
            ArrayList<AST.ASTToken> argExprs,
            Scope.MethodInfo methodInfo,
            AST.Block body) {
        Scope prevScope = currentScope;
        currentScope = new Scope(currentScope);

        try {
            // Bind parameters, handling references specially
            for (int i = 0; i < methodInfo.parameters.size(); i++) {
                Scope.VariableInfo param = methodInfo.parameters.get(i);
                Value argVal = args.get(i);
                AST.ASTToken argExpr = (i < argExprs.size()) ? argExprs.get(i) : null;

                Variable var;
                if (param.isReference && argExpr instanceof AST.VariableCall varCall) {
                    // For reference parameters, bind to the original variable in caller's scope
                    Variable origVar = findVariableInScope(varCall.name, prevScope);
                    if (origVar != null) {
                        var = new Variable(param.name, param.type, argVal, true, origVar);
                    } else {
                        var = new Variable(param.name, param.type, argVal, param.isReference, null);
                    }
                } else {
                    // For non-reference parameters, create a new variable with the value
                    var = new Variable(param.name, param.type, argVal, false, null);
                }

                // Store both VariableInfo and the actual Variable
                currentScope.variables.put(
                        param.name,
                        new Scope.VariableInfo(param.name, param.type, null, param.isReference));
                currentScope.runtimeValues.put(param.name, var);
            }

            executeStatement(body);
            return new Value("void", null);
        } catch (ReturnValue rv) {
            return rv.value;
        } finally {
            currentScope = prevScope;
        }
    }

    private Variable findVariableInScope(String name, Scope scope) {
        Scope s = scope;
        while (s != null) {
            if (s.runtimeValues.containsKey(name)) {
                return (Variable) s.runtimeValues.get(name);
            }
            s = s.parent;
        }
        return null;
    }

    private Scope.MethodInfo findMethodInfo(
            String funcName, ArrayList<Value> args, ArrayList<AST.ASTToken> argExprs) {
        // Find matching function by name and arity
        Scope.MethodInfo bestMatch = null;
        int matchScore = -1; // Higher is better

        for (Scope.MethodInfo method : globalScope.methods.values()) {
            // Check if name matches and arity matches
            if (!method.name.equals(funcName) || method.parameters.size() != args.size()) {
                continue;
            }

            // Check if argument types match parameter types
            boolean canMatch = true;
            int currentScore = 100; // Start with base score

            for (int i = 0; i < args.size(); i++) {
                Scope.VariableInfo param = method.parameters.get(i);
                Value arg = args.get(i);
                AST.ASTToken argExpr = (i < argExprs.size()) ? argExprs.get(i) : null;

                String paramBaseType = param.type.replace("&", "");

                // Check if base types match
                if (!paramBaseType.equals(arg.type)) {
                    canMatch = false;
                    break;
                }

                // For reference parameters, check if argument can bind to reference
                if (param.isReference) {
                    // Reference parameters require that the argument is a variable (not an rvalue)
                    // Literals, function results, and operations are rvalues
                    boolean isVariable = (argExpr instanceof AST.VariableCall);
                    if (!isVariable) {
                        // Cannot bind rvalue to reference parameter
                        canMatch = false;
                        break;
                    }
                    currentScore -= 1; // Slightly lower score for reference match
                } else {
                    // Value parameter - exact match, full score
                    currentScore += 1;
                }
            }

            if (canMatch && currentScore > matchScore) {
                matchScore = currentScore;
                bestMatch = method;
            }
        }

        return bestMatch;
    }

    private Value evaluateAssignment(AST.Assignment assignment) {
        Value rhs = evaluateExpression(assignment.operation);

        // Find the LValue
        if (assignment.chain instanceof AST.VariableCall varCall) {
            // Check if this is a chained assignment (e.g., d.x = 3)
            if (varCall.next != null && varCall.next instanceof AST.VariableCall fieldCall) {
                // This is a field assignment: obj.field = value
                Variable var = findVariable(varCall.name);
                if (var == null) {
                    throw new RuntimeException("Variable not found: " + varCall.name);
                }
                Value objValue = var.getValue();
                if (objValue.value instanceof ClassInstance ci) {
                    ci.fields.put(fieldCall.name, rhs);
                } else {
                    throw new RuntimeException("Cannot access field on non-object");
                }
            } else {
                // Simple variable assignment: var = value
                Variable var = findVariable(varCall.name);

                // If variable not found and we're in a method context, check if it's a field
                if (var == null && currentClass != null) {
                    // Search up the inheritance chain for the field
                    String classToSearch = currentClass;
                    while (classToSearch != null) {
                        Scope.ClassInfo classInfo = globalScope.getClass(classToSearch);
                        if (classInfo != null && classInfo.attributes.containsKey(varCall.name)) {
                            // This is a field assignment - get 'this' from current scope
                            var = findVariable("this");
                            if (var != null) {
                                Value thisValue = var.getValue();
                                if (thisValue.value instanceof ClassInstance ci) {
                                    ci.fields.put(varCall.name, rhs);
                                    return rhs;
                                }
                            }
                            break;
                        }
                        classToSearch = classInfo != null ? classInfo.parent : null;
                    }
                }

                if (var == null) {
                    throw new RuntimeException("Variable not found: " + varCall.name);
                }

                // Handle class instance assignment: deep copy unless assigning to reference
                if (rhs.value instanceof ClassInstance rhsInstance && var.type != null) {
                    if (!var.type.equals(rhsInstance.staticType)) {
                        // Different static types - do slicing
                        Scope.ClassInfo targetClass = globalScope.getClass(var.type);
                        if (targetClass != null) {
                            ClassInstance sliced = new ClassInstance(var.type, var.type, this);
                            // Only copy fields that exist in the target class
                            for (String fieldName : targetClass.attributes.keySet()) {
                                if (rhsInstance.fields.containsKey(fieldName)) {
                                    sliced.fields.put(
                                            fieldName, rhsInstance.fields.get(fieldName).copy());
                                }
                            }
                            rhs = new Value(var.type, sliced);
                        }
                    } else {
                        // Same type - deep copy the instance
                        rhs = new Value(var.type, rhsInstance.deepCopy());
                    }
                }

                var.setValue(rhs);
                // Update in the correct scope's runtime map - for fields, update in all parent
                // scopes
                Scope scope = currentScope;
                while (scope != null) {
                    if (scope.runtimeValues.containsKey(varCall.name)) {
                        scope.runtimeValues.put(varCall.name, var);
                    }
                    scope = scope.parent;
                }
            }
        } else {
            throw new RuntimeException("Invalid assignment target");
        }

        return rhs;
    }

    private Variable findVariable(String name) {
        Scope scope = currentScope;
        while (scope != null) {
            if (scope.runtimeValues.containsKey(name)) {
                Object obj = scope.runtimeValues.get(name);
                if (obj instanceof Variable) {
                    return (Variable) obj;
                }
            }
            if (scope.variables.containsKey(name)) {
                return new Variable(
                        name,
                        scope.variables.get(name).type,
                        getDefaultValue(scope.variables.get(name).type),
                        false,
                        null);
            }
            scope = scope.parent;
        }
        return null;
    }

    private boolean isTruthy(Value val) {
        if (val == null || val.value == null) return false;
        if (val.type.equals("bool")) return (Boolean) val.value;
        if (val.type.equals("int")) return (Integer) val.value != 0;
        if (val.type.equals("char")) return (Character) val.value != '\0';
        if (val.type.equals("string")) return !((String) val.value).isEmpty();
        return true;
    }

    public void callMain() {
        // Call main() function if it exists
        ArrayList<Value> args = new ArrayList<>();
        String methodSig = "main/";

        if (methodBodies.containsKey(methodSig)) {
            Scope prevScope = currentScope;
            currentScope = new Scope(currentScope);

            try {
                AST.Block body = methodBodies.get(methodSig);
                executeStatement(body);
            } catch (ReturnValue rv) {
                // Main returns int, ignore return value
            } finally {
                currentScope = prevScope;
            }
        }
    }

    public Value evaluateForREPL(AST.ASTToken stmt) {
        try {
            if (stmt instanceof AST.VariableDeclaration
                    || stmt instanceof AST.Block
                    || stmt instanceof AST.IfStatement
                    || stmt instanceof AST.WhileLoop
                    || stmt instanceof AST.FunctionDeclaration
                    || stmt instanceof AST.ClassDeclaration) {
                executeStatement(stmt);
                return null;
            } else {
                // Expression statement - return the value
                return evaluateExpression(stmt);
            }
        } catch (ReturnValue rv) {
            this.returnValue = rv;
            return rv.value;
        }
    }

    public void setREPLScope(Scope replScope) {
        this.currentScope = replScope;
    }

    public Scope getREPLScope() {
        return this.currentScope;
    }
}
