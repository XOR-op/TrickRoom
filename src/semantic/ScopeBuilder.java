package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;

/*
 * build scope
 *
 */
public class ScopeBuilder implements ASTVisitor {
    private RootNode root;
    private Scope currentScope;

    private void pushScope(Scope scope) {
        currentScope = scope;
    }

    private void pushScope(ASTNode node) {
        pushScope(node.scope);
    }

    private void popScope() {
        currentScope = currentScope.getUpstream();
    }

    public ScopeBuilder(RootNode tree) {
        root = tree;
    }

    public Scope build() {
        // global file scope
        var top = new FileScope();
        currentScope = top;
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
        func.name = node.funcId;
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
        cls.name = node.className;
        for (var method : node.methods) {
            var func = scanFunction(method);
            scp.registerMethod(func);
            cls.memberFuncs.put(func.name, func);
        }
        for (var member : node.members) {
            scp.registerVar(visitDecl(member));
        }
        // todo constructor
        popScope();
        return cls;
    }

    @Override
    public void visit(DeclarationNode node) {
        currentScope.registerVar(visitDecl(node));
    }

    @Override
    public void visit(ExprStmtNode node) {

    }

    @Override
    public void visit(FuncCallNode node) {

    }

    @Override
    public void visit(ArrayLiteralNode node) {

    }

    @Override
    public void visit(BinaryExprNode node) {

    }

    @Override
    public void visit(BreakNode node) {

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
        visit(node.suite);
        popScope();
    }

    @Override
    public void visit(IdentifierNode node) {

    }

    @Override
    public void visit(LiteralNode node) {

    }

    @Override
    public void visit(SuiteNode node) {
        // node.scope will be built ahead
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
    public void visit(ThisNode node) {

    }

    @Override
    public void visit(UnaryExprNode node) {

    }

    @Override
    public void visit(ConditionalNode node) {
        node.trueStat.scope = new Scope(currentScope);
        pushScope(node.trueStat);
        node.trueStat.accept(this);
        popScope();
        if(node.falseStat!=null){
            node.falseStat.scope=new Scope(currentScope);
            pushScope(node.falseStat);
            node.falseStat.accept(this);
            popScope();
        }
    }

    @Override
    public void visit(ContinueNode node) {

    }

    @Override
    public void visit(DeclarationBlockNode node) {

    }

    @Override
    public void visit(LoopNode node) {
        node.scope=new LoopScope(currentScope);
        pushScope(node);
        if(node.initDecl !=null)visit(node.initDecl);
        node.loopBody.accept(this);
        popScope();
    }

    @Override
    public void visit(NewExprNode node) {

    }

    @Override
    public void visit(ReturnNode node) {

    }

    @Override
    public void visit(RootNode node) {

    }
}
