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
