package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class NewObject extends Exp {
    public Identifier i;

    public NewObject(Identifier ai) {
        i=ai;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public String accept(TypeVisitor v) {
        return String.valueOf(v.visit(this));
    }
}
