//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gj.util;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class Vector<A> implements Serializable, Cloneable {
    protected A[] elementData;
    protected int elementCount;
    protected int capacityIncrement;

    public Vector(int var1, int var2) {
        this.elementData = (A[]) new Object[var1];
        this.capacityIncrement = var2;
    }

    public Vector(int var1) {
        this(var1, 0);
    }

    public Vector() {
        this(10);
    }

    public final synchronized void copyInto(A[] var1) {
        for(int var2 = this.elementCount; var2-- > 0; var1[var2] = (A) this.elementData[var2]) {
        }

    }

    public final synchronized void trimToSize() {
        int var1 = this.elementData.length;
        if (this.elementCount < var1) {
            Object[] var2 = this.elementData;
            this.elementData = (A[]) new Object[this.elementCount];
            System.arraycopy(var2, 0, this.elementData, 0, this.elementCount);
        }

    }

    public final synchronized void ensureCapacity(int var1) {
        int var2 = this.elementData.length;
        if (var1 > var2) {
            Object[] var3 = this.elementData;
            int var4 = this.capacityIncrement > 0 ? var2 + this.capacityIncrement : var2 * 2;
            if (var4 < var1) {
                var4 = var1;
            }

            this.elementData = (A[]) new Object[var4];
            System.arraycopy(var3, 0, this.elementData, 0, this.elementCount);
        }

    }

    public final synchronized void setSize(int var1) {
        if (var1 > this.elementCount) {
            this.ensureCapacity(var1);
        } else {
            for(int var2 = var1; var2 < this.elementCount; ++var2) {
                this.elementData[var2] = null;
            }
        }

        this.elementCount = var1;
    }

    public final int capacity() {
        return this.elementData.length;
    }

    public final int size() {
        return this.elementCount;
    }

    public final boolean isEmpty() {
        return this.elementCount == 0;
    }

    public final synchronized Enumeration<A> elements() {
        return new VectorEnumerator<A>(this);
    }

    public final boolean contains(A var1) {
        return this.indexOf(var1, 0) >= 0;
    }

    public final int indexOf(A var1) {
        return this.indexOf(var1, 0);
    }

    public final synchronized int indexOf(A var1, int var2) {
        for(int var3 = var2; var3 < this.elementCount; ++var3) {
            if (var1.equals(this.elementData[var3])) {
                return var3;
            }
        }

        return -1;
    }

    public final int lastIndexOf(A var1) {
        return this.lastIndexOf(var1, this.elementCount);
    }

    public final synchronized int lastIndexOf(A var1, int var2) {
        int var3 = var2;

        do {
            if (--var3 < 0) {
                return -1;
            }
        } while(!var1.equals(this.elementData[var3]));

        return var3;
    }

    public final synchronized A elementAt(int var1) {
        if (var1 >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(String.valueOf(String.valueOf(var1).concat(String.valueOf(" >= "))).concat(String.valueOf(this.elementCount)));
        } else {
            try {
                return this.elementData[var1];
            } catch (ArrayIndexOutOfBoundsException var3) {
                throw new ArrayIndexOutOfBoundsException(String.valueOf(var1).concat(String.valueOf(" < 0")));
            }
        }
    }

    public final synchronized A firstElement() {
        if (this.elementCount == 0) {
            throw new NoSuchElementException();
        } else {
            return this.elementData[0];
        }
    }

    public final synchronized A lastElement() {
        if (this.elementCount == 0) {
            throw new NoSuchElementException();
        } else {
            return this.elementData[this.elementCount - 1];
        }
    }

    public final synchronized void setElementAt(A var1, int var2) {
        if (var2 >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(String.valueOf(String.valueOf(var2).concat(String.valueOf(" >= "))).concat(String.valueOf(this.elementCount)));
        } else {
            this.elementData[var2] = var1;
        }
    }

    public final synchronized void removeElementAt(int var1) {
        if (var1 >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(String.valueOf(String.valueOf(var1).concat(String.valueOf(" >= "))).concat(String.valueOf(this.elementCount)));
        } else {
            int var2 = this.elementCount - var1 - 1;
            if (var2 > 0) {
                System.arraycopy(this.elementData, var1 + 1, this.elementData, var1, var2);
            }

            --this.elementCount;
            this.elementData[this.elementCount] = null;
        }
    }

    public final synchronized void insertElementAt(A var1, int var2) {
        if (var2 >= this.elementCount + 1) {
            throw new ArrayIndexOutOfBoundsException(String.valueOf(String.valueOf(String.valueOf(var2).concat(String.valueOf(" >= "))).concat(String.valueOf(this.elementCount))).concat(String.valueOf(1)));
        } else {
            this.ensureCapacity(this.elementCount + 1);
            System.arraycopy(this.elementData, var2, this.elementData, var2 + 1, this.elementCount - var2);
            this.elementData[var2] = var1;
            ++this.elementCount;
        }
    }

    public final synchronized void addElement(A var1) {
        this.ensureCapacity(this.elementCount + 1);
        this.elementData[this.elementCount++] = var1;
    }

    public final synchronized boolean removeElement(A var1) {
        int var2 = this.indexOf(var1);
        if (var2 >= 0) {
            this.removeElementAt(var2);
            return true;
        } else {
            return false;
        }
    }

    public final synchronized void removeAllElements() {
        for(int var1 = 0; var1 < this.elementCount; ++var1) {
            this.elementData[var1] = null;
        }

        this.elementCount = 0;
    }

    public synchronized Object clone() {
        Vector<A> var1 = new Vector<A>(this.elementCount, this.capacityIncrement);
        System.arraycopy(this.elementData, 0, var1.elementData, 0, this.elementCount);
        return var1;
    }

    public final synchronized String toString() {
        int var1 = this.size() - 1;
        StringBuffer var2 = new StringBuffer();
        Enumeration<A> var3 = this.elements();
        var2.append("[");

        for(int var4 = 0; var4 <= var1; ++var4) {
            var2.append(var3.nextElement());
            if (var4 < var1) {
                var2.append(", ");
            }
        }

        var2.append("]");
        return var2.toString();
    }
}
