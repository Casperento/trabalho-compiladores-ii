package Tree;

import Temp.Temp;

import java.util.LinkedList;

abstract public class Exp1 extends Stm {
    public Object exp;

    abstract public LinkedList<Exp1> kids();
    abstract public Exp1 build(ExpList kids);

    abstract public void accept(IntVisitor v, int d);

    abstract public Temp accept(CodeVisitor v);

    abstract public <R> R accept(ResultVisitor<R> v);
}