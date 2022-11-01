// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Symbol;
import java.util.HashMap;

class Binder<V> {
    V value;
    Symbol prevtop;
    Binder<V> tail;
    Binder(V v, Symbol p, Binder<V> t) {
        value = v;
        prevtop = p;
        tail = t;
    }
}

/**
 * The Table class is similar to java.util.Dictionary, except that each key
 * must be a Symbol and there is a scope mechanism.
 */


public class Table<V> {
    private HashMap<Symbol, Binder<V>> dict = new HashMap<Symbol, Binder<V>>();
    private Symbol top;
    private Binder<V> marks;

    public Table() {}

    /**
     * Gets the object associated with the specified symbol in the Table.
     */
    public V get(Symbol key) {
        Binder<V> e = dict.get(key);
        if (e == null) return null;
        else return e.value;
    }

    /**
     * Puts the specified value into the Table, bound to the specified Symbol.
     */
    public void put(Symbol key, V value) {
        dict.put(key, new Binder<V>(value, top, dict.get(key)));
        top = key;
    }

    /**
     * Remembers the current state of the Table.
     */
    public void beginScope() {
        marks = new Binder<V>(null,top,marks);
        top=null;
    }

    /**
     * Restores the table to what it was at the most recent beginScope that
     * has not already been ended.
     */
    public void endScope() {
        while (top!=null) {
            Binder<V> e = dict.get(top);
            if (e.tail!=null) dict.put(top,e.tail);
            else dict.remove(top);
            top = e.prevtop;
        }
        top=marks.prevtop;
        marks=marks.tail;
    }

    /**
     * Returns an enumeration of the Table's symbols.
     */
    public java.util.Set keys() {
        return dict.keySet();
    }
}
