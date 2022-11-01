package Tree;

public class SEQ extends Stm {
    public Stm left, right;
    public SEQ(Stm l, Stm r) { left=l; right=r; }
    public ExpList kids() {
        throw new Error("kids() not applicable to SEQ");
    }
    public Stm build(ExpList kids) {
        throw new Error("build() not applicable to SEQ");
    }
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public void accept(CodeVisitor v) { v.visit(this); }
    public <R> R accept(ResultVisitor<R> v) { return v.visit(this); }
}
