package optimization.ir;

import ir.IRFunction;
import ir.IRInfo;
import ir.instruction.Call;
import misc.analysis.FuncCallAnalyzer;
import misc.pass.IRInfoPass;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalInliner extends IRInfoPass {

    private final Set<IRFunction> dfsInlineVisited = new HashSet<>();
    private final FuncCallAnalyzer funcCallAnalyzer;

    public GlobalInliner(IRInfo info) {
        super(info);
        funcCallAnalyzer = new FuncCallAnalyzer(info);
    }

    public static boolean inlinePolicy(IRFunction f) {
        var instSum = f.blocks.stream().mapToInt(b -> b.insts.size()).sum();
        return !(f.blocks.size() > 50 || instSum > 500);
    }

    public static int recurUnfoldPolicy(IRFunction f) {
        int recursionCall = 0;
        for (var b : f.blocks) {
            for (var inst : b.insts) if (inst instanceof Call && ((Call) inst).function == f) recursionCall++;
        }
        return (recursionCall <= 4 && f.originDuplication <= 8 && f.blocks.stream().mapToInt(b -> b.insts.size()).sum() < 400) ? recursionCall : -1;
    }

    @Override
    protected void run() {
        funcCallAnalyzer.collectAndInline();
        inlineFilter();
        funcCallAnalyzer.ableToInline.addAll(funcCallAnalyzer.selfRecursion);
        dfsInlineVisited.addAll(funcCallAnalyzer.selfRecursion);
        dfsInline();
        info.forEachFunction(f -> {
            if (!funcCallAnalyzer.ableToInline.contains(f))
                new Inliner(f, funcCallAnalyzer.ableToInline).invoke();
        });
    }

    private void inlineFilter() {
        funcCallAnalyzer.ableToInline.removeIf(f -> !inlinePolicy(f));
    }

    private void dfsInline() {
        funcCallAnalyzer.ableToInline.forEach(this::dfsInline);
    }

    private void dfsInline(IRFunction func) {
        if (!dfsInlineVisited.contains(func)) {
            dfsInlineVisited.add(func);
            boolean flag = false;
            for (IRFunction f : funcCallAnalyzer.callGraph.get(func)) {
                if (funcCallAnalyzer.ableToInline.contains(f)) {
                    flag = true;
                    dfsInline(f);
                }
            }
            // if func contains inlined-able func
            if (flag)
                new Inliner(func, funcCallAnalyzer.ableToInline).invoke();
        }
    }


}
