package Tree;

public class EXP extends Stm {
    public Exp exp; 
    public EXP(Exp e) { exp=e; }
    public ExpList kids() {
        return new ExpList(exp, null);
    }

    public Stm build(ExpList kids) {
        return new EXP(kids.head);
    }
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public void accept(CodeVisitor v) { v.visit(this); }
    public <R> R accept(ResultVisitor<R> v) { return v.visit(this); }
}
