package Tree;import Temp.Temp;import java.util.LinkedList;public class MOVE extends Stm {    public Exp1 dst, src;    public MOVE(Exp1 d, Exp1 s) {        dst=d;        src=s;    }    public LinkedList<Exp1> kids() {        LinkedList<Exp1> kids = new LinkedList<Exp1>();        if (dst instanceof MEM) {            kids.addFirst(((MEM)dst).exp);            kids.addLast(src);        } else            kids.addFirst(src);        return kids;    }    public Stm build(LinkedList<Exp1> kids) {        if (dst instanceof MEM)            return new MOVE(new MEM(kids.getFirst()), kids.getLast());        else return new MOVE(dst, kids.getFirst());    }    public void accept(IntVisitor v, int d) {        v.visit(this, d);    }    public Temp accept(CodeVisitor v) {        v.visit(this);        return null;    }    public <R> R accept(ResultVisitor<R> v) {        return v.visit(this);    }}