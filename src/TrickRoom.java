import assembly.RVInfo;
import assembly.construct.AssemblyBuilder;
import ast.construct.*;
import ast.struct.RootNode;
import ir.construct.IRBuilder;
import ast.exception.*;
import ir.IRInfo;
import misc.Cst;
import optimization.IROptimizer;
import optimization.assembly.RVBlockCoalesce;
import optimization.ir.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.MxStarLexer;
import parser.MxStarParser;
import ir.construct.SSAConverter;
import ir.construct.SSADestructor;

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
    private static final String SSA_DESTRUCT = "-fno-ssa";
    private static final String INPUT_FILE = "-i";
    private static final String VERBOSE = "-v";
    private static final String DEBUG = "-debug";
    private static final String OUTPUT_FILE = "-o";
    private static final String HELP = "--help";
    private static final String HELP_ABBR = "-h";
    private static final String IR64 = "-ir64";
    private static final String NO_RENAME = "-fnorename-entry";

    private Verbose verb;
    private InputStream is;
    private OutputStream os;
    private Boolean llvmGenFlag;
    private Boolean assemblyGenFlag;
    private Boolean optimizationFlag;
    private Boolean entryRenameFlag;
    private Boolean ssaDestructFlag;

    private void logln(String s) {
        System.err.println(s);
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
        entryRenameFlag = true;
        ssaDestructFlag = false;
        boolean specification = false;
        for (int i = 0; i < args.length; ++i) {
            if (args[i].charAt(0) == '-') {
                // options
                switch (args[i]) {
                    case SYNTAX:
                    case LLVM:
                        if (!specification) specification = true;
                        else error("duplicated specification");
                }
                switch (args[i]) {
                    case SYNTAX -> {
                        llvmGenFlag = false;
                        assemblyGenFlag = false;
                    }
                    case LLVM -> {
                        llvmGenFlag = true;
                        assemblyGenFlag = false;
                    }
                    case OPTIMIZATION -> optimizationFlag = true;
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
                    case HELP, HELP_ABBR -> {
                        System.out.println("See https://github.com/XOR-op/TrickRoom for more information.");
                        System.exit(0);
                    }
                    case IR64 -> Cst.pointerSize = 8;
                    case NO_RENAME -> entryRenameFlag = false;
                    case SSA_DESTRUCT -> ssaDestructFlag = true;
                }
            } else
                error("wrong argument:" + args[i]);
        }
    }

    private void run() {
        try {
            RootNode rootNode = astGen();
            collectType(rootNode);
            if (optimizationFlag) new ConstantEliminator(rootNode).run();
            if (llvmGenFlag || assemblyGenFlag) {
                var info = llvmGen(rootNode);
                if (optimizationFlag) new IROptimizer(info).invoke();
                if (llvmGenFlag) {
                    try {
                        if (ssaDestructFlag){
                            postIROptimization(info);
                        }
                        os.write(info.toLLVMir().getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
                if (assemblyGenFlag) {
                    postIROptimization(info);
                    var rvInfo = assemblyGen(info);
                    if (optimizationFlag) rvOptimize(rvInfo);
                    try {
                        os.write(rvInfo.tell().getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        } catch (SemanticException e) {
            if (verb == Verbose.DEBUG) {
                e.printStackTrace();
            }
            System.exit(-1);
        } catch (Exception e) {
            if (verb == Verbose.DEBUG) {
                e.printStackTrace();
            } else
                System.err.println(e);
            System.exit(-1);
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
        if (entryRenameFlag)
            info.renameMain();
        return info;
    }

    private void rvOptimize(RVInfo rvInfo) {
        rvInfo.forEachFunction(f -> {
            new RVBlockCoalesce(f).invoke();
        });
    }

    private RVInfo assemblyGen(IRInfo irInfo) {
        var builder = new AssemblyBuilder(irInfo);
        var info = builder.constructAssembly();
        info.preOptimize();
        info.registerAllocate();
        return info;
    }

    private void postIROptimization(IRInfo info) {
        info.forEachFunction(f -> {
            new SSADestructor(f).invoke();
            new BlockCoalesce(f).invoke();
            new ConstantDeducer(f).invoke();
            var la=new LoopAnalyzer(f);
            la.invoke();
            la.calculateDepth();
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
