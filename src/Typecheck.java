// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
import Parser.MiniJavaParser;
import Parser.ParseException;
import syntaxtree.Node;
import visitor.TableBuilderVisitor;
import visitor.TypeCheckerVisitor;

public class Typecheck {
    public static void main(String [] args) {

        Node root;
        try {
            //from standard input
            if (args.length == 0)
                root = new MiniJavaParser(System.in).Goal();
            //from file i.e arg[0]
            else
                try {
                    root = new MiniJavaParser
                    (new java.io.FileInputStream(args[0])).Goal();
                } catch ( java.io.FileNotFoundException e) {
                    System.err.println("Unable to read file " + args[0] );
                    return;
                }
        } catch (ParseException e) {
            System.err.println (e.toString() );
            return;
        }

        TableBuilderVisitor v1 = new TableBuilderVisitor();
        root.accept(v1);
        TypeCheckerVisitor v2 = new TypeCheckerVisitor(v1.getTable());
        root.accept(v2);
        System.out.println("Program type checked successfully");
    }
}




