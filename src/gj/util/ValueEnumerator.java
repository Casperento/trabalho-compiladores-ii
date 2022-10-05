//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gj.util;

import java.util.NoSuchElementException;

class ValueEnumerator<A, B> implements Enumeration<B> {
    int index;
    HashtableEntry<A, B>[] table;
    HashtableEntry<A, B> entry;

    ValueEnumerator(HashtableEntry<A, B>[] var1) {
        this.table = var1;
        this.index = var1.length;
    }

    public boolean hasMoreElements() {
        if (this.entry != null) {
            return true;
        } else {
            do {
                if (this.index-- <= 0) {
                    return false;
                }
            } while((this.entry = this.table[this.index]) == null);

            return true;
        }
    }

    public B nextElement() {
        if (this.entry == null) {
            while(this.index-- > 0 && (this.entry = this.table[this.index]) == null) {
            }
        }

        if (this.entry != null) {
            HashtableEntry<A, B> var1 = this.entry;
            this.entry = var1.next;
            return var1.value;
        } else {
            throw new NoSuchElementException("HashtableEnumerator");
        }
    }
}
