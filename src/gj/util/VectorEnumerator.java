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
