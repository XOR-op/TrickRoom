import compnent.scope.*;
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
    public static void main(String[] args) throws Exception {
        try {
            InputStream is = args.length > 0 ? new FileInputStream(args[0]):System.in;
            var tokenStream=new CommonTokenStream(new MxStarLexer(CharStreams.fromStream(is)));
            tokenStream.fill();
            var parser=new MxStarParser(tokenStream);
            var builder=new ASTBuilder();
            RootNode rootNode=(RootNode) builder.visit(parser.code());
            Scope scope=new ScopeBuilder(rootNode).build();
            ObjectDumper.dump(rootNode);
        }catch (Exception e){

            if(e instanceof SemanticException)
                e.printStackTrace();
            throw e;
        }
    }
}
