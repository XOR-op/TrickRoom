package ir;

import compnent.basic.ClassType;
import compnent.basic.FunctionType;
import compnent.basic.Type;
import compnent.basic.TypeConst;
import compnent.scope.FileScope;
import exception.UnimplementedError;
import ir.operand.Register;
import ir.operand.StringConstant;
import ir.typesystem.IRType;
import ir.typesystem.PointerType;
import ir.typesystem.StructureType;
import ir.typesystem.TypeEnum;

import java.util.HashMap;

public class IRInfo {
    private HashMap<String, Function> functions=new HashMap<>();
    private HashMap<String, StructureType> types=new HashMap<>();
    private HashMap<String, StringConstant> strLiterals=new HashMap<>();
    private HashMap<String,String> stringMethods=new HashMap<>();
    private int strCounter=0;
    public IRInfo(FileScope scope){
        // no array.size() here
        // global functions
        addBuiltinFunction(TypeEnum.void_t,"print").addParam(TypeEnum.str,"str");
        addBuiltinFunction(TypeEnum.void_t,"println").addParam(TypeEnum.str,"str");
        addBuiltinFunction(TypeEnum.void_t,"printInt").addParam(TypeEnum.int32,"n");
        addBuiltinFunction(TypeEnum.void_t,"printlnInt").addParam(TypeEnum.int32,"n");
        addBuiltinFunction(TypeEnum.str,"getString");
        addBuiltinFunction(TypeEnum.int32,"getInt");
        addBuiltinFunction(TypeEnum.str,"toString").addParam(TypeEnum.int32,"i");
        // string methods
        addStringMethod(TypeEnum.int32,"length");
        addStringMethod(TypeEnum.int32,"parseInt");
        addStringMethod(TypeEnum.int32,"ord").addParam(TypeEnum.int32,"pos");
        addStringMethod(TypeEnum.str,"substring").addParam(TypeEnum.int32,"left").addParam(TypeEnum.int32,"right");
        // operator overloading
        addStringMethod(TypeEnum.str,"concat").addParam(TypeEnum.str,"rhs");
        addStrCmp("eq");
        addStrCmp("neq");
        addStrCmp("lt");
        addStrCmp("le");
        addStrCmp("gt");
        addStrCmp("ge");
        // array size
        // todo
        scopeScan(scope);
    }
    private void addStrCmp(String name){
        addStringMethod(TypeEnum.bool,name).addParam(TypeEnum.str,"rhs");
    }
    private Function addStringMethod(IRType ret,String name){
        var f=new Function(name,ret);
        f.addParam(TypeEnum.str,"lhs");
        functions.put(".str$"+name,f);
        stringMethods.put(name,".str$"+name);
        return f;
    }
    private Function addBuiltinFunction(IRType ret, String name){
        var f=new Function(name,ret);
        functions.put(name,f);
        return f;
    }
    private String classMethodInterpretation(ClassType cls,FunctionType fty){
        // translation
        return "$"+cls.id+"."+fty.id;
    }
    public Function getStringMethod(String name){
        return functions.get(stringMethods.get(name));
    }
    public Function getClassMethod(ClassType cls,FunctionType fty){
        return functions.get(classMethodInterpretation(cls,fty));
    }

    public void scopeScan(FileScope scope){
        // scan name only
        scope.classTable.forEach((s, classType) -> {
            assert !s.equals("string");
            types.put(s,new StructureType(s));
        });
        scope.classTable.forEach((s,cls)->addClass(cls));
        scope.functionTable.forEach((f,func)->addFunction(func));
    }
    public void addClass(ClassType cls){
        cls.memberFuncs.forEach((name,func)->{
            var f=new Function(classMethodInterpretation(cls,func),resolveType(func.returnType));
            f.addParam(new Register(resolveType(cls),"this"));
            func.parameters.forEach(param->f.addParam(new Register(resolveType(param.getType()),param.getName())));
            functions.put(f.name,f);
        });
    }
    public void addFunction(FunctionType func){
        var f=new Function(func.id,resolveType(func.returnType));
        func.parameters.forEach(param->f.addParam(new Register(resolveType(param.getType()),param.getName())));
        functions.put(f.name,f);
    }
    public IRType resolveType(Type tp){
        if(TypeConst.Int.equals(tp))return TypeEnum.int32;
        else if(TypeConst.Bool.equals(tp))return TypeEnum.bool;
        else if(TypeConst.Void.equals(tp))return TypeEnum.void_t;
        else if(TypeConst.Null.equals(tp)){
            return PointerType.nullptr();
        }else if(tp instanceof ClassType){
            return new PointerType(types.get(tp.id));
        }else throw new UnimplementedError();
    }
    public Function getFunction(FunctionType ft){
        return functions.get(ft);
    }
    public Function getArraySize(){
        // todo
    }
    public String registerStrLiteral(String s){
        if(strLiterals.containsKey(s)){
            return strLiterals.get(s).name;
        }else {
            var name=".str."+strCounter++;
            strLiterals.put(name,new StringConstant(name,s));
            return name;
        }
    }
}
