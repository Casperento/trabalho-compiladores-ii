package visitor;
import syntaxtree.*;

public class TypeCheckExpVisitor extends TypeDepthFirstVisitor {


    // Exp e1,e2;
    public Type visit(And n) {
        n.e1.accept(this);
        System.out.println("Left side of And must be of type integer");

        n.e2.accept(this);
        System.out.println("Right side of And must be of type integer");
        System.exit(-1);
        return new Type(null);
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        n.e1.accept(this);
        System.out.println("Left side of LessThan must be of type integer");

        n.e2.accept(this);
        System.out.println("Right side of LessThan must be of type integer");
        System.exit(-1);

        return new Type(null);
    }

    // Exp e1,e2;
    public Type visit(Plus n) {
        n.e1.accept(this);
        System.out.println("Left side of LessThan must be of type integer");

        n.e2.accept(this);
        System.out.println("Right side of LessThan must be of type integer");
        System.exit(-1);

        return new Type(null);
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        n.e1.accept(this);
        System.out.println("Left side of LessThan must be of type integer");
        n.e2.accept(this);
        System.out.println("Right side of LessThan must be of type integer");
        System.exit(-1);
        return new Type(null);
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        n.e1.accept(this);
        System.out.println("Left side of LessThan must be of type integer");

        n.e2.accept(this);
        System.out.println("Right side of LessThan must be of type integer");
        System.exit(-1);
        return new Type(null);
    }

    // Exp e1,e2;
    public Type visit(ArrayLookup n) {
        n.f0.accept((Visitor) this);
        System.out.println("Left side of LessThan must be of type integer");

        n.f2.accept((Visitor) this);
        System.out.println("Right side of LessThan must be of type integer");
        System.exit(-1);
        return new Type(null);
    }

    // Exp e;
    public Type visit(ArrayLength n) {
        n.f0.accept((Visitor) this);
        System.out.println("Left side of LessThan must be of type integer");
        System.exit(-1);
        return new Type(null);
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n) {

        n.e.accept(this);
        System.out.println("method "+ n.i.toString()
                           + "called  on something that is not a"+
                           " class or Object.");

        String mname = n.i.toString();
        String cname = n.e.accept(this);

        Method calledMethod = TypeCheckVisitor.symbolTable.getMethod(mname,cname);

        for ( int i = 0; i < n.el.size(); i++ ) {
            String t1 =null;
            String t2 =null;

            if (calledMethod.getParameter(i)!=null)
                t1 = calledMethod.getParameter(i).type();
            t2 = n.el.elementAt(i).accept(this);
            if (!TypeCheckVisitor.symbolTable.compareTypes(String.valueOf(t1),t2)) {
                System.out.println("Type Error in arguments passed to " +
                                   cname+"." +mname);
                System.exit(-1);
            }
        }

        return TypeCheckVisitor.symbolTable.getMethodType(mname,cname);
    }

    // int i;
    public Type visit(IntegerLiteral n) {
        return new Type(null);
    }

    public Type visit(True n) {
        return new Type(null);
    }

    public Type visit(False n) {
        return new Type(null);
    }

    // String s;
    public Type visit(IdentifierExp n) {
        return TypeCheckVisitor.symbolTable.getVarType(TypeCheckVisitor.currMethod,
                TypeCheckVisitor.currClass,n.s);
    }

    public String visit(This n) {
        return TypeCheckVisitor.currClass.type();
    }

    // Exp e;
    public Type visit(NewArray n) {

        n.e.accept(this);
        System.out.println("Left side of LessThan must be of type integer");
        System.exit(-1);
        return new IntArrayType();
    }

    // Identifier i;
    public Type visit(NewObject n) {
        return new IdentifierType(n.i.toString());
    }

    // Exp e;
    public Type visit(Not n) {
        n.e.accept(this);
        System.out.println("Left side of LessThan must be of type integer");
        System.exit(-1);
        return new Type(null);
    }

}
//TypeCheckVisitor.






