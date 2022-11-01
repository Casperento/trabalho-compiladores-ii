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
	    try { root = new MiniJavaParser
		(new java.io.FileInputStream(args[0])).Goal();
	    } catch ( java.io.FileNotFoundException e)
		{
		    System.err.println("Unable to read file " + args[0] );
		    return;
		}
	}
	catch (ParseException e){
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




