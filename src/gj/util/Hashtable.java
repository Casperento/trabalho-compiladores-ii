// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gj.util;

import java.io.Serializable;

public class Hashtable<A, B> extends Dictionary<A, B> implements Serializable, Cloneable {
    private HashtableEntry<A, B>[] table;
    private int count;
    private int threshold;
    private float loadFactor;

    public Hashtable(int var1, float var2) {
        if (var1 > 0 && !((double)var2 <= 0.0)) {
            this.loadFactor = var2;
            this.table = new HashtableEntry[var1];
            this.threshold = (int)((float)var1 * var2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Hashtable(int var1) {
        this(var1, 0.75F);
    }

    public Hashtable() {
        this(101, 0.75F);
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public synchronized Enumeration<A> keys() {
        return new KeyEnumerator<A, B>(this.table);
    }

    public synchronized Enumeration<B> elements() {
        return new ValueEnumerator<A, B>(this.table);
    }

    public synchronized boolean contains(B var1) {
        HashtableEntry<A, B>[] var2 = this.table;
        int var3 = var2.length;

        while(var3-- > 0) {
            for(HashtableEntry<A, B> var4 = var2[var3]; var4 != null; var4 = var4.next) {
                if (var4.value.equals(var1)) {
                    return true;
                }
            }
        }

        return false;
    }

    public synchronized boolean containsKey(A var1) {
        HashtableEntry<A, B>[] var2 = this.table;
        int var3 = var1.hashCode();
        int var4 = (var3 & Integer.MAX_VALUE) % var2.length;

        for(HashtableEntry<A, B> var5 = var2[var4]; var5 != null; var5 = var5.next) {
            if (var5.hash == var3 && var5.key.equals(var1)) {
                return true;
            }
        }

        return false;
    }

    public synchronized B get(A var1) {
        HashtableEntry<A, B>[] var2 = this.table;
        int var3 = var1.hashCode();
        int var4 = (var3 & Integer.MAX_VALUE) % var2.length;

        for(HashtableEntry<A, B> var5 = var2[var4]; var5 != null; var5 = var5.next) {
            if (var5.hash == var3 && var5.key.equals(var1)) {
                return var5.value;
            }
        }

        return null;
    }

    protected void rehash() {
        int var1 = this.table.length;
        HashtableEntry<A, B>[] var2 = this.table;
        int var3 = var1 * 2 + 1;
        HashtableEntry<A, B>[] var4 = new HashtableEntry[var3];
        this.threshold = (int)((float)var3 * this.loadFactor);
        this.table = var4;
        int var5 = var1;

        HashtableEntry<A, B> var7;
        int var8;
        while(var5-- > 0) {
            for(HashtableEntry<A, B> var6 = var2[var5]; var6 != null; var4[var8] = var7) {
                var7 = var6;
                var6 = var6.next;
                var8 = (var7.hash & Integer.MAX_VALUE) % var3;
                var7.next = var4[var8];
            }
        }

    }

    public synchronized B put(A var1, B var2) {
        HashtableEntry<A, B>[] var3 = this.table;
        int var4 = var1.hashCode();
        int var5 = (var4 & Integer.MAX_VALUE) % var3.length;

        for(HashtableEntry<A, B> var6 = var3[var5]; var6 != null; var6 = var6.next) {
            if (var6.hash == var4 && var6.key.equals(var1)) {
                B var7 = var6.value;
                var6.value = var2;
                return var7;
            }
        }

        if (this.count >= this.threshold) {
            this.rehash();
            return this.put(var1, var2);
        } else {
            var3[var5] = new HashtableEntry<A, B>(var1, var2, var4, var3[var5]);
            ++this.count;
            return null;
        }
    }

    public synchronized B remove(A var1) {
        HashtableEntry<A, B>[] var2 = this.table;
        int var3 = var1.hashCode();
        int var4 = (var3 & Integer.MAX_VALUE) % var2.length;
        HashtableEntry<A, B> var5 = var2[var4];

        for(HashtableEntry<A, B> var6 = null; var5 != null; var5 = var5.next) {
            if (var5.hash == var3 && var5.key.equals(var1)) {
                if (var6 != null) {
                    var6.next = var5.next;
                } else {
                    var2[var4] = var5.next;
                }

                --this.count;
                return var5.value;
            }

            var6 = var5;
        }

        return null;
    }

    public synchronized void clear() {
        HashtableEntry<A, B>[] var1 = this.table;

        for(int var2 = var1.length; --var2 >= 0; var1[var2] = null) {
        }

        this.count = 0;
    }

    public synchronized Object clone() {
        Hashtable<A, B> var1 = new Hashtable<A, B>(this.table.length, this.loadFactor);

        for(int var2 = this.table.length; var2-- > 0; var1.table[var2] = this.table[var2] != null ? this.table[var2].copy() : null) {
        }

        return var1;
    }

    public synchronized String toString() {
        int var1 = this.size() - 1;
        StringBuffer var2 = new StringBuffer();
        Enumeration<A> var3 = this.keys();
        Enumeration<B> var4 = this.elements();
        var2.append("{");

        for(int var5 = 0; var5 <= var1; ++var5) {
            String var6 = var3.nextElement().toString();
            String var7 = var4.nextElement().toString();
            var2.append(String.valueOf(String.valueOf(var6).concat(String.valueOf("="))).concat(String.valueOf(var7)));
            if (var5 < var1) {
                var2.append(", ");
            }
        }

        var2.append("}");
        return var2.toString();
    }
}
