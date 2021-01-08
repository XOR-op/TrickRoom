package compnent.basic;

import ast.ClassNode;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassType extends Type{
    public HashMap<String,Function> memberFuncs;
    public HashMap<String,Symbol> memberVars;
    public ArrayList<Function> constructor;
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
        super("string",0);
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
        ct.dimension=dimension;
        ct.memberFuncs=memberFuncs;
        ct.memberVars=memberVars;
        ct.constructor=constructor;
        return ct;
    }
}
