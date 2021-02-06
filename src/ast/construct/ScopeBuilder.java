package ast.construct;

import ast.ASTVisitor;
import ast.Symbol;
import ast.struct.*;
import ast.type.*;
import ast.scope.*;
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
    private Stack<LoopNode> loopNodeStack = new Stack<>();
    private HashMap<String, Type> typeCollection = new HashMap<>();
    private int scopeLevel = 0;
    private ClassType currentClass = null;

    private void pushScope(Scope scope) {
        assert scope.getUpstream() == null || scope.getUpstream() == currentScope;
        currentScope = scope;
        scopeLevel++;
    }

    private void pushScope(ASTNode node) {
        pushScope(node.scope);
    }

    private void popScope() {
        currentScope = currentScope.getUpstream();
        scopeLevel--;
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
            return new ArrayObjectType(typeCollection.get(originType.id).copy(), ((ArrayObjectType) originType).dimension);
        }
    }

    public ScopeBuilder(RootNode tree) {
        root = tree;
        build();
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
        root.functions.forEach(fun -> top.registerFunction(scanFunction(fun)));
        root.classes.forEach(cls -> top.registerClass(scanClass(cls)));
        // sequentially visit variable declarations and functions
        root.nodeList.forEach(node -> node.accept(this));
        return top;
    }

    private Symbol scanDecl(DeclarationNode node) {
        node.type = recoverType(node.type, node);
        String prefix = (currentClass != null && currentFunction == null )?("struct."+currentClass.id+".") : "";
        boolean isGlobal=currentClass==null&&currentFunction==null;
        node.sym = new Symbol(node.type, node.id, prefix + node.id,
                isGlobal,!isGlobal&&currentFunction==null,node.expr);
        if(currentFunction==null&&currentClass==null){
            top.globalVarTable.put(node.sym.nameAsReg,node.sym);
        }
        return node.sym;
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
        currentClass=cls;
        var scp = new ClassScope(currentScope, cls);
        node.scope = scp;
        scp.registerVar("this", cls, "this", node);
        pushScope(node);
        for (var member : node.members) {
            var sym = scanDecl(member);
            scp.registerVar(sym, node);
            cls.memberVars.put(member.id, sym);
        }
        for (var method : node.methods) {
            var func = scanFunction(method);
            if (func.id.equals(cls.id)) {
                throw new NoMatchedFunction(method, "invalid name for method");
            }
            func.parentClass = cls;
            scp.registerMethod(func, node);
            cls.memberFuncs.put(func.id, func);
        }
        for (var con : node.constructor) {
            var fn = scanFunction(con);
            if (!fn.id.equals(cls.id)) {
                // mismatched constructor
                throw new NoMatchedFunction(con, "constructor mismatched");
            }
            fn.returnType = cls;
            fn.parentClass = cls;
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
        currentClass=null;
        return cls;
    }

    @Override
    public Void visit(DeclarationBlockNode node) {
        for (var sub : node.decls)
            visit(sub);
        return null;
    }

    @Override
    public Void visit(DeclarationNode node) {
        if (node.expr != null) node.expr.accept(this);
        currentScope.registerVar(scanDecl(node), node);
        return null;
    }

    @Override
    public Void visit(ClassNode node) {
        pushScope(node);
        node.constructor.forEach(this::visit);
        node.methods.forEach(this::visit);
        popScope();
        return null;
    }

    @Override
    public Void visit(FunctionNode node) {
        pushScope(node);
        currentFunction = node;
        visit(node.suite);
        currentFunction = null;
        popScope();
        return null;
    }

    @Override
    public Void visit(SuiteNode node) {
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
        return null;
    }

    @Override
    public Void visit(ConditionalNode node) {
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
        return null;
    }

    @Override
    public Void visit(LoopNode node) {
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
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        if (currentFunction == null) throw new WrongReturn(node);
        if (node.returnExpr != null) node.returnExpr.accept(this);
        node.correspondingFunction = currentFunction;
        return null;
    }

    @Override
    public Void visit(BreakNode node) {
        if (loopNodeStack.empty()) throw new WrongControlStatement(node);
        node.correspondingLoop = loopNodeStack.peek();
        return null;
    }

    @Override
    public Void visit(ContinueNode node) {
        if (loopNodeStack.empty()) throw new WrongControlStatement(node);
        node.correspondingLoop = loopNodeStack.peek();
        return null;
    }

    @Override
    public Void visit(ExprStmtNode node) {
        if (node.expr != null)
            node.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(IdentifierNode node) {
        node.sym = currentScope.getVarSymbol(node.id, node);
        node.type = node.sym.getType();
        return null;
    }

    @Override
    public Void visit(BinaryExprNode node) {
        node.lhs.accept(this);
        node.rhs.accept(this);
        return null;
    }

    @Override
    public Void visit(UnaryExprNode node) {
        node.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(ThisNode node) {
        node.type = currentScope.getVarType("this", node);
        return null;
    }

    @Override
    public Void visit(MemberNode node) {
        node.object.accept(this);
        return null;
    }

    @Override
    public Void visit(NewExprNode node) {
        // fix type
        if (node.arrNew != null) visit(node.arrNew);
        return null;
    }

    @Override
    public Void visit(LiteralNode node) {
        // do nothing
        return null;
    }

    @Override
    public Void visit(FuncCallNode node) {
        // check global function later
        if (!(node.callee instanceof IdentifierNode))
            node.callee.accept(this);
        for (var arg : node.arguments) arg.accept(this);
        return null;
    }

    @Override
    public Void visit(AssignmentNode node) {
        node.rhs.accept(this);
        node.lhs.accept(this);
        return null;
    }

    @Override
    public Void visit(ArrayLiteralNode node) {
        node.type = recoverType(node.type, node);
        node.dimArr.forEach(sub -> sub.accept(this));
        return null;
    }

    @Override
    public Void visit(SubscriptionNode node) {
        node.lhs.accept(this);
        node.rhs.accept(this);
        return null;
    }

    @Override
    public Void visit(PrefixLeftValueNode node) {
        node.expr.accept(this);
        return null;
    }
}

