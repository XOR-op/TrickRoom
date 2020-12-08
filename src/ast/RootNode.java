package ast;

import compnent.scope.Scope;

class RootNode extends ASTNode {

    @Override
    Scope getBelonging() {
        return null;
    }
}
