package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;
import exception.semantic.DuplicateSyntaxException;
import exception.semantic.MissingSyntaxException;
import exception.semantic.WrongControlFlowStatementException;
import exception.semantic.WrongReturnException;
import utils.L;

import java.util.HashMap;
import java.util.Stack;

/*
 * build scope
 *
 */
public class ScopeBuilder implements ASTVisitor {
    private RootNode root;
    private Scope currentScope;
    private FileScope top;
    private FunctionNode currentFunction;
    private Stack<LoopNode> loopNodeStack;
    private HashMap<String,Type> typeCollection;

    private void pushScope(Scope scope) {
        assert scope.getUpstream()==null||scope.getUpstream()==currentScope;
//        L.i(scope.toString());
        currentScope = scope;
    }

    private void pushScope(ASTNode node) {
        pushScope(node.scope);
    }

    private void popScope() {
//        L.i(currentScope.toString());
        currentScope = currentScope.getUpstream();
    }

    private void globalRegisterFunc(String s){
        ((FileScope)currentScope).registerFunction(Function.parse(s));
    }

    private void stringRegisterMethod(String s){
        Function f=Function.parse(s);
        ((ClassType)TypeConst.String).memberFuncs.put(f.id,f);
    }

    private void registerType(Type tp,ASTNode node){
        if(typeCollection.containsKey(tp.id))throw new DuplicateSyntaxException(node,tp.id);
        typeCollection.put(tp.id,tp);
    }

    private Type recoverType(Type originType,ASTNode node){
        if(!typeCollection.containsKey(originType.id))throw new MissingSyntaxException(node,originType.id);
        if(originType.dimension==0)
            return typeCollection.get(originType.id);
        else {
            Type t=typeCollection.get(originType.id).copy();
            t.dimension=originType.dimension;
            return t;
        }
    }

    public ScopeBuilder(RootNode tree) {
        root = tree;
        loopNodeStack=new Stack<>();
        typeCollection=new HashMap<>();
        build();
    }

    public Scope getCompletedScope(){return top;}

    private Scope build() {
        // global file scope
        top = new FileScope();
        currentScope = top;
        root.scope=top;
        // register builtin functions
        globalRegisterFunc("void print(string str);");
        globalRegisterFunc("void println(string str);");
        globalRegisterFunc("void printInt(int n);");
        globalRegisterFunc("void printlnInt(int n);");
        globalRegisterFunc("string getString();");
        globalRegisterFunc("int getInt();");
        globalRegisterFunc("string toString(int i);");
        stringRegisterMethod("int length();");
        stringRegisterMethod("string substring(int left, int right);");
        stringRegisterMethod("int parseInt();");
        stringRegisterMethod("int ord(int pos);");
        // preload types
        typeCollection.put("int",TypeConst.Int);
        typeCollection.put("string",TypeConst.String);
        typeCollection.put("bool",TypeConst.Bool);
        typeCollection.put("void",TypeConst.Void);
        typeCollection.put("null",TypeConst.Null);
        for (var cls : root.classes) {
            preScanClass(cls);
        }
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
        node.type=recoverType(node.type,node);
        return new Symbol(node.type, node.id);
    }

    private Function scanFunction(FunctionNode node) {
        // will check parameters
        node.scope = new FunctionScope(currentScope);
        var func = new Function(node);
        func.id = node.funcId;
        node.returnType=recoverType(node.returnType,node);
        func.returnType=node.returnType;
        for (var para : node.parameters) {
            var re = visitDecl(para);
            node.scope.registerVar(re,node);
            func.parameters.add(re);
        }
        return func;
    }

    private void preScanClass(ClassNode node){
        registerType(node.cls,node);
    }

    private ClassType scanClass(ClassNode node) {
        // will check names
        var cls=node.cls;
        var scp = new ClassScope(currentScope, cls);
        node.scope = scp;
        pushScope(node);
        for (var method : node.methods) {
            var func = scanFunction(method);
            scp.registerMethod(func,node);
            cls.memberFuncs.put(func.id, func);
        }
        for (var member : node.members) {
            var sym=visitDecl(member);
            scp.registerVar(sym,node);
            cls.memberVars.put(member.id,sym);
        }
        for(var con:node.constructor){
            var fn=scanFunction(con);
            fn.returnType=cls;
            cls.constructor.add(fn);
            // todo check for duplicated constructor
        }
        if(cls.constructor.isEmpty()){
            var fn=new FunctionNode(cls.id);
            fn.suite=new SuiteNode();
            fn.returnType=cls;
            cls.constructor.add(new Function(fn));
        }
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
        currentScope.registerVar(visitDecl(node),node);
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
        if(currentFunction==null)throw new WrongReturnException(node);
        node.correspondingFunction=currentFunction;
    }

    @Override
    public void visit(BreakNode node) {
        if(loopNodeStack.empty())throw new WrongControlFlowStatementException(node);
        node.correspondingLoop=loopNodeStack.peek();
    }

    @Override
    public void visit(ContinueNode node) {
        if(loopNodeStack.empty())throw new WrongControlFlowStatementException(node);
        node.correspondingLoop=loopNodeStack.peek();
    }

    @Override
    public void visit(ExprStmtNode node) {
        // do nothing
    }
}

