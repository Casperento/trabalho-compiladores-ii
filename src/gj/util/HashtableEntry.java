//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gj.util;

import java.io.Serializable;

class HashtableEntry<A, B> implements Serializable {
    A key;
    B value;
    int hash;
    HashtableEntry<A, B> next;

    HashtableEntry(A var1, B var2, int var3, HashtableEntry<A, B> var4) {
        this.key = var1;
        this.value = var2;
        this.hash = var3;
        this.next = var4;
    }

    protected HashtableEntry<A, B> copy() {
        return new HashtableEntry<A, B>(this.key, this.value, this.hash, this.next != null ? this.next.copy() : null);
    }
}
