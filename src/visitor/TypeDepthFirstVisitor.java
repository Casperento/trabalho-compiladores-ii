// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package visitor;

import syntaxtree.*;

public class TypeDepthFirstVisitor implements TypeVisitor {

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
        n.f1.accept((Visitor) this);
        n.f11.accept((Visitor) this);
        n.f14.accept((Visitor) this);
        return null;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclSimple n) {
        n.i.accept((Visitor) this);
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
        n.i.accept((Visitor) this);
        n.j.accept((Visitor) this);
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
        n.t.accept((Visitor) this);
        n.i.accept((Visitor) this);
        return null;
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Type visit(MethodDecl n) {
        n.t.accept((Visitor) this);
        n.i.accept((Visitor) this);
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
        return null;
    }

    // Type t;
    // Identifier i;
    public Type visit(Formal n) {
        n.t.accept((Visitor) this);
        n.i.accept((Visitor) this);
        return null;
    }

    public Type visit(IntArrayType n) {
        return null;
    }

    public Type visit(BooleanType n) {
        return null;
    }

    public Type visit(IntegerType n) {
        return null;
    }

    // String s;
    public Type visit(IdentifierType n) {
        return null;
    }

    // StatementList sl;
    public Type visit(Block n) {
        for ( int i = 0; i < n.f1.size(); i++ ) {
            n.f1.elementAt(i).accept((Visitor) this);
        }
        return null;
    }

    // Exp e;
    // Statement s1,s2;
    public Type visit(If n) {
        n.e.accept(this);
        n.s1.accept((Visitor) this);
        n.s2.accept((Visitor) this);
        return null;
    }

    // Exp e;
    // Statement s;
    public Type visit(While n) {
        n.e.accept(this);
        n.s.accept((Visitor) this);
        return null;
    }

    // Exp e;
    public Type visit(Print n) {
        n.e.accept(this);
        return null;
    }

    // Identifier i;
    // Exp e;
    public Type visit(Assign n) {
        n.i.accept((Visitor) this);
        n.e.accept(this);
        return null;
    }

    // Identifier i;
    // Exp e1,e2;
    public Type visit(ArrayAssign n) {
        n.i.accept((Visitor) this);
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e1,e2;
    public Type visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e1,e2;
    public Type visit(Plus n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e1,e2;
    public Type visit(ArrayLookup n) {
        n.f0.accept((Visitor) this);
        n.f2.accept((Visitor) this);
        return null;
    }

    // Exp e;
    public Type visit(ArrayLength n) {
        n.f0.accept((Visitor) this);
        return null;
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n) {
        n.e.accept(this);
        n.i.accept((Visitor) this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
        return null;
    }

    // int i;
    public Type visit(IntegerLiteral n) {
        return null;
    }

    public Type visit(True n) {
        return null;
    }

    public Type visit(False n) {
        return null;
    }

    // String s;
    public Type visit(IdentifierExp n) {
        return null;
    }

    public Type visit(This n) {
        return null;
    }

    // Exp e;
    public Type visit(NewArray n) {
        n.e.accept(this);
        return null;
    }

    // Identifier i;
    public Type visit(NewObject n) {
        return null;
    }

    // Exp e;
    public Type visit(Not n) {
        n.e.accept(this);
        return null;
    }

    // String s;
    public Type visit(Identifier n) {
        return null;
    }
}
