package compnent.basic;

import compnent.scope.Scope;

public class Identifier {
    public Type type;
    public String id;
    public Scope belonging;
    public Identifier(String id,Scope belonging){
        this.id=id;
        this.belonging=belonging;
    }
}
