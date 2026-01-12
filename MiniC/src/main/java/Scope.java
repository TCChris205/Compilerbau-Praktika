import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Scope {
    public Scope parent;
    public Map<String, VariableInfo> variables = new HashMap<>();
    public Map<String, MethodInfo> methods = new HashMap<>();
    public Map<String, ClassInfo> classes = new HashMap<>();

    public static class VariableInfo {
        String name;
        String type;
        String value;
        Boolean isReference;

        public VariableInfo(String name,
        String type,
        String value,
        Boolean isReference) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.isReference = isReference;
        }
        
    }

    public static class ClassInfo {
        Scope classScope;
        String name;
        String parent;
        boolean hasDefaultConstructor = false;

        public ClassInfo(String name, String parent) {
            this.name = name;
            this.parent = parent;
        }
    }
    
    public static class MethodInfo {
        String name;
        String returnType;
        List<String> paramTypes;
        List<String> paramNames;
        boolean isVirtual;
        String definingClass;

        public MethodInfo(
                String name,
                String returnType,
                List<String> paramTypes,
                List<String> paramNames,
                boolean isVirtual,
                String definingClass) {
            this.name = name;
            this.returnType = returnType;
            this.paramTypes = paramTypes;
            this.paramNames = paramNames;
            this.isVirtual = isVirtual;
            this.definingClass = definingClass;
        }

        public String getSignature() {
            StringBuilder sig = new StringBuilder(name);
            sig.append("/");
            for (String paramType : paramTypes) {
                sig.append(paramType).append(",");
            }
            return sig.toString();
        }
    }

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public void declareVariable(VariableInfo var) {
        if (variables.containsKey(var.name)) {
            throw new SemanticException("Variable '" + var.name + "' already declared in this scope");
        }
        variables.put(var.name, var);
    }

    public void declareMethod(String signature, MethodInfo info) {
        if (variables.containsKey(signature)) {
            throw new SemanticException("Variable '" + signature + "' already declared in this scope");
        }
        methods.put(signature, info);
    }

    public void declareClass(String name, ClassInfo info) {
        if (variables.containsKey(name)) {
            throw new SemanticException("Variable '" + name + "' already declared in this scope");
        }
        classes.put(name, info);
    }

    public VariableInfo getVariable(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        if (parent != null) {
            return parent.getVariable(name);
        }
        return null;
    }

    public ClassInfo getClass(String name) {
        if (classes.containsKey(name)) {
            return classes.get(name);
        }
        if (parent != null) {
            return parent.getClass(name);
        }
        return null;
    }

    public MethodInfo getMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }
        if (parent != null) {
            return parent.getMethod(name);
        }
        return null;
    }

}



