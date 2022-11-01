package Tree;

import Temp.Temp;

public class TEMP extends Exp {
    public Temp temp;
    public TEMP(Temp t) { temp=t; }
    public ExpList kids() {
        return null;
    }
    public Exp build(ExpList kids) {
        return this;
    }
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public Temp accept(CodeVisitor v) { return v.visit(this); }
    public <R> R accept(ResultVisitor<R> v) { return v.visit(this); }
}
