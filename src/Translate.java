import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Translate {

    public static void main(String[] args) throws Exception {

        String inputFile = null;

        if (args.length > 0 )
            inputFile = args[0];

        InputStream is = System.in;

        if (inputFile != null ) is = new FileInputStream(inputFile);

        ANTLRInputStream input = new ANTLRInputStream(is);

        FunlLexer lexer = new FunlLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        FunlParser parser = new FunlParser(tokens);

        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();

        FListener translate = new FListener(parser);

        walker.walk(translate, tree);



    }
    
}
