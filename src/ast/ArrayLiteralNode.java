package ast;

import compnent.basic.Type;

import java.util.ArrayList;

public class ArrayLiteralNode extends ASTNode{
    public Type type;
    public int dimension;
    public ArrayList<Integer> dimArr;
}
