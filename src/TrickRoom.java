import assembly.construct.AsmBuilder;
import ast.construct.ASTBuilder;
import ast.construct.ParsingErrorHandler;
import ast.construct.ScopeBuilder;
import ast.construct.TypeCollector;
import ast.struct.RootNode;
import ir.construct.IRBuilder;
import exception.UnimplementedError;
import exception.semantic.*;
import ir.IRInfo;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.MxStarLexer;
import parser.MxStarParser;
import optimization.ConstantDeducer;
import optimization.SSAConverter;
import optimization.SSADestructor;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TrickRoom {
    private enum Verbose {
        SILENT, INFO, DEBUG
    }

    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String SYNTAX = "-fsyntax-only";
    private static final String OPTIMIZATION = "-O2";
    private static final String LLVM = "-emit-llvm";
    private static final String INPUT_FILE = "-i";
    private static final String VERBOSE = "-v";
    private static final String DEBUG = "-debug";
    private static final String OUTPUT_FILE = "-o";

    private Verbose verb;
    private InputStream is;
    private OutputStream os;
    private Boolean llvmGenFlag;
    private Boolean assemblyGenFlag;
    private Boolean optimizationFlag;

    private void logln(String s) {
        System.out.println(s);
    }

    private void error(String s) {
        logln(s);
        System.exit(1);
    }

    public static void main(String[] args) {
        new TrickRoom(args).run();
    }

    private TrickRoom(String[] args) {
        is = System.in;
        os = System.out;
        verb = Verbose.SILENT;
        llvmGenFlag = false;
        assemblyGenFlag = true;
        optimizationFlag = false;
        boolean specification = false;
        for (int i = 0; i < args.length; ++i) {
            if (args[i].charAt(0) == '-') {
                // options
                switch (args[i]) {
                    case SYNTAX:
                    case LLVM:
                    case OPTIMIZATION:
                        if (!specification) specification = true;
                        else error("duplicated specification");
                }
                switch (args[i]) {
                    case SYNTAX -> {
                        llvmGenFlag = false;
                        assemblyGenFlag = false;
                        optimizationFlag = false;
                    }
                    case LLVM -> {
                        llvmGenFlag = true;
                        assemblyGenFlag = false;
                        optimizationFlag = false;
                    }
                    case OPTIMIZATION -> {
                        llvmGenFlag = false;
                        assemblyGenFlag = true;
                        optimizationFlag = true;
                    }
                    case INPUT_FILE -> {
                        if (i + 1 >= args.length || args[i + 1].charAt(0) == '-') error("no file input");
                        try {
                            is = new FileInputStream(args[i + 1]);
                            i++;
                        } catch (FileNotFoundException e) {
                            error("file not found:" + args[i + 1]);
                        }
                    }
                    case OUTPUT_FILE -> {
                        if (i + 1 >= args.length || args[i + 1].charAt(0) == '-') error("no output file specified");
                        try {
                            os = new FileOutputStream(args[i + 1]);
                            i++;
                        } catch (FileNotFoundException e) {
                            error("cannot write file:" + args[i + 1]);
                        }
                    }
                    case VERBOSE -> verb = Verbose.INFO;
                    case DEBUG -> verb = Verbose.DEBUG;
                }
            } else
                error("wrong argument:" + args[i]);
        }
    }

    private void run() {
        try {
            RootNode rootNode = astGen();
            collectType(rootNode);
            if (llvmGenFlag || assemblyGenFlag) {
                var info = llvmGen(rootNode);
                if (optimizationFlag) optimize(info);
                if (assemblyGenFlag){
                    postOptimization(info);
                    assemblyGen(info);
                }
            }
        } catch (SemanticException e) {
            if (verb == Verbose.DEBUG) {
                e.printStackTrace();
            }
            System.exit(-1);
        } catch (Exception e) {
            throw e;
        }
    }

    private void collectType(RootNode rootNode) {
        try {
            new ScopeBuilder(rootNode);
            new TypeCollector().visit(rootNode);
            if (verb == Verbose.INFO)
                logln("Success");
        } catch (SemanticException e) {
            logln(ANSI_PURPLE + e.toString() + ANSI_RESET);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private IRInfo llvmGen(RootNode rootNode) {
        IRBuilder builder = new IRBuilder(rootNode);
        IRInfo info = builder.constructIR();
        info.forEachFunction(f -> new SSAConverter(f).invoke());
        try {
            if (llvmGenFlag)
                os.write(info.toLLVMir().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println(e);
        }
        return info;
    }

    private void optimize(IRInfo irInfo){
        throw new UnimplementedError();
    }

    private void assemblyGen(IRInfo irInfo) {
        var builder = new AsmBuilder(irInfo);
        var info = builder.constructAssembly();
        info.preOptimize();
        info.registerAllocate();
        try {
            os.write(info.tell().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void postOptimization(IRInfo info) {
        info.forEachFunction(f -> {
            new SSADestructor(f).invoke();
            new ConstantDeducer(f).invoke();
        });
    }

    private RootNode astGen() {
        try {
            var lexer = new MxStarLexer(CharStreams.fromStream(is));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new ParsingErrorHandler());
            var tokenStream = new CommonTokenStream(lexer);
            tokenStream.fill();
            var parser = new MxStarParser(tokenStream);
            parser.removeErrorListeners();
            parser.addErrorListener(new ParsingErrorHandler());
            var builder = new ASTBuilder();
            return (RootNode) builder.visit(parser.code());
        } catch (SemanticException e) {
            logln(ANSI_PURPLE + e.toString() + ANSI_RESET);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
