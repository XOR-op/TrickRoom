package assembly.instruction;

public abstract class RVInst {
    public enum RelaType{
        eq,ne,lt,le,gt,ge
    }

    public enum WidthType{
        b,h,w
    }
    public abstract String tell();
}
