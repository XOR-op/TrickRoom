package compnent.basic;

import ast.FunctionNode;

import java.util.ArrayList;

public class Function {
    public String name;
    public ArrayList<Symbol> parameters;
    public FunctionNode node;
    public Function(FunctionNode fn){
        node=fn;
        parameters=new ArrayList<>();
    }
}
