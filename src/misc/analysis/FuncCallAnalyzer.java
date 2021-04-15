package misc.analysis;

import ir.IRFunction;
import ir.IRInfo;
import misc.pass.IRInfoPass;
import optimization.ir.GlobalInliner;
import optimization.ir.Inliner;

import java.util.*;

public class FuncCallAnalyzer extends IRInfoPass {
    public final Map<IRFunction, Set<IRFunction>> callGraph = new HashMap<>();
    private final Map<IRFunction, Set<IRFunction>> backwards = new HashMap<>();
    private final Stack<IRFunction> tarjanStack = new Stack<>();
    private final Map<IRFunction, Integer> index = new HashMap<>();
    private final Map<IRFunction, Integer> lowLink = new HashMap<>();
    private int counter = 0;
    public final Set<IRFunction> ableToInline = new HashSet<>();
    public final Set<IRFunction> selfRecursion = new HashSet<>();

    public FuncCallAnalyzer(IRInfo info) {
        super(info);
    }

    @Override
    protected void run() {
        info.forEachFunction(f -> collectDependency(f, false));
    }

    public void collectAndInline() {
        info.forEachFunction(f -> collectDependency(f, true));
    }

    private void collectDependency(IRFunction func, boolean doInline) {
        if (callGraph.containsKey(func)) return;
        if (func.isBuiltin()) {
            callGraph.put(func, new HashSet<>());
            backwards.put(func, new HashSet<>());
            return;
        }
        var next = new HashSet<IRFunction>();
        callGraph.put(func, next);
        backwards.put(func, new HashSet<>());
        tarjanStack.push(func);
        index.put(func, counter);
        lowLink.put(func, counter++);

        func.invokedFunctions.forEach((successor, count) -> {
            next.add(successor);
            if (!callGraph.containsKey(successor)) {
                collectDependency(successor,doInline);
                if (!successor.isBuiltin())
                    lowLink.put(func, Math.min(lowLink.get(successor), lowLink.get(func)));
            } else if (!successor.isBuiltin() && tarjanStack.contains(successor)) {
                lowLink.put(func, Math.min(lowLink.get(func), index.get(successor)));
            }
            backwards.get(successor).add(func);
        });
        if (lowLink.get(func).equals(index.get(func))) {
            // root
            if (tarjanStack.peek() == func) {
                tarjanStack.pop();
                if (!func.invokedFunctions.containsKey(func))
                    ableToInline.add(func);
                else if (doInline) {
                    int callNumber = GlobalInliner.recurUnfoldPolicy(func);
                    if (callNumber > 0) {
                        for (; callNumber > 0; callNumber = GlobalInliner.recurUnfoldPolicy(func)) {
                            new Inliner(func, ableToInline, callNumber).invoke();
                            func.originDuplication *= 1 << callNumber;
                        }
                        selfRecursion.add(func);
                    }
                }
            } else {
                IRFunction one;
                do {
                    // strongly connected functions cannot be inlined
                    one = tarjanStack.pop();
                } while (one != func);
            }
        }
    }

}
