package utils;

import java.lang.reflect.*;
import java.util.ArrayList;

public class ObjectDumper {
    private static final String pre="|   ";
    private static void DFS(Object obj,String prefix){
        if(obj==null)return;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (!Modifier.isStatic(f.getModifiers())&&!Modifier.isFinal(f.getModifiers())) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(prefix);
                    f.setAccessible(true);
                    Object value = f.get(obj);
                    buffer.append(f.getType().getSimpleName());
                    buffer.append(" ");
                    buffer.append(f.getName());
                    buffer.append("=");
                    buffer.append(value);
                    System.out.println(buffer.toString());
                    if(value instanceof ArrayList){
                        @SuppressWarnings("unchecked") var ls=(ArrayList<Object>)value;
                        for(var o:ls){
                            DFS(o,prefix.concat(pre));
                        }
                    }else if(!Modifier.isNative(f.getModifiers())
//                            &&f.getType()!=Type.class&&f.getType()!=Identifier.class
                            &&f.getType()!=String.class&&f.getType()!=Integer.class
                    &&f.getType()!=Boolean.class){
                        DFS(value,prefix.concat(pre));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void dump(Object obj)  {
        try {
            DFS(obj,"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
