package ir;

import ast.type.*;
import ast.scope.FileScope;
import ir.operand.GlobalVar;
import ir.operand.Register;
import ir.operand.StringConstant;
import ir.typesystem.*;
import misc.Cst;
import misc.UnimplementedError;
import misc.pass.IRFunctionPass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class IRInfo {
    private final HashMap<String, IRFunction> functions = new HashMap<>();
    private final HashMap<String, StructureType> types = new HashMap<>();
    private final HashMap<String, StringConstant> strLiterals = new HashMap<>();
    private final HashMap<String, String> stringMethods = new HashMap<>();

    private final HashMap<String, GlobalVar> globalVars = new HashMap<>();
    private final HashSet<IRFunction> globalFunction = new HashSet<>();
    private int strCounter = 0;
    private IRFunction init = null;

    public IRInfo(FileScope scope) {
        // global functions
        addBuiltinFunction(Cst.void_t, "print").addParam(Cst.str, "str");
        addBuiltinFunction(Cst.void_t, "println").addParam(Cst.str, "str");
        addBuiltinFunction(Cst.void_t, "printInt").addParam(Cst.int32, "n");
        addBuiltinFunction(Cst.void_t, "printlnInt").addParam(Cst.int32, "n");
        addBuiltinFunction(Cst.str, "getString");
        addBuiltinFunction(Cst.int32, "getInt");
        addBuiltinFunction(Cst.str, "toString").addParam(Cst.int32, "i");
        if (false) {
            // gc-ver malloc
            var f = new IRFunction(Cst.globalPrefix(Cst.MALLOC) + "_gcVer", new PointerType(Cst.byte_t), true);
            functions.put(Cst.MALLOC, f);
            globalFunction.add(f);
            f.addParam(Cst.int32, "len");
            addBuiltinFunction(Cst.void_t, Cst.GC_HINT).addParam(Cst.int32, "len");
            addBuiltinFunction(Cst.void_t, Cst.GC_UNHINT).addParam(Cst.int32, "len");
        } else
            addBuiltinFunction(new PointerType(Cst.byte_t), Cst.MALLOC).addParam(Cst.int32, "len");
        // string methods
        addStringMethod(Cst.int32, "length").hasSideEffect = false;
        addStringMethod(Cst.int32, "parseInt").hasSideEffect = false;
        addStringMethod(Cst.int32, "ord").addParam(Cst.int32, "pos").hasSideEffect = false;
        addStringMethod(Cst.str, "substring").addParam(Cst.int32, "left").addParam(Cst.int32, "right");
        // operator overloading
        addStringMethod(Cst.str, "concat").addParam(Cst.str, "rhs");
        addStrCmp("eq");
        addStrCmp("ne");
        addStrCmp("lt");
        addStrCmp("le");
        addStrCmp("gt");
        addStrCmp("ge");
        scopeScan(scope);
    }

    private void addStrCmp(String name) {
        addStringMethod(Cst.bool, name).addParam(Cst.str, "rhs").hasSideEffect = false;
    }

    private IRFunction addStringMethod(IRType ret, String name) {
        var f = new IRFunction("_str_" + name, ret, true);
        f.addParam(Cst.str, "lhs");
        functions.put(Cst.STR_FUNC + name, f);
        stringMethods.put(name, Cst.STR_FUNC + name);
        globalFunction.add(f);
        return f;
    }

    private IRFunction addBuiltinFunction(IRType ret, String name) {
        var f = new IRFunction(Cst.globalPrefix(name), ret, true);
        functions.put(name, f);
        globalFunction.add(f);
        return f;
    }

    private String classMethodInterpretation(String className, String functionName) {
        // translation
        return Cst.STRUCT + className + "." + functionName;
    }

    public IRFunction getStringMethod(String name) {
        return functions.get(stringMethods.get(name));
    }

    public IRFunction getClassMethod(String cls, String func) {
        return functions.get(classMethodInterpretation(cls, func));
    }

    public void scopeScan(FileScope scope) {
        // scan name only
        scope.classTable.forEach((s, classType) -> {
            assert !s.equals("string");
            types.put(s, new StructureType(s));
        });
        scope.classTable.forEach((s, cls) -> addClass(cls));
        scope.functionTable.forEach((f, func) -> {
            if (!func.isBuiltin)
                addFunction(func);
        });
        scope.globalVarTable.forEach((s, v) -> {
            var glo = new GlobalVar(resolveType(v.getType()), v.nameAsReg);
            globalVars.put(s, glo);
        });
    }


    private IRFunction addMethod(ClassType cls, FunctionType func) {
        var f = new IRFunction(classMethodInterpretation(cls.id, func.id), resolveType(func.returnType));
        f.addParam(new Register(resolveType(cls), "this"));
        func.parameters.forEach(param -> f.addParam(new Register(resolveType(param.getType()), param.nameAsReg)));
        functions.put(f.name, f);
        return f;
    }

    public void addClass(ClassType cls) {
        cls.memberFuncs.forEach((name, func) -> addMethod(cls, func));
        cls.constructor.forEach(func -> addMethod(cls, func));
        var struct = types.get(cls.id);
        cls.memberVars.forEach((k, v) -> struct.addMember(new Register(resolveType(v.getType()), k)));
    }

    public void addFunction(FunctionType func) {
        var f = new IRFunction(func.id, resolveType(func.returnType));
        func.parameters.forEach(param -> f.addParam(new Register(resolveType(param.getType()), param.nameAsReg)));
        functions.put(f.name, f);
    }

    public IRType resolveType(Type tp) {
        if (TypeConst.Int.equals(tp)) return Cst.int32;
        else if (TypeConst.Bool.equals(tp)) return Cst.bool;
        else if (TypeConst.Void.equals(tp)) return Cst.void_t;
        else if (TypeConst.String.equals(tp)) {
            return Cst.str;
        } else if (tp instanceof ClassType) {
            return new PointerType(types.get(tp.id));
        } else if (tp.isArray()) {
            var type = resolveType(((ArrayObjectType) tp).elementType);
            if (type instanceof PointerType)
                return new PointerType(((PointerType) type).baseType, tp.dim() + 1);
            else
                return new PointerType(type, tp.dim());
        } else throw new IllegalStateException();
    }

    public StructureType resolveClass(ClassType cls) {
        return types.get(cls.id);
    }

    public IRFunction getFunction(String ft) {
        return functions.get(ft);
    }

    public StringConstant registerStrLiteral(String s) {
        String name;
        if (strLiterals.containsKey(s)) {
            return strLiterals.get(s);
        } else {
            name = Cst.STR_LITERAL + strCounter++;
            var ret = new StringConstant(name, s);
            strLiterals.put(s, ret);
            return ret;
        }
    }

    public String toLLVMir() {
        StringBuilder builder = new StringBuilder();
        strLiterals.forEach((k, v) -> {
            builder.append(v.toDefinition()).append('\n');
        });
        builder.append('\n');

        globalFunction.forEach(f -> builder.append(f.toDeclaration()));

        types.forEach((k, v) -> builder.append(v).append(" = ").append(v.isWhat()).append('\n'));
        builder.append('\n');

        globalVars.forEach((k, v) ->
                builder.append(v.tell()).append(" = global ").append(v.type).append(" ").append(v.initValue).append('\n')
        );
        builder.append('\n');

        functions.forEach((k, v) -> {
            if (!v.isBuiltin())
                builder.append(v.tell()).append('\n');
        });
        return builder.toString();
    }

    public IRFunction getInit() {
        return init;
    }

    public void setInit(IRFunction init) {
        assert this.init == null;
        this.init = init;
        functions.put(init.name, init);
    }

    public void forEachFunction(Consumer<IRFunction> f) {
        functions.forEach((k, v) -> {
            if (!v.isBuiltin())
                f.accept(v);
        });
    }

    public void forEachFunctionIncludingBuiltin(Consumer<IRFunction> f) {
        functions.forEach((k, v) -> f.accept(v));
    }

    public HashMap<String, StringConstant> getStringLiteral() {
        return strLiterals;
    }

    public HashMap<String, GlobalVar> getGlobalVars() {
        return globalVars;
    }

    public void renameMain() {
        forEachFunction(f -> {
            if (f.name.equals("main")) {
                f.name = "r.m";
            } else if (f.name.equals(Cst.INIT)) {
                f.name = "main";
            }
        });

    }

    public IRFunction getMain() {
        for (var entry : functions.entrySet()) {
            if (entry.getValue().name.equals("main")) return entry.getValue();
        }
        throw new IllegalStateException();
    }

}
