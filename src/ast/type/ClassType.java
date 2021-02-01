package ast.type;

import ast.Symbol;
import ast.struct.ClassNode;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassType extends Type{
    public HashMap<String, FunctionType> memberFuncs;
    public HashMap<String, Symbol> memberVars;
    public ArrayList<FunctionType> constructor;
    public ClassNode node;
    private static final ClassNode BUILTIN_CLASS=new ClassNode("BUILTIN");
    public ClassType(ClassNode cn){
        super(null);
        node=cn;
        memberFuncs=new HashMap<>();
        memberVars=new HashMap<>();
        constructor=new ArrayList<>();
    }
    private ClassType(int forstring){
        super("string");
        node=BUILTIN_CLASS;
        memberFuncs=new HashMap<>();
        memberVars=new HashMap<>();
        constructor=new ArrayList<>();
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
