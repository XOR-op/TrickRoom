package optimization.ir;

import ir.IRFunction;
import ir.IRInfo;
import misc.pass.IRInfoPass;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalInliner extends IRInfoPass {

    private final Map<IRFunction, Set<IRFunction>> callGraph = new HashMap<>();
    private final Map<IRFunction, Set<IRFunction>> backwards = new HashMap<>();
    private final Stack<IRFunction> tarjanStack = new Stack<>();
    private final Map<IRFunction, Integer> index = new HashMap<>();
    private final Map<IRFunction, Integer> lowLink = new HashMap<>();
    private int counter = 0;
    private final Set<IRFunction> ableToInline = new HashSet<>();
    private final Set<IRFunction> dfsInlineVisited = new HashSet<>();

    public GlobalInliner(IRInfo info) {
        super(info);
    }

    public static boolean inlinePolicy(IRFunction f){
        var instSum = f.blocks.stream().mapToInt(b -> b.insts.size()).sum();
        return !(f.blocks.size() > 10 || instSum > 50);
    }

    @Override
    protected void run() {
        collectDependency();
        inlineFilter();
        dfsInline();
        info.forEachFunction(f -> {
            if (!ableToInline.contains(f))
                new Inliner(f, ableToInline).invoke();
        });
    }

    private void dfsInline() {
        ableToInline.forEach(this::dfsInline);
    }

    private void inlineFilter() {
        ableToInline.removeIf(f->!inlinePolicy(f));
    }

    private void dfsInline(IRFunction func) {
        if (!dfsInlineVisited.contains(func)) {
            dfsInlineVisited.add(func);
            AtomicBoolean flag = new AtomicBoolean(false);
            callGraph.get(func).forEach(f -> {
                if (ableToInline.contains(f)) {
                    flag.set(true);
                    dfsInline(func);
                }
            });
            // if func contains inlined func
            if (flag.get())
                new Inliner(func, ableToInline).invoke();
        }
    }

    private void collectDependency() {
        info.forEachFunction(this::collectDependency);
    }

    private void collectDependency(IRFunction func) {
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
                collectDependency(successor);
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
