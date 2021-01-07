package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;
import exception.semantic.WrongControlFlowStatementException;
import exception.semantic.WrongReturnException;
import utils.L;

import java.util.Stack;

/*
 * build scope
 *
 */
public class ScopeBuilder implements ASTVisitor {
    private RootNode root;
    private Scope currentScope;
    private FunctionNode currentFunction;
    private Stack<LoopNode> loopNodeStack;

    private void pushScope(Scope scope) {
        assert scope.getUpstream()==null||scope.getUpstream()==currentScope;
        L.i(scope.toString());
        currentScope = scope;
    }

    private void pushScope(ASTNode node) {
        pushScope(node.scope);
    }

    private void popScope() {
        L.i(currentScope.toString());
        currentScope = currentScope.getUpstream();
    }

    private void globalRegisterFunc(String s){
        ((FileScope)currentScope).registerFunction(Function.parse(s));
    }

    public ScopeBuilder(RootNode tree) {
        root = tree;
        loopNodeStack=new Stack<>();
    }

    public Scope build() {
        // global file scope
        var top = new FileScope();
        currentScope = top;
        // register builtin functions
        globalRegisterFunc("void print(string str);");
        globalRegisterFunc("void println(string str);");
        globalRegisterFunc("void printInt(int n);");
        globalRegisterFunc("void printlnInt(int n);");
        globalRegisterFunc("string getString();");
        globalRegisterFunc("int getInt();");
        globalRegisterFunc("string toString(int i);");
        // scan class and function definition to support forwarding reference
        for (var fun : root.functions) {
            top.registerFunction(scanFunction(fun));
        }
        for (var cls : root.classes) {
            top.registerClass(scanClass(cls));
        }
        // sequentially visit variable declarations and functions
        for (var node : root.nodeList) {
            node.accept(this);
        }
        return top;
    }

    private Symbol visitDecl(DeclarationNode node) {
        return new Symbol(node.type, node.id);
    }

    private Function scanFunction(FunctionNode node) {
        // will check parameters
        node.scope = new FunctionScope(currentScope);
        var func = new Function(node);
        func.id = node.funcId;
        func.returnType=func.node.returnType;
        for (var para : node.parameters) {
            var re = visitDecl(para);
            node.scope.registerVar(re);
            func.parameters.add(re);
        }
        return func;
    }

    private ClassType scanClass(ClassNode node) {
        // will check names
        var cls = new ClassType(node);
        var scp = new ClassScope(currentScope, cls);
        node.scope = scp;
        pushScope(node);
        cls.id = node.className;
        for (var method : node.methods) {
            var func = scanFunction(method);
            scp.registerMethod(func);
            cls.memberFuncs.put(func.id, func);
        }
        for (var member : node.members) {
            scp.registerVar(visitDecl(member));
        }
        // todo constructor
        popScope();
        return cls;
    }

    @Override
    public void visit(DeclarationBlockNode node) {
        for(var sub:node.decls)
            visit(sub);
    }

    @Override
    public void visit(DeclarationNode node) {
        currentScope.registerVar(visitDecl(node));
    }

    @Override
    public void visit(ClassNode node) {
        pushScope(node);
        for (var con : node.constructor)
            visit(con);
        for (var method : node.methods)
            visit(method);
        popScope();
    }

    @Override
    public void visit(FunctionNode node) {
        pushScope(node);
        currentFunction=node;
        visit(node.suite);
        currentFunction=null;
        popScope();
    }

    @Override
    public void visit(SuiteNode node) {
        // node.scope will be built ahead
        // suite will have corresponding scope except those belonging to function
        for (var sub : node.statements) {
            if (sub instanceof SuiteNode) {
                sub.scope = new Scope(currentScope);
                pushScope(sub);
                sub.accept(this);
                popScope();
            } else
                sub.accept(this);
        }
    }

    @Override
    public void visit(ConditionalNode node) {
        node.trueStat.scope = new Scope(currentScope);
        pushScope(node.trueStat);
        node.trueStat.accept(this);
        popScope();
        if (node.falseStat != null) {
            node.falseStat.scope = new Scope(currentScope);
            pushScope(node.falseStat);
            node.falseStat.accept(this);
            popScope();
        }
    }

    @Override
    public void visit(LoopNode node) {
        node.scope = new LoopScope(currentScope);
        pushScope(node);
        loopNodeStack.push(node);
        if (node.initDecl != null) visit(node.initDecl);
        node.loopBody.accept(this);
        loopNodeStack.pop();
        popScope();
    }

    @Override
    public void visit(ReturnNode node) {
        if(currentFunction==null)throw new WrongReturnException();
        node.correspondingFunction=currentFunction;
    }

    @Override
    public void visit(BreakNode node) {
        if(loopNodeStack.empty())throw new WrongControlFlowStatementException();
        node.correspondingLoop=loopNodeStack.peek();
    }

    @Override
    public void visit(ContinueNode node) {
        if(loopNodeStack.empty())throw new WrongControlFlowStatementException();
        node.correspondingLoop=loopNodeStack.peek();
    }

    @Override
    public void visit(ExprStmtNode node) {
        // do nothing
    }
}

