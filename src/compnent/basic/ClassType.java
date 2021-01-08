package compnent.basic;

import ast.ClassNode;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassType extends Type{
    public HashMap<String,Function> memberFuncs;
    public HashMap<String,Symbol> memberVars;
    public ArrayList<Function> constructor;
    public ClassNode node;
    public ClassType(ClassNode cn){
        super(null);
        node=cn;
        memberFuncs=new HashMap<>();
        memberVars=new HashMap<>();
        constructor=new ArrayList<>();
    }
    private ClassType(int forstring){
        super("string",0,true);
    }
    protected static ClassType stringType(){
        return new ClassType(0);
    }
}
