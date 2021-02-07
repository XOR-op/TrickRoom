package ast.type;

import ast.Symbol;
import ast.struct.ClassNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClassType extends Type{
    public HashMap<String, FunctionType> memberFuncs=new HashMap<>();
    public LinkedHashMap<String, Symbol> memberVars=new LinkedHashMap<>();
    public ArrayList<FunctionType> constructor=new ArrayList<>();
    public ClassNode node;
    private static final ClassNode BUILTIN_CLASS=new ClassNode("BUILTIN");
    public ClassType(ClassNode cn){
        super(null);
        node=cn;
    }
    private ClassType(int forstring){
        super("string");
        node=BUILTIN_CLASS;
    }
    protected static ClassType stringType(){
        return new ClassType(0);
    }

    @Override
    public Type copy() {
        ClassType ct=new ClassType(node);
        ct.id=id;
        ct.memberFuncs=memberFuncs;
        ct.memberVars=memberVars;
        ct.constructor=constructor;
        return ct;
    }
}
