package Tree;

abstract public class Stm implements Hospitable {
    public abstract ExpList kids();
    public abstract Stm build(ExpList kids);
    public abstract void accept(IntVisitor v, int d);
    public abstract <R> R accept(ResultVisitor<R> v);
    public abstract void accept(CodeVisitor v);
}
