package compnent.basic;

import ast.FunctionNode;

import java.util.ArrayList;

public class FunctionType extends Type{
    public Type returnType;
    public ArrayList<Symbol> parameters;
    public FunctionNode node;
    public static final FunctionNode BUILTIN_FUNCTION=new FunctionNode("");
    public static FunctionType arraySize=getArraySize();
    private static FunctionType parse(Type returnType, String identifier, ArrayList<Symbol> paraTypes){
        FunctionType f=new FunctionType(BUILTIN_FUNCTION);
        f.id=identifier;
        f.returnType=returnType;
        f.parameters=paraTypes;
        return f;
    }
    private static FunctionType getArraySize(){
        FunctionType f=new FunctionType(BUILTIN_FUNCTION);
        f.id="size";
        f.returnType=TypeConst.Int;
        f.parameters=new ArrayList<>();
        return f;
    }
    private static Type newType(String s){
        Type t;
        switch (s){
            case "string"->t=TypeConst.String;
            case "int"->t=TypeConst.Int;
            case "bool"->t=TypeConst.Bool;
            case "void"->t=TypeConst.Void;
            default -> t=new Type(s);
        }
        return t;
    }
    public static FunctionType parse(String s){
        var slice=s.split("[ ,]+|\\(|\\);");
        ArrayList<Symbol> ls=new ArrayList<>();
        for(int i=2;i<slice.length;i+=2){
            assert !slice[i].equals("");
            assert i+1<slice.length;
            ls.add(new Symbol(newType(slice[i]),slice[i+1]));
        }
        return parse(newType(slice[0]),slice[1],ls);
    }
    public FunctionType(FunctionNode fn){
        super(null);
        node=fn;
        returnType=fn.returnType;
        parameters=new ArrayList<>();
    }

    @Override
    public Type copy() {
        FunctionType fn=new FunctionType(node);
        fn.returnType=returnType;
        fn.parameters=parameters;
        fn.id=id;
        return fn;
    }
}
