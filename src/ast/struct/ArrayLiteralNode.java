package ast.struct;

import ast.ASTVisitor;
import ast.type.Type;

import java.util.ArrayList;

public class ArrayLiteralNode extends ExprNode {
    public Type type;
    public ArrayList<ExprNode> dimArr;
    public ArrayLiteralNode(Type t){
        type=t;
        dimArr=new ArrayList<>();
    }
    @Override
    public Object accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
