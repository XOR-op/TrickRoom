package ast;

import compnent.basic.Type;

import java.util.ArrayList;

public class ArrayLiteralNode extends ASTNode{
    public Type type;
    public int dimension;
    public ArrayList<ExprNode> dimArr;
    public ArrayLiteralNode(Type t,int dim){
        type=t;
        dimension=dim;
        dimArr=new ArrayList<>();
    }
}
