package ir;

import ast.type.*;
import ast.scope.FileScope;
import ir.operand.GlobalVar;
import ir.operand.Parameter;
import ir.operand.Register;
import ir.operand.StringConstant;
import ir.typesystem.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.StringJoiner;

public class IRInfo {
    private final HashMap<String, Function> functions = new HashMap<>();
    private final HashMap<String, StructureType> types = new HashMap<>();
    private final HashMap<String, StringConstant> strLiterals = new HashMap<>();
    private final HashMap<String, String> stringMethods = new HashMap<>();
    private final HashMap<String, GlobalVar> globalVars = new HashMap<>();
    private final HashSet<Function> globalFunction=new HashSet<>();
    private int strCounter = 0;

    public IRInfo(FileScope scope) {
        // global functions
        addBuiltinFunction(Cst.void_t, "print").addParam(Cst.str, "str");
        addBuiltinFunction(Cst.void_t, "println").addParam(Cst.str, "str");
        addBuiltinFunction(Cst.void_t, "printInt").addParam(Cst.int32, "n");
        addBuiltinFunction(Cst.void_t, "printlnInt").addParam(Cst.int32, "n");
        addBuiltinFunction(Cst.str, "getString");
        addBuiltinFunction(Cst.int32, "getInt");
        addBuiltinFunction(Cst.str, "toString").addParam(Cst.int32, "i");
        addBuiltinFunction(new PointerType(Cst.byte_t),Cst.MALLOC).addParam(Cst.int32,"len");
        // string methods
        addStringMethod(Cst.int32, "length");
        addStringMethod(Cst.int32, "parseInt");
        addStringMethod(Cst.int32, "ord").addParam(Cst.int32, "pos");
        addStringMethod(Cst.str, "substring").addParam(Cst.int32, "left").addParam(Cst.int32, "right");
        // operator overloading
        addStringMethod(Cst.str, "concat").addParam(Cst.str, "rhs");
        addStrCmp("eq");
        addStrCmp("neq");
        addStrCmp("lt");
        addStrCmp("le");
        addStrCmp("gt");
        addStrCmp("ge");
        // array size
        addBuiltinFunction(Cst.int32, Cst.ARRAY_SIZE).addParam(PointerType.baseArrayType(), "arr");

        scopeScan(scope);
    }

    private void addStrCmp(String name) {
        addStringMethod(Cst.bool, name).addParam(Cst.str, "rhs");
    }

    private Function addStringMethod(IRType ret, String name) {
        var f = new Function(name, ret, true);
        f.addParam(Cst.str, "lhs");
        functions.put(Cst.STR_FUNC + name, f);
        stringMethods.put(name, Cst.STR_FUNC + name);
        globalFunction.add(f);
        return f;
    }

    private Function addBuiltinFunction(IRType ret, String name) {
        var f = new Function(name, ret, true);
        functions.put(name, f);
        globalFunction.add(f);
        return f;
    }

    private String classMethodInterpretation(String className, String functionName) {
        // translation
        return Cst.STRUCT + className + "." + functionName;
    }

    public Function getStringMethod(String name) {
        return functions.get(stringMethods.get(name));
    }

    public Function getClassMethod(String cls, String func) {
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
        scope.globalVarTable.forEach((s,v)->{
            //todo
//            var glo= v.initExpr==null?
//                    new GlobalVar(resolveType(v.getType()),v.nameAsReg):
//                    new GlobalVar(resolveType(v.getType()),v.nameAsReg,ConstantEvaluator.evaluate(v.initExpr));
            var glo=new GlobalVar(resolveType(v.getType()),v.nameAsReg);
            globalVars.put(s,glo);
        });
    }


    private Function addMethod(ClassType cls, FunctionType func) {
        var f = new Function(classMethodInterpretation(cls.id, func.id), resolveType(func.returnType));
        f.addParam(new Register(resolveType(cls), "this"));
        func.parameters.forEach(param -> f.addParam(new Register(resolveType(param.getType()), param.getName())));
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
        var f = new Function(func.id, resolveType(func.returnType));
        func.parameters.forEach(param -> f.addParam(new Register(resolveType(param.getType()), param.getName())));
        functions.put(f.name, f);
    }

    public IRType resolveType(Type tp) {
        if (TypeConst.Int.equals(tp)) return Cst.int32;
        else if (TypeConst.Bool.equals(tp)) return Cst.bool;
        else if (TypeConst.Void.equals(tp)) return Cst.void_t;
        else if (TypeConst.Null.equals(tp)) {
            return PointerType.nullptr();
        } else if (tp instanceof ClassType) {
            return new PointerType(types.get(tp.id));
        } else if (tp.isArray()) {
            var struct = new StructureType(Cst.ARRAY_TYPE + tp.id);
            struct.addMember(new Register(Cst.int32, "size"));
            struct.addMember(new Register(resolveType(((ArrayObjectType) tp).elementType), "element"));
            return new PointerType(struct);
        } else throw new IllegalStateException();
    }

    public StructureType resolveClass(ClassType cls){
        return types.get(cls.id);
    }

    public Function getFunction(String ft) {
        return functions.get(ft);
    }

    public Function getArraySize() {
        return getFunction(Cst.ARRAY_SIZE);
    }

    public String registerStrLiteral(String s) {
        if (strLiterals.containsKey(s)) {
            return strLiterals.get(s).name;
        } else {
            var name = Cst.STR_LITERAL + strCounter++;
            strLiterals.put(name, new StringConstant(name, s));
            return name;
        }
    }

    public String toLLVMir() {
        StringBuilder builder = new StringBuilder();
        strLiterals.forEach((k, v) -> builder.append(v.tell()).append('\n'));
        builder.append('\n');

        globalFunction.forEach(f->builder.append(f.toDeclaration()));

        types.forEach((k, v) -> builder.append(v).append(" = ").append(v.isWhat()).append('\n'));
        builder.append('\n');

        globalVars.forEach((k,v)->
                builder.append(v.tell()).append(" = global ").append(v.type).append(" ").append(v.initValue).append('\n')
        );
        builder.append('\n');

        functions.forEach((k, v) -> {
            if (!v.isBuiltin())
                builder.append(v.tell()).append('\n');
        });
        return builder.toString();
    }
}
