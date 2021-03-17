package optimization.ir;

import ir.IRFunction;
import ir.IRInfo;
import misc.pass.IRInfoPass;

import java.util.*;

public class GlobalInliner extends IRInfoPass {

    private final Map<IRFunction, Set<IRFunction>> callGraph = new HashMap<>();
    private final Map<IRFunction, Set<IRFunction>> backwards = new HashMap<>();
    private final Stack<IRFunction> tarjanStack = new Stack<>();
    private final Map<IRFunction, Integer> index = new HashMap<>();
    private final Map<IRFunction, Integer> lowLink = new HashMap<>();
    private int counter = 0;
    private final Set<IRFunction> ableToInline = new HashSet<>();

    public GlobalInliner(IRInfo info) {
        super(info);
    }

    @Override
    protected void run() {
        dfs();
    }

    private void dfs() {
        info.forEachFunction(this::dfs);
    }

    private void dfs(IRFunction func) {
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

        func.invokedFunctions.forEach(successor -> {
            next.add(successor);
            if (!callGraph.containsKey(successor)) {
                dfs(successor);
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
                ableToInline.add(func);
            } else {
                IRFunction one;
                do {
                    // strongly connected functions cannot be inlined
                    one = tarjanStack.pop();
                } while (one != func);
            }
        }
    }

    private void filter() {

    }
}
