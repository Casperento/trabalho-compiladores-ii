package Tree;import Temp.Temp;import java.util.LinkedList;public class ESEQ extends Exp1 {    public Stm stm;    public Exp1 exp;    public ESEQ(Stm s, Exp1 e) {        stm=s;        exp=e;    }    public LinkedList<Exp1> kids() {        throw new Error("kids() not applicable to ESEQ");    }    @Override    public Exp1 build(ExpList kids) {        return null;    }    public Exp1 build(LinkedList<Exp1> kids) {        throw new Error("build() not applicable to ESEQ");    }    public void accept(IntVisitor v, int d) {        v.visit(this, d);    }    public Temp accept(CodeVisitor v) {        return v.visit(this);    }    public <R> R accept(ResultVisitor<R> v) {        return v.visit(this);    }}