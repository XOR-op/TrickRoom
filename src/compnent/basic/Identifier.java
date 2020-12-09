package compnent.basic;

import compnent.scope.Scope;

public class Identifier {
    public Type type;
    public String id;
    public Scope belonging;
    public Identifier(Type t,String id){
        this.id=id;
        this.type=t;
    }
    public Identifier(String name){
        this(null,name);
    }

}
