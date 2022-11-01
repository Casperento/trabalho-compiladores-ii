// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Symbol;
import java.util.HashMap;
public class Symbol {
    private String name;
    private Symbol(String n) {
        name = n;
    }
    private static HashMap<String, Symbol> dict
        = new HashMap<String, Symbol>();

    public String toString() {
        return name;
    }


    /**
     * Gets the unique symbol associated with a string.  Repeated calls to
     * <code>symbol("abc")</code> will return the same symbol.
     */
    public static Symbol symbol(String n) {
        String u = n.intern();
        Symbol s = dict.get(u);
        if (s==null) {
            s = new Symbol(u);
            dict.put(u,s);
        }
        return s;
    }
}
