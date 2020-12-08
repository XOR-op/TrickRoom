package compnent.scope;

import compnent.basic.Identifier;

import java.util.HashSet;

public class BasicScope extends Scope{
    HashSet<Identifier> map;
    Scope parent;
    @Override
    public boolean contains(Identifier id) {
        return map.contains(id);
    }

    @Override
    public Scope getParent() {
        return parent;
    }
}
