package ast.scope;

import java.util.HashMap;

public class FunctionScope extends Scope{
    public FunctionScope(Scope up){super(up);}

    private final HashMap<String,Integer> recording=new HashMap<>();


    @Override
    public String getSuffix(String s) {
        Integer i=recording.get(s);
        if(i==null){
            recording.put(s,1);
            return "";
        }
        else {
            recording.replace(s,i+1);
            return Integer.toString(i);
        }
    }
}
