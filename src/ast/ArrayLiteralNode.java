package ast;

import compnent.basic.Type;
import semantic.ASTVisitor;

import java.util.ArrayList;

public class ArrayLiteralNode implements ExprNode {
    public Type type;
    public int dimension;
    public ArrayList<ExprNode> dimArr;
    public ArrayLiteralNode(Type t,int dim){
        type=t;
        dimension=dim;
        dimArr=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
