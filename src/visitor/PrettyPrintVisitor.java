// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package visitor;

import syntaxtree.*;

public class PrettyPrintVisitor implements Visitor {

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            System.out.println();
            n.cl.elementAt(i).accept(this);
        }
    }

    @Override
    public void visit(NodeList n) {

    }

    @Override
    public void visit(NodeListOptional n) {

    }

    @Override
    public void visit(NodeOptional n) {

    }

    @Override
    public void visit(NodeSequence n) {

    }

    @Override
    public void visit(NodeToken n) {

    }

    @Override
    public void visit(Goal n) {

    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        System.out.print("class ");
        n.f1.accept(this);
        System.out.println(" {");
        System.out.print("  public static void main (String [] ");
        n.f11.accept(this);
        System.out.println(") {");
        System.out.print("    ");
        n.f14.accept(this);
        System.out.println("  }");
        System.out.println("}");
    }

    @Override
    public void visit(TypeDeclaration n) {

    }

    @Override
    public void visit(ClassDeclaration n) {

    }

    @Override
    public void visit(ClassExtendsDeclaration n) {

    }

    @Override
    public void visit(VarDeclaration n) {

    }

    @Override
    public void visit(MethodDeclaration n) {

    }

    @Override
    public void visit(FormalParameterList n) {

    }

    @Override
    public void visit(FormalParameter n) {

    }

    @Override
    public void visit(FormalParameterRest n) {

    }

    @Override
    public void visit(Type n) {

    }

    @Override
    public void visit(ArrayType n) {

    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        System.out.print("class ");
        n.i.accept(this);
        System.out.println(" { ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print("  ");
            n.vl.elementAt(i).accept(this);
            if ( i+1 < n.vl.size() ) {
                System.out.println();
            }
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            System.out.println();
            n.ml.elementAt(i).accept(this);
        }
        System.out.println();
        System.out.println("}");
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        System.out.print("class ");
        n.i.accept(this);
        System.out.println(" extends ");
        n.j.accept(this);
        System.out.println(" { ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print("  ");
            n.vl.elementAt(i).accept(this);
            if ( i+1 < n.vl.size() ) {
                System.out.println();
            }
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            System.out.println();
            n.ml.elementAt(i).accept(this);
        }
        System.out.println();
        System.out.println("}");
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
        System.out.print(";");
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        System.out.print("  public ");
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
        System.out.print(" (");
        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.elementAt(i).accept(this);
            if (i+1 < n.fl.size()) {
                System.out.print(", ");
            }
        }
        System.out.println(") { ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print("    ");
            n.vl.elementAt(i).accept(this);
            System.out.println("");
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            System.out.print("    ");
            n.sl.elementAt(i).accept(this);
            if ( i < n.sl.size() ) {
                System.out.println("");
            }
        }
        System.out.print("    return ");
        n.e.accept(this);
        System.out.println(";");
        System.out.print("  }");
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
    }

    public void visit(IntArrayType n) {
        System.out.print("int []");
    }

    public void visit(BooleanType n) {
        System.out.print("boolean");
    }

    public void visit(IntegerType n) {
        System.out.print("int");
    }

    @Override
    public void visit(Statement n) {

    }

    // String s;
    public void visit(IdentifierType n) {
        System.out.print(n.s);
    }

    // StatementList sl;
    public void visit(Block n) {
        System.out.println("{ ");
        for ( int i = 0; i < n.f1.size(); i++ ) {
            System.out.print("      ");
            n.f1.elementAt(i).accept(this);
            System.out.println();
        }
        System.out.print("    } ");
    }

    @Override
    public void visit(AssignmentStatement n) {

    }

    @Override
    public void visit(ArrayAssignmentStatement n) {

    }

    @Override
    public void visit(IfStatement n) {

    }

    @Override
    public void visit(WhileStatement n) {

    }

    @Override
    public void visit(PrintStatement n) {

    }

    @Override
    public void visit(Expression n) {

    }

    @Override
    public void visit(AndExpression n) {

    }

    @Override
    public void visit(CompareExpression n) {

    }

    @Override
    public void visit(PlusExpression n) {

    }

    @Override
    public void visit(MinusExpression n) {

    }

    @Override
    public void visit(TimesExpression n) {

    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        System.out.print("if (");
        n.e.accept(this);
        System.out.println(") ");
        System.out.print("    ");
        n.s1.accept(this);
        System.out.println();
        System.out.print("    else ");
        n.s2.accept(this);
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        System.out.print("while (");
        n.e.accept(this);
        System.out.print(") ");
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {
        System.out.print("System.out.println(");
        n.e.accept(this);
        System.out.print(");");
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        n.i.accept(this);
        System.out.print(" = ");
        n.e.accept(this);
        System.out.print(";");
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        System.out.print("[");
        n.e1.accept(this);
        System.out.print("] = ");
        n.e2.accept(this);
        System.out.print(";");
    }

    // Exp e1,e2;
    public void visit(And n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" && ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" < ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" + ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" - ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(Times n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" * ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        n.f0.accept(this);
        System.out.print("[");
        n.f2.accept(this);
        System.out.print("]");
    }

    // Exp e;
    public void visit(ArrayLength n) {
        n.f0.accept(this);
        System.out.print(".length");
    }

    @Override
    public void visit(MessageSend n) {

    }

    @Override
    public void visit(ExpressionList n) {

    }

    @Override
    public void visit(ExpressionRest n) {

    }

    @Override
    public void visit(PrimaryExpression n) {

    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
        n.e.accept(this);
        System.out.print(".");
        n.i.accept(this);
        System.out.print("(");
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
            if ( i+1 < n.el.size() ) {
                System.out.print(", ");
            }
        }
        System.out.print(")");
    }

    // int i;
    public void visit(IntegerLiteral n) {
        System.out.print(n.f0);
    }

    @Override
    public void visit(TrueLiteral n) {

    }

    @Override
    public void visit(FalseLiteral n) {

    }

    public void visit(True n) {
        System.out.print("true");
    }

    public void visit(False n) {
        System.out.print("false");
    }

    // String s;
    public void visit(IdentifierExp n) {
        System.out.print(n.s);
    }

    public void visit(This n) {
        System.out.print("this");
    }

    // Exp e;
    public void visit(NewArray n) {
        System.out.print("new int [");
        n.e.accept(this);
        System.out.print("]");
    }

    // Identifier i;
    public void visit(NewObject n) {
        System.out.print("new ");
        System.out.print(n.i.toString());
        System.out.print("()");
    }

    // Exp e;
    public void visit(Not n) {
        System.out.print("!");
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
        System.out.print(n.toString());
    }

    @Override
    public void visit(ThisExpression n) {

    }

    @Override
    public void visit(ArrayAllocationExpression n) {

    }

    @Override
    public void visit(AllocationExpression n) {

    }

    @Override
    public void visit(NotExpression n) {

    }

    @Override
    public void visit(BracketExpression n) {

    }
}
