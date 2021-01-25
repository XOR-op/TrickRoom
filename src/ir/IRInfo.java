package ir;

import compnent.basic.ClassType;
import compnent.basic.FunctionType;
import compnent.basic.Type;
import compnent.basic.TypeConst;
import compnent.scope.FileScope;
import exception.UnimplementedError;
import ir.operand.IntConstant;
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
    public void scopeScan(FileScope scope){
        scope.classTable.forEach((s, classType) -> {
            assert !s.equals("string");
            types.put(s,new StructureType(s));
        });
        scope.classTable.forEach((s,cls)->addClass(cls));
        scope.functionTable.forEach((f,func)->addFunction(func));
    }
    public void addClass(ClassType cls){
        cls.memberFuncs.forEach((name,func)->{
            var f=new Function("$"+cls.id+"."+name,resolveType(func.returnType));
            f.addParam(new Register(resolveType(cls),"this"));
            func.parameters.forEach(param->f.addParam(new Register(resolveType(param.getType()),param.getName())));
        });
    }
    public void addFunction(FunctionType func){
        var f=new Function(func.id,resolveType(func.returnType));
        func.parameters.forEach(param->f.addParam(new Register(resolveType(param.getType()),param.getName())));
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
}
