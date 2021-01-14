package semantic;

import ast.*;
import compnent.basic.*;
import compnent.scope.*;
import exception.semantic.*;

import java.util.HashMap;
import java.util.Stack;

/*
 * build scope
 *  - check only variables but not members
 *    members will be checked in TypeCollector
 *  - check break, continue and return statements
 */
public class ScopeBuilder implements ASTVisitor {
    private RootNode root;
    private Scope currentScope;
    private FileScope top;
    private FunctionNode currentFunction;
    private Stack<LoopNode> loopNodeStack;
    private HashMap<String, Type> typeCollection;

    private void pushScope(Scope scope) {
        assert scope.getUpstream() == null || scope.getUpstream() == currentScope;
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

    private void globalRegisterFunc(String s) {
        ((FileScope) currentScope).registerFunction(FunctionType.parse(s));
    }

    private void stringRegisterMethod(String s) {
        FunctionType f = FunctionType.parse(s);
        ((ClassType) TypeConst.String).memberFuncs.put(f.id, f);
    }

    private void registerType(Type tp, ASTNode node) {
        if (typeCollection.containsKey(tp.id)) throw new DuplicateSyntax(node, tp.id);
        typeCollection.put(tp.id, tp);
    }

    // used for recover type with class specification
    // e.g. class will be Type instead of ClassType in ASTBuilder
    private Type recoverType(Type originType, ASTNode node) {
        if (!typeCollection.containsKey(originType.id)) throw new MissingSyntax(node, originType.id);
        if (!originType.isArray())
            return typeCollection.get(originType.id);
        else {
            return new ArrayType(typeCollection.get(originType.id).copy(),((ArrayType)originType).dimension);
        }
    }

    public ScopeBuilder(RootNode tree) {
        root = tree;
        loopNodeStack = new Stack<>();
        typeCollection = new HashMap<>();
        build();
    }

    public Scope getCompletedScope() {
        return top;
    }

    private Scope build() {
        // global file scope
        top = new FileScope();
        currentScope = top;
        root.scope = top;
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
        typeCollection.put("int", TypeConst.Int);
        typeCollection.put("string", TypeConst.String);
        typeCollection.put("bool", TypeConst.Bool);
        typeCollection.put("void", TypeConst.Void);
        typeCollection.put("null", TypeConst.Null);
        for (var cls : root.classes) {
            preScanClass(cls);
        }
        // scan class and function definition to support forwarding reference
        root.functions.forEach(fun->top.registerFunction(scanFunction(fun)));
        root.classes.forEach(cls->top.registerClass(scanClass(cls)));
        // sequentially visit variable declarations and functions
        root.nodeList.forEach(node -> node.accept(this));
        return top;
    }

    private Symbol scanDecl(DeclarationNode node) {
        node.type = recoverType(node.type, node);
        return new Symbol(node.type, node.id);
    }

    private FunctionType scanFunction(FunctionNode node) {
        // will check parameters
        node.scope = new FunctionScope(currentScope);
        var func = new FunctionType(node);
        func.id = node.funcId;
        node.returnType = recoverType(node.returnType, node);
        func.returnType = node.returnType;
        for (var para : node.parameters) {
            var re = scanDecl(para);
            node.scope.registerVar(re, node);
            func.parameters.add(re);
        }
        return func;
    }

    private void preScanClass(ClassNode node) {
        registerType(node.cls, node);
    }

    private ClassType scanClass(ClassNode node) {
        // will check names
        var cls = node.cls;
        var scp = new ClassScope(currentScope, cls);
        node.scope = scp;
        scp.registerVar("this",cls,node);
        pushScope(node);
        for (var member : node.members) {
            var sym = scanDecl(member);
            scp.registerVar(sym, node);
            cls.memberVars.put(member.id, sym);
        }
        for (var method : node.methods) {
            var func = scanFunction(method);
            if(func.id.equals(cls.id)){
                throw new NoMatchedFunction(method,"invalid name for method");
            }
            scp.registerMethod(func, node);
            cls.memberFuncs.put(func.id, func);
        }
        for (var con : node.constructor) {
            var fn = scanFunction(con);
            if(!fn.id.equals(cls.id)){
                // mismatched constructor
                throw new NoMatchedFunction(con,"constructor mismatched");
            }
            fn.returnType = cls;
            cls.constructor.add(fn);
            // todo check for duplicated constructor
        }
        if (cls.constructor.isEmpty()) {
            var fn = new FunctionNode(cls.id);
            fn.suite = new SuiteNode();
            fn.returnType = cls;
            cls.constructor.add(new FunctionType(fn));
        }
        popScope();
        return cls;
    }

    @Override
    public void visit(DeclarationBlockNode node) {
        for (var sub : node.decls)
            visit(sub);
    }

    @Override
    public void visit(DeclarationNode node) {
        if (node.expr != null) node.expr.accept(this);
        currentScope.registerVar(scanDecl(node), node);
    }

    @Override
    public void visit(ClassNode node) {
        pushScope(node);
        node.constructor.forEach(this::visit);
        node.methods.forEach(this::visit);
        popScope();
    }

    @Override
    public void visit(FunctionNode node) {
        pushScope(node);
        currentFunction = node;
        visit(node.suite);
        currentFunction = null;
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
        node.condExpr.accept(this);
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
        if (node.initExpr != null) node.initExpr.accept(this);
        if (node.condExpr != null) node.condExpr.accept(this);
        if (node.updateExpr != null) node.updateExpr.accept(this);
        node.loopBody.accept(this);
        loopNodeStack.pop();
        popScope();
    }

    @Override
    public void visit(ReturnNode node) {
        if (currentFunction == null) throw new WrongReturn(node);
        if(node.returnExpr!=null)node.returnExpr.accept(this);
        node.correspondingFunction = currentFunction;
    }

    @Override
    public void visit(BreakNode node) {
        if (loopNodeStack.empty()) throw new WrongControlStatement(node);
        node.correspondingLoop = loopNodeStack.peek();
    }

    @Override
    public void visit(ContinueNode node) {
        if (loopNodeStack.empty()) throw new WrongControlStatement(node);
        node.correspondingLoop = loopNodeStack.peek();
    }

    @Override
    public void visit(ExprStmtNode node) {
        if (node.expr != null)
            node.expr.accept(this);
    }

    @Override
    public void visit(IdentifierNode node) {
        node.type = currentScope.getVarType(node.id, node);
    }

    @Override
    public void visit(BinaryExprNode node) {
        node.lhs.accept(this);
        node.rhs.accept(this);
    }

    @Override
    public void visit(UnaryExprNode node) {
        node.expr.accept(this);
    }

    @Override
    public void visit(ThisNode node) {
        node.type = currentScope.getVarType("this", node);
    }

    @Override
    public void visit(MemberNode node) {
        node.object.accept(this);
    }

    @Override
    public void visit(NewExprNode node) {
        // fix type
        if(node.arrNew!=null)visit(node.arrNew);
    }

    @Override
    public void visit(LiteralNode node) {
        // do nothing
    }

    @Override
    public void visit(FuncCallNode node) {
        // check global function later
        if (!(node.callee instanceof IdentifierNode))
            node.callee.accept(this);
        for (var arg : node.arguments) arg.accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        node.rhs.accept(this);
        node.lhs.accept(this);
    }

    @Override
    public void visit(ArrayLiteralNode node) {
        node.type=recoverType(node.type,node);
        node.dimArr.forEach(sub-> sub.accept(this));
    }

    @Override
    public void visit(SubscriptionNode node) {
        node.lhs.accept(this);
        node.rhs.accept(this);
    }

    @Override
    public void visit(PrefixLeftValueNode node) {
        node.expr.accept(this);
    }
}

