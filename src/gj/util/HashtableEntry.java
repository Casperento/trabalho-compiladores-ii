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
