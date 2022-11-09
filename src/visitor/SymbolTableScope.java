// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package visitor;
import gj.util.Hashtable;

class SymbolTableScope extends SymbolTableEntry {
    private Hashtable<String, Variable> vars;

    public SymbolTableScope(String id, String type) {
        super(id, type);
        vars = new Hashtable<String, Variable>();
    }

    public boolean addVar(String id, String type) {
        if(containsVar(id)) {
            return false;
        } else {
            vars.put(id, new Variable(id, type));
            return true;
        }
    }

    public Variable getVar(String id) {
        if(containsVar(id)) {
            return vars.get(id);
        } else {
            return null;
        }
    }

    private boolean containsVar(String id) {
        return vars.containsKey(id);
    }
}
