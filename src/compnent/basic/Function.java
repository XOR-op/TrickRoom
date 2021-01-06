package compnent.basic;

import ast.FunctionNode;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;

public class Function {
    public Type returnType;
    public String name;
    public ArrayList<Symbol> parameters;
    public FunctionNode node;
    public static final FunctionNode BUILTIN_FUNCTION=new FunctionNode("");
    private static Function parse(Type returnType, String identifier, ArrayList<Symbol> paraTypes){
        Function f=new Function(BUILTIN_FUNCTION);
        f.name=identifier;
        f.returnType=returnType;
        f.parameters=paraTypes;
        return f;
    }
    public static Function parse(String s){
        var slice=s.split(" |\\(|\\);");
        ArrayList<Symbol> ls=new ArrayList<>();
        for(int i=2;i<slice.length;++i){
            assert !slice[i].equals("");
            ls.add(new Symbol(new Type(slice[i]),slice[i+1]));
        }
        return parse(new Type(slice[0]),slice[1],ls);
    }
    public Function(FunctionNode fn){
        node=fn;
        parameters=new ArrayList<>();
    }
}
