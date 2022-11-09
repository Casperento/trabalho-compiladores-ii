// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package visitor;
import gj.util.Hashtable;

class Class extends SymbolTableScope {
    private Hashtable<String, Method> methods;
    private String parent;

    public Class(String id, String parent) {
        super(id, id);
        this.parent = parent;
        methods = new Hashtable<String, Method>();
    }

    public boolean addMethod(String id, String type) {
        if(containsMethod(id)) {
            return false;
        } else {
            methods.put(id, new Method(id, type));
            return true;
        }
    }

    public Method getMethod(String id) {
        if(containsMethod(id)) {
            return methods.get(id);
        } else {
            return null;
        }
    }

    private boolean containsMethod(String id) {
        return methods.containsKey(id);
    }

    public String parent() {
        return parent;
    }
}
