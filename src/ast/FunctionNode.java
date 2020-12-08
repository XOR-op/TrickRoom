package ast;

import compnent.basic.Identifier;
import compnent.basic.Type;
import compnent.scope.Scope;

import java.util.ArrayList;

public class FunctionNode extends ASTNode{
    public Identifier funcId;
    public ArrayList<Identifier> parameters;
}
