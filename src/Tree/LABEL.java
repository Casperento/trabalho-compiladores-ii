package Tree;import Temp.Label;public class LABEL extends Stm {     public Label label;    public LABEL(Label l) { label=l; }    public ExpList kids() {        return null;    }    public Stm build(ExpList kids) {        return this;    }    public void accept(IntVisitor v, int d) { v.visit(this, d); }    public void accept(CodeVisitor v) { v.visit(this); }    public <R> R accept(ResultVisitor<R> v) { return v.visit(this); }}