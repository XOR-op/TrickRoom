import exception.semantic.*;
import semantic.*;
import ast.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.MxStarLexer;
import parser.MxStarParser;
import utils.ObjectDumper;

import java.io.FileInputStream;
import java.io.InputStream;

public class TrickRoom {
    public static void main(String[] args) {
        try {
            InputStream is = args.length > 0 ? new FileInputStream(args[0]):System.in;
            var lexer=new MxStarLexer(CharStreams.fromStream(is));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new ParsingErrorHandler());
            var tokenStream=new CommonTokenStream(lexer);
            tokenStream.fill();
            var parser=new MxStarParser(tokenStream);
            parser.removeErrorListeners();
            parser.addErrorListener(new ParsingErrorHandler());
            var builder=new ASTBuilder();
            RootNode rootNode=(RootNode) builder.visit(parser.code());
            new ScopeBuilder(rootNode);
            new TypeCollector().visit(rootNode);
//            ObjectDumper.dump(rootNode);
            System.out.println("Success");
        } catch (SemanticException e){
//            e.printStackTrace();
            System.out.println(ObjectDumper.ANSI_PURPLE+e.toString()+ObjectDumper.ANSI_RESET);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
