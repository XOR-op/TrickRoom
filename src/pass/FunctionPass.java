package pass;

public abstract class FunctionPass {
    public abstract void run();

    public ir.Function irFunc;

    public FunctionPass(ir.Function f) {
        irFunc = f;
    }
}
