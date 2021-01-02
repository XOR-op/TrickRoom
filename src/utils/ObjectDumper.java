package utils;

import ast.ASTNode;
import ast.DeclarationNode;
import ast.RootNode;
import parser.MxStarParser;

import java.lang.reflect.*;
import java.util.ArrayList;

public class ObjectDumper {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static final String pre = "|   ";

    private static void println(String s){
        System.out.println(s);
    }
    private static void print(String s){
        System.out.print(s);
    }
    private static void DFS(Object obj, String prefix) {
        if (obj == null) return;
        try {
            if (obj instanceof RootNode) {
                System.out.println(ANSI_PURPLE + "ROOT" + ANSI_RESET);
                Field[] fields = obj.getClass().getDeclaredFields();
                for(Field f:fields){
                    if(f.getName().equals("nodeList")){
                        @SuppressWarnings("unchecked") var nl=(ArrayList<ASTNode>)f.get(obj);
                        for (var o : nl) {
                            println(ANSI_GREEN+o.getClass().getName()+ANSI_RESET);
                            DFS(o, prefix.concat(pre));
                        }
                    }
                }
            } else {
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field f : fields) {
                    if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
                        StringBuilder buffer = new StringBuilder();
                        f.setAccessible(true);
                        Object value = f.get(obj);
                        //  colorful
                        if (value instanceof ASTNode) buffer.append(ANSI_GREEN);
                        else if (value instanceof compnent.basic.Type) buffer.append(ANSI_YELLOW);
                        else if (value instanceof ArrayList) buffer.append(ANSI_BLUE);
                        buffer.append(prefix);
                        buffer.append(f.getType().getSimpleName());
                        buffer.append(" ");
                        buffer.append(f.getName());
                        buffer.append("=");
                        if (value instanceof String) buffer.append(ANSI_RED);
                        buffer.append(value);
                        buffer.append(ANSI_RESET);
                        println(buffer.toString());
                        if (value instanceof ArrayList) {
                            @SuppressWarnings("unchecked") var ls = (ArrayList<Object>) value;
                            for (var o : ls) {
                                DFS(o, prefix.concat(pre));
                            }
                        } else if (!Modifier.isNative(f.getModifiers()) && f.getType() != String.class && f.getType() != Integer.class && f.getType() != Boolean.class) {
                            DFS(value, prefix.concat(pre));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dump(Object obj) {
        try {
            DFS(obj, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
