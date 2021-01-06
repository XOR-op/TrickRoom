package ast;

import compnent.basic.Type;
import semantic.ASTVisitor;

import java.util.ArrayList;

public class ArrayLiteralNode extends ExprNode {
    public Type type;
    public int dimension;
    public ArrayList<ExprNode> dimArr;
    public ArrayLiteralNode(Type t){
        type=t;
        dimArr=new ArrayList<>();
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
