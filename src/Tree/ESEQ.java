package Tree;

import Temp.Temp;
public class ESEQ extends Exp {
    public Stm stm;
    public Exp exp;
    public ESEQ(Stm s, Exp e) { stm=s; exp=e; }
    public ExpList kids() {
        throw new Error("kids() not applicable to ESEQ");
    }

    public Exp build(ExpList kids) {
        throw new Error("build() not applicable to ESEQ");
    }
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public Temp accept(CodeVisitor v) { return v.visit(this); }
    public <R> R accept(ResultVisitor<R> v) { return v.visit(this); }
}
