package compnent.scope;

import compnent.basic.Identifier;

public abstract class Scope {
    abstract public boolean contains(Identifier id);
    abstract public Scope getParent();
    public Identifier attach(String name){
        return new Identifier(name,this);
    }
    public Scope(){

    }
}
