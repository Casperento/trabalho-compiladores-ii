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
