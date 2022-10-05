//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gj.util;

public abstract class Dictionary<A, B> {
    public Dictionary() {
    }

    public abstract int size();

    public abstract boolean isEmpty();

    public abstract Enumeration<A> keys();

    public abstract Enumeration<B> elements();

    public abstract B get(A var1);

    public abstract B put(A var1, B var2);

    public abstract B remove(A var1);
}
