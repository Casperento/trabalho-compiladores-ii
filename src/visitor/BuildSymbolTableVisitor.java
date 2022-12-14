// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
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
        currMethod.addVar(n.f11.toString(), new IdentifierType("String[]").s);
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
        currClass = symbolTable.getClass(n.i.toString());
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
            if (!currClass.addVar(id, String.valueOf(t))) {
                System.out.println(id + "is already defined in "
                                   + currClass.id());
                System.exit(-1);
            }
        } else {

            if (!currMethod.addVar(id, String.valueOf(t))) {
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

        if (!currClass.addMethod(id, String.valueOf(t))) {
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

        if (!currMethod.addParameter(id, String.valueOf(t))) {
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

    public Type visit(BooleanType n) {
        return n;
    }

    public Type visit(IntegerType n) {
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
