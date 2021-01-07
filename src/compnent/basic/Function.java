package compnent.basic;

import ast.FunctionNode;

import java.util.ArrayList;

public class Function extends Type{
    public Type returnType;
    public ArrayList<Symbol> parameters;
    public FunctionNode node;
    public static final FunctionNode BUILTIN_FUNCTION=new FunctionNode("");
    private static Function parse(Type returnType, String identifier, ArrayList<Symbol> paraTypes){
        Function f=new Function(BUILTIN_FUNCTION);
        f.id=identifier;
        f.returnType=returnType;
        f.parameters=paraTypes;
        return f;
    }
    public static Function parse(String s){
        var slice=s.split(" |\\(|\\);");
        ArrayList<Symbol> ls=new ArrayList<>();
        for(int i=2;i<slice.length;i+=2){
            assert !slice[i].equals("");
            assert i+1<slice.length;
            ls.add(new Symbol(new Type(slice[i]),slice[i+1]));
        }
        return parse(new Type(slice[0]),slice[1],ls);
    }
    public Function(FunctionNode fn){
        super(null);
        node=fn;
        parameters=new ArrayList<>();
    }
}
