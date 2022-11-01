package Tree;

import Temp.Label;
import Temp.Temp;
public class NAME extends Exp {
    public Label label;
    public NAME(Label l) { label=l; }
    public ExpList kids() { return null; }
    public Exp build(ExpList kids) {
        return this;
    }
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public Temp accept(CodeVisitor v) { return v.visit(this); }
    public <R> R accept(ResultVisitor<R> v) { return v.visit(this); }
}
