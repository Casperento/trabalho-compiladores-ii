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

import java.util.EmptyStackException;

public class Stack<A> extends Vector<A> {
    public Stack() {
    }

    public A push(A var1) {
        this.addElement(var1);
        return var1;
    }

    public A pop() {
        int var2 = this.size();
        A var1 = this.peek();
        this.removeElementAt(var2 - 1);
        return var1;
    }

    public A peek() {
        int var1 = this.size();
        if (var1 == 0) {
            throw new EmptyStackException();
        } else {
            return this.elementAt(var1 - 1);
        }
    }

    public boolean empty() {
        return this.size() == 0;
    }

    public int search(A var1) {
        int var2 = this.lastIndexOf(var1);
        return var2 >= 0 ? this.size() - var2 : -1;
    }
}
