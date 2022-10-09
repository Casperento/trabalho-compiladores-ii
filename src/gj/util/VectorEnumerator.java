//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gj.util;

import java.util.NoSuchElementException;

final class VectorEnumerator<A> implements Enumeration<A> {
    Vector<A> vector;
    int count;

    VectorEnumerator(Vector<A> var1) {
        this.vector = var1;
        this.count = 0;
    }

    public boolean hasMoreElements() {
        return this.count < this.vector.elementCount;
    }

    public A nextElement() {
        Vector<A> var1 = this.vector;
        synchronized(var1){}

        try {
            if (this.count < this.vector.elementCount) {
                A var2 = this.vector.elementData[this.count++];
                return var2;
            }
        } finally {
            ;
        }

        throw new NoSuchElementException("VectorEnumerator");
    }
}
