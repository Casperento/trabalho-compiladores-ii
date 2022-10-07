package visitor;
import syntaxtree.*;

public class BuildSymbolTableVisitor extends TypeDepthFirstVisitor {

    SymbolTable symbolTable;

    public BuildSymbolTableVisitor() {
        symbolTable = new SymbolTable();
    }

    public SymbolTable getSymTab() {
        return symbolTable;
    }

    private Class currClass;
    private Method currMethod;


    // MainClass m;
    // ClassDeclList cl;
    public Type visit(Program n) {
        n.m.accept((Visitor) this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }
        return null;
    }

    // Identifier i1,i2;
    // Statement s;
    public Type visit(MainClass n) {
        symbolTable.addClass( n.f1.toString(), null);
        currClass = symbolTable.getClass(n.f1.toString());

        //this is an ugly hack.. but its not worth having a Void and
        //String[] type just for one occourance
        currMethod = new Method ("main", new IdentifierType("void").s);
        currMethod.addVar(n.f11.toString(),
                          new IdentifierType("String[]").s);
        n.f14.accept((Visitor) this);

        currMethod = null;

        return null;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclSimple n) {
        if(!symbolTable.addClass( n.i.toString(), null)) {
            System.out.println("Class " +  n.i.toString()
                               + "is already defined" );
            System.exit(-1);
        }
        currClass =  symbolTable.getClass(n.i.toString());
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        return null;
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclExtends n) {
        if(!symbolTable.addClass( n.i.toString(),  n.j.toString())) {
            System.out.println("Class " +  n.i.toString()
                               + "is already defined" );
            System.exit(-1);
        }
        currClass = symbolTable.getClass(n.i.toString());

        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        return null;
    }

    // Type t;
    // Identifier i;
    public Type visit(VarDecl n) {

        Type t =  n.t.accept(this);
        String id =  n.i.toString();

        if (currMethod == null) {
            if (!currClass.addVar(id,t.toString())) {
                System.out.println(id + "is already defined in "
                                   + currClass.id());
                System.exit(-1);
            }
        } else {

            if (!currMethod.addVar(id,t.toString())) {
                System.out.println(id + "is already defined in "
                                   + currClass.id() + "." +
                                   currMethod.id());
                System.exit(-1);
            }
        }
        return null;
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Type visit(MethodDecl n) {
        Type t = n.t.accept(this);
        String id = n.i.toString();

        if (!currClass.addMethod(id,t.toString())) {
            System.out.println("Method " + id
                               + "is already defined in "
                               + currClass.id());
            System.exit(-1);
        }

        currMethod = currClass.getMethod(id);

        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept((Visitor) this);
        }

        n.e.accept(this);
        currMethod = null;
        return null;
    }

    // Type t;
    // Identifier i;
    public Type visit(Formal n) {

        Type t = n.t.accept(this);
        String id = n.i.toString();

        if (!currMethod.addParameter(id,t.toString())) {
            System.out.println("Formal" + id + "is already defined in "
                               + currClass.id() + "." +
                               currMethod.id());
            System.exit(-1);
        }
        return null;
    }

    public Type visit(IntArrayType n) {
        return n;
    }

    public BooleanType visit(BooleanType n) {
        return n;
    }

    public IntegerType visit(IntegerType n) {
        return n;
    }

    // String s;
    public Type visit(IdentifierType n) {
        return n;
    }

    // StatementList sl;
    public Type visit(Block n) {
        for ( int i = 0; i < n.f1.size(); i++ ) {
            n.f1.elementAt(i).accept((Visitor) this);
        }
        return null;
    }
}
