package compnent.basic;

public class TypeConst {
    public static final Type Int = new Type("int", 0, true);
    public static final Type Bool = new Type("bool", 0, true);
    public static final Type Null = new Type("null", 0, true);
    public static final Type Void = new Type("void", 0, true);
    public static final Type String = ClassType.stringType();
}
