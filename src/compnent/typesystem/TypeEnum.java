package compnent.typesystem;

public class TypeConst {
    public static final Type Int = new Type("int");
    public static final Type Bool = new Type("bool");
    public static final Type Null = new Type("null");
    public static final Type Void = new Type("void");
    public static final Type String = ClassType.stringType();
}
