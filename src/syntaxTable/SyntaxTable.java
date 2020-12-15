package syntaxTable;

import compnent.basic.Symbol;
import compnent.basic.Type;
import exception.semantic.UndeclaredSyntaxException;

import java.util.HashMap;

public class SyntaxTable {
    protected HashMap<String, Symbol> records;
    protected SyntaxTable parent;

    public SyntaxTable(SyntaxTable parent) {
        records = new HashMap<>();
        this.parent = parent;
    }

    public SyntaxTable() {
        this(null);
    }

    public void Put(String name, Symbol sym) {
        if (records.get(name) != null) throw new UndeclaredSyntaxException();
        records.put(name, sym);
    }

    public SyntaxTable getParent() {
        return parent;
    }

    public Type getType(String name) {
        Symbol t;
        if ((t = records.get(name)) != null) return t.getType();
        else if (parent != null) return parent.getType(name);
        else throw new UndeclaredSyntaxException();
    }
}
