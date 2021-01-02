package compnent.basic;

import ast.ClassNode;

import java.util.HashMap;

public class ClassType {
    public String name;
    public HashMap<String,Function> memberFuncs;
    public HashMap<String,Symbol> memberVars;
    public ClassNode node;
    public ClassType(ClassNode cn){
        node=cn;
        memberFuncs=new HashMap<>();
        memberVars=new HashMap<>();
    }
}
