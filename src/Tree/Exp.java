package Tree;

import Temp.Temp;

abstract public class Exp implements Hospitable {
    abstract public ExpList kids();
    abstract public Exp build(ExpList kids);
    abstract public void accept(IntVisitor v, int d);
    abstract public Temp accept(CodeVisitor v);
    abstract public <R> R accept(ResultVisitor<R> v);
}
