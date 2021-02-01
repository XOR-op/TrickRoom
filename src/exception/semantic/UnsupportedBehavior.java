package exception.semantic;

import ast.struct.ASTNode;

public class UnsupportedBehavior extends SemanticException {
    private String info;

    public UnsupportedBehavior(String s, ASTNode node) {
        super(node.coor);
        this.info = s;
    }

    @Override
    public String toString() {
        return "UnsupportedBehavior{" + coor.toString() +
                "info='" + info + '\'' +
                '}';
    }
}
